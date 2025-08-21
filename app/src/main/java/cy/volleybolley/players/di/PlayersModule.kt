package cy.volleybolley.players.di

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.players.data.network.PlayerRequest
import cy.volleybolley.players.data.network.PlayerResponse
import cy.volleybolley.players.data.network.PlayersNetworkClient
import cy.volleybolley.players.data.repository.PlayersRepositoryImpl
import cy.volleybolley.players.domain.repository.PlayersRepository
import cy.volleybolley.players.domain.usecase.AddToFavoritesUseCase
import cy.volleybolley.players.domain.usecase.GetAllPlayersUseCase
import cy.volleybolley.players.domain.usecase.GetPlayerDetailUseCase
import cy.volleybolley.players.domain.usecase.RemoveFromFavoritesUseCase
import cy.volleybolley.players.domain.usecase.SearchPlayersUseCase
import io.ktor.client.HttpClient
import org.koin.dsl.module

val playersModule = module {
    single<NetworkClient<PlayerRequest, PlayerResponse>> {
        val httpClient: HttpClient = get(HttpClientQualifier.COURTS.qualifier)
        PlayersNetworkClient(lazy { httpClient })
    }
    single<PlayersRepository> { PlayersRepositoryImpl(get()) }

    single<GetAllPlayersUseCase> { GetAllPlayersUseCase { get<PlayersRepository>().getAllPlayers() } }
    single<SearchPlayersUseCase> { SearchPlayersUseCase { name -> get<PlayersRepository>().searchPlayers(name) } }
    single<GetPlayerDetailUseCase> { GetPlayerDetailUseCase { id -> get<PlayersRepository>().getPlayerDetail(id) } }
    single<AddToFavoritesUseCase> { AddToFavoritesUseCase { id -> get<PlayersRepository>().addToFavorites(id) } }
    single<RemoveFromFavoritesUseCase> {
        RemoveFromFavoritesUseCase { id ->
            get<PlayersRepository>().removeFromFavorites(
                id
            )
        }
    }
}
