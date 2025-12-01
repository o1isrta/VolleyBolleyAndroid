package cy.volleybolley.games.domain.model.event.game

import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.games.domain.model.entity.Host
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.profile.domain.model.PaymentType
import cy.volleybolley.referencedata.domain.model.CurrencyType

data class GameDetails(
    val gameId: Int,
    val host: Host,
    val message: String,
    val isPrivate: Boolean,
    val courtLocation: Location,
    val startTime: String,
    val endTime: String,
    val gender: String,
    val levels: List<String>,
    val pricePerPerson: String,
    val maximumPlayers: Int,
    val paymentType: PaymentType,
    val paymentAccount: String,
    val currencyType: CurrencyType,
    val players: List<PlayerShort>,
)
