package cy.volleybolley.core.presentation.ui.screens.authorization.authorization.di

import cy.volleybolley.auth.data.AuthNetworkClient
import cy.volleybolley.auth.data.AuthRepositoryImpl
import cy.volleybolley.auth.ui.GoogleSignInHelper
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authorizationModule = module {
    single { AuthNetworkClient() }
    single { AuthRepositoryImpl(get()) }
    single { GoogleSignInHelper(androidContext(), "20383666755-8u850oene64ckce5i194d1rag5st0v73.apps.googleusercontent.com") }
    viewModel {
        AuthorizationViewModel(get(), get())
    }
}
