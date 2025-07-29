package cy.volleybolley.games.domain.model

data class Game(
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
    val currencyType: String,
    val players: List<Int>,
)
