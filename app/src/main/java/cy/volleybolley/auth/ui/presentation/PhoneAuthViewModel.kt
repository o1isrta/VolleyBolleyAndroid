package cy.volleybolley.auth.ui.presentation

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.PhoneAuthProvider
import cy.volleybolley.auth.ui.PhoneAuthHelper
import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PhoneAuthViewModel(private val phoneAuthHelper: PhoneAuthHelper) :
    BaseViewModel<PhoneAuthState, PhoneAuthEvent, PhoneAuthEffect>(initialState = PhoneAuthState()) {

    override val tag: String
        get() = "PhoneAuthVM"

    private var resendJob: Job? = null

    override fun obtainEvent(event: PhoneAuthEvent) {
        when (event) {
            is PhoneAuthEvent.SendCode -> sendCode(event.phone)
            is PhoneAuthEvent.ResendCode -> resendCode()
            is PhoneAuthEvent.VerifyCode -> verifyCode(event.code)
        }
    }

    private fun sendCode(phone: String) {
        if (phone.isBlank()) {
            sendUiEffect(PhoneAuthEffect.ShowError("Введите номер телефона"))
            return
        }
        updateState { copy(phoneNumber = phone, isLoading = true) }
        sendUiEffect(PhoneAuthEffect.RequestPhoneVerification(phone))
    }

    private fun resendCode() {
        val phone = uiState.value.phoneNumber
        val token = uiState.value.resendToken
        if (phone.isBlank() || token == null) {
            sendUiEffect(PhoneAuthEffect.ShowError("Невозможно повторно отправить код"))
            return
        }
        updateState { copy(isLoading = true) }
        sendUiEffect(PhoneAuthEffect.RequestPhoneVerification(phone, token))
    }

    private fun verifyCode(code: String) {
        val verificationId = uiState.value.verificationId
        if (verificationId == null) {
            sendUiEffect(PhoneAuthEffect.ShowError("Сначала получите код"))
            return
        }
        updateState { copy(isLoading = true, code = code) }
        phoneAuthHelper.verifyCode(
            code,
            verificationId,
            onIdTokenReceived = { idToken ->
                updateState { copy(isLoading = false, isAuthorized = true) }
                idToken?.let { sendUiEffect(PhoneAuthEffect.NavigateToHome(it)) }
            },
            onError = { e ->
                updateState { copy(isLoading = false) }
                sendUiEffect(PhoneAuthEffect.ShowError(e.message ?: "Ошибка"))
            }
        )
    }

    fun onCodeSent(
        verificationId: String,
        token: PhoneAuthProvider.ForceResendingToken
    ) {
        updateState {
            copy(
                verificationId = verificationId,
                resendToken = token,
                isLoading = false,
                isCodeSent = true
            )
        }
        startResendTimer()
    }

    fun onIdTokenReceived(token: String?) {
        updateState { copy(isLoading = false, isAuthorized = true) }
        token?.let { sendUiEffect(PhoneAuthEffect.NavigateToHome(it)) }
    }

    fun onError(e: Throwable) {
        updateState { copy(isLoading = false) }
        sendUiEffect(PhoneAuthEffect.ShowError(e.message ?: "Ошибка"))
    }

    private fun startResendTimer() {
        resendJob?.cancel()
        resendJob = viewModelScope.launch {
            for (seconds in 30 downTo 1) {
                updateState { copy(resendTimeout = seconds) }
                delay(1000L)
            }
            updateState { copy(resendTimeout = 0) }
        }
    }
}
