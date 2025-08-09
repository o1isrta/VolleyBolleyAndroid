package cy.volleybolley.games.domain.model

data class PlayerShort(
    val playerId: Int,
    val name: String? = null,
    val level: String? = null,
    val avatar: String? = null,
)
