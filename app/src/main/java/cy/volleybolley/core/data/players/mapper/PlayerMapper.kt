package cy.volleybolley.core.data.players.mapper

import cy.volleybolley.core.data.network.model.*
import cy.volleybolley.core.domain.players.model.*

fun PlayerDto.toDomain(): Player {
    return Player(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatarUrl = avatarUrl,
        isFavorite = isFavorite,
        level = level
    )
}

fun PlayerDtoDetail.toDomain(): PlayerDetail {
    return PlayerDetail(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatarUrl = avatarUrl,
        isFavorite = isFavorite,
        level = level,
        latestActivity = latestActivity.map { it.toDomain() }
    )
}

fun ActivityDto.toDomain(): Activity {
    return Activity(
        eventTimestamp = eventTimestamp,
        courtLocation = courtLocation.toDomain()
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
