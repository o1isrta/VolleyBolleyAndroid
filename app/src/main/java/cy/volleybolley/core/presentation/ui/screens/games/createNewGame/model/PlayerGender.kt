package cy.volleybolley.core.presentation.ui.screens.games.createNewGame.model

enum class PlayerGender(val displayText: String) {
    Male("Male"),
    Female("Female");

    fun toDomainString(): String {
        return when (this) {
            Male -> "Male"
            Female -> "Female"
        }
    }

    fun toApiString(): String {
        return when (this) {
            Male -> "MALE"
            Female -> "FEMALE"
        }
    }

    companion object {
        fun fromDomainString(gender: String): PlayerGender {
            return when (gender.uppercase()) {
                "MALE" -> Male
                "FEMALE" -> Female
                else -> Male
            }
        }

        fun fromApiString(gender: String): PlayerGender {
            return when (gender.uppercase()) {
                "MALE" -> Male
                "FEMALE" -> Female
                else -> Male
            }
        }
    }
}
