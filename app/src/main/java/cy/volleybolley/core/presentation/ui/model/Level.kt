package cy.volleybolley.core.presentation.ui.model

enum class Level(val id: Int, val displayText: String) {
    Light(id = 1, displayText = "Light"),
    Medium(id = 2, displayText = "Medium"),
    Hard(id = 3, displayText = "Hard"),
    Pro(id = 4, displayText = "Pro");

    companion object {
        fun getById(id: Int): Level = entries.find { it.id == id } ?: Light

        fun fromApiString(level: String): Level {
            return when (level.uppercase()) {
                "LIGHT" -> Light
                "MEDIUM" -> Medium
                "HARD" -> Hard
                "PRO" -> Pro
                else -> Medium
            }
        }
        fun fromDomainString(level: String): Level {
            return when (level.uppercase()) {
                "L" -> Light
                "M" -> Medium
                "H" -> Hard
                "P" -> Pro
                else -> Medium
            }
        }
    }
    fun toApiString(): String {
        return when (this) {
            Light -> "LIGHT"
            Medium -> "MEDIUM"
            Hard -> "HARD"
            Pro -> "PRO"
        }
    }
    fun toDomainString(): String {
        return when (this) {
            Light -> "L"
            Medium -> "M"
            Hard -> "H"
            Pro -> "P"
        }
    }
}
