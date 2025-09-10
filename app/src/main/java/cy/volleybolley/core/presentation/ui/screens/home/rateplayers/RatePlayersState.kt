package cy.volleybolley.core.presentation.ui.screens.home.rateplayers

data class RatePlayersState (
    val isLoading: Boolean = true,
    val players: List<PlayerShortUI> = emptyList(),
    val error: String? = null
)

data class RatePlayer(
    val playerId: Int,
    val levelChanged: RatingType,
)

enum class RatingType {
    UP,
    CONFIRM,
    DOWN,
}

data class PlayerShortUI(
    val playerId: Int,
    val name: String,
    val level: LevelType,
    val avatar: String,
    val rating: RatingType = RatingType.CONFIRM
)

enum class LevelType(val level: String) {
    UNCONFINED(""),
    LIGHT("Light"),
    MEDIUM("Medium"),
    HARD("Hard"),
    PRO("Pro")
}
