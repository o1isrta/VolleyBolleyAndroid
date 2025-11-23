package cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen.BasicGameSetupScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.CreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.FakeCreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.GameData
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.Privacy
import cy.volleybolley.players.domain.model.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.random.Random

open class GameEnteringConditionsScreenViewModel (private val gameRepository: CreateNewGameRepository) :
    BaseViewModel<GameEnteringConditionsScreenState, GameEnteringConditionsScreenEvent, GameEnteringConditionsScreenEffect>(
        GameEnteringConditionsScreenState()
    ) {
    override val tag: String = "GameEnteringConditionsScreenViewModel"

    init {
        // проверяем, есть  ли аккаунт
        obtainEvent(GameEnteringConditionsScreenEvent.CheckIfAccountExists)
        // !!! Ключевой момент: Подписка на изменения GameData из репозитория !!!
        viewModelScope.launch {
            gameRepository.gameData
                .collectLatest { gameDataFromRepo ->
                    // Когда GameData в репозитории меняется, обновляем соответствующие части UI State
                    uiStateMutable.update { currentState ->
                        currentState.copy(
                            // Обновляем список игроков из репозитория
                            players = gameDataFromRepo.players,
                            // Обновляем остальные поля из GameData (если они нужны на этом экране)
                            maximumPlayers = gameDataFromRepo.maximumPlayers,
                            selectedPrivacy = gameDataFromRepo.selectedPrivacy,
                            perPerson = gameDataFromRepo.perPerson,
                            accountNumber = gameDataFromRepo.accountNumber // Это, возможно, будет приходить из другого источника или быть частью GameData
                        )
                    }
                }
        }
    }

    override fun obtainEvent(event: GameEnteringConditionsScreenEvent) {
        when (event) {
            is GameEnteringConditionsScreenEvent.OnPublicSelected -> {
                // Обработка выбора Public (Private через OpenPrivacyRequested)
              //  uiStateMutable.value = uiStateMutable.value.copy(selectedPrivacy = event.privacy)
                uiStateMutable.value = uiStateMutable.value.copy(selectedPrivacy = Privacy.Public)
            }
//            is GameEnteringConditionsScreenEvent.OnPrivateSelected -> {
//                val current = uiStateMutable.value
//                val manageMode = current.selectedPrivacy == Privacy.Private
//                // отправляем эффект навигации (manageMode = true, если уже был private выбран до нажатия)
//                sendUiEffect(GameEnteringConditionsScreenEffect.NavigateToPrivacy(manageMode))
//            }
            is GameEnteringConditionsScreenEvent.OnPrivateSelected -> {
                val current = uiStateMutable.value.selectedPrivacy
                // Если до этого было Public -> открыть Privacy screen (для первичного выбора)
                if (current == Privacy.Public) {
                    uiStateMutable.value = uiStateMutable.value.copy(selectedPrivacy = Privacy.Private)
                    //val manageMode = current.selectedPrivacy == Privacy.Private
                    // отправляем эффект навигации (manageMode = true, если уже был private выбран до нажатия)
                    sendUiEffect(GameEnteringConditionsScreenEffect.NavigateToPrivacy)//manageMode))
                }
                // Если уже Private — ничего не делать
            }
//            is GameEnteringConditionsScreenEvent.PlayersSelected -> {
//                // Пользователь вернулся с Privacy screen, нажав Add
//                val current = uiStateMutable.value
//                uiStateMutable.value = current.copy(
//                    selectedPrivacy = Privacy.Private,
//                    players = event.players
//                )
//            }
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
        }
    }

    private fun saveGame() {
        // какая-то логика по сохранению настроек ?
        uiStateMutable.update { it.copy(isLoading = true, errorMessage = null) } // Начинаем загрузку, очищаем предыдущие ошибки
        launchSafe(
            dispatcher = Dispatchers.IO,
            getErrorLogMessage = { "Error saving game: ${it.message ?: "Unknown error"}" },
            onError = { er ->
                uiStateMutable.update { it.copy(isLoading = false, errorMessage = er.message ?: "Failed to save game") }
                sendUiEffect(GameEnteringConditionsScreenEffect.ShowError(er.message ?: "Failed to save game"))
            }
        ) {
           // val currentUiState = uiStateMutable.value
//            val gameDataToSave = GameData(
//                players = gameRepository.gameData.value.players,
//                maximumPlayers = currentUiState.maximumPlayers,
//                selectedPrivacy = currentUiState.selectedPrivacy,
//                perPerson = currentUiState.perPerson,
//                accountNumber = currentUiState.accountNumber
//            )
            gameRepository.saveGameDataToServer()//gameDataToSave)
            uiStateMutable.update { it.copy(isLoading = false) } // Завершаем загрузку
            sendUiEffect(GameEnteringConditionsScreenEffect.NavigateToSuccess)
        }
        // и переход на экран Success
        //sendUiEffect(GameEnteringConditionsScreenEffect.NavigateToSuccess)
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
    }
}
// Специальный ViewModel для Preview
class GameEnteringConditionsScreenViewModelPreview : GameEnteringConditionsScreenViewModel( FakeCreateNewGameRepository(MutableStateFlow(GameData())) ) {
 }
