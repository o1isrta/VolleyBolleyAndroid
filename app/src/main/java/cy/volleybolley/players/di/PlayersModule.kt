package cy.volleybolley.players.di

import android.content.Context
import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.players.data.network.AccessTokenProvider
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
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private class SharedPrefsAccessTokenProvider(
    private val context: Context
) : AccessTokenProvider {
    override suspend fun getAccessToken(): String? {
        val raw = context
            .getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
            .getString("access_token", null)
        return raw?.let { "Bearer $it" }
    }
}

val playersModule = module {
    single<AccessTokenProvider> { SharedPrefsAccessTokenProvider(androidContext()) }
    single<NetworkClient<PlayerRequest, PlayerResponse>> { PlayersNetworkClient() }
    single<PlayersRepository> { PlayersRepositoryImpl(get()) }

    factory<GetAllPlayersUseCase> { GetAllPlayersUseCase { get<PlayersRepository>().getAllPlayers() } }
    factory<SearchPlayersUseCase> { SearchPlayersUseCase { query -> get<PlayersRepository>().searchPlayers(query) } }
    factory<GetPlayerDetailUseCase> { GetPlayerDetailUseCase { id -> get<PlayersRepository>().getPlayerDetail(id) } }
    factory<AddToFavoritesUseCase> { AddToFavoritesUseCase { id -> get<PlayersRepository>().addToFavorites(id) } }
    factory<RemoveFromFavoritesUseCase> {
        RemoveFromFavoritesUseCase { id ->
            get<PlayersRepository>().removeFromFavorites(
                id
            )
        }
    }
}
