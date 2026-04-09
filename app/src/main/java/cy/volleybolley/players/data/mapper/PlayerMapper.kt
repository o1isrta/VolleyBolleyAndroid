package cy.volleybolley.players.data.mapper

import cy.volleybolley.core.presentation.ui.screens.createNewGame.createNewGameRepository.Gender
import cy.volleybolley.courts.data.dto.toDomain
import cy.volleybolley.players.data.dto.PlayerActivityDto
import cy.volleybolley.players.data.dto.PlayerDetailDto
import cy.volleybolley.players.data.dto.PlayerDto
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
        level = level,
        // не забыть заменить, когда api поправят
        gender = Gender.Men.displayText
    )
}

fun PlayerDetailDto.toDomain(): PlayerDetail {
    return PlayerDetail(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatarUrl = avatarUrl,
        isFavorite = isFavorite,
        level = level,
        // не забыть заменить, когда api поправят
        gender = Gender.Men.displayText,
        latestActivity = latestActivity.map { it.toDomain() }
    )
}

fun PlayerActivityDto.toDomain(): PlayerActivity {
    return PlayerActivity(
        eventTimestamp = eventTimestamp,
        courtLocation = courtLocation.toDomain()
    )
}
