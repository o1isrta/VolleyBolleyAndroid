package cy.volleybolley.jointhegame

import cy.volleybolley.R
import cy.volleybolley.core.ResourceProvider
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.games.domain.api.game.GetGameDetailsUseCase
import cy.volleybolley.games.domain.api.game.JoinGameUseCase
import kotlinx.coroutines.flow.update

class JoinTheGameViewModel(
    private val gameId: Int,
    private val getGameDetailsUseCase: GetGameDetailsUseCase,
    private val joinGameUseCase: JoinGameUseCase,
    private val resourceProvider: ResourceProvider,
) : BaseViewModel<JoinTheGameState, JoinTheGameEvent, JoinTheGameEffect>(
    JoinTheGameState()
) {

    init {
        getGameDetails()
        /*
        uiStateMutable.update {
            it.copy(
                isRefreshing = false,
                details = GameDetails(
                    gameId = 1,
                    host = Host(
                        id = 1,
                        name = "Artem Ivanov",
                        avatar = null,
                        level = LevelType.LIGHT
                    ),
                    message = "Hi! Just old friends meet at the court.",
                    courtLocation = Location(
                        longitude = 0.6,
                        latitude = 0.7,
                        courtName = "Karon Beach Club",
                        locationName = "Ratak Rd, Mueng Phuket"
                    ),
                    gender = "Mix",
                    levels = listOf("Light"),
                    pricePerPerson = "5",
                    maximumPlayers = 5,
                    paymentType = PaymentType.THAIBANK,
                    paymentAccount = "988 016 7890",
                    currencyType = CurrencyType.EUR,
                    players = listOf(
                        PlayerShort(
                            playerId = 1,
                            name = "Artem Ivanov",
                            level = LevelType.LIGHT,
                            avatar = null
                        )
                    ),
                    isPrivate = false,
                    startTime = "10 October, 6:00-8:00",
                    endTime = "10 October, 6:00-8:00"
                )
            )
        }
         */

    }

    override fun obtainEvent(event: JoinTheGameEvent) {
        when (event) {
            JoinTheGameEvent.OnBack -> sendUiEffect(JoinTheGameEffect.NavigateBack)
            JoinTheGameEvent.OnJoinGame -> {
                joinGame()
            }

            is JoinTheGameEvent.OnMap -> sendUiEffect(JoinTheGameEffect.OpenMap(event.location))
            JoinTheGameEvent.OnRefresh -> {
                uiStateMutable.update { it.copy(isRefreshing = true) }
                getGameDetails()
            }
        }
    }

    private fun getGameDetails() {
        launchSafe(
            getErrorLogMessage = { throwable ->
                "$tag getGameDetails method ${throwable.message}"
            }
        ) {
            when (val result = getGameDetailsUseCase.getGameDetails(gameId = gameId)) {
                is VolleyResult.Success -> {
                    uiStateMutable.update { current ->
                        current.copy(
                            isRefreshing = false,
                            details = result.data.toUi(),
                            errorMessage = ""
                        )
                    }
                }

                is VolleyResult.Failure -> {
                    uiStateMutable.update { current ->
                        current.copy(
                            isRefreshing = false,
                            details = null,
                            errorMessage = resourceProvider.getString(R.string.error_message_standard)
                        )
                    }
                }
            }
        }
    }

    private fun joinGame() {
        launchSafe(
            getErrorLogMessage = { throwable ->
                "$tag joinGame method ${throwable.message}"
            }
        ) {
            when (val result = joinGameUseCase.joinGame(gameId)) {
                is VolleyResult.Success -> {
                    uiStateMutable.update {
                        it.copy(errorMessage = "")
                    }
                    sendUiEffect(JoinTheGameEffect.JoinGame)
                }

                is VolleyResult.Failure -> {
                    uiStateMutable.update {
                        it.copy(errorMessage = resourceProvider.getString(R.string.error_message_fail_to_join))
                    }
                }
            }
        }
    }
}
