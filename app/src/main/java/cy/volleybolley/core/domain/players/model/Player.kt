package cy.volleybolley.core.domain.players.model

data class Player(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String?,
    val isFavorite: Boolean,
    val level: String
)

