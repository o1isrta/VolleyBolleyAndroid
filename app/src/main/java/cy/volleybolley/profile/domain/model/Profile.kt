package cy.volleybolley.profile.domain.model

data class Profile(
    val personalData: PersonalData,
    val payments: List<Payment>,
)
