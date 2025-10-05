package cy.volleybolley.core.presentation.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
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
import cy.volleybolley.core.presentation.ui.screens.findagame.JoinTheGameScreen
import cy.volleybolley.core.presentation.ui.screens.findagame.JoinTheGameViewModel
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
import cy.volleybolley.core.presentation.ui.screens.home.HomeScreen
import cy.volleybolley.core.presentation.ui.screens.home.RatePlayersScreen
import cy.volleybolley.core.presentation.ui.screens.home.SearchCourtScreen
import cy.volleybolley.core.presentation.ui.screens.home.SuccessScreen
import cy.volleybolley.core.presentation.ui.screens.profile.AboutScreen
import cy.volleybolley.core.presentation.ui.screens.profile.ChangePhotoScreen
import cy.volleybolley.core.presentation.ui.screens.profile.EnterPaymentDataScreen
import cy.volleybolley.core.presentation.ui.screens.profile.FaqScreen
import cy.volleybolley.core.presentation.ui.screens.profile.PaymentsScreen
import cy.volleybolley.core.presentation.ui.screens.profile.PersonalDataScreen
import cy.volleybolley.core.presentation.ui.screens.profile.PlayerProfileScreen
import cy.volleybolley.core.presentation.ui.screens.profile.PlayersScreen
import cy.volleybolley.core.presentation.ui.screens.profile.ProfileScreen
import org.koin.androidx.compose.koinViewModel

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
                    finisher = activityFinisher,
                )
            }
            composable<SearchCourtRoute> { SearchCourtScreen(navController) }
            composable<RatePlayersRoute> { RatePlayersScreen(navController) }
            composable<SuccessRoute> { SuccessScreen(navController) }

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
                    finisher = activityFinisher,
                )
            }
            composable<PlayersRoute> { PlayersScreen(navController) }
            composable<PlayerProfileRoute> { PlayerProfileScreen(navController) }
            composable<PersonalDataRoute> { PersonalDataScreen(navController) }
            composable<ChangePhotoRoute> { ChangePhotoScreen(navController) }
            composable<PaymentsRoute> { PaymentsScreen(navController) }
            composable<EnterPaymentDataRoute> { EnterPaymentDataScreen(navController) }
            composable<FaqRoute> { FaqScreen(navController) }
            composable<AboutRoute> { AboutScreen(navController) }
        }
    }
}
