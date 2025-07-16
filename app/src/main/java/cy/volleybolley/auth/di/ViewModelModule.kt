package cy.volleybolley.auth.di

import cy.volleybolley.auth.ui.presentation.AuthViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authViewModelModule = module {
    viewModel {
        AuthViewModel()
    }
}