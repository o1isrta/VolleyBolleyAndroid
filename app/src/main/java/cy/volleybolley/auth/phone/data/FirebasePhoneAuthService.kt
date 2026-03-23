package cy.volleybolley.auth.phone.data

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import cy.volleybolley.auth.data.FirebaseResendCodeToken
import cy.volleybolley.auth.phone.domain.CodeSentResult
import cy.volleybolley.auth.phone.domain.PhoneAuthError
import cy.volleybolley.auth.phone.domain.PhoneAuthService
import cy.volleybolley.auth.phone.domain.ResendCodeToken
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.util.VolleyLog
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import com.google.firebase.auth.PhoneAuthProvider as FirebasePhoneAuthProvider

class FirebasePhoneAuthService(
    private val auth: FirebaseAuth,
    private val activityProvider: CurrentActivityProvider
) : PhoneAuthService {

    @Suppress("LabeledExpression") // Нужен suspendCancellableCoroutine для интеграции в коррутину callback
    override suspend fun sendCode(
        phoneNumber: String,
        resendToken: ResendCodeToken?
    ): VolleyResult<CodeSentResult, PhoneAuthError> = suspendCancellableCoroutine { continuation ->
        VolleyLog.d(TAG, "sendCode() called with phoneNumber=$phoneNumber, resendToken=$resendToken")

        val activity = activityProvider.currentActivity
        if (activity == null) {
            VolleyLog.e(TAG, "sendCode() FAILED: currentActivity is null")
            continuation.resume(VolleyResult.Failure(PhoneAuthError.UNKNOWN))
            return@suspendCancellableCoroutine
        }

        VolleyLog.d(TAG, "sendCode() activity=${activity::class.simpleName}")

        val firebaseToken = (resendToken as? FirebaseResendCodeToken)?.token
        VolleyLog.d(TAG, "sendCode() firebaseToken=${if (firebaseToken != null) "present" else "null"}")

        val callbacks = object : FirebasePhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                VolleyLog.i(TAG, "sendCode() -> onVerificationCompleted() called - auto-verification")
                // Auto-verification - we still need to get the token
                // This will be handled separately if needed
            }

            override fun onVerificationFailed(e: FirebaseException) {
                VolleyLog.e(TAG, "sendCode() -> onVerificationFailed()", e)
                val error = mapFirebaseException(e)
                VolleyLog.e(TAG, "sendCode() -> onVerificationFailed() mapped to error=$error")
                continuation.resume(VolleyResult.Failure(error))
            }

            override fun onCodeSent(
                verificationId: String,
                token: FirebasePhoneAuthProvider.ForceResendingToken
            ) {
                VolleyLog.i(TAG, "sendCode() -> onCodeSent() verificationId=$verificationId")
                val result = CodeSentResult(
                    verificationId = verificationId,
                    resendToken = FirebaseResendCodeToken(token)
                )
                VolleyLog.d(TAG, "sendCode() -> onCodeSent() returning Success")
                continuation.resume(VolleyResult.Success(result))
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(PHONE_AUTH_TIMEOUT, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)

        firebaseToken?.let {
            VolleyLog.d(TAG, "sendCode() setting forceResendingToken")
            options.setForceResendingToken(it)
        }

        VolleyLog.d(TAG, "sendCode() calling PhoneAuthProvider.verifyPhoneNumber()")
        FirebasePhoneAuthProvider.verifyPhoneNumber(options.build())
    }

    override suspend fun verifyCode(
        verificationId: String,
        code: String
    ): VolleyResult<String, PhoneAuthError> = suspendCancellableCoroutine { continuation ->
        VolleyLog.d(TAG, "verifyCode() called with verificationId=$verificationId, code=${code.take(2)}***")

        val credential = FirebasePhoneAuthProvider.getCredential(verificationId, code)
        VolleyLog.d(TAG, "verifyCode() credential created, calling signInWithCredential")

        auth.signInWithCredential(credential)
            .addOnSuccessListener { result ->
                VolleyLog.i(TAG, "verifyCode() -> signInWithCredential success, user=${result.user?.uid}")
                result.user?.getIdToken(false)
                    ?.addOnSuccessListener { tokenResult ->
                        val token = tokenResult.token
                        if (token != null) {
                            VolleyLog.d(TAG, "verifyCode() -> getIdToken success, token length=${token.length}")
                            continuation.resume(VolleyResult.Success(token))
                        } else {
                            VolleyLog.e(TAG, "verifyCode() -> getIdToken returned null token")
                            continuation.resume(VolleyResult.Failure(PhoneAuthError.UNKNOWN))
                        }
                    }
                    ?.addOnFailureListener {
                        VolleyLog.e(TAG, "verifyCode() -> getIdToken FAILED", it)
                        continuation.resume(VolleyResult.Failure(PhoneAuthError.UNKNOWN))
                    } ?: run {
                    VolleyLog.e(TAG, "verifyCode() -> getIdToken returned null task")
                    continuation.resume(VolleyResult.Failure(PhoneAuthError.UNKNOWN))
                }
            }
            .addOnFailureListener {
                VolleyLog.e(TAG, "verifyCode() -> signInWithCredential FAILED", it)
                val error = mapSignInException(it)
                VolleyLog.e(TAG, "verifyCode() -> signInWithCredential mapped to error=$error")
                continuation.resume(VolleyResult.Failure(error))
            }
    }

    private fun mapFirebaseException(e: FirebaseException): PhoneAuthError {
        VolleyLog.d(TAG, "mapFirebaseException() exception type=${e::class.simpleName}, message=${e.message}")
        return when (e) {
            is FirebaseAuthException -> {
                val result = when {
                    e.message?.contains("phone", ignoreCase = true) == true ||
                        e.message?.contains("format", ignoreCase = true) == true -> {
                        VolleyLog.d(TAG, "mapFirebaseException() -> INVALID_PHONE_NUMBER")
                        PhoneAuthError.INVALID_PHONE_NUMBER
                    }
                    e.message?.contains("quota", ignoreCase = true) == true -> {
                        VolleyLog.d(TAG, "mapFirebaseException() -> SMS_QUOTA_EXCEEDED")
                        PhoneAuthError.SMS_QUOTA_EXCEEDED
                    }
                    else -> {
                        VolleyLog.d(TAG, "mapFirebaseException() -> INVALID_VERIFICATION_CODE (fallback)")
                        PhoneAuthError.INVALID_VERIFICATION_CODE
                    }
                }
                result
            }
            else -> {
                VolleyLog.d(TAG, "mapFirebaseException() -> UNKNOWN (not FirebaseAuthException)")
                PhoneAuthError.UNKNOWN
            }
        }
    }

    private fun mapSignInException(e: Exception): PhoneAuthError {
        VolleyLog.d(TAG, "mapSignInException() exception type=${e::class.simpleName}, message=${e.message}")
        return when (e) {
            is FirebaseAuthException -> {
                when {
                    e.message?.contains("invalid", ignoreCase = true) == true -> {
                        VolleyLog.d(TAG, "mapSignInException() -> INVALID_VERIFICATION_CODE")
                        PhoneAuthError.INVALID_VERIFICATION_CODE
                    }
                    e.message?.contains("expired", ignoreCase = true) == true -> {
                        VolleyLog.d(TAG, "mapSignInException() -> CODE_EXPIRED")
                        PhoneAuthError.CODE_EXPIRED
                    }
                    else -> {
                        VolleyLog.d(TAG, "mapSignInException() -> UNKNOWN (fallback)")
                        PhoneAuthError.UNKNOWN
                    }
                }
            }
            else -> {
                VolleyLog.d(TAG, "mapSignInException() -> UNKNOWN (not FirebaseAuthException)")
                PhoneAuthError.UNKNOWN
            }
        }
    }

    private companion object {
        const val TAG = "FirebasePhoneAuthService"
        const val PHONE_AUTH_TIMEOUT = 30L
    }
}
