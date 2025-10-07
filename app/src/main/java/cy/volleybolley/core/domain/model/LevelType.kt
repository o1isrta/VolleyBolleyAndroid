package cy.volleybolley.core.domain.model

enum class LevelType(val level: String) {
    UNCONFINED(""),
    LIGHT("Light"),
    MEDIUM("Medium"),
    HARD("Hard"),
    PRO("Pro");

    companion object {
        @JvmStatic
        fun findByLevelName(level: String?): LevelType {
            return entries.find { it.level == level } ?: UNCONFINED
        }
    }
}
