package cy.volleybolley.core.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.navigation.navigation
import cy.volleybolley.core.presentation.ui.screens.authorization.AboutLevelsScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.LaunchScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.OnboardingScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.RegistrationByPhoneScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.RegistrationScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.SignUpScreen
import cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen
import cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen
import cy.volleybolley.core.presentation.ui.screens.createnewgame.PrivacyOptionsScreen
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.BasicTourneySetupScreen
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.TourneyEnteringConditionsScreen
import cy.volleybolley.core.presentation.ui.screens.findagame.JoinTheGameScreen
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
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NavHostContainer(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: NavMap = LaunchRoute,
    activityFinisher: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // authorization
        composable<LaunchRoute> { LaunchScreen(navController) }
        composable<OnboardingRoute> { OnboardingScreen(navController) }
        composable<SignUpRoute> { SignUpScreen(navController) }
        composable<RegistrationRoute> { RegistrationScreen(navController) }
        composable<RegistrationByPhoneRoute> { RegistrationByPhoneScreen(navController) }
        composable<AboutLevelsRoute> { AboutLevelsScreen(navController) }

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
            composable<JoinTheGameRoute> { JoinTheGameScreen(navController) }

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

            composable<AboutRoute> { AboutScreen(navController) }

            composable<ChangePhotoRoute> { backStackEntry ->
                val avatarString = backStackEntry.toRoute<ChangePhotoRoute>().avatarUrl
                ChangePhotoScreen(navController = navController, avatarFromPersonalData = avatarString)
            }

            composable<FaqRoute> { FaqScreen(navController) }

            composable<PaymentsRoute> { backStackEntry ->
                val viewModel = koinViewModel<PaymentsScreenViewModel> {
                    parametersOf(BackPaymentsHolder(backStackEntry.savedStateHandle))
                }
                PaymentsScreen(navController, viewModel)
            }

            composable<PersonalDataRoute> { backStackEntry ->
                val viewModel = koinViewModel<PersonalDataScreenViewModel> {
                    parametersOf(BackAvatarHolder(backStackEntry.savedStateHandle))
                }
                PersonalDataScreen(navController, viewModel)
            }

            composable<PlayerProfileRoute> { backStackEntry ->
                val playerId = backStackEntry.toRoute<PlayerProfileRoute>().playerId
                val viewModel = koinViewModel<PlayerProfileScreenViewModel> {
                    parametersOf(playerId)
                }
                PlayerProfileScreen(navController, viewModel)
            }

            composable<PlayersRoute> { backStackEntry ->
                val viewModel = koinViewModel<PlayersScreenViewModel> {
                    parametersOf(BackPlayerIdHolder(backStackEntry.savedStateHandle))
                }
                PlayersScreen(navController, viewModel)
            }

            composable<EnterPaymentDataRoute> { backStackEntry ->
                val routeWithArgs = backStackEntry.toRoute<EnterPaymentDataRoute>()
                val viewModel = koinViewModel<EnterPaymentDataScreenViewModel> {
                    parametersOf(routeWithArgs.paymentTypeName, routeWithArgs.paymentsJsonString)
                }
                EnterPaymentDataScreen(navController, viewModel)
            }
        }
    }
}
