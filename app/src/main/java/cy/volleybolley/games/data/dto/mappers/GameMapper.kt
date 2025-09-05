package cy.volleybolley.games.data.dto.mappers

import cy.volleybolley.core.domain.model.PaymentType
import cy.volleybolley.courts.data.dto.toDomain
import cy.volleybolley.games.data.dto.CreateGameDto
import cy.volleybolley.games.data.dto.GamePreviewDto
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.domain.model.event.Event
import cy.volleybolley.games.domain.model.event.EventType
import cy.volleybolley.games.domain.model.event.game.CreateGame
import cy.volleybolley.games.domain.model.event.game.CreatedGame
import cy.volleybolley.games.domain.model.event.game.GameDetails
import cy.volleybolley.games.domain.model.event.game.JoinedGame

fun CreateGame.toData(): CreateGameDto = CreateGameDto(
    courtId = courtId,
    message = message,
    startTime = startTime,
    endTime = endTime,
    gender = gender,
    levels = levels,
    isPrivate = isPrivate,
    maximumPlayers = maximumPlayers,
    price = pricePerPerson,
    paymentType = paymentType.name,
    players = players
)

fun GamesResponse.CreateGame.toDomain(): CreatedGame = CreatedGame(
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
    paymentType = enumValues<PaymentType>().firstOrNull { it.name == paymentType } ?: PaymentType.CASH,
    paymentAccount = paymentAccount,
    currencyType = currencyType,
    players = players,
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
    paymentType = enumValues<PaymentType>().firstOrNull { it.name == paymentType } ?: PaymentType.CASH,
    paymentAccount = paymentAccount,
    maximumPlayers = maximumPlayers,
    players = players.map { it.toDomain() },
)

fun GamesResponse.JoinGame.toDomain(): JoinedGame = JoinedGame(
    gameId = gameId,
    gameType = gameType,
    isJoined = isJoined,
    isPrivate = isPrivate,
    host = host.toDomain(),
    message = message,
    courtLocation = courtLocation.toDomain(),
    startTime = startTime,
    endTime = endTime,
    gender = gender,
    levels = levels,
    pricePerPerson = pricePerPerson,
    maximumPlayers = maximumPlayers,
    paymentType = enumValues<PaymentType>().firstOrNull { it.name == paymentType } ?: PaymentType.CASH,
    paymentAccount = paymentAccount,
    currencyType = currencyType,
    players = players.toDomain()
)

fun GamesResponse.GetMyGames.toDomain(): List<Event> {
    return this.games.map { it.toDomain() } + this.tournaments.map { it.toDomain() }
}

fun GamesResponse.GetInvites.toDomain(): List<Event> {
    return this.games.map { it.toDomain() } + this.tournaments.map { it.toDomain() }
}

fun GamesResponse.GetArchive.toDomain(): List<Event> {
    return this.games.map { it.toDomain() } + this.tournaments.map { it.toDomain() }
}

fun GamesResponse.GetUpcoming.toDomain(): List<Event> {
    return this.games.map { it.toDomain() } + this.tournaments.map { it.toDomain() }
}

fun GamePreviewDto.toDomain(): Event = Event(
    id = gameId,
    type = EventType.GAME,
    host = host.toDomain(),
    location = location.toDomain(),
    message = message,
    startTime = startTime,
    endTime = endTime
)
