package cy.volleybolley.rateplayers

import cy.volleybolley.core.presentation.base.UiState

data class RatePlayersState(
    val isLoading: Boolean = true,
    val players: List<PlayerShortUI> = emptyList(),
) : UiState

data class RatePlayer(
    val playerId: Int,
    val levelChanged: RatingType,
)

object RatingIds {
    const val UP_LEVEL = 1
    const val CONFIRM_LEVEL = 2
    const val DOWN_LEVEL = 3
}

enum class RatingType(val checkId: Int) {
    UP(RatingIds.UP_LEVEL),
    CONFIRM(RatingIds.CONFIRM_LEVEL),
    DOWN(RatingIds.DOWN_LEVEL)
}

data class PlayerShortUI(
    val playerId: Int,
    val name: String,
    val level: LevelType,
    val avatar: String?,
    val rating: RatingType = RatingType.CONFIRM
)

enum class LevelType(val level: String) {
    UNCONFINED(""),
    LIGHT("Light"),
    MEDIUM("Medium"),
    HARD("Hard"),
    PRO("Pro")
}

