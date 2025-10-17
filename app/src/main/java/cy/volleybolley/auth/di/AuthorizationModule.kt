package cy.volleybolley.auth.di

import cy.volleybolley.auth.data.AuthRepositoryImpl
import cy.volleybolley.auth.data.TokensRepositoryImpl
import cy.volleybolley.auth.data.network.AuthNetworkClient
import cy.volleybolley.auth.data.network.model.AuthRequest
import cy.volleybolley.auth.data.network.model.AuthResponse
import cy.volleybolley.auth.domain.AuthRepository
import cy.volleybolley.auth.domain.AuthUseCase
import cy.volleybolley.auth.domain.TokensInteractor
import cy.volleybolley.auth.domain.TokensRepository
import cy.volleybolley.auth.domain.impl.AuthUseCaseImpl
import cy.volleybolley.auth.domain.impl.TokensInteractorImpl
import cy.volleybolley.auth.ui.GoogleSignInHelper
import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authorizationModule = module {
    single<TokensRepository> { TokensRepositoryImpl(get()) }
    single<TokensInteractor> { TokensInteractorImpl(get()) }

    single<NetworkClient<AuthRequest, AuthResponse>>(HttpClientQualifier.AUTH.qualifier) {
        AuthNetworkClient()
    }

    single<AuthRepository> { AuthRepositoryImpl(get(named(HttpClientQualifier.AUTH.value))) }
    single<AuthUseCase> { AuthUseCaseImpl(get()) }

    single {
        GoogleSignInHelper(
            get()
        )
    }

    viewModel {
        AuthorizationViewModel(get(), get())
    }
}
