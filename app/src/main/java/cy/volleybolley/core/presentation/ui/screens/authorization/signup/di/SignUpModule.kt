package cy.volleybolley.core.presentation.ui.screens.authorization.signup.di

import cy.volleybolley.core.presentation.ui.screens.authorization.signup.SignUpViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val signUpModule = module {
    viewModel {
        SignUpViewModel()
    }
}
