package cy.volleybolley.games.domain.model.entity

import cy.volleybolley.core.domain.model.LevelType

data class Host(
    val id: Int,
    val name: String,
    val avatar: String?,
    val level: LevelType,
)
