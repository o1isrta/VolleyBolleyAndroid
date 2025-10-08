package cy.volleybolley.rateplayers

import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.games.domain.api.game.GetPlayersToRateUseCase
import cy.volleybolley.games.domain.api.game.RatePlayersUseCase
import cy.volleybolley.games.domain.model.entity.RatePlayer
import cy.volleybolley.games.domain.model.entity.RatingType
import cy.volleybolley.games.domain.model.event.EventType
import kotlinx.coroutines.flow.update

class RatePlayersViewModel(
    private val eventId: Int,
    private val eventType: EventType,
    private val ratePlayersUseCase: RatePlayersUseCase,
    private val getPlayersToRateUseCase: GetPlayersToRateUseCase,
) : BaseViewModel<RatePlayersState, RatePlayersEvent, RatePlayersEffect>(
    initialState = RatePlayersState()
) {

    override val tag: String = RatePlayersViewModel::class.simpleName ?: ""

    init {
        launchSafe(
            getErrorLogMessage = { throwable ->
                "$tag init ${throwable.message}"
            }
        ) {
            when (val result = getPlayersToRateUseCase.getPlayers(eventId, eventType)) {
                is VolleyResult.Success -> {
                    uiStateMutable.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            players = result.data.map {
                                PlayerShortUI(
                                    playerId = it.playerId,
                                    name = it.name,
                                    level = it.level,
                                    avatar = it.avatar
                                )
                            })
                    }
                }

                is VolleyResult.Failure -> {
                    uiStateMutable.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            players = emptyList()
                        )
                    }
                }
            }
        }
    }

    override fun obtainEvent(event: RatePlayersEvent) {
        when (event) {
            RatePlayersEvent.ConfirmRate -> confirmRating()
            is RatePlayersEvent.RatePlayer -> ratePlayer(event.playerId, event.rating)
        }
    }

    private fun confirmRating() {
        val ratingPlayers = uiStateMutable.value.players.map { player ->
            RatePlayer(
                playerId = player.playerId,
                levelChanged = player.rating
            )
        }
        launchSafe(
            getErrorLogMessage = { throwable ->
                "$tag confirmRating ${throwable.message}"
            }
        ) {

            ratePlayersUseCase.ratePlayers(
                id = eventId,
                type = eventType,
                players = ratingPlayers,
            )

            sendUiEffect(RatePlayersEffect.CloseScreen)
        }
    }

    private fun ratePlayer(playerId: Int, newRating: RatingType) {
        uiStateMutable.update { currentState ->
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
}
