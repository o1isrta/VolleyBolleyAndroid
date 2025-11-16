package cy.volleybolley.games.domain.model.entity

data class Team(
    val teamId: Int,
    val players: List<PlayerShort>,
)
