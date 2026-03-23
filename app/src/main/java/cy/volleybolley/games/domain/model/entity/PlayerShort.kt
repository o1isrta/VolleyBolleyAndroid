package cy.volleybolley.games.domain.model.entity

import cy.volleybolley.core.domain.model.LevelType

data class PlayerShort(
    val playerId: Int,
    val name: String,
    val level: LevelType,
    val avatar: String?,
)
