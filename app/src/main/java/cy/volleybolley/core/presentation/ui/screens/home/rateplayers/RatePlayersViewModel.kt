package cy.volleybolley.core.presentation.ui.screens.home.rateplayers

import cy.volleybolley.core.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.update

class RatePlayersViewModel(
    private val eventId: Int,
    private val eventType: String,
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
            /*
            getPlayersToRateUseCase.get(id, type)
            Получение и маппинг в ui модель, чтобы сразу ставить RatingType
            Новый state:
                isLoading = false
                players = PlayersShortUI
             */

            // моковые данные
            uiStateMutable.update { currentState ->
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
            /*
            поменять скоуп в репозитории на AppScope,
            так как экран может закрыться раньше чем отправятся данные!!!

            ratePlayersUseCase.ratePlayers(ratingPlayers)
             */
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
