package cy.volleybolley.games.di

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.core.presentation.ui.screens.findatourney.ChooseTeamScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.findatourney.IndividualPlayersViewModel
import cy.volleybolley.core.presentation.ui.screens.findatourney.InvitePlayersViewModel
import cy.volleybolley.core.presentation.ui.screens.findatourney.JoinIndividualViewModel
import cy.volleybolley.core.presentation.ui.screens.findatourney.JoinTeamScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.viewmodel.ArchiveViewModel
import cy.volleybolley.core.presentation.ui.screens.games.archive.pastgamescreen.viewmodel.PastGameViewModel
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.viewmodel.PastTourneyViewModel
import cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen.viewmodel.TeamsScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.games.gameinvites.GameInvitesViewModel
import cy.volleybolley.core.presentation.ui.screens.games.gameinvites.JoinTheTourneyViewModel
import cy.volleybolley.core.presentation.ui.screens.games.mygames.changeteam.ChangeTeamViewModel
import cy.volleybolley.core.presentation.ui.screens.games.mygames.gamehome.GameHomeViewModel
import cy.volleybolley.core.presentation.ui.screens.games.mygames.manageplayers.ManagePlayersViewModel
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mygame.MyGameViewModel
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess.MyGamesViewModel
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney.MyTourneyViewModel
import cy.volleybolley.core.presentation.ui.screens.games.upcominggames.JoinedPlayersScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.games.upcominggames.UpcomingGameDetailsViewModel
import cy.volleybolley.core.presentation.ui.screens.games.upcominggames.UpcomingGamesViewModel
import cy.volleybolley.core.presentation.ui.screens.games.upcominggames.UpcomingTourneyDetailsViewModel
import cy.volleybolley.core.presentation.ui.screens.games.upcominggames.dataholder.TournamentDetailsDataHolder
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
import cy.volleybolley.games.domain.api.game.GetGameScreenPreviewUseCase
import cy.volleybolley.games.domain.api.game.GetGamesUseCase
import cy.volleybolley.games.domain.api.game.GetPlayersToRateUseCase
import cy.volleybolley.games.domain.api.game.InvitePlayersToGameUseCase
import cy.volleybolley.games.domain.api.game.JoinGameUseCase
import cy.volleybolley.games.domain.api.game.RatePlayersUseCase
import cy.volleybolley.games.domain.api.game.SkipRatingUseCase
import cy.volleybolley.games.domain.api.tournament.CancelTournamentUseCase
import cy.volleybolley.games.domain.api.tournament.CreateTournamentUseCase
import cy.volleybolley.games.domain.api.tournament.DeclineTournamentInviteUseCase
import cy.volleybolley.games.domain.api.tournament.GetTournamentDetailsUseCase
import cy.volleybolley.games.domain.api.tournament.InvitePlayersToTournamentUseCase
import cy.volleybolley.games.domain.api.tournament.JoinTournamentUseCase
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails
import cy.volleybolley.games.domain.usecases.game.CancelGameUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.CreateGameUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.DeclineGameInviteUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.GetGameDetailsUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.GetGameScreenPreviewUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.GetGamesUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.GetPlayersToRateUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.InvitePlayersToGameUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.JoinGameUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.RatePlayersUseCaseImpl
import cy.volleybolley.games.domain.usecases.game.SkipRatingUseCaseImpl
import cy.volleybolley.games.domain.usecases.tournament.CancelTournamentUseCaseImpl
import cy.volleybolley.games.domain.usecases.tournament.CreateTournamentUseCaseImpl
import cy.volleybolley.games.domain.usecases.tournament.DeclineTournamentInviteUseCaseImpl
import cy.volleybolley.games.domain.usecases.tournament.GetTournamentDetailsUseCaseImpl
import cy.volleybolley.games.domain.usecases.tournament.InvitePlayersToTournamentUseCaseImpl
import cy.volleybolley.games.domain.usecases.tournament.JoinTournamentUseCaseImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val gamesModule = module {
    // Data
    single<NetworkClient<GamesRequest, GamesResponse>>(HttpClientQualifier.GAMES.qualifier) {
        GamesNetworkClient()
    }
    single<NetworkClient<TournamentsRequest, TournamentsResponse>>(HttpClientQualifier.TOURNAMENTS.qualifier) {
        TournamentsNetworkClient()
    }

    single<GamesRepository> {
        GamesRepositoryImpl(networkClient = get(qualifier = HttpClientQualifier.GAMES.qualifier))
    }
    single<GameParticipationRepository> {
        GameParticipationRepositoryImpl(networkClient = get(qualifier = HttpClientQualifier.GAMES.qualifier))
    }
    single<GameFeedRepository> {
        GameFeedRepositoryImpl(networkClient = get(qualifier = HttpClientQualifier.GAMES.qualifier))
    }
    single<GameRatingRepository> {
        GameRatingRepositoryImpl(
            networkClient = get(qualifier = HttpClientQualifier.GAMES.qualifier),
            applicationScope = get()
        )
    }

    single<TournamentsRepository> {
        TournamentsRepositoryImpl(networkClient = get(qualifier = HttpClientQualifier.TOURNAMENTS.qualifier))
    }
    single<TournamentParticipationRepository> {
        TournamentParticipationRepositoryImpl(networkClient = get(qualifier = HttpClientQualifier.GAMES.qualifier))
    }
    single<TournamentRatingRepository> {
        TournamentRatingRepositoryImpl(
            networkClient = get(qualifier = HttpClientQualifier.GAMES.qualifier),
            applicationScope = get()
        )
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

    factory<GetPlayersToRateUseCase> {
        GetPlayersToRateUseCaseImpl(
            gameRepository = get(),
            tournamentRepository = get()
        )
    }
    factory<RatePlayersUseCase> {
        RatePlayersUseCaseImpl(
            gameRepository = get(),
            tournamentRepository = get()
        )
    }
    factory<SkipRatingUseCase> {
        SkipRatingUseCaseImpl(
            gameRepository = get(),
            tournamentRepository = get()
        )
    }

    factory<CreateTournamentUseCase> { CreateTournamentUseCaseImpl(repository = get()) }
    factory<GetTournamentDetailsUseCase> { GetTournamentDetailsUseCaseImpl(repository = get()) }
    factory<CancelTournamentUseCase> { CancelTournamentUseCaseImpl(repository = get()) }

    factory<JoinTournamentUseCase> { JoinTournamentUseCaseImpl(repository = get()) }
    factory<DeclineTournamentInviteUseCase> { DeclineTournamentInviteUseCaseImpl(repository = get()) }
    factory<InvitePlayersToTournamentUseCase> { InvitePlayersToTournamentUseCaseImpl(repository = get()) }

    // Класс для передачи TournamentDetails с экрана UpcomingTourneyDetails на экран JoinedPlayersScreen
    single<TournamentDetailsDataHolder> { TournamentDetailsDataHolder() }

    // ViewModels - Archive
    viewModel { ArchiveViewModel() }
    viewModel { PastGameViewModel() }
    viewModel { PastTourneyViewModel() }
    viewModel { TeamsScreenViewModel() }

    // ViewModels - MyGames
    viewModel { MyGamesViewModel() }
    viewModel { MyGameViewModel() }
    viewModel { MyTourneyViewModel() }
    viewModel { ManagePlayersViewModel() }
    viewModel { ChangeTeamViewModel() }
    viewModel { GameHomeViewModel() }

    // ViewModels - UpcomingGames
    viewModel { UpcomingGamesViewModel() }
    viewModel { UpcomingGameDetailsViewModel() }
    viewModel { (tournamentDetails: TournamentDetails?) ->
        UpcomingTourneyDetailsViewModel(tournamentDetails)
    }
    viewModel { (tournamentDetails: TournamentDetails) ->
        JoinedPlayersScreenViewModel(tournamentDetails)
    }

    // ViewModels - GameInvites
    viewModel { GameInvitesViewModel() }
    viewModel { JoinTheTourneyViewModel() }

    // ViewModels - FindATourney
    viewModel { ChooseTeamScreenViewModel() }
    viewModel { JoinTeamScreenViewModel() }
    viewModel { JoinIndividualViewModel() }
    viewModel { IndividualPlayersViewModel() }
    viewModel { InvitePlayersViewModel() }
}
