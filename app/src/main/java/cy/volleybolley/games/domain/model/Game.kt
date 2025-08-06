package cy.volleybolley.games.domain.model

data class Game(
    val gameId: Int? = null, // при создании
    val courtId: Int,
    val message: String,
    val startTime: String,
    val endTime: String,
    val gender: String,
    val levels: List<String>,
    val isPrivate: Boolean,
    val maximumPlayers: Int,
    val price: String,
    val paymentType: String,
    val paymentAccount: String? = null, // при создании
    val currencyType: String? = null, // при создании
    val players: List<Int>,
)
