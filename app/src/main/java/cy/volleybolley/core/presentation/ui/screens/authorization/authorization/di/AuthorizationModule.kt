package cy.volleybolley.core.presentation.ui.screens.authorization.authorization.di

import android.content.Context
import android.content.SharedPreferences
import cy.volleybolley.auth.data.AuthNetworkClient
import cy.volleybolley.auth.data.AuthRepositoryImpl
import cy.volleybolley.auth.data.TokensRepositoryImpl
import cy.volleybolley.auth.domain.AuthInteractor
import cy.volleybolley.auth.domain.AuthRepository
import cy.volleybolley.auth.domain.TokensInteractor
import cy.volleybolley.auth.domain.TokensRepository
import cy.volleybolley.auth.domain.impl.AuthInteractorImpl
import cy.volleybolley.auth.domain.impl.TokensInteractorImpl
import cy.volleybolley.auth.ui.GoogleSignInHelper
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authorizationModule = module {
    factory<SharedPreferences> {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    factory<TokensRepository> { TokensRepositoryImpl(get()) }
    factory<TokensInteractor> { TokensInteractorImpl(get()) }

    single { AuthNetworkClient() }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<AuthInteractor> { AuthInteractorImpl(get()) }

    single {
        GoogleSignInHelper(
            androidContext(),
            "20383666755-8u850oene64ckce5i194d1rag5st0v73.apps.googleusercontent.com"
        )
    }

    viewModel {
        AuthorizationViewModel(get(), get(), get())
    }
}
