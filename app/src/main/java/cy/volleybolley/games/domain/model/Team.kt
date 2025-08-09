package cy.volleybolley.games.domain.model

data class Team(
    val teamId: Int? = null,
    val players: List<PlayerShort>,
)

