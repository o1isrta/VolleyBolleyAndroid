package cy.volleybolley.core.presentation.ui.screens.createnewgame.gameEnteringConditionsScreen

import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository.CreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository.FakeCreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository.GameData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

open class GameEnteringConditionsScreenViewModel(
    private val gameRepository: CreateNewGameRepository
) :
    BaseViewModel<
        GameEnteringConditionsScreenState,
        GameEnteringConditionsScreenEvent,
        GameEnteringConditionsScreenEffect
        >(
        GameEnteringConditionsScreenState()
    ) {
    override val tag: String = "GameEnteringConditionsScreenViewModel"

    init {
        // проверяем, есть  ли аккаунт
        obtainEvent(GameEnteringConditionsScreenEvent.CheckIfAccountExists)
        // Подписка на изменения GameData из репозитория
        viewModelScope.launch {
            gameRepository.gameData
                .collectLatest { gameDataFromRepo ->
                    uiStateMutable.update { currentState ->
                        currentState.copy(
                            // Обновляем список игроков из репозитория
                            players = gameDataFromRepo.players,
                            maximumPlayers = gameDataFromRepo.maximumPlayers,
                            perPerson = gameDataFromRepo.perPerson,
                            accountNumber = gameDataFromRepo.accountNumber
                        )
                    }
                }
        }
    }

    override fun obtainEvent(event: GameEnteringConditionsScreenEvent) {
        when (event) {
            is GameEnteringConditionsScreenEvent.OnPublicSelected -> onPublicSelected()
            is GameEnteringConditionsScreenEvent.OnPrivateSelected -> onPrivateSelected()
            is GameEnteringConditionsScreenEvent.RemovePlayer -> onRemovePlayer(event.index)
            is GameEnteringConditionsScreenEvent.OnSaveGameClick -> saveGame()
            is GameEnteringConditionsScreenEvent.CheckIfAccountExists -> checkIfAccountExists()
            is GameEnteringConditionsScreenEvent.OnManagePlayersClick -> gotoPrivacyOptions()
            is GameEnteringConditionsScreenEvent.PerPersonChanged -> {
                uiStateMutable.value = uiStateMutable.value.copy(perPerson = event.perPerson)
            }

            is GameEnteringConditionsScreenEvent.MaximumPlayersChanged -> {
                uiStateMutable.value = uiStateMutable.value.copy(maximumPlayers = event.maximumPersons)
            }

            is GameEnteringConditionsScreenEvent.OnBackClicked -> {
                sendUiEffect(GameEnteringConditionsScreenEffect.NavigateBack)
            }

            is GameEnteringConditionsScreenEvent.OnAddPaymentClick -> {
                sendUiEffect(GameEnteringConditionsScreenEffect.NavigateToPayments)
            }
        }
    }

    private fun onPublicSelected() {
        viewModelScope.launch {
            gameRepository.updateGameData { gameData ->
                gameData.copy(
                    players = emptyList()
                )
            }
        }
    }

    private fun onPrivateSelected() {
        if (uiStateMutable.value.players.isEmpty()) {
            gotoPrivacyOptions()
        }
    }

    private fun gotoPrivacyOptions() {
        viewModelScope.launch {
            gameRepository.updateGameData { gameData ->
                gameData.copy(
                    maximumPlayers = uiState.value.maximumPlayers,
                    accountNumber = uiState.value.accountNumber,
                    perPerson = uiState.value.perPerson,
                    players = uiState.value.players
                )
            }
        }
        sendUiEffect(
            GameEnteringConditionsScreenEffect.NavigateToPrivacy
        )
    }

    private fun onRemovePlayer(index: Int) {
        val current = uiStateMutable.value
        if (index in current.players.indices) {
            val newPlayers = current.players.toMutableList().apply { removeAt(index) }
            uiStateMutable.value = current.copy(players = newPlayers)
        } else {
            uiStateMutable.value = current.copy(errorMessage = "Invalid player index: $index")
        }
    }

    private fun saveGame() {
        uiStateMutable.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        } // Начинаем загрузку, очищаем предыдущие ошибки

        launchSafe(
            dispatcher = Dispatchers.IO,
            getErrorLogMessage = { "Error saving game: ${it.message ?: "Unknown error"}" },
            onError = { er ->
                uiStateMutable.update { it.copy(isLoading = false, errorMessage = er.message ?: "Failed to save game") }
                sendUiEffect(GameEnteringConditionsScreenEffect.ShowErrorMessage(er.message ?: "Failed to save game"))
            }
        ) {
            gameRepository.updateGameData { gameData ->
                gameData.copy(
                    maximumPlayers = uiState.value.maximumPlayers,
                    accountNumber = uiState.value.accountNumber,
                    perPerson = uiState.value.perPerson,
                    players = uiState.value.players
                )
            }
            gameRepository.saveGameDataToServer()
            uiStateMutable.update { it.copy(isLoading = false) } // Завершаем загрузку
            sendUiEffect(GameEnteringConditionsScreenEffect.NavigateToSuccess)
        }
    }

    private fun checkIfAccountExists() { // если accountNumber != Null, аккааунт существует
        launchSafe(
            dispatcher = Dispatchers.IO,
            getErrorLogMessage = { "Error checking account existence: ${it.message ?: "Unknown error"}" },
            onError = { er ->
                sendUiEffect(GameEnteringConditionsScreenEffect.ShowErrorMessage(er.message ?: "Failed to check account"))
            }
        ) {
            val accountNumber = getAccountNumber() // Получение номера счета (аккаунта), если он есть
            uiStateMutable.value = uiStateMutable.value.copy(accountNumber = accountNumber)
        }
    }

    private fun getAccountNumber(): String? {
        return if (Random.nextBoolean()) "123 45 6789" else null // для теста, заменить на получение номера из профиля
    }
}

class GameEnteringConditionsScreenViewModelPreview :
    GameEnteringConditionsScreenViewModel(FakeCreateNewGameRepository(GameData()))
