package cy.volleybolley.core.presentation.ui.screens.authorization.registration.di

import cy.volleybolley.core.presentation.ui.screens.authorization.registration.RegistrationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val registrationModule = module {
    viewModel {
        RegistrationViewModel()
    }
}
