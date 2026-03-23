package cy.volleybolley.auth.phone.domain

import cy.volleybolley.core.domain.model.VolleyResult

interface PhoneAuthService {
    suspend fun sendCode(
        phoneNumber: String,
        resendToken: ResendCodeToken? = null
    ): VolleyResult<CodeSentResult, PhoneAuthError>

    suspend fun verifyCode(verificationId: String, code: String): VolleyResult<String, PhoneAuthError>
}

data class CodeSentResult(
    val verificationId: String,
    val resendToken: ResendCodeToken
)

enum class PhoneAuthError {
    INVALID_PHONE_NUMBER,
    TOO_MANY_REQUESTS,
    SMS_QUOTA_EXCEEDED,
    INVALID_VERIFICATION_CODE,
    CODE_EXPIRED,
    NETWORK_ERROR,
    UNKNOWN
}
