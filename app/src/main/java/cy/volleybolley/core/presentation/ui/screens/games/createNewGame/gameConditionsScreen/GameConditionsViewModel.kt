package cy.volleybolley.core.presentation.ui.screens.games.createNewGame.gameConditionsScreen

import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.games.createNewGame.CreateGameSharedViewModel
import cy.volleybolley.core.presentation.ui.screens.games.createNewGame.model.GameData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

open class GameConditionsViewModel(
    private val createGameSharedViewModel: CreateGameSharedViewModel
) : BaseViewModel<GameConditionsState, GameConditionsEvent, GameConditionsEffect>(
    GameConditionsState()
) {
    init {
        obtainEvent(GameConditionsEvent.CheckIfAccountExists)
        viewModelScope.launch {
            createGameSharedViewModel.gameData
                .collectLatest { gameData ->
                    uiStateMutable.update { currentState ->
                        currentState.copy(
                            players = gameData.players ?: emptyList(),
                            maximumPlayers = gameData.maximumPlayers ?: GameData().maximumPlayers,
                            perPerson = gameData.perPerson ?: GameData().perPerson,
                            accountNumber = gameData.accountNumber,
                            isPrivate = gameData.players?.isNotEmpty() ?: false
                        )
                    }
                }
        }
    }

    override fun obtainEvent(event: GameConditionsEvent) {
        when (event) {
            is GameConditionsEvent.OnPublicSelected -> onPublicSelected()
            is GameConditionsEvent.OnPrivateSelected -> onPrivateSelected()
            is GameConditionsEvent.RemovePlayer -> onRemovePlayer(event.index)
            is GameConditionsEvent.OnSaveGameClick -> saveGame()
            is GameConditionsEvent.CheckIfAccountExists -> checkIfAccountExists()
            is GameConditionsEvent.OnManagePlayersClick -> gotoPrivacyOptions()
            is GameConditionsEvent.PerPersonChanged -> {
                uiStateMutable.update { it.copy(perPerson = event.perPerson) }
               // updateGameData { it.copy(perPerson = event.perPerson) }
            }

            is GameConditionsEvent.MaximumPlayersChanged -> {
                uiStateMutable.update { it.copy(maximumPlayers = event.maximumPlayers) }
              //  updateGameData { it.copy(maximumPlayers = event.maximumPlayers) }
            }

            is GameConditionsEvent.OnBackClicked -> {
                sendUiEffect(GameConditionsEffect.NavigateBack)
            }

            is GameConditionsEvent.OnAddPaymentClick -> {
                sendUiEffect(GameConditionsEffect.NavigateToPayments)
            }
        }
    }

  /*  private fun updateGameData(update: (GameData) -> GameData) {
        viewModelScope.launch {
            createGameSharedViewModel.updateGameData(update)
        }
    }*/

    private fun onPublicSelected() {
        viewModelScope.launch {
            createGameSharedViewModel.updateGameData { gameData ->
                gameData.copy(players = emptyList())
            }
        }
        uiStateMutable.update { it.copy(isPrivate = false, players = emptyList()) }
    }

    private fun onPrivateSelected() {
        if (uiStateMutable.value.players.isEmpty()) {
            gotoPrivacyOptions()
        }
    }

    private fun gotoPrivacyOptions() {
        viewModelScope.launch {
            createGameSharedViewModel.updateGameData { gameData ->
                gameData.copy(
                    maximumPlayers = uiState.value.maximumPlayers,
                    accountNumber = uiState.value.accountNumber,
                    perPerson = uiState.value.perPerson,
                    players = uiState.value.players
                )
            }
        }
        sendUiEffect(
            GameConditionsEffect.NavigateToPrivacy
        )
    }

    private fun onRemovePlayer(index: Int) {
        uiStateMutable.update { current ->
            if (index in current.players.indices) {
                val newPlayers = current.players.toMutableList().apply { removeAt(index) }
                current.copy(players = newPlayers, isPrivate = newPlayers.isNotEmpty())
            } else {
                current.copy(errorMessage = "Invalid player index: $index")
            }
        }
        // updateGameData { it.copy(players = uiState.value.players) }
    }

    private fun saveGame() {
        uiStateMutable.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }

        launchSafe(
            dispatcher = Dispatchers.IO,
            getErrorLogMessage = { "Error saving game: ${it.message ?: "Unknown error"}" },
            onError = { er ->
                uiStateMutable.update { it.copy(isLoading = false, errorMessage = er.message ?: "Failed to save game") }
                sendUiEffect(GameConditionsEffect.ShowErrorMessage(er.message ?: "Failed to save game"))
            }
        ) {
            // Сохраняем данные перед отправкой на сервер
            createGameSharedViewModel.updateGameData { gameData ->
                gameData.copy(
                    maximumPlayers = uiState.value.maximumPlayers,
                    accountNumber = uiState.value.accountNumber,
                    perPerson = uiState.value.perPerson,
                    players = uiState.value.players
                )
            }

            // Вызываем saveGame у SharedViewModel
            when (val result = createGameSharedViewModel.saveGame()) {
                is VolleyResult.Success -> {
                    uiStateMutable.update { it.copy(isLoading = false) }
                    sendUiEffect(GameConditionsEffect.NavigateToSuccess)
                }

                is VolleyResult.Failure -> {
                    uiStateMutable.update { it.copy(isLoading = false, errorMessage = result.error.toString()) }
                    sendUiEffect(GameConditionsEffect.ShowErrorMessage(result.error.toString()))
                }
            }

//            createGameSharedViewModel.saveGameDataToServer()
//            uiStateMutable.update { it.copy(isLoading = false) }
//            sendUiEffect(GameConditionsEffect.NavigateToSuccess)
        }
    }

    private fun checkIfAccountExists() {
        launchSafe(
            getErrorLogMessage = { "Error checking account existence: ${it.message ?: "Unknown error"}" },
            onError = { error ->
                sendUiEffect(
                    GameConditionsEffect.ShowErrorMessage(error.message ?: "Failed to check account")
                )
            }
        ) {
            val accountNumber = getAccountNumber()
            uiStateMutable.value = uiStateMutable.value.copy(accountNumber = accountNumber)
        }
    }

    private fun getAccountNumber(): String? {
        return if (Random.nextBoolean()) "123 45 6789" else null
    }
}
