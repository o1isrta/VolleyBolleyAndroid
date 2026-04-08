package cy.volleybolley.core.presentation.ui.screens.games.createNewGame.model

enum class GameGender(val displayText: String) {
    Mix("Mix"),
    Men("Men"),
    Women("Women");

    fun toApiString(): String {
        return when (this) {
            Mix -> "MIX"
            Men -> "MEN"
            Women -> "WOMEN"
        }
    }
    companion object {
        fun fromApiString(gender: String): GameGender {
            return when (gender.uppercase()) {
                "MEN" -> Men
                "WOMEN" -> Women
                "MIX" -> Mix
                else -> Mix
            }
        }
    }
}
