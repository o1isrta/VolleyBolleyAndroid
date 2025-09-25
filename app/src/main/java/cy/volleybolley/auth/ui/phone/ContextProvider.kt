package cy.volleybolley.auth.ui.phone

import android.app.Activity

data class ContextProvider(
    val activity: Activity,
    val viewModel: PhoneAuthViewModel
)
