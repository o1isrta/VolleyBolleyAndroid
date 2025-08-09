package cy.volleybolley.games.data.dto.mappers

import cy.volleybolley.courts.data.dto.toDomain
import cy.volleybolley.games.data.dto.GameDto
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.domain.model.Game
import cy.volleybolley.games.domain.model.GameDetails

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
    price = pricePerPerson,
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
