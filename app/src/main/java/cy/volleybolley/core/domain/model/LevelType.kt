package cy.volleybolley.core.domain.model

@Suppress("MagicNumber")
enum class LevelType(
    val id: Int,
    val level: String
) {
    UNCONFINED(0, "UNCONFINED"),
    LIGHT(1, "LIGHT"),
    MEDIUM(2, "MEDIUM"),
    HARD(3, "HARD"),
    PRO(4, "PRO");

    companion object {
        @JvmStatic
        fun findByLevelName(level: String?): LevelType {
            return entries.find { it.level == level } ?: UNCONFINED
        }

        @JvmStatic
        fun getIdByLevelName(level: String): Int {
            return entries.find { it.level == level }?.id ?: UNCONFINED.id
        }

        @JvmStatic
        fun getLevelNameById(inputId: Int): String {
            return entries.find { it.id == inputId }?.level ?: UNCONFINED.level
        }
    }
}
