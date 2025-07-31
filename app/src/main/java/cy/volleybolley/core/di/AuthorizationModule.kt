package cy.volleybolley.core.di

import cy.volleybolley.authorization.data.dto.AuthorizationRequest
import cy.volleybolley.authorization.data.dto.AuthorizationResponse
import cy.volleybolley.core.data.network.api.NetworkClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import cy.volleybolley.authorization.data.network.AuthorizationKtorNetworkClient

val authorizationModule = module {
    single<NetworkClient<AuthorizationRequest, AuthorizationResponse>>(named("authorization")) {
        AuthorizationKtorNetworkClient()
    }
}
