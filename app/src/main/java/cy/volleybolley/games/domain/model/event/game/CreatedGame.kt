package cy.volleybolley.games.domain.model.event.game

import cy.volleybolley.core.domain.model.PaymentType
import cy.volleybolley.courts.domain.model.Location

data class CreatedGame(
    val gameId: Int,
    val courtId: Int,
    val message: String,
    val startTime: String,
    val endTime: String,
    val gender: String,
    val levels: List<String>,
    val isPrivate: Boolean = false,
    val maximumPlayers: Int,
    val pricePerPerson: String,
    val paymentType: PaymentType,
    val paymentAccount: String?,
    val currencyType: String,
    val players: List<Int>,
    val location: Location,
)

