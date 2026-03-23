package cy.volleybolley.core.presentation.ui.screens.home.rateplayers

import cy.volleybolley.core.domain.model.LevelType
import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.games.domain.model.entity.RatingType

data class RatePlayersState(
    val isLoading: Boolean = true,
    val players: List<PlayerShortUI> = emptyList(),
) : UiState

data class RatePlayer(
    val playerId: Int,
    val levelChanged: RatingType,
)

data class PlayerShortUI(
    val playerId: Int,
    val name: String,
    val level: LevelType,
    val avatar: String?,
    val rating: RatingType = RatingType.CONFIRM
)
