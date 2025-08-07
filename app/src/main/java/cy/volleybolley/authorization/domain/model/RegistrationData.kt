package cy.volleybolley.authorization.domain.model

data class RegistrationData(
    val firstName: String,
    val lastName: String,
    val gender: String,
    val birthDate: String,
    val level: String,
    val countryId: Int,
    val cityId: Int
)
