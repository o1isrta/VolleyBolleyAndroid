package cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen

import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlin.random.Random

class GameEnteringConditionsScreenViewModel :
    BaseViewModel<GameEnteringConditionsScreenState, GameEnteringConditionsScreenEvent, GameEnteringConditionsScreenEffect>(
        GameEnteringConditionsScreenState()
    ) {
    override val tag: String = "GameEnteringConditionsScreenViewModel"

    init {
        // проверяем, есть  ли аккаунт
        obtainEvent(GameEnteringConditionsScreenEvent.CheckIfAccountExists)
    }

    override fun obtainEvent(event: GameEnteringConditionsScreenEvent) {
        when (event) {
            is GameEnteringConditionsScreenEvent.PrivacySelected -> {
                // Используем setState для обновления uiState
               /* setState { currentState ->
                    currentState.copy(selectedPrivacy = event.privacy)
                }*/
                uiStateMutable.value = uiStateMutable.value.copy(selectedPrivacy = event.privacy)
            }
            is GameEnteringConditionsScreenEvent.PerPersonChanged -> {
               /* setState { currentState ->
                    currentState.copy(perPerson = event.perPerson)
                }*/
                uiStateMutable.value = uiStateMutable.value.copy(perPerson = event.perPerson)
            }
            GameEnteringConditionsScreenEvent.CheckIfAccountExists -> {
                checkIfAccountExists()
            }
            is GameEnteringConditionsScreenEvent.MaximumPlayersChanged -> {
                /*setState { currentState ->
                    currentState.copy(maximumPlayers = event.maximumPersons)
                }*/
                uiStateMutable.value = uiStateMutable.value.copy(maximumPlayers = event.maximumPersons)
            }
            GameEnteringConditionsScreenEvent.OnBackClicked -> {
                sendUiEffect(GameEnteringConditionsScreenEffect.NavigateBack)
            }
            GameEnteringConditionsScreenEvent.OnAddPaymentClick -> {
                sendUiEffect(GameEnteringConditionsScreenEffect.NavigateToPayments)
            }
            GameEnteringConditionsScreenEvent.OnSaveGameClick -> {
                saveGame()
            }
        }
    }

    private fun saveGame() {
        // какая-то логика по сохранению настроек ?

        // и переход на экран Success
        sendUiEffect(GameEnteringConditionsScreenEffect.NavigateToSuccess)
    }

    private fun checkIfAccountExists() { // если accountNumber != Null, аккааунт существует
        launchSafe(
            dispatcher = Dispatchers.IO,
            getErrorLogMessage = { "Error checking account existence: ${it.message ?: "Unknown error"}" },
            onError = { er -> sendUiEffect(GameEnteringConditionsScreenEffect.ShowError(er.message ?: "Failed to check account"))
            }
        ){
            val accountNumber = getAccountNumber() // Получение номера счета (аккаунта), если он есть
            /*setState { currentState ->  currentState.copy( accountNumber = accountNumber ) }*/
            uiStateMutable.value = uiStateMutable.value.copy( accountNumber = accountNumber )
        }
    }

    private fun getAccountNumber(): String? {
        return if (Random.nextBoolean()) "123 45 6789" else null // для теста, заменить на получение номера из профиля
    }
}
