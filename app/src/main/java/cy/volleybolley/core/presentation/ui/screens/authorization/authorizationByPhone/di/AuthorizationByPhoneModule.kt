package cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.di

import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.sendCode.presentation.AuthorizationByPhoneViewModel
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.verifyCode.presentation.VerifyPhoneNumberViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authorizationByPhoneModule = module {
    viewModel {
        AuthorizationByPhoneViewModel()
    }

    viewModel {
        VerifyPhoneNumberViewModel()
    }
}
