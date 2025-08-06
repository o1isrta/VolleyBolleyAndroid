package cy.volleybolley.games.di

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.games.data.GamesRepositoryImpl
import cy.volleybolley.games.data.network.GamesNetworkClient
import cy.volleybolley.games.data.network.GamesRequest
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.domain.CreateGameUseCaseImpl
import cy.volleybolley.games.domain.GetGameDetailsUseCaseImpl
import cy.volleybolley.games.domain.api.CreateGameUseCase
import cy.volleybolley.games.domain.api.GamesRepository
import cy.volleybolley.games.domain.api.GetGameDetailsUseCase
import org.koin.dsl.module

val gamesModule = module {
    // Data
    single<NetworkClient<GamesRequest, GamesResponse>>(HttpClientQualifier.GAMES.qualifier) {
        GamesNetworkClient()
    }
    single<GamesRepository>(HttpClientQualifier.GAMES.qualifier) {
        GamesRepositoryImpl(networkClient = get())
    }

    // Domain
    single<CreateGameUseCase> { CreateGameUseCaseImpl(repository = get()) }
    single<GetGameDetailsUseCase> { GetGameDetailsUseCaseImpl(repository = get()) }

    // ViewModel
}
