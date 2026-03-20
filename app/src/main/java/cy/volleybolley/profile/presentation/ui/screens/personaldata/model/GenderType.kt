package cy.volleybolley.profile.presentation.ui.screens.personaldata.model

enum class GenderType(
    val id: Int,
    val nameValue: String,
    val displayText: String
) {
    MALE(1, "MALE", "Male"),
    FEMALE(2, "FEMALE", "Female"),
    UNKNOWN(0, "", "");

    companion object {
        @JvmStatic
        fun getIdByStringValue(value: String): Int = entries.find { it.nameValue == value }?.id ?: UNKNOWN.id

        @JvmStatic
        fun getNameValueById(inputId: Int): String = entries.find { it.id == inputId }?.nameValue ?: UNKNOWN.nameValue

        @JvmStatic
        fun getById(inputId: Int): GenderType = entries.find { it.id == inputId } ?: UNKNOWN
    }
}
