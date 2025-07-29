package cy.volleybolley.core.di

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.courts.data.network.CourtsNetworkClient
import cy.volleybolley.courts.data.network.CourtsRequest
import cy.volleybolley.courts.data.network.CourtsResponse
import cy.volleybolley.games.data.network.GamesNetworkClient
import cy.volleybolley.games.data.network.GamesRequest
import cy.volleybolley.games.data.network.GamesResponse
import org.koin.dsl.module

val dataModule = module {
    single<NetworkClient<CourtsRequest, CourtsResponse>>(CourtsClientQualifier) {
        CourtsNetworkClient()
    }

    single<NetworkClient<GamesRequest, GamesResponse>>(GamesClientQualifier) {
        GamesNetworkClient()
    }
}
