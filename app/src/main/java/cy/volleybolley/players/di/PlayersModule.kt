package cy.volleybolley.players.di

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.players.data.network.PlayerRequest
import cy.volleybolley.players.data.network.PlayerResponse
import cy.volleybolley.players.data.network.PlayersNetworkClient
import cy.volleybolley.players.data.repository.PlayersRepositoryImpl
import cy.volleybolley.players.domain.repository.PlayersRepository
import cy.volleybolley.players.domain.usecase.*
import cy.volleybolley.players.domain.usecase.impl.*
import io.ktor.client.HttpClient
import org.koin.dsl.module

val playersModule = module {
    single<NetworkClient<PlayerRequest, PlayerResponse>> {
        val httpClient: HttpClient = get(HttpClientQualifier.COURTS.qualifier)
        PlayersNetworkClient(lazy { httpClient })
    }

    single<PlayersRepository> { PlayersRepositoryImpl(get()) }

    single<GetAllPlayersUseCase> { GetAllPlayersUseCaseImpl(get()) }
    single<SearchPlayersUseCase> { SearchPlayersUseCaseImpl(get()) }
    single<GetPlayerDetailUseCase> { GetPlayerDetailUseCaseImpl(get()) }
    single<AddToFavoritesUseCase> { AddToFavoritesUseCaseImpl(get()) }
    single<RemoveFromFavoritesUseCase> { RemoveFromFavoritesUseCaseImpl(get()) }
}
