package cy.volleybolley.players.data.mapper

import cy.volleybolley.courts.data.dto.toDomain
import cy.volleybolley.players.data.dto.ActivityDto
import cy.volleybolley.players.data.dto.PlayerDto
import cy.volleybolley.players.data.dto.PlayerDtoDetail
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.model.PlayerActivity
import cy.volleybolley.players.domain.model.PlayerDetail

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

fun ActivityDto.toDomain(): PlayerActivity {
    return PlayerActivity(
        eventTimestamp = eventTimestamp,
        courtLocation = courtLocation.toDomain()
    )
}
