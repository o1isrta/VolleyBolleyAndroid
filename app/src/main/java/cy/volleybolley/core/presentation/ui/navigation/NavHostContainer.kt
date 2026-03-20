package cy.volleybolley.core.presentation.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import cy.volleybolley.auth.chooseMethod.AuthorizationScreen
import cy.volleybolley.auth.phone.ui.AuthorizationByPhoneScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.launch.LaunchScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.onboarding.OnboardingScreen
import cy.volleybolley.core.presentation.ui.screens.createNewGame.basicGameSetupScreen.BasicGameSetupScreen
import cy.volleybolley.core.presentation.ui.screens.createNewGame.gameConditions.GameConditionsScreen
import cy.volleybolley.core.presentation.ui.screens.createNewGame.privacyOptionsScreen.PrivacyOptionsScreen
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.BasicTourneySetupScreen
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.TourneyEnteringConditionsScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.ChooseTeamScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.IndividualPlayersScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.InvitePlayersScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.JoinIndividualScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.JoinTeamScreen
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.ArchiveScreen
import cy.volleybolley.core.presentation.ui.screens.games.archive.pastgamescreen.PastGameScreen
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.PastTourneyScreen
import cy.volleybolley.core.presentation.ui.screens.games.archive.teamsscreen.TeamsScreen
import cy.volleybolley.core.presentation.ui.screens.games.gameinvites.GameInvitesScreen
import cy.volleybolley.core.presentation.ui.screens.games.gameinvites.JoinTheTourneyScreen
import cy.volleybolley.core.presentation.ui.screens.games.mygames.changeteam.ChangeTeamScreen
import cy.volleybolley.core.presentation.ui.screens.games.mygames.gamehome.GameHomeScreen
import cy.volleybolley.core.presentation.ui.screens.games.mygames.manageplayers.ManagePlayersScreen
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mygame.MyGameScreen
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess.MyGamesScreen
import cy.volleybolley.core.presentation.ui.screens.games.mygames.mytourney.MyTourneyScreen
import cy.volleybolley.core.presentation.ui.screens.games.upcominggames.JoinedPlayersScreen
import cy.volleybolley.core.presentation.ui.screens.games.upcominggames.UpcomingGameDetailsScreen
import cy.volleybolley.core.presentation.ui.screens.games.upcominggames.UpcomingGamesScreen
import cy.volleybolley.core.presentation.ui.screens.games.upcominggames.UpcomingTourneyDetailsScreen
import cy.volleybolley.core.presentation.ui.screens.games.upcominggames.dataholder.TournamentDetailsDataHolder
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreen
import cy.volleybolley.courts.presentation.SearchCourtScreen
import cy.volleybolley.games.domain.model.event.EventType
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails
import cy.volleybolley.jointhegame.JoinTheGameScreen
import cy.volleybolley.jointhegame.JoinTheGameViewModel
import cy.volleybolley.notification.presentation.NotificationsScreen
import cy.volleybolley.profile.presentation.ui.screens.about.AboutScreen
import cy.volleybolley.profile.presentation.ui.screens.changephoto.ChangePhotoScreen
import cy.volleybolley.profile.presentation.ui.screens.enterpaymentdata.EnterPaymentDataScreen
import cy.volleybolley.profile.presentation.ui.screens.enterpaymentdata.EnterPaymentDataScreenViewModel
import cy.volleybolley.profile.presentation.ui.screens.faq.FaqScreen
import cy.volleybolley.profile.presentation.ui.screens.payments.PaymentsScreen
import cy.volleybolley.profile.presentation.ui.screens.payments.PaymentsScreenViewModel
import cy.volleybolley.profile.presentation.ui.screens.payments.model.BackPaymentsHolder
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreen
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenViewModel
import cy.volleybolley.profile.presentation.ui.screens.personaldata.model.BackAvatarHolder
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.PlayerProfileScreen
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.PlayerProfileScreenViewModel
import cy.volleybolley.profile.presentation.ui.screens.players.PlayersScreen
import cy.volleybolley.profile.presentation.ui.screens.players.PlayersScreenViewModel
import cy.volleybolley.profile.presentation.ui.screens.players.model.BackPlayerIdHolder
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreen
import cy.volleybolley.rateplayers.RatePlayersScreen
import cy.volleybolley.registration.presentation.ui.screens.aboutlevels.AboutLevelsScreen
import cy.volleybolley.registration.presentation.ui.screens.registration.RegistrationScreen
import cy.volleybolley.registration.presentation.ui.screens.registration.RegistrationViewModel
import cy.volleybolley.success.SucceedGame
import cy.volleybolley.success.SuccessScreen
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun NavHostContainer(
    paddingFromSystemUi: PaddingValues,
    navController: NavHostController,
    startDestination: NavMap = LaunchRoute,
    activityFinisher: () -> Unit,
    onRequestNotificationPermission: () -> Unit = {},
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // authorization
        composable<LaunchRoute> {
            LaunchScreen(
                paddingFromSystemUi = paddingFromSystemUi,
                onNavigateToOnboarding = {
                    navController.navigate(OnboardingRoute) {
                        popUpTo(LaunchRoute) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(HomeTopLevelRoute) {
                        popUpTo(LaunchRoute) { inclusive = true }
                    }
                },
                onNavigateToAuthorization = {
                    navController.navigate(AuthorizationRoute) {
                        popUpTo(LaunchRoute) { inclusive = true }
                    }
                }
            )
        }
        composable<OnboardingRoute> {
            OnboardingScreen(
                onNextScreenRequested = { navController.navigate(AuthorizationRoute) },
                paddingFromSystemUi = paddingFromSystemUi
            )
        }
        composable<AuthorizationRoute> {
            AuthorizationScreen(
                paddingFromSystemUi = paddingFromSystemUi,
                onNavigateToRegisterByPhoneRequested = { navController.navigate(AuthorizationByPhoneRoute) },
                onNavigateToRegistration = { navController.navigate(RegistrationRoute) },
                onNavigateToHome = { navController.navigate(HomeRoute) }
            )
        }
        composable<RegistrationRoute> {
            val viewModel = koinViewModel<RegistrationViewModel>()

            RegistrationScreen(
                paddingFromSystemUi = paddingFromSystemUi,
                viewModel = viewModel,
                onRegistrationSuccessEvent = {
                    navController.navigate(HomeRoute) {
                        popUpTo(LaunchRoute) { inclusive = false }
                    }
                },
                onRequestNavigateToAboutLevels = { navController.navigate(AboutLevelsRoute) }
            )
        }
        composable<AuthorizationByPhoneRoute> {
            AuthorizationByPhoneScreen(
                paddingFromSystemUi = paddingFromSystemUi,
                onBackNavigationRequested = { navController.popBackStack() },
                onNavigateToRegistration = { navController.navigate(RegistrationRoute) },
                onNavigateToHome = { navController.navigate(HomeRoute) }
            )
        }

        composable<AboutLevelsRoute> {
            AboutLevelsScreen(onBackNavigationRequested = { navController.popBackStack() })
        }

        // notifications
        composable<NotificationsRoute> { NotificationsScreen(navController) }

        // Home nested graph
        navigation<HomeTopLevelRoute>(startDestination = HomeRoute) {
            // home
            composable<HomeRoute> {
                BackHandler { activityFinisher() }
                HomeScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateToSearchCourt = { eventType ->
                        navController.navigate(SearchCourtRoute(eventType = eventType))
                    },
                    onRequestNotificationPermission = onRequestNotificationPermission
                )
            }

            composable<SearchCourtRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<SearchCourtRoute>()
                val eventType = args.eventType
                SearchCourtScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateToGameCreation = { type ->
                        when (type) {
                            EventType.GAME -> navController.navigate(BasicGameSetupRoute)
                            EventType.TOURNAMENT -> navController.navigate(BasicTourneySetupRoute)
                        }
                    },
                    onNavigateBack = { navController.popBackStack() },
                    viewModel = koinViewModel {
                        parametersOf(eventType)
                    }
                )
            }

            composable<RatePlayersRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<RatePlayersRoute>()
                val eventId = args.eventId
                val eventType = args.eventType
                RatePlayersScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateBack = { navController.popBackStack() },
                    viewModel = koinViewModel {
                        parametersOf(eventId, eventType)
                    }
                )
            }
            composable<SuccessRoute> { backStackEntry ->
                val event = Json.decodeFromString<SucceedGame>(
                    backStackEntry.toRoute<SuccessRoute>().succeedGame
                )
                SuccessScreen(
                    onNavigateToHome = {
                        navController.navigate(HomeRoute) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToInvitePlayers = { eventId ->
                        navController.navigate(InvitePlayersRoute(id = eventId))
                    },
                    viewModel = koinViewModel {
                        parametersOf(event)
                    }
                )
            }
            // create game
            composable<BasicGameSetupRoute> {
                BasicGameSetupScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateToNextStep = { navController.navigate(GameEnteringConditionsRoute) },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable<GameEnteringConditionsRoute> {
                GameConditionsScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateToPayments = { navController.navigate(PaymentsRoute) },
                    onNavigateToPrivacyOptions = { navController.navigate(PrivacyOptionsRoute) },
                    onNavigateToSuccess = { succeedGame ->
                        val json = Json.encodeToString(SucceedGame.serializer(), succeedGame)
                        navController.navigate(SuccessRoute(json))
                    },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable<PrivacyOptionsRoute> {
                PrivacyOptionsScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // create tourney
            composable<BasicTourneySetupRoute> {
                BasicTourneySetupScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateToSearchCourt = { navController.navigate(SearchCourtRoute(EventType.TOURNAMENT)) },
                    onNavigateToNextStep = { navController.navigate(TourneyEnteringConditionsRoute) },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable<TourneyEnteringConditionsRoute> {
                TourneyEnteringConditionsScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateToPayments = { navController.navigate(PaymentsRoute) },
                    onNavigateToChangeTeam = { navController.navigate(ChangeTeamRoute) },
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // find game
            composable<JoinTheGameRoute> { backStackEntry ->
                val gameId = backStackEntry.toRoute<JoinTheGameRoute>().gameId
                JoinTheGameScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToSuccess = { succeedGameJson ->
                        navController.navigate(SuccessRoute(succeedGameJson))
                    },
                    viewModel = koinViewModel<JoinTheGameViewModel> {
                        parametersOf(gameId)
                    }
                )
            }

            // find tourney
            composable<ChooseTeamRoute> {
                ChooseTeamScreen(
                    onNavigateBack = { navController.popBackStack() },
                    paddingFromSystemUi = paddingFromSystemUi
                )
            }
            composable<IndividualPlayersRoute> {
                IndividualPlayersScreen(
                    onNavigateBack = { navController.popBackStack() },
                    paddingFromSystemUi = paddingFromSystemUi
                )
            }
            composable<InvitePlayersRoute> {
                InvitePlayersScreen(
                    onNavigateBack = { navController.popBackStack() },
                    paddingFromSystemUi = paddingFromSystemUi
                )
            }
            composable<JoinIndividualRoute> {
                JoinIndividualScreen(
                    onNavigateBack = { navController.popBackStack() },
                    paddingFromSystemUi = paddingFromSystemUi
                )
            }
            composable<JoinTeamRoute> {
                JoinTeamScreen(
                    onNavigateBack = { navController.popBackStack() },
                    paddingFromSystemUi = paddingFromSystemUi
                )
            }
        }

        // Game Home nested graph
        navigation<GameHomeTopLevelRoute>(startDestination = GameHomeRoute) {
            composable<GameHomeRoute> {
                BackHandler { activityFinisher() }
                GameHomeScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateToMyGames = { navController.navigate(MyGamesRoute) },
                    onNavigateToUpcomingGames = { navController.navigate(UpcomingGamesRoute) },
                    onNavigateToInvites = { navController.navigate(GameInvitesRoute) },
                    onNavigateToArchive = { navController.navigate(ArchiveRoute) }
                )
            }

            // archive
            composable<ArchiveRoute> {
                ArchiveScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateToCreateGame = { navController.navigate(BasicGameSetupRoute) },
                    onNavigateToPastGame = { navController.navigate(PastGameRoute) },
                    onNavigateToPastTourney = { navController.navigate(PastTourneyRoute) },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable<PastGameRoute> {
                PastGameScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable<PastTourneyRoute> {
                PastTourneyScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateToTeams = { navController.navigate(TeamsRoute) },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable<TeamsRoute> {
                TeamsScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // game invites
            composable<GameInvitesRoute> {
                GameInvitesScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable<JoinTheTourneyRoute> {
                JoinTheTourneyScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // my games
            composable<ChangeTeamRoute> {
                ChangeTeamScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable<MyGamesRoute> {
                MyGamesScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateToCreateGame = { navController.navigate(BasicGameSetupRoute) },
                    onNavigateToMyGame = { navController.navigate(MyGameRoute) },
                    onNavigateToMyTourney = { navController.navigate(MyTourneyRoute) },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable<ManagePlayersRoute> {
                ManagePlayersScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable<MyGameRoute> {
                MyGameScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable<MyTourneyRoute> {
                MyTourneyScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateToManagePlayers = { navController.navigate(ManagePlayersRoute) },
                    onNavigateToChangeTeam = { navController.navigate(ChangeTeamRoute) },
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            // upcoming games
            composable<JoinedPlayersRoute> { backStackEntry ->
                val holderKey = backStackEntry.toRoute<JoinedPlayersRoute>().tournamentDetailsHolderKey
                val holder: TournamentDetailsDataHolder = koinInject<TournamentDetailsDataHolder>()
                val tournamentDetails = holder.get<TournamentDetails>(holderKey)
                JoinedPlayersScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateBack = { navController.popBackStack() },
                    viewModel = koinViewModel { parametersOf(tournamentDetails) }
                )
            }
            composable<UpcomingGameDetailsRoute> {
                UpcomingGameDetailsScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable<UpcomingGamesRoute> {
                UpcomingGamesScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable<UpcomingTourneyDetailsRoute> {
                UpcomingTourneyDetailsScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    tournamentDetails = null,
                    onNavigateToJoinedPlayers = { key ->
                        navController.navigate(JoinedPlayersRoute(tournamentDetailsHolderKey = key))
                    },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }

        // Profile nested graph
        navigation<ProfileTopLevelRoute>(startDestination = ProfileRoute) {
            composable<ProfileRoute> {
                BackHandler { activityFinisher() }
                ProfileScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateToPlayers = { navController.navigate(PlayersRoute) },
                    onNavigateToPersonalData = { navController.navigate(PersonalDataRoute) },
                    onNavigateToPayments = { navController.navigate(PaymentsRoute) },
                    onNavigateToFaq = { navController.navigate(FaqRoute) },
                    onNavigateToAbout = { navController.navigate(AboutRoute) },
                    onNavigateToAuthorization = {
                        navController.navigate(AuthorizationRoute) {
                            popUpTo(LaunchRoute) { inclusive = true }
                        }
                    }
                )
            }

            composable<AboutRoute> {
                AboutScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable<ChangePhotoRoute> { backStackEntry ->
                val avatarString = backStackEntry.toRoute<ChangePhotoRoute>().avatarUrl
                ChangePhotoScreen(
                    avatarFromPersonalData = avatarString,
                    onNavigateBack = { newAvatar ->
                        newAvatar?.let {
                            navController.previousBackStackEntry?.savedStateHandle?.set(BackAvatarHolder.AVATAR_KEY, it)
                        }
                        navController.popBackStack()
                    },
                    paddingFromSystemUi = paddingFromSystemUi
                )
            }

            composable<FaqRoute> {
                FaqScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable<PaymentsRoute> { backStackEntry ->
                val viewModel = koinViewModel<PaymentsScreenViewModel> {
                    parametersOf(BackPaymentsHolder(backStackEntry.savedStateHandle))
                }
                PaymentsScreen(
                    paddingFromSystemUi = paddingFromSystemUi,
                    onNavigateToEnterPaymentData = { paymentTypeName, paymentsJsonString ->
                        navController.navigate(EnterPaymentDataRoute(paymentTypeName, paymentsJsonString))
                    },
                    onNavigateBack = { navController.popBackStack() },
                    viewModel = viewModel
                )
            }

            composable<PersonalDataRoute> { backStackEntry ->
                val viewModel = koinViewModel<PersonalDataScreenViewModel> {
                    parametersOf(BackAvatarHolder(backStackEntry.savedStateHandle))
                }
                PersonalDataScreen(
                    onNavigateToChangePhoto = { avatarUrl ->
                        navController.navigate(ChangePhotoRoute(avatarUrl = avatarUrl))
                    },
                    onNavigateBack = { navController.popBackStack() },
                    viewModel = viewModel,
                    paddingFromSystemUi = paddingFromSystemUi
                )
            }

            composable<PlayerProfileRoute> { backStackEntry ->
                val playerId = backStackEntry.toRoute<PlayerProfileRoute>().playerId
                val viewModel = koinViewModel<PlayerProfileScreenViewModel> {
                    parametersOf(playerId)
                }
                PlayerProfileScreen(
                    onNavigateBack = { backPlayerId ->
                        backPlayerId?.let {
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                BackPlayerIdHolder.PLAYER_ID_KEY,
                                it
                            )
                        }
                        navController.popBackStack()
                    },
                    viewModel = viewModel,
                    paddingFromSystemUi = paddingFromSystemUi,
                )
            }

            composable<PlayersRoute> { backStackEntry ->
                val viewModel = koinViewModel<PlayersScreenViewModel> {
                    parametersOf(BackPlayerIdHolder(backStackEntry.savedStateHandle))
                }
                PlayersScreen(
                    onNavigateToPlayerProfile = { playerId ->
                        navController.navigate(PlayerProfileRoute(playerId = playerId))
                    },
                    onNavigateBack = { navController.popBackStack() },
                    viewModel = viewModel,
                    paddingFromSystemUi = paddingFromSystemUi
                )
            }

            composable<EnterPaymentDataRoute> { backStackEntry ->
                val routeWithArgs = backStackEntry.toRoute<EnterPaymentDataRoute>()
                val viewModel = koinViewModel<EnterPaymentDataScreenViewModel> {
                    parametersOf(routeWithArgs.paymentTypeName, routeWithArgs.paymentsJsonString)
                }
                EnterPaymentDataScreen(
                    onNavigateBack = { updatedPaymentsJsonString ->
                        updatedPaymentsJsonString?.let {
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                BackPaymentsHolder.PAYMENTS_KEY,
                                it
                            )
                        }
                        navController.popBackStack()
                    },
                    viewModel = viewModel,
                    paddingFromSystemUi = paddingFromSystemUi
                )
            }
        }

        composable<ShareLinkRoute>(
            deepLinks = listOf(
                navDeepLink { uriPattern = "volleybolley://invite/{type}/{id}" }
            )
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<ShareLinkRoute>()
            JoinTheGameScreen(
                paddingFromSystemUi = paddingFromSystemUi,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToSuccess = { succeedGameJson ->
                    navController.navigate(SuccessRoute(succeedGameJson))
                },
                viewModel = koinViewModel<JoinTheGameViewModel> {
                    parametersOf(route.id.toIntOrNull())
                }
            )
        }
    }
}
