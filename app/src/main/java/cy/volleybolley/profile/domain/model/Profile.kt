package cy.volleybolley.profile.domain.model

class Profile(
    val personalData: PersonalData,
    val payments: List<Payment>,
    val avatar: String,
)

data class PersonalData(
    val firstName: String,
    val lastName: String,
    val gender: String,
    val birthDate: String,
    val level: String,
    val country: String,
    val city: String,
)

data class Payment(
    val type: String,
    val account: String,
    val isPreferred: Boolean,
)
