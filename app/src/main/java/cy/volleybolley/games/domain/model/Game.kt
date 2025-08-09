package cy.volleybolley.games.domain.model

import cy.volleybolley.courts.domain.model.Location

data class Game(
    val gameId: Int? = null, // при создании
    val courtId: Int? = null,
    val gameType: String? = null, // при создании
    val host: Host? = null, // при создании
    val message: String,
    val courtLocation: Location? = null, // при создании
    val startTime: String,
    val endTime: String,
    val gender: String,
    val levels: List<String>,
    val isPrivate: Boolean = false,
    val maximumPlayers: Int,
    val pricePerPerson: String,
    val paymentType: String,
    val paymentAccount: String? = null, // при создании
    val currencyType: String? = null, // при создании
    val players: List<PlayerShort>,
)

