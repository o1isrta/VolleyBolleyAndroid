package cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.di

import cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.sendCode.presentation.RegistrationByPhoneViewModel
import cy.volleybolley.core.presentation.ui.screens.authorization.signupByPhone.verifyCode.presentation.VerifyPhoneNumberViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val signupByPhoneModule = module {
    viewModel {
        RegistrationByPhoneViewModel()
    }

    viewModel {
        VerifyPhoneNumberViewModel()
    }
}
