package cy.volleybolley.core.presentation.ui.screens.authorization.authorization.di

import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authorizationModule = module {
    viewModel {
        AuthorizationViewModel()
    }
}
