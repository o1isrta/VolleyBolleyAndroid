package cy.volleybolley.games.data.dto.mappers

import cy.volleybolley.courts.data.dto.toDomain
import cy.volleybolley.games.data.dto.GameDto
import cy.volleybolley.games.data.dto.GamePreviewDto
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.domain.model.Game
import cy.volleybolley.games.domain.model.GamePreview
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
    pricePerPerson = price,
    paymentType = paymentType,
    paymentAccount = paymentAccount,
    currencyType = currencyType,
    players = players.map { PlayerShort(playerId = it) },
)

fun Game.toData(): GameDto = GameDto(
    courtId = courtId ?: 0,
    message = message,
    startTime = startTime,
    endTime = endTime,
    gender = gender,
    levels = levels,
    isPrivate = isPrivate,
    maximumPlayers = maximumPlayers,
    price = pricePerPerson,
    paymentType = paymentType,
    players = players.map { it.playerId }
)

fun GamesResponse.GetGameDetails.toDomain(): Game = Game(
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
    players = players.map { it.toDomain() },
)

fun GamePreviewDto.toDomain(): GamePreview = GamePreview(
    gameId = gameId,
    host = host.toDomain(),
    locationDto = location.toDomain(),
    message = message,
    startTime = startTime,
    endTime = endTime
)
