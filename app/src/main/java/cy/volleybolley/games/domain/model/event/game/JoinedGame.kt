package cy.volleybolley.games.domain.model.event.game

import cy.volleybolley.core.domain.model.PaymentType
import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.games.domain.model.entity.Host
import cy.volleybolley.games.domain.model.entity.PlayerShort

data class JoinedGame(
    val gameId: Int,
    val gameType: String,
    val isJoined: Boolean,
    val isPrivate: Boolean,
    val host: Host,
    val message: String,
    val courtLocation: Location,
    val startTime: String,
    val endTime: String,
    val gender: String,
    val levels: List<String>,
    val pricePerPerson: String,
    val maximumPlayers: Int,
    val paymentType: PaymentType,
    val paymentAccount: String,
    val currencyType: String,
    val players: List<PlayerShort>,
)
