package cy.volleybolley.core.presentation.ui.screens.games.createNewGame.model

import cy.volleybolley.core.domain.model.PaymentType
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.players.domain.model.Player
import java.time.LocalDate

private const val DEFAULT_START_HOUR = 2
private const val DEFAULT_START_MINUTE = 0
private const val DEFAULT_FINISH_HOUR = 4
private const val DEFAULT_FINISH_MINUTE = 0
private const val DEFAULT_MAXIMUM_PLAYERS = 8
private const val DEFAULT_PER_PERSON = "5.0"

data class GameData(
    // BasicGameSetupScreen
    val message: String = "Join us for a great game of volleyball!",
    val placeCourt: Court = Court(
        courtId = 1,
        price = "1$",
        description = "Karon Beach Club: Patak Rd, Mueang Phuket",
        location = Location(
            longitude = 55.0,
            latitude = 56.0,
            courtName = "Karon Beach Club",
            locationName = "Patak Rd, Mueang Phuket"
        ),
        contacts = listOf(),
        photo = "",
        tags = listOf()
    ),
    val date: LocalDate = LocalDate.now(),
    val startTime: VolleyTimeStamp? = VolleyTimeStamp(DEFAULT_START_HOUR, DEFAULT_START_MINUTE, true),
    val finishTime: VolleyTimeStamp? = VolleyTimeStamp(DEFAULT_FINISH_HOUR, DEFAULT_FINISH_MINUTE, true),
    val gender: GameGender = GameGender.Mix,
    val levels: Set<Level> = setOf(Level.Light, Level.Medium, Level.Hard),

    // GameEnteringConditionsScreen
    val maximumPlayers: Int = DEFAULT_MAXIMUM_PLAYERS,
    val perPerson: String = DEFAULT_PER_PERSON,
    val accountNumber: String? = null,

    // PrivacyOptionsScreen
    val players: List<Player> = emptyList(),

    // Дополнительные поля
    val isPrivate: Boolean = false,
    val paymentType: PaymentType = PaymentType.CASH,
    val gameId: Int? = null,
    val showCalendar: Boolean = false
)

enum class Privacy(val displayText: String, val showRightIcon: Boolean) {
    Public("Public", false),
    Private("Private", true)
}
