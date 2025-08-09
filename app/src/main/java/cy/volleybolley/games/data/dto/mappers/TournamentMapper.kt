package cy.volleybolley.games.data.dto.mappers

import cy.volleybolley.games.data.dto.TournamentDto
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.domain.model.Tournament
import cy.volleybolley.games.domain.model.TournamentDetails

fun GamesResponse.CreateTournament.toDomain(): Tournament = Tournament(
    tournamentId = tournamentId,
    courtId = courtId,
    message = message,
    startTime = startTime,
    endTime = endTime,
    gender = gender,
    levels = levels,
    isIndividual = isIndividual,
    maximumPlayers = maximumPlayers,
    maximumTeams = maximumTeams,
    pricePerPerson = price,
    paymentType = paymentType,
    paymentAccount = paymentAccount,
    currencyType = currencyType,
    teams = teams.toDomain()
)

fun Tournament.toData(): TournamentDto = TournamentDto(
    courtId = courtId,
    message = message,
    startTime = startTime,
    endTime = endTime,
    isIndividual = isIndividual,
    gender = gender,
    levels = levels,
    maximumPlayers = maximumPlayers,
    maximumTeams = maximumTeams,
    price = pricePerPerson,
    paymentType = paymentType,
    teams = teams.toData()
)

fun GamesResponse.GetTournamentDetails.toDomain(): TournamentDetails = TournamentDetails(
    tournamentId = TODO(),
    gameType = TODO(),
    host = TODO(),
    message = TODO(),
    courtLocation = TODO(),
    startTime = TODO(),
    endTime = TODO(),
    levels = TODO(),
    gender = TODO(),
    pricePerPerson = TODO(),
    currencyType = TODO(),
    paymentType = TODO(),
    paymentAccount = TODO(),
    maximumPlayers = TODO(),
    maximumTeams = TODO(),
    players = TODO()
)
