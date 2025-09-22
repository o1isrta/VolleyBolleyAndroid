package cy.volleybolley.core.presentation.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavMap

// --- top level routes ---
@Serializable
object HomeTopLevelRoute : NavMap

@Serializable
object MyGamesTopLevelRoute : NavMap

@Serializable
object ProfileTopLevelRoute : NavMap

// --- authorization flow ---
@Serializable
object LaunchRoute : NavMap

@Serializable
object OnboardingRoute : NavMap

@Serializable
object SignUpRoute : NavMap

@Serializable
object RegistrationRoute : NavMap

@Serializable
object RegistrationByPhoneRoute : NavMap

@Serializable
object AboutLevelsRoute : NavMap

// --- create new game flow ---
@Serializable
object BasicGameSetupRoute : NavMap

@Serializable
object GameEnteringConditionsRoute : NavMap

@Serializable
object PrivacyOptionsRoute : NavMap

// --- create new tourney flow ---
@Serializable
object BasicTourneySetupRoute : NavMap

@Serializable
object TourneyEnteringConditionsRoute : NavMap

// --- find a game flow ---
@Serializable
object JoinTheGameRoute : NavMap

// --- find a tourney flow ---
@Serializable
object ChooseTeamRoute : NavMap

@Serializable
object IndividualPlayersRoute : NavMap

@Serializable
object InvitePlayersRoute : NavMap

@Serializable
object JoinIndividualRoute : NavMap

@Serializable
object JoinTeamRoute : NavMap

// --- archive flow ---
@Serializable
object ArchiveRoute : NavMap

@Serializable
object PastGameRoute : NavMap

@Serializable
object PastTourneyRoute : NavMap

@Serializable
object TeamsRoute : NavMap

// --- game invites flow ---
@Serializable
object GameInvitesRoute : NavMap

@Serializable
object JoinTheTourneyRoute : NavMap

// --- my games flow ---
@Serializable
object ChangeTeamRoute : NavMap

@Serializable
object GameHomeRoute : NavMap

@Serializable
object ManagePlayersRoute : NavMap

@Serializable
object MyGameRoute : NavMap

@Serializable
object MyGamesRoute : NavMap

@Serializable
object MyTourneyRoute : NavMap

// --- upcoming games flow ---
@Serializable
object JoinedPlayersRoute : NavMap

@Serializable
object UpcomingGameDetailsRoute : NavMap

@Serializable
object UpcomingGamesRoute : NavMap

@Serializable
object UpcomingTourneyDetailsRoute : NavMap

// --- home flow ---
@Serializable
object HomeRoute : NavMap

@Serializable
object SearchCourtRoute : NavMap

@Serializable
object RatePlayersRoute : NavMap

@Serializable
object SuccessRoute : NavMap

// --- profile flow ---
@Serializable
object AboutRoute : NavMap

@Serializable
data class ChangePhotoRoute(
    val avatarUrl: String?
) : NavMap

@Serializable
object FaqRoute : NavMap

@Serializable
object PaymentsRoute : NavMap

@Serializable
object PersonalDataRoute : NavMap

@Serializable
data class PlayerProfileRoute(
    val playerId: Int
) : NavMap

@Serializable
object PlayersRoute : NavMap

@Serializable
object ProfileRoute : NavMap

@Serializable
data class EnterPaymentDataRoute(
    val paymentTypeName: String,
    val paymentsJsonString: String
) : NavMap
