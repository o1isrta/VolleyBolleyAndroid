package cy.volleybolley.profile.domain.model

data class Profile(
    var personalData: PersonalData?,
    var payments: List<Payment>?,
)
