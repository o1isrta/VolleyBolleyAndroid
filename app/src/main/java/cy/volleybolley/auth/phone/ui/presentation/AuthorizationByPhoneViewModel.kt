package cy.volleybolley.auth.phone.ui.presentation

import androidx.lifecycle.viewModelScope
import cy.volleybolley.auth.domain.api.usecase.SaveAccessTokenUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveIsRegisteredUseCase
import cy.volleybolley.auth.domain.api.usecase.SavePersonalDataUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveRefreshTokenTimestampUseCase
import cy.volleybolley.auth.domain.api.usecase.SaveRefreshTokenUseCase
import cy.volleybolley.auth.domain.models.LoginData
import cy.volleybolley.auth.phone.domain.PhoneTokenAuthUseCase
import cy.volleybolley.auth.phone.domain.PhoneValidator
import cy.volleybolley.auth.phone.domain.ResendCodeToken
import cy.volleybolley.auth.phone.ui.presentation.model.AuthorizationByPhoneEffect
import cy.volleybolley.auth.phone.ui.presentation.model.AuthorizationByPhoneEvent
import cy.volleybolley.auth.phone.ui.presentation.model.AuthorizationByPhoneState
import cy.volleybolley.core.domain.model.onFailure
import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException

class AuthorizationByPhoneViewModel(
    private val phoneTokenAuthUseCase: PhoneTokenAuthUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    private val saveRefreshTokenTimestampUseCase: SaveRefreshTokenTimestampUseCase,
    private val savePersonalDataUseCase: SavePersonalDataUseCase,
    private val saveIsRegisteredUseCase: SaveIsRegisteredUseCase
) :
    BaseViewModel<AuthorizationByPhoneState, AuthorizationByPhoneEvent, AuthorizationByPhoneEffect>(
        initialState = AuthorizationByPhoneState()
    ) {
    private companion object {
        const val RESEND_TIMEOUT_SECONDS = 30
    }

    private var resendTimerJob: Job? = null

    override val tag = AuthorizationByPhoneViewModel::class.simpleName ?: ""

    override fun obtainEvent(event: AuthorizationByPhoneEvent) {
        when (event) {

            is AuthorizationByPhoneEvent.TypePhone -> {
                val isValid = PhoneValidator.isValidPhoneNumber(event.phone)
                uiStateMutable.update {
                    it.copy(
                        phoneNumber = event.phone,
                        isPhoneNumberValid = isValid,
                        isPhoneNumberInputError = event.phone.isNotEmpty() && !isValid
                    )
                }
            }

            is AuthorizationByPhoneEvent.TypeCode -> {
                uiStateMutable.update {
                    it.copy(
                        code = event.code,
                        isCodeInputError = false
                    )
                }
            }

            AuthorizationByPhoneEvent.SendCodeClicked -> {
                sendCode()
            }

            AuthorizationByPhoneEvent.ResendCodeClicked -> {
                resendCode()
            }

            AuthorizationByPhoneEvent.VerifyCodeClicked -> {
                verifyCode()
            }
        }
    }

    private fun normalizePhone(phone: String): String {
        return if (phone.startsWith("+")) {
            phone
        } else {
            "+$phone"
        }
    }

    fun sendCode() {
        val state = uiState.value
        if (!state.isPhoneNumberValid) return

        uiStateMutable.update { it.copy(isLoading = true) }

        uiEffectMutable.trySend(
            AuthorizationByPhoneEffect.RequestSendCode(
                phone = normalizePhone(state.phoneNumber),
                resendToken = null
            )
        )
    }

    private fun verifyCode() {
        val state = uiState.value
        val verificationId = state.verificationId ?: return

        if (state.code.isBlank()) {
            return
        }

        if (state.code.length < 6) {
            uiStateMutable.update { it.copy(isCodeInputError = true) }
            return
        }

        uiStateMutable.update { it.copy(isLoading = true) }

        uiEffectMutable.trySend(
            AuthorizationByPhoneEffect.RequestVerifyCode(
                verificationId = verificationId,
                code = state.code
            )
        )
    }

    private fun resendCode() {
        val state = uiState.value
        val token = state.resendToken ?: return

        uiStateMutable.update {
            it.copy(
                isResendEnabled = false,
                remainingResendTime = RESEND_TIMEOUT_SECONDS
            )
        }

        startResendTimer()

        uiEffectMutable.trySend(
            AuthorizationByPhoneEffect.RequestSendCode(
                phone = state.phoneNumber,
                resendToken = token
            )
        )
    }

    private suspend fun saveLoginData(loginData: LoginData) {
        saveRefreshTokenUseCase.execute(loginData.refreshToken)
        saveAccessTokenUseCase.execute(loginData.accessToken)
        saveRefreshTokenTimestampUseCase.execute(System.currentTimeMillis())
        savePersonalDataUseCase.execute(loginData.userPersonalData)
        saveIsRegisteredUseCase.execute(loginData.isRegistered)
    }

    private fun startResendTimer() {
        resendTimerJob?.cancel()

        resendTimerJob = viewModelScope.launch {
            try {
                for (seconds in RESEND_TIMEOUT_SECONDS downTo 1) {
                    uiStateMutable.update {
                        it.copy(remainingResendTime = seconds)
                    }
                    delay(1000)
                }

                uiStateMutable.update {
                    it.copy(
                        remainingResendTime = 0,
                        isResendEnabled = true,
                    )
                }

            } catch (e: CancellationException) {

            } catch (t: Throwable) {
                uiStateMutable.update {
                    it.copy(
                        remainingResendTime = 0,
                        isResendEnabled = true,
                    )
                }
            }
        }
    }

    fun onCodeSent(
        verificationId: String,
        resendToken: ResendCodeToken
    ) {
        uiStateMutable.update {
            it.copy(
                isLoading = false,
                verificationId = verificationId,
                resendToken = resendToken,
                step = AuthorizationByPhoneState.Step.VERIFY_CODE,
                isResendVisible = true,
                isResendEnabled = false,
                remainingResendTime = RESEND_TIMEOUT_SECONDS,
                isCodeInputError = false
            )
        }
        startResendTimer()
    }

    fun onAuthorized(idToken: String) {
        launchSafe(
            block = {
                uiStateMutable.update { it.copy(isLoading = true) }
                val result = phoneTokenAuthUseCase.loginWithPhone(idToken)
                uiStateMutable.update { it.copy(isLoading = false) }

                result
                    .onSuccess { loginData ->
                        saveLoginData(loginData)

                        if (loginData.isRegistered) {
                            uiEffectMutable.trySend(AuthorizationByPhoneEffect.NavigateHome)
                        } else {
                            val userJson = Json.encodeToString(loginData.userPersonalData)
                            uiEffectMutable.trySend(AuthorizationByPhoneEffect.NavigateToRegistration(userJson))
                        }
                    }
                    .onFailure {
                        uiEffectMutable.trySend(AuthorizationByPhoneEffect.ShowError("Authorization error"))
                    }
            },
            onError = {
                uiStateMutable.update { it.copy(isLoading = false) }
                uiEffectMutable.trySend(AuthorizationByPhoneEffect.ShowError("Unexpected error during authorization"))
            },
            getErrorLogMessage = { "PhoneTokenReceived: unexpected error -> ${it.message}" }
        )
    }

    fun onError() {
        uiStateMutable.update {
            it.copy(
                isLoading = false,
                isCodeInputError = true
            )
        }
    }
}
