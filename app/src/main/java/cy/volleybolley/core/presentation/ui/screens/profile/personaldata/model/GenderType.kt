package cy.volleybolley.core.presentation.ui.screens.profile.personaldata.model

enum class GenderType(
    val id: Int,
    val nameValue: String,
) {
    MALE(1, "MALE"),
    FEMALE(2, "FEMALE"),
    UNKNOWN(0, "");

    companion object {
        @JvmStatic
        fun getIdByStringValue(value: String): Int = entries.find { it.nameValue == value }?.id ?: UNKNOWN.id

        @JvmStatic
        fun getNameValueById(inputId: Int): String = entries.find { it.id == inputId }?.nameValue ?: UNKNOWN.nameValue
    }
}
