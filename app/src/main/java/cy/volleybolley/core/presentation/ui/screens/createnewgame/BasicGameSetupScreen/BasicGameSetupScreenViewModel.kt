package cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen.GameEnteringConditionsScreenEffect
import cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen.GameEnteringConditionsScreenEvent
import cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen.Privacy
import kotlinx.coroutines.Dispatchers
import kotlin.random.Random

class BasicGameSetupScreenViewModel :
    BaseViewModel<BasicGameSetupScreenState, BasicGameSetupScreenEvent, BasicGameSetupScreenEffect>(
        BasicGameSetupScreenState()
    ) {
    override val tag: String = "BasicGameSetupScreenViewModel"

    init {
        // проверяем, есть  ли аккаунт
        //obtainEvent(BasicGameSetupScreenEvent.)
    }

    override fun obtainEvent(event: BasicGameSetupScreenEvent) {
      /*  when (event) {
            is GameEnteringConditionsScreenEvent.PrivacySelected -> {
                // Обработка выбора Public (Private через OpenPrivacyRequested)
                uiStateMutable.value = uiStateMutable.value.copy(selectedPrivacy = event.privacy)
            }
            is GameEnteringConditionsScreenEvent.OpenPrivacyOptions -> {
                val current = uiStateMutable.value
                val manageMode = current.selectedPrivacy == Privacy.Private
                // отправляем эффект навигации (manageMode = true, если уже был private выбран до нажатия)
                sendUiEffect(GameEnteringConditionsScreenEffect.NavigateToPrivacy(manageMode))
            }

            is GameEnteringConditionsScreenEvent.PlayersSelected -> {
                // Пользователь вернулся с Privacy screen, нажав нажал Add
                val current = uiStateMutable.value
                uiStateMutable.value = current.copy(
                    selectedPrivacy = Privacy.Private,
                    players = event.players
                )
            }
            is GameEnteringConditionsScreenEvent.PerPersonChanged -> {
                uiStateMutable.value = uiStateMutable.value.copy(perPerson = event.perPerson)
            }
            GameEnteringConditionsScreenEvent.CheckIfAccountExists -> {
                checkIfAccountExists()
            }
            is GameEnteringConditionsScreenEvent.MaximumPlayersChanged -> {
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
            is GameEnteringConditionsScreenEvent.RemovePlayer -> {
                val current = uiStateMutable.value
                if (event.index in current.players.indices) {
                    val newPlayers = current.players.toMutableList().apply { removeAt(event.index) }
                    uiStateMutable.value = current.copy(players = newPlayers)
                } else {
                    // опционально: логируем или показываем ошибку
                    uiStateMutable.value = current.copy(errorMessage = "Invalid player index: ${event.index}")
                }
            }
        }*/
    }

  /*  private fun saveGame() {
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
            uiStateMutable.value = uiStateMutable.value.copy( accountNumber = accountNumber )
        }
    }

    private fun getAccountNumber(): String? {
        return if (Random.nextBoolean()) "123 45 6789" else null // для теста, заменить на получение номера из профиля
    }*/
}
