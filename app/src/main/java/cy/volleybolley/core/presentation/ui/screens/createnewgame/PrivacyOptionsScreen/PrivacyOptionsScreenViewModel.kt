package cy.volleybolley.core.presentation.ui.screens.createnewgame.PrivacyOptionsScreen

import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.CreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.FakeCreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.FakeSearchPlayersUseCase
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.GameData
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.usecase.SearchPlayersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class PrivacyOptionsScreenViewModel(private val gameRepository: CreateNewGameRepository,
                                         private val searchPlayersUseCase: SearchPlayersUseCase) : BaseViewModel<PrivacyOptionsScreenState, PrivacyOptionsScreenEvent, PrivacyOptionsScreenEffect>(
    PrivacyOptionsScreenState()
) {
    override val tag: String = "PrivacyOptionsScreenViewModel"
    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            gameRepository.gameData
                .collectLatest { gameDataFromRepo ->
                    // Когда GameData в репозитории меняется, обновляем соответствующие части UI State
                    uiStateMutable.update { currentState ->
                        currentState.copy(
                            // Обновляем список игроков из репозитория
                            flagFavorites = false,
                            playersSearchResult = emptyList(),
                            selectedPlayers = gameDataFromRepo.players.toMutableSet()
                        )
                    }
                }
        }
    }

    override fun obtainEvent(event: PrivacyOptionsScreenEvent) {
        when (event) {
            is PrivacyOptionsScreenEvent.OnBackClicked -> {
                sendUiEffect(PrivacyOptionsScreenEffect.NavigateBack)
            }

            is PrivacyOptionsScreenEvent.OnAddSelectedClick -> {
                onAddSelectedPlayersClick()
                sendUiEffect(PrivacyOptionsScreenEffect.NavigateBack)
            }

            is PrivacyOptionsScreenEvent.OnPlayerSelectionClick -> {
                onPlayerSelectionChange(event.player)
            }

            is PrivacyOptionsScreenEvent.OnQueryChanged -> {
                onQueryChange(event.text)
            }

            is PrivacyOptionsScreenEvent.AllOrFavoritesSelected -> {
                uiStateMutable.value = uiStateMutable.value.copy(flagFavorites = event.isFavorites)
            }
        }
    }

    private fun onQueryChange(queryText: String) {
         // Отменяем предыдущий Job, если он существует
        searchJob?.cancel()
        // Запускаем новый Job с задержкой
        searchJob = viewModelScope.launch {
            withContext(Dispatchers.Main){
                uiStateMutable.value = uiStateMutable.value.copy(query = queryText)
            }
            delay(300)
            searchPlayers(uiStateMutable.value.query)
        }
    }

    //Вызов performSearch по нажатию enter
    fun onEnterPressed() {
        searchJob?.cancel()
        searchPlayers(uiStateMutable.value.query)
    }

    private fun searchPlayers(query: String) {
        launchSafe(
            onError = { throwable ->
                sendUiEffect(PrivacyOptionsScreenEffect.ShowError(throwable.localizedMessage ?: "Unknown error"))
                uiStateMutable.update {
                    it.copy(
                        isLoading = false,
                        playersSearchResult = emptyList()
                    )
                }
            },
            getErrorLogMessage = { "Error searching players for query: $query - $it" }
        ) {
            uiStateMutable.update { it.copy(isLoading = true) }
            delay(500) // Имитируем задержку сети

            // Вызываем UseCase
            when (val result = searchPlayersUseCase(query)) {
                is VolleyResult.Success -> {
                    val filteredPlayers = if (query.isBlank()) {
                        result.data // Возвращаем все игроки при пустом запросе
                    } else {
                        result.data.filter { player ->
                            player.firstName.contains(query, ignoreCase = true) ||
                                player.lastName.contains(query, ignoreCase = true)
                        }
                    }

                    uiStateMutable.update {
                        it.copy(
                            playersSearchResult = filteredPlayers,
                            isLoading = false
                        )
                    }
                }
                is VolleyResult.Failure -> {
                    val errorMessage = when (result.error) {
                        ErrorType.UNAUTHORIZED -> "Authentication required"
                        ErrorType.NO_CONNECTION -> "Network unavailable, please check your connection."
                        ErrorType.SERVER_ERROR -> "Server is busy, please try again later."
                        ErrorType.NOT_FOUND -> "No players found."
                        ErrorType.UNKNOWN_ERROR -> "An unexpected error occurred."
                        ErrorType.BAD_REQUEST -> "Bad request"
                        ErrorType.NO_REFRESH_TOKEN -> "No refresh token"
                    }
                    sendUiEffect(PrivacyOptionsScreenEffect.ShowError(errorMessage))
                    uiStateMutable.update {
                        it.copy(
                            isLoading = false,
                            playersSearchResult = emptyList()
                        )
                    }
                }
            }
        }
    }

    private fun onPlayerSelectionChange(player: Player) {
        uiStateMutable.update { currentState ->
            val updatedSelectedPlayers = currentState.selectedPlayers.toMutableSet()
            if (isPlayerSelected(player)) { // если был выбран, то при нажатии, становится не выбран. и наоборот
                updatedSelectedPlayers.remove(player)  // снимаем выбор
            } else {
                if (updatedSelectedPlayers.size < gameRepository.gameData.value.maximumPlayers)
                    updatedSelectedPlayers.add(player) // выбираем
            }
            currentState.copy(selectedPlayers = updatedSelectedPlayers)
        }
    }

    private fun onAddSelectedPlayersClick() {
        viewModelScope.launch {
            gameRepository.updateGameData { currentData ->
                currentData.copy(players = uiState.value.selectedPlayers.toList())
            }
        }.invokeOnCompletion { //вызывается когда корутина завершилась
            clearSearchResults() // Очищаем результаты поиска после завершения
        }
    }

    // Функция для очистки списка результатов поиска
    private fun clearSearchResults() {
        uiStateMutable.update { currentState ->
            currentState.copy(playersSearchResult = emptyList())
        }
    }

    fun isPlayerSelected(player: Player): Boolean{
        return uiStateMutable.value.selectedPlayers.contains(player)
    }

}

// Специальный ViewModel для Preview
class PrivacyOptionsScreenViewModelPreview : PrivacyOptionsScreenViewModel(
    FakeCreateNewGameRepository(GameData()), FakeSearchPlayersUseCase()
) {
}
