package cy.volleybolley.auth.ui.presentation

import android.app.Activity
import com.google.firebase.auth.PhoneAuthProvider
import cy.volleybolley.auth.ui.PhoneAuthHelper

class PhoneAuthDelegate(private val phoneAuthHelper: PhoneAuthHelper) {
    fun startVerification(
        activity: Activity,
        phoneNumber: String,
        viewModel: PhoneAuthViewModel,
        onIdTokenReceived: (String?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        phoneAuthHelper.startPhoneNumberVerification(
            activity,
            phoneNumber,
            viewModel,
            onIdTokenReceived,
            onError
        )
    }

    fun resendCode(
        activity: Activity,
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken,
        viewModel: PhoneAuthViewModel,
        onIdTokenReceived: (String?) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        phoneAuthHelper.resendCode(
            activity,
            phoneNumber,
            token,
            viewModel,
            onIdTokenReceived,
            onError
        )
    }
}
