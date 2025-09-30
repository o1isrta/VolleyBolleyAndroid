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
object AuthorizationRoute : NavMap

@Serializable
object RegistrationRoute : NavMap

@Serializable
object AuthorizationByPhoneRoute : NavMap

@Serializable
object VerifyPhoneNumberRoute : NavMap

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
data class JoinTheGameRoute(val gameId: Int) : NavMap

// --- find a tourney flow ---
@Serializable
object ChooseTeamRoute : NavMap

@Serializable
object IndividualPlayersRoute : NavMap

@Serializable
data class InvitePlayersRoute(
    val id: Int
) : NavMap

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
data class JoinTheTourneyRoute(val tourneyId: Int) : NavMap

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
data class RatePlayersRoute(
    val eventId: Int,
    val eventType: String,
) : NavMap

@Serializable
data class SuccessRoute(
    val succeedGame: String
) : NavMap

// --- profile flow ---
@Serializable
object AboutRoute : NavMap

@Serializable
object ChangePhotoRoute : NavMap

@Serializable
object FaqRoute : NavMap

@Serializable
object PaymentsRoute : NavMap

@Serializable
object PersonalDataRoute : NavMap

@Serializable
object PlayerProfileRoute : NavMap

@Serializable
object PlayersRoute : NavMap

@Serializable
object ProfileRoute : NavMap

@Serializable
object EnterPaymentDataRoute : NavMap

@Serializable
object NotificationsRoute : NavMap
