package cy.volleybolley.players.domain.model

data class Player(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String?,
    val isFavorite: Boolean,
    val level: String,
    val gender: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Player
        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}
