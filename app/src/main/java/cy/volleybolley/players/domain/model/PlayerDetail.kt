package cy.volleybolley.players.domain.model

data class PlayerDetail(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String?,
    val isFavorite: Boolean,
    val level: String,
    val gender: String,
    val latestActivity: List<PlayerActivity>
)
