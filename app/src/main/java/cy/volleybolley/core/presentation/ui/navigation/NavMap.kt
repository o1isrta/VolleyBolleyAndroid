package cy.volleybolley.core.presentation.ui.navigation

sealed interface NavMap {
    val route: String

    // authorization flow
    object OnboardingScreen : NavMap {
        override val route = "authorization/OnboardingScreen"
    }

    object SignUpScreen : NavMap {
        override val route = "authorization/SignUpScreen"
    }

    object RegistrationScreen : NavMap {
        override val route = "authorization/RegistrationScreen"
    }

    object RegistrationByPhoneScreen : NavMap {
        override val route = "authorization/RegistrationByPhoneScreen"
    }

    object AboutLevelsScreen : NavMap {
        override val route = "authorization/AboutLevelsScreen"
    }

    // create new game flow
    object BasicGameSetupScreen : NavMap {
        override val route = "create_new_game/BasicGameSetupScreen"
    }

    object GameEnteringConditionsScreen : NavMap {
        override val route = "create_new_game/GameEnteringConditionsScreen"
    }

    object PrivacyOptionsScreen : NavMap {
        override val route = "create_new_game/PrivacyOptionsScreen"
    }

    // create new tourney flow
    object BasicTourneySetupScreen : NavMap {
        override val route = "create_new_tourney/BasicTourneySetupScreen"
    }

    object TourneyEnteringConditionsScreen : NavMap {
        override val route = "create_new_tourney/TourneyEnteringConditionsScreen"
    }

    // find a game flow
    object JoinTheGameScreen : NavMap {
        override val route = "find_a_game/JoinTheGameScreen"
    }

    // find a tourney flow
    object ChooseTeamScreen : NavMap {
        override val route = "find_a_tourney/ChooseTeamScreen"
    }

    object IndividualPlayersScreen : NavMap {
        override val route = "find_a_tourney/IndividualPlayersScreen"
    }

    object InvitePlayersScreen : NavMap {
        override val route = "find_a_tourney/InvitePlayersScreen"
    }

    object JoinIndividualScreen : NavMap {
        override val route = "find_a_tourney/ JoinIndividualScreen"
    }

    object JoinTeamScreen : NavMap {
        override val route = "find_a_tourney/JoinTeamScreen"
    }

    // archive flow
    object ArchiveScreen : NavMap {
        override val route = "archive/ArchiveScreen"
    }

    object PastGameScreen : NavMap {
        override val route = "archive/PastGameScreen"
    }

    object PastTourneyScreen : NavMap {
        override val route = "archive/PastTourneyScreen"
    }

    object TeamsScreen : NavMap {
        override val route = "archive/TeamsScreen"
    }

    // game invites flow
    object GameInvitesScreen : NavMap {
        override val route = "game_invites/GameInvitesScreen"
    }

    object JoinTheTourneyScreen : NavMap {
        override val route = "game_invites/JoinTheTourneyScreen"
    }

    // my games flow
    object ChangeTeamScreen : NavMap {
        override val route = "my_games/ChangeTeamScreen"
    }

    object GameHomeScreen : NavMap {
        override val route = "my_games/GameHomeScreen"
    }

    object ManagePlayersScreen : NavMap {
        override val route = "my_games/ManagePlayersScreen"
    }

    object MyGameScreen : NavMap {
        override val route = "my_games/MyGameScreen"
    }

    object MyGamesScreen : NavMap {
        override val route = "my_games/MyGamesScreen"
    }

    object MyTourneyScreen : NavMap {
        override val route = "my_games/MyTourneyScreen"
    }

    // upcoming games flow
    object JoinedPlayersScreen : NavMap {
        override val route = "upcoming_games/JoinedPlayersScreen"
    }

    object UpcomingGameDetailsScreen : NavMap {
        override val route = "upcoming_games/UpcomingGameDetailsScreen"
    }

    object UpcomingGamesScreen : NavMap {
        override val route = "upcoming_games/UpcomingGamesScreen"
    }

    object UpcomingTourneyDetailsScreen : NavMap {
        override val route = "upcoming_games/UpcomingTourneyDetailsScreen"
    }

    // home flow
    object HomeScreen : NavMap {
        override val route = "home/HomeScreen"
    }

    object SearchCourtScreen : NavMap {
        override val route = "home/SearchCourtScreen"
    }

    object RatePlayersScreen : NavMap {
        override val route = "home/RatePlayersScreen"
    }

    object SuccessScreen : NavMap {
        override val route = "home/SuccessScreen"
    }

    // profile flow
    object AboutScreen : NavMap {
        override val route = "profile/AboutScreen"
    }

    object ChangePhotoScreen : NavMap {
        override val route = "profile/ChangePhotoScreen"
    }

    object FaqScreen : NavMap {
        override val route = "profile/FaqScreen"
    }

    object PaymentsScreen : NavMap {
        override val route = "profile/PaymentsScreen"
    }

    object PersonalDataScreen : NavMap {
        override val route = "profile/ PersonalDataScreen"
    }

    object PlayerProfileScreen : NavMap {
        override val route = "profile/PlayerProfileScreen"
    }

    object PlayersScreen : NavMap {
        override val route = "profile/PlayersScreen"
    }

    object ProfileScreen : NavMap {
        override val route = "profile/ProfileScreen"
    }

    object EnterPaymentDataScreen : NavMap {
        override val route = "profile/EnterPaymentDataScreen"
    }

}
