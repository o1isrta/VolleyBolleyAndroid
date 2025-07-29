package cy.volleybolley.games.data.dto

import cy.volleybolley.games.domain.model.Game

fun GameDto.toDomain(): Game = Game(
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
    currencyType = currencyType,
    players = players.map { it.playerId }
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
    currencyType = currencyType,
    players = players.map { PlayerIdDto(it) }
)
