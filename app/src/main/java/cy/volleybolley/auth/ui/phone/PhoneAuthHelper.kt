package cy.volleybolley.auth.ui.phone

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneAuthHelper(
    private val auth: FirebaseAuth,
) {
    companion object {
        private const val TIMEOUT = 30L
    }

    fun startPhoneNumberVerification(
        contextProvider: ContextProvider,
        phoneNumber: String,
        onIdTokenReceived: (String?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val activity = contextProvider.activity
        val viewModel = contextProvider.viewModel
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(TIMEOUT, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithCredential(credential, onIdTokenReceived, onError)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    onError(e)
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    viewModel.onCodeSent(verificationId, token)
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun resendCode(
        contextProvider: ContextProvider,
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken,
        onIdTokenReceived: (String?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val activity = contextProvider.activity
        val viewModel = contextProvider.viewModel
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(TIMEOUT, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithCredential(credential, onIdTokenReceived, onError)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    onError(e)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    viewModel.onCodeSent(verificationId, token)
                }
            })
            .setForceResendingToken(token)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyCode(
        code: String,
        verificationId: String,
        onIdTokenReceived: (String?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithCredential(credential, onIdTokenReceived, onError)
    }

    private fun signInWithCredential(
        credential: PhoneAuthCredential,
        onIdTokenReceived: (String?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        auth.signInWithCredential(credential)
            .addOnSuccessListener { result ->
                result.user?.getIdToken(false)
                    ?.addOnSuccessListener { tokenResult ->
                        onIdTokenReceived(tokenResult.token)
                    }
                    ?.addOnFailureListener(onError)
            }
            .addOnFailureListener(onError)
    }
}
