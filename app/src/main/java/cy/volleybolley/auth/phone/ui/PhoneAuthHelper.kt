package cy.volleybolley.auth.phone.ui

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import cy.volleybolley.auth.data.FirebaseResendCodeToken
import cy.volleybolley.auth.phone.domain.ResendCodeToken
import java.util.concurrent.TimeUnit

class PhoneAuthHelper(
    private val auth: FirebaseAuth,
) {

    fun sendCode(
        activity: Activity,
        phone: String,
        resendToken: ResendCodeToken?,
        onCodeSent: (String, ResendCodeToken) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val firebaseToken = (resendToken as? FirebaseResendCodeToken)?.token

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) = Unit

            override fun onVerificationFailed(e: FirebaseException) {
                onError(e)
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                onCodeSent(verificationId, FirebaseResendCodeToken(token))
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)
            .setTimeout(30L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)

        firebaseToken?.let {
            options.setForceResendingToken(it)
        }

        PhoneAuthProvider.verifyPhoneNumber(options.build())
    }

    fun verifyCode(
        verificationId: String,
        code: String,
        onSuccess: (String?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)

        auth.signInWithCredential(credential)
            .addOnSuccessListener { result ->
                result.user?.getIdToken(false)
                    ?.addOnSuccessListener {
                        onSuccess(it.token)
                    }
                    ?.addOnFailureListener(onError)
            }
            .addOnFailureListener(onError)
    }
}
