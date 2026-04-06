package cy.volleybolley.core.presentation.ui.screens.createNewGame.model

import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.players.domain.model.Player

object PlayerMapper {
    fun toUi(domain: Player): PlayerUi {
        return PlayerUi(
            id = domain.id,
            firstName = domain.firstName,
            lastName = domain.lastName,
            avatarUrl = domain.avatarUrl,
            isFavorite = domain.isFavorite,
            level = Level.fromDomainString(domain.level),
            gender = Gender.fromDomainString(domain.gender)
        )
    }

    fun toDomain(ui: PlayerUi): Player {
        return Player(
            id = ui.id,
            firstName = ui.firstName,
            lastName = ui.lastName,
            avatarUrl = ui.avatarUrl,
            isFavorite = ui.isFavorite,
            gender = ui.gender.toDomainString(),
            level = ui.level.toDomainString()
        )
    }
}
