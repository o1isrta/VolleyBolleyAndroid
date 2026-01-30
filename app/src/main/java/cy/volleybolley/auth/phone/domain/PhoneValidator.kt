package cy.volleybolley.auth.phone.domain

object PhoneValidator {
    private const val RUS = "7"
    private const val RUS_PHONE_LENGTH = 10 + RUS.length
    private const val CYP = "357"
    private const val CYP_PHONE_LENGTH_MIN = 8 + CYP.length
    private const val CYP_PHONE_LENGTH_MAX = 11 + CYP.length
    private const val THA = "66"
    private const val THA_PHONE_LENGTH_MIN = 8 + CYP.length
    private const val THA_PHONE_LENGTH_MAX = 9 + CYP.length
    private const val MIN_WORLD_PHONE_LENGTH = 5
    private const val MAX_WORLD_PHONE_LENGTH = 23

    @JvmStatic
    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return when {
            phoneNumber.startsWith(RUS) -> {
                phoneNumber.length == RUS_PHONE_LENGTH
            }
            phoneNumber.startsWith(CYP) -> {
                phoneNumber.length in CYP_PHONE_LENGTH_MIN..CYP_PHONE_LENGTH_MAX
            }
            phoneNumber.startsWith(THA) -> {
                phoneNumber.length in THA_PHONE_LENGTH_MIN..THA_PHONE_LENGTH_MAX
            }
            else -> phoneNumber.length in MIN_WORLD_PHONE_LENGTH..MAX_WORLD_PHONE_LENGTH
        }
    }
}
