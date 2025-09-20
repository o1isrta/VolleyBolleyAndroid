package cy.volleybolley.core.presentation.ui.component.model

import cy.volleybolley.R

enum class LevelIcon(val resId: Int) {
    LIGHT(R.drawable.ic_level_light),
    MEDIUM(R.drawable.ic_level_medium),
    HARD(R.drawable.ic_level_hard),
    PRO(R.drawable.ic_level_pro);

    companion object {
        @JvmStatic
        fun fromLevelName(levelName: String): LevelIcon {
            val name = levelName
            return when {
                name.contains("LIGHT", ignoreCase = true) -> LIGHT
                name.contains("MEDIUM", ignoreCase = true) -> MEDIUM
                name.contains("HARD", ignoreCase = true) -> HARD
                name.contains("PRO", ignoreCase = true) -> PRO
                else -> LIGHT
            }
        }
    }
}
