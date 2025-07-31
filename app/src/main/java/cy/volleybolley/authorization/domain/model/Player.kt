package cy.volleybolley.authorization.domain.model

data class Player(
    val playerId: Int,
    val avatar: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val gender: Int? = null,
    val dateOfBirth: String? = null,
    val level: Int? = null,
    val country: Int? = null,
    val city: Int? = null
)
