package cy.volleybolley.games.di

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.games.data.GameFeedRepositoryImpl
import cy.volleybolley.games.data.GameParticipationRepositoryImpl
import cy.volleybolley.games.data.GameRatingRepositoryImpl
import cy.volleybolley.games.data.GamesRepositoryImpl
import cy.volleybolley.games.data.TournamentParticipationRepositoryImpl
import cy.volleybolley.games.data.TournamentRatingRepositoryImpl
import cy.volleybolley.games.data.TournamentsRepositoryImpl
import cy.volleybolley.games.data.network.GamesNetworkClient
import cy.volleybolley.games.data.network.GamesRequest
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.data.network.TournamentsNetworkClient
import cy.volleybolley.games.data.network.TournamentsRequest
import cy.volleybolley.games.data.network.TournamentsResponse
import cy.volleybolley.games.domain.api.GameFeedRepository
import cy.volleybolley.games.domain.api.GameParticipationRepository
import cy.volleybolley.games.domain.api.GameRatingRepository
import cy.volleybolley.games.domain.api.GamesRepository
import cy.volleybolley.games.domain.api.TournamentParticipationRepository
import cy.volleybolley.games.domain.api.TournamentRatingRepository
import cy.volleybolley.games.domain.api.TournamentsRepository
import cy.volleybolley.games.domain.api.game.CancelGameUseCase
import cy.volleybolley.games.domain.api.game.CreateGameUseCase
import cy.volleybolley.games.domain.api.game.DeclineGameInviteUseCase
import cy.volleybolley.games.domain.api.game.GetGameDetailsUseCase
import cy.volleybolley.games.domain.api.game.GetGamePlayersToRateUseCase
import cy.volleybolley.games.domain.api.game.GetGameScreenPreviewUseCase
import cy.volleybolley.games.domain.api.game.GetGamesUseCase
import cy.volleybolley.games.domain.api.game.InvitePlayersToGameUseCase
import cy.volleybolley.games.domain.api.game.JoinGameUseCase
import cy.volleybolley.games.domain.api.game.RateGamePlayersUseCase
import cy.volleybolley.games.domain.api.game.SkipGameRatingUseCase
import cy.volleybolley.games.domain.api.tournament.CancelTournamentUseCase
import cy.volleybolley.games.domain.api.tournament.CreateTournamentUseCase
import cy.volleybolley.games.domain.api.tournament.DeclineTournamentInviteUseCase
import cy.volleybolley.games.domain.api.tournament.GetTournamentDetailsUseCase
import cy.volleybolley.games.domain.api.tournament.GetTournamentPlayersToRateUseCase
import cy.volleybolley.games.domain.api.tournament.InvitePlayersToTournamentUseCase
import cy.volleybolley.games.domain.api.tournament.JoinTournamentUseCase
import cy.volleybolley.games.domain.api.tournament.RateTournamentPlayersUseCase
import cy.volleybolley.games.domain.api.tournament.SkipTournamentRatingUseCase
import cy.volleybolley.games.domain.usecases.game.CancelGameUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.CreateGameUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.DeclineGameInviteUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.GetGameDetailsUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.GetGamePlayersToRateUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.GetGameScreenPreviewUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.GetGamesUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.InvitePlayersToGameUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.JoinGameUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.RateGamePlayersUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.SkipGameRatingUseCaseImpl
import cy.volleybolley.games.domain.usecases.tournament.CancelTournamentUseCaseImpl
import cy.volleybolley.games.domain.usecases.tournament.CreateTournamentUseCaseImpl
import cy.volleybolley.games.domain.usecases.tournament.DeclineTournamentInviteUseCaseImpl
import cy.volleybolley.games.domain.usecases.tournament.GetTournamentDetailsUseCaseImpl
import cy.volleybolley.games.domain.usecases.tournament.GetTournamentPlayersToRateUseCaseImpl
import cy.volleybolley.games.domain.usecases.tournament.InvitePlayersToTournamentUseCaseImpl
import cy.volleybolley.games.domain.usecases.tournament.JoinTournamentUseCaseImpl
import cy.volleybolley.games.domain.usecases.tournament.RateTournamentPlayersUseCaseImpl
import cy.volleybolley.games.domain.usecases.tournament.SkipTournamentRatingUseCaseImpl
import org.koin.dsl.module

val gamesModule = module {
    // Data
    single<NetworkClient<GamesRequest, GamesResponse>>(HttpClientQualifier.GAMES.qualifier) {
        GamesNetworkClient()
    }
    single<NetworkClient<TournamentsRequest, TournamentsResponse>>(HttpClientQualifier.TOURNAMENTS.qualifier) {
        TournamentsNetworkClient()
    }

    single<GamesRepository>(HttpClientQualifier.GAMES.qualifier) {
        GamesRepositoryImpl(networkClient = get())
    }
    single<GameParticipationRepository>(HttpClientQualifier.GAMES.qualifier) {
        GameParticipationRepositoryImpl(networkClient = get())
    }
    single<GameFeedRepository>(HttpClientQualifier.GAMES.qualifier) {
        GameFeedRepositoryImpl(networkClient = get())
    }
    single<GameRatingRepository>(HttpClientQualifier.GAMES.qualifier) {
        GameRatingRepositoryImpl(networkClient = get())
    }

    single<TournamentsRepository>(HttpClientQualifier.TOURNAMENTS.qualifier) {
        TournamentsRepositoryImpl(networkClient = get())
    }
    single<TournamentParticipationRepository>(HttpClientQualifier.GAMES.qualifier) {
        TournamentParticipationRepositoryImpl(networkClient = get())
    }
    single<TournamentRatingRepository>(HttpClientQualifier.GAMES.qualifier) {
        TournamentRatingRepositoryImpl(networkClient = get())
    }

    // Domain
    factory<CreateGameUseCase> { CreateGameUseCaseImpl(repository = get()) }
    factory<GetGameDetailsUseCase> { GetGameDetailsUseCaseImpl(repository = get()) }
    factory<CancelGameUseCase> { CancelGameUseCaseImpl(repository = get()) }

    factory<GetGameScreenPreviewUseCase> { GetGameScreenPreviewUseCaseImpl(repository = get()) }
    factory<GetGamesUseCase> { GetGamesUseCaseImpl(repository = get()) }

    factory<JoinGameUseCase> { JoinGameUseCaseImpl(repository = get()) }
    factory<DeclineGameInviteUseCase> { DeclineGameInviteUseCaseImpl(repository = get()) }
    factory<InvitePlayersToGameUseCase> { InvitePlayersToGameUseCaseImpl(repository = get()) }

    factory<GetGamePlayersToRateUseCase> { GetGamePlayersToRateUseCaseImpl(repository = get()) }
    factory<RateGamePlayersUseCase> { RateGamePlayersUseCaseImpl(repository = get()) }
    factory<SkipGameRatingUseCase> { SkipGameRatingUseCaseImpl(repository = get()) }

    factory<CreateTournamentUseCase> { CreateTournamentUseCaseImpl(repository = get()) }
    factory<GetTournamentDetailsUseCase> { GetTournamentDetailsUseCaseImpl(repository = get()) }
    factory<CancelTournamentUseCase> { CancelTournamentUseCaseImpl(repository = get()) }

    factory<JoinTournamentUseCase> { JoinTournamentUseCaseImpl(repository = get()) }
    factory<DeclineTournamentInviteUseCase> { DeclineTournamentInviteUseCaseImpl(repository = get()) }
    factory<InvitePlayersToTournamentUseCase> { InvitePlayersToTournamentUseCaseImpl(repository = get()) }

    factory<GetTournamentPlayersToRateUseCase> { GetTournamentPlayersToRateUseCaseImpl(repository = get()) }
    factory<RateTournamentPlayersUseCase> { RateTournamentPlayersUseCaseImpl(repository = get()) }
    factory<SkipTournamentRatingUseCase> { SkipTournamentRatingUseCaseImpl(repository = get()) }

    // ViewModel
}
