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
    private val ratePlayersUseCase: Any,
    private val getPlayersToRateUseCase: Any,
    private val appScope: CoroutineScope
) : ViewModel() {

    private val _state = MutableStateFlow(RatePlayersState())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<RatePlayersEffect>()
    val effects = _effects.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            /*
            getPlayersToRateUseCase.get(id)
            Получение и маппинг в ui модель, чтобы сразу ставить RatingType
            Новый state:
                isLoading = false
                players = PlayersShortUI
             */
        }
    }

    fun dispatch(action: RatePlayersAction) {
        when (action) {
            RatePlayersAction.ConfirmRate -> confirmRating()
            is RatePlayersAction.RatePlayer -> ratePlayer(action.playerId, action.rating)
        }
    }

    private fun confirmRating() {
        appScope.launch(Dispatchers.IO) {
            /*
            ratePlayersUseCase.ratePlayers(_state.players)
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
