package cy.volleybolley.core.presentation.ui.screens.home.rateplayers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RatePlayersViewModel(
    private val eventId: Int,
    private val eventType: String,
    private val appScope: CoroutineScope
) : ViewModel() {

    private val _state = MutableStateFlow(RatePlayersState())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<RatePlayersEffect>()
    val effects = _effects.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            /*
            getPlayersToRateUseCase.get(id, type)
            Получение и маппинг в ui модель, чтобы сразу ставить RatingType
            Новый state:
                isLoading = false
                players = PlayersShortUI
             */

            // моковые данные
            _state.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    players = listOf(
                        PlayerShortUI(
                            playerId = 1,
                            name = "Kristina Popova",
                            level = LevelType.LIGHT,
                            avatar = null,
                            rating = RatingType.CONFIRM
                        ),
                        PlayerShortUI(
                            playerId = 2,
                            name = "Jane Dow",
                            level = LevelType.HARD,
                            avatar = null,
                            rating = RatingType.CONFIRM
                        ),
                        PlayerShortUI(
                            playerId = 3,
                            name = "John Smith",
                            level = LevelType.LIGHT,
                            avatar = null,
                            rating = RatingType.CONFIRM
                        )
                    )
                )
            }
        }
    }

    fun obtainEvent(event: RatePlayersEvent) {
        when (event) {
            RatePlayersEvent.ConfirmRate -> confirmRating()
            is RatePlayersEvent.RatePlayer -> ratePlayer(event.playerId, event.rating)
        }
    }

    private fun confirmRating() {
        appScope.launch(Dispatchers.IO) {
            val ratingPlayers = _state.value.players.map { player ->
                RatePlayer(
                    playerId = player.playerId,
                    levelChanged = player.rating
                )
            }
            /*
            ratePlayersUseCase.ratePlayers(ratingPlayers)
             */
            emit(RatePlayersEffect.CloseScreen)
        }
    }

    private fun ratePlayer(playerId: Int, newRating: RatingType) {
        _state.update { currentState ->
            currentState.copy(
                players = currentState.players.map { player ->
                    if (player.playerId == playerId) {
                        player.copy(rating = newRating)
                    } else {
                        player
                    }
                }
            )
        }
    }

    private fun emit(effect: RatePlayersEffect) {
        viewModelScope.launch { _effects.emit(effect) }
    }
}
