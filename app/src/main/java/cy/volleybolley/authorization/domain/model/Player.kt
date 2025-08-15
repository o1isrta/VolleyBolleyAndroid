package cy.volleybolley.authorization.domain.model

data class Player(
    val playerId: Int,
    val avatar: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val gender: String? = null,
    val dateOfBirth: String? = null,
    val level: String? = null,
    val countryId: Int? = null,
    val cityId: Int? = null
)
