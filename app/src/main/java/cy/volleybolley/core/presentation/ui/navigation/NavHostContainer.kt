package cy.volleybolley.core.presentation.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import cy.volleybolley.auth.ui.screens.authorization.AuthorizationScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.sendCode.presentation.AuthorizationByPhoneScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.verifyCode.presentation.VerifyPhoneNumberScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.launch.LaunchScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.onboarding.OnboardingScreen
import cy.volleybolley.core.presentation.ui.screens.createnewgame.basicGameSetupScreen.BasicGameSetupScreen
import cy.volleybolley.core.presentation.ui.screens.createnewgame.basicGameSetupScreen.BasicGameSetupScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewgame.gameEnteringConditionsScreen.GameEnteringConditionsScreen
import cy.volleybolley.core.presentation.ui.screens.createnewgame.gameEnteringConditionsScreen.GameEnteringConditionsScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewgame.privacyOptionsScreen.PrivacyOptionsScreen
import cy.volleybolley.core.presentation.ui.screens.createnewgame.privacyOptionsScreen.PrivacyOptionsScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.BasicTourneySetupScreen
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.TourneyEnteringConditionsScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.ChooseTeamScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.IndividualPlayersScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.InvitePlayersScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.JoinIndividualScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.JoinTeamScreen
import cy.volleybolley.core.presentation.ui.screens.games.archive.pastgamescreen.PastGameScreen
import cy.volleybolley.core.presentation.ui.screens.games.archive.pasttourneyscreen.PastTourneyScreen
import cy.volleybolley.core.presentation.ui.screens.games.archive.archivescreen.ArchiveScreen
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
import cy.volleybolley.success.SucceedGame
import cy.volleybolley.success.SuccessScreen
import cy.volleybolley.notification.presentation.NotificationsScreen
import cy.volleybolley.courts.presentation.SearchCourtScreen
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails
import cy.volleybolley.jointhegame.JoinTheGameScreen
import cy.volleybolley.jointhegame.JoinTheGameViewModel
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
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun NavHostContainer(
    paddingFromSystemUi: PaddingValues,
    navController: NavHostController,
    // startDestination: NavMap = LaunchRoute,
    startDestination: NavMap = HomeTopLevelRoute,
    activityFinisher: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // authorization
        composable<LaunchRoute> { LaunchScreen(navController, paddingFromSystemUi) }
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
                onSuccessGetNotRegisterUser = { user ->
                    navController.navigate(RegistrationRoute(user))
                },
                onSuccessGetRegisterUser = { navController.navigate(HomeRoute) }
            )
        }
        composable<RegistrationRoute> { backStackEntry ->
            val userData = backStackEntry.toRoute<RegistrationRoute>().user
            val viewModel = koinViewModel<RegistrationViewModel> {
                parametersOf(userData)
            }

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
                requestNavigateToVerifyPhoneScreen = {
                    navController.navigate(VerifyPhoneNumberRoute)
                }
            )
        }
        composable<VerifyPhoneNumberRoute> {
            VerifyPhoneNumberScreen(
                paddingFromSystemUi = paddingFromSystemUi,
                onBackNavigationRequested = { navController.popBackStack() },
                onNavigateToRegistrationScreenRequested = {
                    navController.navigate(RegistrationRoute)
                }
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
                HomeScreen(
                    navController = navController,
                    paddingFromSystemUi = paddingFromSystemUi,
                    finisher = activityFinisher,
                )
            }

            composable<SearchCourtRoute> { backStackEntry ->
                val args = backStackEntry.toRoute<SearchCourtRoute>()
                val eventType = args.eventType
                SearchCourtScreen(
                    navController = navController,
                    paddingFromSystemUi = paddingFromSystemUi,
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
                    navController = navController,
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
                    navController = navController,
                    viewModel = koinViewModel {
                        parametersOf(event)
                    }
                )
            }
            // create game
            composable<BasicGameSetupRoute> {
                val viewModel: BasicGameSetupScreenViewModel = koinViewModel()
                BasicGameSetupScreen(
                    navController = navController,
                    viewModel = viewModel,
                    paddingFromSystemUi = paddingFromSystemUi,
                )
            }
            composable<GameEnteringConditionsRoute> {
                val viewModel: GameEnteringConditionsScreenViewModel = koinViewModel()
                GameEnteringConditionsScreen(
                    navController = navController,
                    viewModel = viewModel,
                    paddingFromSystemUi = paddingFromSystemUi,
                )
            }
            composable<PrivacyOptionsRoute> {
                val viewModel: PrivacyOptionsScreenViewModel = koinViewModel()
                PrivacyOptionsScreen(
                    navController = navController,
                    viewModel = viewModel,
                    paddingFromSystemUi = paddingFromSystemUi,
                )
            }

            // create tourney
            composable<BasicTourneySetupRoute> { BasicTourneySetupScreen(/*navController*/) }
            composable<TourneyEnteringConditionsRoute> { TourneyEnteringConditionsScreen(navController) }

            // find game
            composable<JoinTheGameRoute> { backStackEntry ->
                val gameId = backStackEntry.toRoute<JoinTheGameRoute>().gameId
                JoinTheGameScreen(
                    navController = navController,
                    viewModel = koinViewModel<JoinTheGameViewModel> {
                        parametersOf(gameId)
                    }
                )
            }

            // find tourney
            composable<ChooseTeamRoute> { ChooseTeamScreen(navController) }
            composable<IndividualPlayersRoute> { IndividualPlayersScreen(navController) }
            composable<InvitePlayersRoute> { InvitePlayersScreen(navController) }
            composable<JoinIndividualRoute> { JoinIndividualScreen(navController) }
            composable<JoinTeamRoute> { JoinTeamScreen(navController) }
        }

        // Game Home nested graph
        navigation<GameHomeTopLevelRoute>(startDestination = GameHomeRoute) {
            composable<GameHomeRoute> {
                GameHomeScreen(
                    navController = navController,
                    finisher = activityFinisher,
                )
            }

            // archive
            composable<ArchiveRoute> { ArchiveScreen(navController) }
            composable<PastGameRoute> { PastGameScreen(navController) }
            composable<PastTourneyRoute> { PastTourneyScreen(navController) }
            composable<TeamsRoute> { TeamsScreen(navController) }

            // game invites
            composable<GameInvitesRoute> { GameInvitesScreen(navController) }
            composable<JoinTheTourneyRoute> { JoinTheTourneyScreen(navController) }

            // my games
            composable<ChangeTeamRoute> { ChangeTeamScreen(navController) }
            composable<MyGamesRoute> { MyGamesScreen(navController) }
            composable<ManagePlayersRoute> { ManagePlayersScreen(navController) }
            composable<MyGameRoute> { MyGameScreen(navController) }
            composable<MyTourneyRoute> { MyTourneyScreen(navController) }

            // upcoming games
            composable<JoinedPlayersRoute> { backStackEntry ->
                val holderKey = backStackEntry.toRoute<JoinedPlayersRoute>().tournamentDetailsHolderKey
                val holder: TournamentDetailsDataHolder = koinInject<TournamentDetailsDataHolder>()
                val tournamentDetails = holder.get<TournamentDetails>(holderKey)
                JoinedPlayersScreen(
                    navController = navController,
                    paddingFromSystemUi = paddingFromSystemUi,
                    tournamentDetails = tournamentDetails,
                )
            }
            composable<UpcomingGameDetailsRoute> { UpcomingGameDetailsScreen(navController) }
            composable<UpcomingGamesRoute> { UpcomingGamesScreen(navController) }
            composable<UpcomingTourneyDetailsRoute> { UpcomingTourneyDetailsScreen(navController) }
        }

        // Profile nested graph
        navigation<ProfileTopLevelRoute>(startDestination = ProfileRoute) {
            composable<ProfileRoute> {
                ProfileScreen(
                    navController = navController,
                    paddingFromSystemUi = paddingFromSystemUi,
                    finisher = activityFinisher,
                )
            }

            composable<AboutRoute> {
                AboutScreen(
                    navController = navController,
                    paddingFromSystemUi = paddingFromSystemUi
                )
            }

            composable<ChangePhotoRoute> { backStackEntry ->
                val avatarString = backStackEntry.toRoute<ChangePhotoRoute>().avatarUrl
                ChangePhotoScreen(
                    navController = navController,
                    avatarFromPersonalData = avatarString,
                    paddingFromSystemUi = paddingFromSystemUi
                )
            }

            composable<FaqRoute> {
                FaqScreen(
                    navController = navController,
                    paddingFromSystemUi = paddingFromSystemUi
                )
            }

            composable<PaymentsRoute> { backStackEntry ->
                val viewModel = koinViewModel<PaymentsScreenViewModel> {
                    parametersOf(BackPaymentsHolder(backStackEntry.savedStateHandle))
                }
                PaymentsScreen(
                    navController = navController,
                    viewModel = viewModel,
                    paddingFromSystemUi = paddingFromSystemUi
                )
            }

            composable<PersonalDataRoute> { backStackEntry ->
                val viewModel = koinViewModel<PersonalDataScreenViewModel> {
                    parametersOf(BackAvatarHolder(backStackEntry.savedStateHandle))
                }
                PersonalDataScreen(
                    navController = navController,
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
                    navController = navController,
                    viewModel = viewModel,
                    paddingFromSystemUi = paddingFromSystemUi,
                )
            }

            composable<PlayersRoute> { backStackEntry ->
                val viewModel = koinViewModel<PlayersScreenViewModel> {
                    parametersOf(BackPlayerIdHolder(backStackEntry.savedStateHandle))
                }
                PlayersScreen(
                    navController = navController,
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
                    navController = navController,
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
            val viewModel = koinViewModel<JoinTheGameViewModel>()
            JoinTheGameScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}
