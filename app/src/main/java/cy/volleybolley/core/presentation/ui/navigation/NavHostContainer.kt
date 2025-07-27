package cy.volleybolley.core.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.core.presentation.ui.screens.authorization.AboutLevelsScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.OnboardingScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.RegistrationByPhoneScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.RegistrationScreen
import cy.volleybolley.core.presentation.ui.screens.authorization.SignUpScreen
import cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen
import cy.volleybolley.core.presentation.ui.screens.createnewgame.GameCreatedScreen
import cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen
import cy.volleybolley.core.presentation.ui.screens.createnewgame.PrivacyOptionsScreen
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.BasicTourneySetupScreen
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.TourneyCreatedScreen
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.TourneyEnteringConditionsScreen
import cy.volleybolley.core.presentation.ui.screens.findagame.JoinTheGameScreen
import cy.volleybolley.core.presentation.ui.screens.findagame.JoinedTheGameScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.ChooseTeamScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.IndividualPlayersScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.InvitePlayersScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.JoinIndividualScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.JoinTeamScreen
import cy.volleybolley.core.presentation.ui.screens.findatourney.JoinedTheTourneyScreen
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
import cy.volleybolley.core.presentation.ui.screens.profile.AboutScreen
import cy.volleybolley.core.presentation.ui.screens.profile.ChangePhotoScreen
import cy.volleybolley.core.presentation.ui.screens.profile.EnterPaymentDataScreen
import cy.volleybolley.core.presentation.ui.screens.profile.FaqScreen
import cy.volleybolley.core.presentation.ui.screens.profile.PaymentsScreen
import cy.volleybolley.core.presentation.ui.screens.profile.PersonalDataScreen
import cy.volleybolley.core.presentation.ui.screens.profile.PlayerProfileScreen
import cy.volleybolley.core.presentation.ui.screens.profile.PlayersScreen
import cy.volleybolley.core.presentation.ui.screens.profile.ProfileScreen

@Composable
fun NavHostContainer(
    modifier: Modifier = Modifier,
    startDestination: NavMap = NavMap.OnboardingScreen
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        // authorization flow
        composable(NavMap.OnboardingScreen.route) {
            OnboardingScreen(navController)
        }
        composable(NavMap.SignUpScreen.route) {
            SignUpScreen(navController)
        }
        composable(NavMap.RegistrationScreen.route) {
            RegistrationScreen(navController)
        }
        composable(NavMap.RegistrationByPhoneScreen.route) {
            RegistrationByPhoneScreen(navController)
        }
        composable(NavMap.AboutLevelsScreen.route) {
            AboutLevelsScreen(navController)
        }

        // create new game flow
        composable(NavMap.BasicGameSetupScreen.route) {
            BasicGameSetupScreen(navController)
        }
        composable(NavMap.GameCreatedScreen.route) {
            GameCreatedScreen(navController)
        }
        composable(NavMap.GameEnteringConditionsScreen.route) {
            GameEnteringConditionsScreen(navController)
        }
        composable(NavMap.PrivacyOptionsScreen.route) {
            PrivacyOptionsScreen(navController)
        }

        // create new tourney flow
        composable(NavMap.BasicTourneySetupScreen.route) {
            BasicTourneySetupScreen(navController)
        }
        composable(NavMap.TourneyCreatedScreen.route) {
            TourneyCreatedScreen(navController)
        }
        composable(NavMap.TourneyEnteringConditionsScreen.route) {
            TourneyEnteringConditionsScreen(navController)
        }

        // find a game flow
        composable(NavMap.JoinedTheGameScreen.route) {
            JoinedTheGameScreen(navController)
        }
        composable(NavMap.JoinTheGameScreen.route) {
            JoinTheGameScreen(navController)
        }

        // find a tourney flow
        composable(NavMap.ChooseTeamScreen.route) {
            ChooseTeamScreen(navController)
        }
        composable(NavMap.IndividualPlayersScreen.route) {
            IndividualPlayersScreen(navController)
        }
        composable(NavMap.InvitePlayersScreen.route) {
            InvitePlayersScreen(navController)
        }
        composable(NavMap.JoinedTheTourneyScreen.route) {
            JoinedTheTourneyScreen(navController)
        }
        composable(NavMap.JoinIndividualScreen.route) {
            JoinIndividualScreen(navController)
        }
        composable(NavMap.JoinTeamScreen.route) {
            JoinTeamScreen(navController)
        }

        // archive flow
        composable(NavMap.ArchiveScreen.route) {
            ArchiveScreen(navController)
        }
        composable(NavMap.PastGameScreen.route) {
            PastGameScreen(navController)
        }
        composable(NavMap.PastTourneyScreen.route) {
            PastTourneyScreen(navController)
        }
        composable(NavMap.TeamsScreen.route) {
            TeamsScreen(navController)
        }

        // game invites flow
        composable(NavMap.GameInvitesScreen.route) {
            GameInvitesScreen(navController)
        }
        composable(NavMap.JoinTheTourneyScreen.route) {
            JoinTheTourneyScreen(navController)
        }

        // my games flow
        composable(NavMap.ChangeTeamScreen.route) {
            ChangeTeamScreen(navController)
        }
        composable(NavMap.GameHomeScreen.route) {
            GameHomeScreen(navController)
        }
        composable(NavMap.ManagePlayersScreen.route) {
            ManagePlayersScreen(navController)
        }
        composable(NavMap.MyGameScreen.route) {
            MyGameScreen(navController)
        }
        composable(NavMap.MyGamesScreen.route) {
            MyGamesScreen(navController)
        }
        composable(NavMap.MyTourneyScreen.route) {
            MyTourneyScreen(navController)
        }

        // upcoming games flow
        composable(NavMap.JoinedPlayersScreen.route) {
            JoinedPlayersScreen(navController)
        }
        composable(NavMap.UpcomingGameDetailsScreen.route) {
            UpcomingGameDetailsScreen(navController)
        }
        composable(NavMap.UpcomingGamesScreen.route) {
            UpcomingGamesScreen(navController)
        }
        composable(NavMap.UpcomingTourneyDetailsScreen.route) {
            UpcomingTourneyDetailsScreen(navController)
        }

        // home flow
        composable(NavMap.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(NavMap.SearchCourtScreen.route) {
            SearchCourtScreen(navController)
        }
        composable(NavMap.RatePlayersScreen.route) {
            RatePlayersScreen(navController)
        }

        // profile flow
        composable(NavMap.AboutScreen.route) {
            AboutScreen(navController)
        }
        composable(NavMap.ChangePhotoScreen.route) {
            ChangePhotoScreen(navController)
        }
        composable(NavMap.FaqScreen.route) {
            FaqScreen(navController)
        }
        composable(NavMap.PaymentsScreen.route) {
            PaymentsScreen(navController)
        }
        composable(NavMap.PersonalDataScreen.route) {
            PersonalDataScreen(navController)
        }
        composable(NavMap.PlayerProfileScreen.route) {
            PlayerProfileScreen(navController)
        }
        composable(NavMap.PlayersScreen.route) {
            PlayersScreen(navController)
        }
        composable(NavMap.ProfileScreen.route) {
            ProfileScreen(navController)
        }
        composable(NavMap.EnterPaymentDataScreen.route) {
            EnterPaymentDataScreen(navController)
        }
    }
}
