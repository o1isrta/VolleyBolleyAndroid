package cy.volleybolley.courts.presentation.model

import cy.volleybolley.courts.domain.model.Contact
import cy.volleybolley.courts.domain.model.Location

data class CourtUi(
    val courtId: Int,
    val price: String,
    val description: String,
    val contacts: List<Contact>,
    val photo: String,
    val tags: List<String>,
    val location: Location,
    val distanceText: String = "-- km"
)
