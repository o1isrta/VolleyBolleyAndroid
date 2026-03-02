package cy.volleybolley.jointhegame

import cy.volleybolley.core.DateFormatter
import cy.volleybolley.core.domain.model.PaymentType
import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.games.domain.model.entity.Host
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.event.game.GameDetails
import cy.volleybolley.referencedata.domain.model.CurrencyType

data class JoinTheGameState(
    val isRefreshing: Boolean = true,
    val details: GameDetailsUi? = null,
    val errorMessage: String = "",
) : UiState

data class GameDetailsUi(
    val gameId: Int,
    val isPrivate: Boolean,
    val host: HostUi,
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
    val players: List<PlayerShortUi>,
)

data class HostUi(
    val id: Int,
    val name: String,
    val avatar: String?,
    val level: String,
)

data class PlayerShortUi(
    val id: Int,
    val name: String,
    val level: String,
    val avatar: String?,
)

fun GameDetails.toUi(): GameDetailsUi = GameDetailsUi(
    gameId = gameId,
    isPrivate = isPrivate,
    host = host.toUi(),
    message = message,
    courtLocation = courtLocation,
    time = DateFormatter.formatRangeToDayAndPeriodTime(startTime, endTime),
    gender = gender,
    levels = levels,
    pricePerPerson = pricePerPerson,
    maximumPlayers = maximumPlayers,
    paymentType = paymentType,
    paymentAccount = paymentAccount,
    currencyType = currencyType,
    players = players.map { it.toUi() }
)

private fun Host.toUi(): HostUi = HostUi(
    id = id,
    name = name,
    avatar = avatar,
    level = level.level.uppercase().first().toString()
)

private fun PlayerShort.toUi(): PlayerShortUi = PlayerShortUi(
    id = playerId,
    name = name,
    level = level.level.uppercase().first().toString(),
    avatar = avatar
)
