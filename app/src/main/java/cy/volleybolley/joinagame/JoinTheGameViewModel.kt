package cy.volleybolley.joinagame

import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.profile.domain.model.PaymentType
import cy.volleybolley.referencedata.domain.model.CurrencyType
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JoinTheGameViewModel :
    BaseViewModel<JoinTheGameState, JoinTheGameEvent, JoinTheGameEffect>(
        JoinTheGameState(
            isRefreshing = true,
            GameDetails(
                gameId = -1,
                gameType = "",
                host = Host(
                    id = -1,
                    name = "",
                    avatar = null,
                    level = ""
                ),
                message = "",
                courtLocation = Location(
                    longitude = -1.0,
                    latitude = -1.0,
                    courtName = "",
                    locationName = ""
                ),
                time = "",
                gender = "",
                levels = emptyList(),
                pricePerPerson = "",
                maximumPlayers = -1,
                paymentType = PaymentType.UNKNOWN,
                paymentAccount = "",
                currencyType = CurrencyType.UNKNOWN,
                players = emptyList()
            )
        )
    ) {

    override val tag: String = JoinTheGameViewModel::class.simpleName ?: ""

    init {
        uiStateMutable.update {
            it.copy(
                isRefreshing = false,
                details = GameDetails(
                    gameId = 1,
                    gameType = "",
                    host = Host(
                        id = 1,
                        name = "Artem Ivanov",
                        avatar = null,
                        level = "L"
                    ),
                    message = "Hi! Just old friends meet at the court.",
                    courtLocation = Location(
                        longitude = 0.6,
                        latitude = 0.7,
                        courtName = "Karon Beach Club",
                        locationName = "Ratak Rd, Mueng Phuket"
                    ),
                    time = "10 October, 6:00-8:00",
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
                            level = "L",
                            avatar = null
                        )
                    )
                )
            )
        }

    }

    override fun obtainEvent(event: JoinTheGameEvent) {
        when (event) {
            JoinTheGameEvent.OnBack -> sendUiEffect(JoinTheGameEffect.NavigateBack)
            JoinTheGameEvent.OnJoinGame -> {
                // вызов из domain

                sendUiEffect(JoinTheGameEffect.JoinGame)
            }

            is JoinTheGameEvent.OnMap -> sendUiEffect(JoinTheGameEffect.OpenMap(event.location))
            JoinTheGameEvent.OnRefresh -> {
                // вызов из domain
                uiStateMutable.update { it.copy(isRefreshing = true) }
                viewModelScope.launch {
                    uiStateMutable.update { it.copy(isRefreshing = false) }
                }
            }
        }
    }
}
