package cy.volleybolley.authorization.di

import cy.volleybolley.authorization.data.AuthorizationRepositoryImpl
import cy.volleybolley.authorization.data.dto.AuthorizationRequest
import cy.volleybolley.authorization.data.dto.AuthorizationResponse
import cy.volleybolley.authorization.data.network.AuthorizationKtorNetworkClient
import cy.volleybolley.authorization.domain.AuthorizationUseCaseImpl
import cy.volleybolley.authorization.domain.api.AuthorizationRepository
import cy.volleybolley.authorization.domain.api.AuthorizationUseCase
import cy.volleybolley.authorization.presentation.AuthorizationViewModel
import cy.volleybolley.core.data.network.api.NetworkClient
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authorizationModule = module {
    single<NetworkClient<AuthorizationRequest, AuthorizationResponse>>(named("authorization")) {
        AuthorizationKtorNetworkClient()
    }
    single<AuthorizationRepository>(named("authorization")) {
        AuthorizationRepositoryImpl(get())
    }
    single<AuthorizationUseCase>(named("authorization")) {
        AuthorizationUseCaseImpl(get())
    }
    viewModel {
        AuthorizationViewModel(get())
    }
}
