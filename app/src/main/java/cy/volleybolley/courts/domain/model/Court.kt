package cy.volleybolley.courts.domain.model

data class Court(
    val courtId: Int,
    val price: String,
    val description: String,
    val contacts: List<Contact>,
    val photo: String,
    val tags: List<String>,
    val location: Location,
)
