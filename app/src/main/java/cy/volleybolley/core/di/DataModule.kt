package cy.volleybolley.core.di

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.courts.data.network.CourtsNetworkClient
import cy.volleybolley.courts.data.network.CourtsRequest
import cy.volleybolley.courts.data.network.CourtsResponse
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<NetworkClient<CourtsRequest, CourtsResponse>>(named("courts")) {
        CourtsNetworkClient(
            baseUrl = "http://158.160.175.32/api/"
        )
    }
}