package cy.volleybolley.games.domain.model.event.tournament

data class CreateTournament(
    val courtId: Int,
    val message: String,
    val startTime: String,
    val endTime: String,
    val isIndividual: Boolean,
    val gender: String,
    val levels: List<String>,
    val maximumPlayers: Int,
    val maximumTeams: Int,
    val pricePerPerson: String,
    val paymentType: String,
    val teams: List<List<Int>>,
)
