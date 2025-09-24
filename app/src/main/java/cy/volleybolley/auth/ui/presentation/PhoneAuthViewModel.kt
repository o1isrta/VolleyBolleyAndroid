package cy.volleybolley.auth.ui.presentation

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.PhoneAuthProvider
import cy.volleybolley.R
import cy.volleybolley.auth.ui.PhoneAuthHelper
import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PhoneAuthViewModel(private val phoneAuthHelper: PhoneAuthHelper) :
    BaseViewModel<PhoneAuthState, PhoneAuthEvent, PhoneAuthEffect>(initialState = PhoneAuthState()) {

    companion object {
        private const val TAG = "PhoneAuthViewModel"
    }

    override val tag: String
        get() = TAG

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
            sendUiEffect(PhoneAuthEffect.ShowError(R.string.your_phone_number))
            return
        }
        _uiState.update { it.copy(phoneNumber = phone, isLoading = true) }
        sendUiEffect(PhoneAuthEffect.RequestPhoneVerification(phone))
    }

    private fun resendCode() {
        val phone = uiState.value.phoneNumber
        val token = uiState.value.resendToken
        if (phone.isBlank() || token == null) {
            sendUiEffect(PhoneAuthEffect.ShowError(R.string.cant_resend))
            return
        }
        _uiState.update { it.copy(isLoading = true) }
        sendUiEffect(PhoneAuthEffect.RequestPhoneVerification(phone, token))
    }

    private fun verifyCode(code: String) {
        val verificationId = uiState.value.verificationId
        if (verificationId == null) {
            sendUiEffect(PhoneAuthEffect.ShowError(R.string.invalid_code))
            return
        }
        _uiState.update { it.copy(isLoading = true, code = code) }
        phoneAuthHelper.verifyCode(
            code,
            verificationId,
            onIdTokenReceived = { idToken ->
                _uiState.update { it.copy(isLoading = false, isAuthorized = true) }
                idToken?.let { sendUiEffect(PhoneAuthEffect.PhoneAuth(it)) }
            },
            onError = { e ->
                _uiState.update { it.copy(isLoading = false) }
                sendUiEffect(PhoneAuthEffect.ShowError(R.string.default_error))
            }
        )
    }

    fun onCodeSent(
        verificationId: String,
        token: PhoneAuthProvider.ForceResendingToken
    ) {
        _uiState.update {
            it.copy(
                verificationId = verificationId,
                resendToken = token,
                isLoading = false,
                isCodeSent = true
            )
        }
        startResendTimer()
    }

    fun onIdTokenReceived(token: String?) {
        _uiState.update { it.copy(isLoading = false, isAuthorized = true) }
        token?.let { sendUiEffect(PhoneAuthEffect.PhoneAuth(it)) }
    }

    fun onError(e: Throwable) {
        _uiState.update { it.copy(isLoading = false) }
        sendUiEffect(PhoneAuthEffect.ShowError(R.string.default_error))
    }

    fun onPhoneChanged(phone: String) {
        _uiState.update { it.copy(phoneNumber = phone) }
    }

    fun onCodeChanged(code: String) {
        _uiState.update { it.copy(code = code) }
    }

    private fun startResendTimer() {
        resendJob?.cancel()
        resendJob = viewModelScope.launch {
            for (seconds in 30 downTo 1) {
                _uiState.update { it.copy(resendTimeout = seconds) }
                delay(1000L)
            }
            _uiState.update { it.copy(resendTimeout = 0) }
        }
    }
}
