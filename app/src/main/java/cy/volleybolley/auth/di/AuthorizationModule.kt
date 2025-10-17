package cy.volleybolley.auth.di

import cy.volleybolley.BuildConfig
import cy.volleybolley.auth.data.network.AuthNetworkClient
import cy.volleybolley.auth.data.AuthRepositoryImpl
import cy.volleybolley.auth.data.TokensRepositoryImpl
import cy.volleybolley.auth.data.network.model.AuthRequest
import cy.volleybolley.auth.domain.AuthInteractor
import cy.volleybolley.auth.domain.AuthRepository
import cy.volleybolley.auth.domain.TokensInteractor
import cy.volleybolley.auth.domain.TokensRepository
import cy.volleybolley.auth.domain.impl.AuthInteractorImpl
import cy.volleybolley.auth.domain.impl.TokensInteractorImpl
import cy.volleybolley.auth.data.network.model.AuthResponse
import cy.volleybolley.auth.ui.GoogleSignInHelper
import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authorizationModule = module {
    factory<TokensRepository> { TokensRepositoryImpl(get()) }
    factory<TokensInteractor> { TokensInteractorImpl(get()) }

    single<NetworkClient<AuthRequest, AuthResponse>>(HttpClientQualifier.AUTH.qualifier) {
        AuthNetworkClient()
    }

    single<AuthRepository> { AuthRepositoryImpl(get(named(HttpClientQualifier.AUTH.value))) }
    single<AuthInteractor> { AuthInteractorImpl(get()) }

    single {
        GoogleSignInHelper(
            androidContext(),
            BuildConfig.WEB_CLIENT_ID
        )
    }

    viewModel {
        AuthorizationViewModel(get(), get(), get())
    }
}
