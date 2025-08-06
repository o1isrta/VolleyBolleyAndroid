package cy.volleybolley.games.data.dto

import cy.volleybolley.courts.data.dto.toDomain
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.domain.model.Game
import cy.volleybolley.games.domain.model.GameDetails
import cy.volleybolley.games.domain.model.Host
import cy.volleybolley.games.domain.model.PlayerShort

fun GamesResponse.CreateGame.toDomain(): Game = Game(
    gameId = gameId,
    courtId = courtId,
    message = message,
    startTime = startTime,
    endTime = endTime,
    gender = gender,
    levels = levels,
    isPrivate = isPrivate,
    maximumPlayers = maximumPlayers,
    price = price,
    paymentType = paymentType,
    paymentAccount = paymentAccount,
    currencyType = currencyType,
    players = players
)

fun Game.toData(): GameDto = GameDto(
    courtId = courtId,
    message = message,
    startTime = startTime,
    endTime = endTime,
    gender = gender,
    levels = levels,
    isPrivate = isPrivate,
    maximumPlayers = maximumPlayers,
    price = price,
    paymentType = paymentType,
    players = players
)

fun GamesResponse.GetGameDetails.toDomain(): GameDetails = GameDetails(
    gameId = gameId,
    gameType = gameType,
    host = host.toDomain(),
    message = message,
    courtLocation = courtLocation.toDomain(),
    startTime = startTime,
    endTime = endTime,
    levels = levels,
    gender = gender,
    pricePerPerson = pricePerPerson,
    currencyType = currencyType,
    paymentType = paymentType,
    paymentAccount = paymentAccount,
    maximumPlayers = maximumPlayers,
    players = players.map { it.toDomain() }
)

fun HostDto.toDomain(): Host = Host(
    id = id,
    name = "$firstName $lastName",
    avatar = avatar,
    level = level
)

fun PlayerShortDto.toDomain(): PlayerShort = PlayerShort(
    playerId = playerId,
    name = "$firstName $lastName",
    level = level
)
