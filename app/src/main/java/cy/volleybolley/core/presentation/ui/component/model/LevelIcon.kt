package cy.volleybolley.core.presentation.ui.component.model

import cy.volleybolley.R

enum class LevelIcon(val resId: Int) {
    LIGHT(R.drawable.ic_level_light),
    MEDIUM(R.drawable.ic_level_medium),
    HARD(R.drawable.ic_level_hard),
    PRO(R.drawable.ic_level_pro);

    companion object {
        // Функция по умолчанию для определения иконки по строке levelName
        fun fromLevelName(levelName: String?): LevelIcon {
            val name = levelName.orEmpty()
            return when {
                name.contains("light", ignoreCase = true) -> LIGHT
                name.contains("medium", ignoreCase = true) -> MEDIUM
                name.contains("hard", ignoreCase = true) -> HARD
                name.contains("pro", ignoreCase = true) -> PRO
                else -> LIGHT
            }
        }
    }
}