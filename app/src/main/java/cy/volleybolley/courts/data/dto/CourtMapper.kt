package cy.volleybolley.courts.data.dto

import cy.volleybolley.courts.domain.model.Contact
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.domain.model.Location

fun List<CourtDto>.toDomain(): List<Court> {
    return this.map { it.toDomain() }
}

fun CourtDto.toDomain(): Court {
    return Court(
        courtId = courtId,
        price = price ?: "",
        description = description ?: "",
        contacts = contacts?.map { it.toDomain() } ?: emptyList(),
        photo = photo ?: "",
        tags = tags ?: emptyList(),
        location = location.toDomain()
    )
}

fun ContactDto.toDomain(): Contact {
    return Contact(
        contactType = contactType,
        contact = contact
    )
}

fun LocationDto.toDomain(): Location {
    return Location(
        longitude = longitude,
        latitude = latitude,
        courtName = courtName,
        locationName = locationName
    )
}
