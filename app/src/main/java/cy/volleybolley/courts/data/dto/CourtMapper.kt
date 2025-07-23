package cy.volleybolley.courts.data.dto

import cy.volleybolley.courts.domain.model.Contact
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.domain.model.Location

fun List<CourtDto>.toDomain(): List<Court> {
    return this.map { it.toDomain() }
}

fun CourtDto.toDomain(): Court {
    return Court(
        courtId = this.courtId,
        price = this.price,
        description = this.description,
        contacts = this.contacts?.map { it.toDomain() },
        tags = this.tags,
        location = this.location.toDomain()
    )
}

fun ContactDto.toDomain(): Contact {
    return Contact(
        contactType = this.contactType,
        contact = this.contact
    )
}

fun LocationDto.toDomain(): Location {
    return Location(
        longitude = this.longitude,
        latitude = this.latitude,
        courtName = this.courtName,
        locationName = this.locationName
    )
}
