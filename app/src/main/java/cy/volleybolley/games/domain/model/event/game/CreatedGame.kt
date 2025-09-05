package cy.volleybolley.games.domain.model.event.game

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
    val paymentType: String,
    val paymentAccount: String?,
    val currencyType: String,
    val players: List<Int>,
)

