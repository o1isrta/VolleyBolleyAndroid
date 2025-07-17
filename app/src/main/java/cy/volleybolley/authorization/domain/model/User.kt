package cy.volleybolley.authorization.domain.model

data class User(
    val userId: Int,
    val isRegistered: Boolean,
    val avatar: String?,
    val firstName: String?,
    val lastName: String?,
    val genderId: Int?,
    val dateOfBirth: String?,
    val levelId: Int?,
    val countryId: Int?,
    val cityId: Int?
)
