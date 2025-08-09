package cy.volleybolley.games.domain.model

data class Tournament (
    val tournamentId: Int? = null, // при создании
    val courtId: Int,
    val message: String,
    val startTime: String,
    val endTime: String,
    val gender: String,
    val levels: List<String>,
    val isIndividual: Boolean,
    val maximumPlayers: Int,
    val maximumTeams: Int,
    val pricePerPerson: String,
    val paymentType: String,
    val paymentAccount: String? = null, // при создании
    val currencyType: String? = null, // при создании
    val teams: List<Team>,
)
