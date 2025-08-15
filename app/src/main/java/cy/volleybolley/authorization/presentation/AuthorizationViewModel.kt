package cy.volleybolley.authorization.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cy.volleybolley.authorization.domain.api.AuthorizationUseCase
import cy.volleybolley.authorization.domain.model.AuthorizationResult
import cy.volleybolley.authorization.domain.model.AuthorizationType
import cy.volleybolley.authorization.domain.model.Player
import cy.volleybolley.core.TokensManager
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.domain.model.onFailure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorizationViewModel (
    val useCase: AuthorizationUseCase
): ViewModel() {

    fun authorization(
        authType: AuthorizationType,
        idToken: String?
    ) {
        idToken?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val result = useCase.authorization(authType, idToken)
                if(result is VolleyResult.Success) {
                    savingTokens(result.data)
                    savingUserData(result.data.player)
                } else {
                    result.onFailure { errorType ->
                        errorHandling(errorType)
                    }
                }
            }
        }
    }

    fun registration(
        accessToken: String,
        registrationData: Player
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase.registration(accessToken, registrationData)
            if(result is VolleyResult.Success) {
                savingUserData(registrationData)
            } else {
                result.onFailure { errorType ->
                    errorHandling(errorType)
                }
            }
        }
    }

    fun logOut() = useCase.logOut()

    private fun savingTokens(data: AuthorizationResult) {
        TokensManager.accessToken = data.accessToken
        TokensManager.refreshToken = data.refreshToken
    }

    private fun savingUserData(user: Player) {
        // Сохранение данных пользователя из user
    }

    private fun errorHandling(errorType: ErrorType) {
        when (errorType) {
            ErrorType.NO_CONNECTION -> Log.e("REQUEST ERRORS", "Нет сети")
            ErrorType.NOT_FOUND -> Log.e("REQUEST ERRORS", "Страница не найдена")
            ErrorType.BAD_REQUEST -> Log.e("REQUEST ERRORS", "Плохой запрос")
            ErrorType.SERVER_ERROR -> Log.e("REQUEST ERRORS", "Ошибка сервера")
            ErrorType.UNKNOWN_ERROR -> Log.e("REQUEST ERRORS", "Неизвестная ошибка")
        }
    }
}
