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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class PrivacyOptionsScreenViewModel(private val createNewGameRepository: CreateNewGameRepository,
                                         private val searchPlayersUseCase: SearchPlayersUseCase) : BaseViewModel<PrivacyOptionsScreenState, PrivacyOptionsScreenEvent, PrivacyOptionsScreenEffect>(
    PrivacyOptionsScreenState()
) {
    override val tag: String = "PrivacyOptionsScreenViewModel"
    private var searchJob: Job? = null
//    init {
//        // Загружаем игроков при инициализации ViewModel
//        //obtainEvent(PrivacyOptionsScreenEvent.LoadPlayers)
//        loadPlayers()
//    }

    override fun obtainEvent(event: PrivacyOptionsScreenEvent) {
        when (event) {
            is PrivacyOptionsScreenEvent.OnBackClicked -> {
                sendUiEffect(PrivacyOptionsScreenEffect.NavigateBack)
            }

            is PrivacyOptionsScreenEvent.OnAddSelectedClick -> {
                onAddSelectedPlayersClick()
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

//    fun onSearchTextChanged(text: String) {
//        searchJob?.cancel()
//        searchJob = viewModelScope.launch {
//            delay(500) // Задержка 500ms
//            performSearch(text)
//        }
//    }
//
//    private fun performSearch(query: String) {
//        // Выполните здесь логику поиска, например, запрос к базе данных или API
//        // Обновите _uiState с результатами поиска
//        val searchResults = searchPlayers(query) //  метод с логикой поиска
//        uiStateMutable.update { it.copy(playersSearchResult = searchResults) }
//    }
//
//    private fun searchPlayers(query: String) {
//        launchSafe(
//            onError = { throwable ->
//                sendUiEffect(PrivacyOptionsScreenEffect.ShowError(throwable.localizedMessage ?: "Unknown error"))
//                uiStateMutable.update {
//                    it.copy(
//                        isLoading = false,
//                        playersSearchResult = emptyList()
//                    )
//                } // Сбросить загрузку и очистить/обновить игроков
//            },
//            getErrorLogMessage = { "Error searching players for query: $query - $it" }
//        ) {
//            uiStateMutable.update { it.copy(isLoading = true) }
//
//            when (val result = searchPlayersUseCase(query)) {
//                is VolleyResult.Success -> {
//                    uiStateMutable.update { it.copy(playersSearchResult = result.data, isLoading = false) }
//                }
//
//                is VolleyResult.Failure -> {
//                    val errorMessage = when (result.error) {
//                        ErrorType.UNAUTHORIZED -> "Authentication required"
//                        ErrorType.NO_CONNECTION -> "Network unavailable, please check your connection."
//                        ErrorType.SERVER_ERROR -> "Server is busy, please try again later."
//                        ErrorType.NOT_FOUND -> "No players found."
//                        ErrorType.UNKNOWN_ERROR -> "An unexpected error occurred."
//                        ErrorType.BAD_REQUEST -> "Bad request"// Добавьте другие ErrorType по мере необходимости
//                    }
//                    sendUiEffect(PrivacyOptionsScreenEffect.ShowError(errorMessage))
//                    uiStateMutable.update {
//                        it.copy(
//                            isLoading = false,
//                            playersSearchResult = emptyList()
//                        )
//                    } // Сбросить или очистить игроков при ошибке
//                }
//            }
//        }
//    }

    private fun onQueryChange(query: String) {
        uiStateMutable.update { it.copy(query = query) }
            //searchPlayers(query)
        debouncedSearch(query)
    }

    // Debounce функция для поиска
    private fun debouncedSearch(query: String) {
        searchPlayers(query)
    }

   /* private fun performSearch(query: String) {
        val searchResults = searchPlayers(query) //  метод с логикой поиска
        uiStateMutable.update { it.copy(playersSearchResult = searchResults) }
    }*/

    private fun searchPlayers(query: String) {
        launchSafe(
            onError = { throwable ->
                sendUiEffect(PrivacyOptionsScreenEffect.ShowError(throwable.localizedMessage ?: "Unknown error"))
                uiStateMutable.update {
                    it.copy(
                        isLoading = false,
                        playersSearchResult = emptyList()
                    )
                } // Сбросить загрузку и очистить/обновить игроков
            },
            getErrorLogMessage = { "Error searching players for query: $query - $it" }
        ) {
            uiStateMutable.update { it.copy(isLoading = true) }

            when (val result = searchPlayersUseCase(query)) {
                is VolleyResult.Success -> {
                    uiStateMutable.update { it.copy(playersSearchResult = result.data, isLoading = false) }
                }

                is VolleyResult.Failure -> {
                    val errorMessage = when (result.error) {
                        ErrorType.UNAUTHORIZED -> "Authentication required"
                        ErrorType.NO_CONNECTION -> "Network unavailable, please check your connection."
                        ErrorType.SERVER_ERROR -> "Server is busy, please try again later."
                        ErrorType.NOT_FOUND -> "No players found."
                        ErrorType.UNKNOWN_ERROR -> "An unexpected error occurred."
                        ErrorType.BAD_REQUEST -> "Bad request"// Добавьте другие ErrorType по мере необходимости
                    }
                    sendUiEffect(PrivacyOptionsScreenEffect.ShowError(errorMessage))
                    uiStateMutable.update {
                        it.copy(
                            isLoading = false,
                            playersSearchResult = emptyList()
                        )
                    } // Сбросить или очистить игроков при ошибке
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
                updatedSelectedPlayers.add(player) // выбираем
            }
            currentState.copy(selectedPlayers = updatedSelectedPlayers)
        }
    }

    private fun onAddSelectedPlayersClick() {
        viewModelScope.launch {
            createNewGameRepository.updateGameData { currentData ->
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
    FakeCreateNewGameRepository(/*MutableStateFlow(GameData())*/ GameData()), FakeSearchPlayersUseCase()
) {
    init {
        // Например, чтобы показать, что что-то загружается
        // _state.value = _state.value.copy(isLoading = true)
    }
}
