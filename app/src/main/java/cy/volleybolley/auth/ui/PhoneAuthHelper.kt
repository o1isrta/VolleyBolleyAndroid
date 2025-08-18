package cy.volleybolley.auth.ui

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import cy.volleybolley.auth.ui.presentation.PhoneAuthViewModel
import java.util.concurrent.TimeUnit

class PhoneAuthHelper(
    private val activity: Activity,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    fun startPhoneNumberVerification(
        phoneNumber: String,
        viewModel: PhoneAuthViewModel,
        onIdTokenReceived: (String?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(30L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithCredential(credential, onIdTokenReceived, onError)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    onError(e)
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(verificationId, token)// тут поменять на вызов метода во вьюмодели
                }
            })
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
