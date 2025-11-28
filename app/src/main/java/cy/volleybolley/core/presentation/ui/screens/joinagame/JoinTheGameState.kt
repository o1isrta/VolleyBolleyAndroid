package cy.volleybolley.core.presentation.ui.screens.joinagame

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.profile.domain.model.PaymentType
import cy.volleybolley.referencedata.domain.model.CurrencyType

data class JoinTheGameState(
    val isRefreshing: Boolean = false,
    val details: GameDetails
) : UiState

data class GameDetails(
    val gameId: Int,
    val gameType: String,
    val host: Host,
    val message: String,
    val courtLocation: Location,
    val time: String,
    val gender: String,
    val levels: List<String>,
    val pricePerPerson: String,
    val maximumPlayers: Int,
    val paymentType: PaymentType,
    val paymentAccount: String?,
    val currencyType: CurrencyType,
    val players: List<PlayerShort>,
)

data class PlayerShort(
    val playerId: Int,
    val name: String,
    val level: String,
    val avatar: String?,
)

data class Host(
    val id: Int,
    val name: String,
    val avatar: String?,
    val level: String,
)

data class Location(
    val longitude: Double,
    val latitude: Double,
    val courtName: String,
    val locationName: String,
)
