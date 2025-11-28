package cy.volleybolley.core.presentation.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import androidx.navigation.toRoute
import cy.volleybolley.core.presentation.ui.screens.authorization.aboutlevels.AboutLevelsScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.authorization.AuthorizationScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.sendCode.presentation.AuthorizationByPhoneScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.authorizationByPhone.verifyCode.presentation.VerifyPhoneNumberScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.launch.LaunchScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.onboarding.OnboardingScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.registration.RegistrationScreen
import cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen
import cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen
import cy.volleybolley.core.presentation.ui.screens.createnewgame.PrivacyOptionsScreen
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.BasicTourneySetupScreen
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.TourneyEnteringConditionsScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.ChooseTeamScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.IndividualPlayersScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.InvitePlayersScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.JoinIndividualScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.JoinTeamScreen
import cy.volleybolley.core.presentation.ui.screens.games.archive.ArchiveScreen
import cy.volleybolley.core.presentation.ui.screens.games.archive.PastGameScreen
import cy.volleybolley.core.presentation.ui.screens.games.archive.PastTourneyScreen
import cy.volleybolley.core.presentation.ui.screens.games.archive.TeamsScreen
import cy.volleybolley.core.presentation.ui.screens.games.gameinvites.GameInvitesScreen
import cy.volleybolley.core.presentation.ui.screens.games.gameinvites.JoinTheTourneyScreen
import cy.volleybolley.core.presentation.ui.screens.games.mygames.ChangeTeamScreen
import cy.volleybolley.core.presentation.ui.screens.games.mygames.GameHomeScreen
import cy.volleybolley.core.presentation.ui.screens.games.mygames.ManagePlayersScreen
import cy.volleybolley.core.presentation.ui.screens.games.mygames.MyGameScreen
import cy.volleybolley.core.presentation.ui.screens.games.mygames.MyGamesScreen
import cy.volleybolley.core.presentation.ui.screens.games.mygames.MyTourneyScreen
import cy.volleybolley.core.presentation.ui.screens.games.upcominggames.JoinedPlayersScreen
import cy.volleybolley.core.presentation.ui.screens.games.upcominggames.UpcomingGameDetailsScreen
import cy.volleybolley.core.presentation.ui.screens.games.upcominggames.UpcomingGamesScreen
import cy.volleybolley.core.presentation.ui.screens.games.upcominggames.UpcomingTourneyDetailsScreen
import cy.volleybolley.core.presentation.ui.screens.home.SearchCourtScreen
import cy.volleybolley.core.presentation.ui.screens.home.home.HomeScreen
import cy.volleybolley.core.presentation.ui.screens.home.success.SucceedGame
import cy.volleybolley.core.presentation.ui.screens.home.success.SuccessScreen
import cy.volleybolley.core.presentation.ui.screens.joinagame.JoinTheGameScreen
import cy.volleybolley.core.presentation.ui.screens.joinagame.JoinTheGameViewModel
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
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NavHostContainer(
    paddingFromSystemUi: PaddingValues,
    navController: NavHostController,
    startDestination: NavMap = LaunchRoute,
    activityFinisher: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // authorization
        composable<LaunchRoute> { LaunchScreen(navController) }
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
                onSuccessRegisteredAction = { navController.navigate(RegistrationRoute) }
            )
        }
        composable<RegistrationRoute> {
            RegistrationScreen(
                paddingFromSystemUi = paddingFromSystemUi,
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
            composable<SearchCourtRoute> { SearchCourtScreen(navController) }

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
            composable<BasicGameSetupRoute> { BasicGameSetupScreen(navController) }
            composable<GameEnteringConditionsRoute> { GameEnteringConditionsScreen(navController) }
            composable<PrivacyOptionsRoute> { PrivacyOptionsScreen(navController) }

            // create tourney
            composable<BasicTourneySetupRoute> { BasicTourneySetupScreen(navController) }
            composable<TourneyEnteringConditionsRoute> { TourneyEnteringConditionsScreen(navController) }

            // find game
            composable<JoinTheGameRoute> {
                JoinTheGameScreen(
                    navController = navController,
                    viewModel = koinViewModel<JoinTheGameViewModel>()
                )
            }

            // find tourney
            composable<ChooseTeamRoute> { ChooseTeamScreen(navController) }
            composable<IndividualPlayersRoute> { IndividualPlayersScreen(navController) }
            composable<InvitePlayersRoute> { InvitePlayersScreen(navController) }
            composable<JoinIndividualRoute> { JoinIndividualScreen(navController) }
            composable<JoinTeamRoute> { JoinTeamScreen(navController) }
        }

        // My games nested graph
        navigation<MyGamesTopLevelRoute>(startDestination = MyGamesRoute) {
            composable<MyGamesRoute> {
                MyGamesScreen(
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
            composable<GameHomeRoute> { GameHomeScreen(navController) }
            composable<ManagePlayersRoute> { ManagePlayersScreen(navController) }
            composable<MyGameRoute> { MyGameScreen(navController) }
            composable<MyTourneyRoute> { MyTourneyScreen(navController) }

            // upcoming games
            composable<JoinedPlayersRoute> { JoinedPlayersScreen(navController) }
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
