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
import cy.volleybolley.players.domain.usecase.GetFavoritePlayersUseCase
import cy.volleybolley.players.domain.usecase.GetPlayerDetailUseCase
import cy.volleybolley.players.domain.usecase.GetPlayersInteractor
import cy.volleybolley.players.domain.usecase.RemoveFromFavoritesUseCase
import cy.volleybolley.players.domain.usecase.impl.AddToFavoritesUseCaseImpl
import cy.volleybolley.players.domain.usecase.impl.GetAllPlayersUseCaseImpl
import cy.volleybolley.players.domain.usecase.impl.GetFavoritePlayersUseCaseImpl
import cy.volleybolley.players.domain.usecase.impl.GetPlayerDetailUseCaseImpl
import cy.volleybolley.players.domain.usecase.impl.GetPlayersInteractorImpl
import cy.volleybolley.players.domain.usecase.impl.RemoveFromFavoritesUseCaseImpl
import org.koin.dsl.module

val playersModule = module {
    single<NetworkClient<PlayerRequest, PlayerResponse>>(qualifier = HttpClientQualifier.PLAYERS.qualifier) {
        PlayersNetworkClient()
    }

    // Что-то из моковых штук, пока оставлю
//    single<PlayersRepository> {
//        if (BuildConfig.DEBUG) {
//            MockPlayersRepositoryImpl() // Использовать Mock в debug сборке
//        } else {
//            PlayersRepositoryImpl(
//                networkClient = get(qualifier = HttpClientQualifier.PLAYERS.qualifier),
//                // tokenProvider = { null } // заменить на реальную реализацию при появлении
//            )
//        }
//    }

    single<PlayersRepository> {
        PlayersRepositoryImpl(networkClient = get(HttpClientQualifier.PLAYERS.qualifier))
    }

    single<GetAllPlayersUseCase> { GetAllPlayersUseCaseImpl(get()) }
    single<GetFavoritePlayersUseCase> { GetFavoritePlayersUseCaseImpl(get()) }
    single<GetPlayerDetailUseCase> { GetPlayerDetailUseCaseImpl(get()) }
    single<AddToFavoritesUseCase> { AddToFavoritesUseCaseImpl(get()) }
    single<RemoveFromFavoritesUseCase> { RemoveFromFavoritesUseCaseImpl(get()) }

    single<GetPlayersInteractor> {
        GetPlayersInteractorImpl(
            getAllPlayersUseCase = get(),
            getFavoritePlayersUseCase = get(),
            repository = get()
        )
    }
}
