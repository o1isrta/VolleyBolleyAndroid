package cy.volleybolley.core.presentation.ui.screens.home.rateplayers

data class RatePlayersState(
    val isLoading: Boolean = true,
    val players: List<PlayerShortUI> = emptyList(),
)

data class RatePlayer(
    val playerId: Int,
    val levelChanged: RatingType,
)

object RatingIds {
    const val UP = 1
    const val CONFIRM = 2
    const val DOWN = 3
}

enum class RatingType(val checkId: Int) {
    UP(RatingIds.UP),
    CONFIRM(RatingIds.CONFIRM),
    DOWN(RatingIds.DOWN)
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

