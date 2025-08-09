package cy.volleybolley.games.data.dto.mappers

import cy.volleybolley.courts.data.dto.toDomain
import cy.volleybolley.games.data.dto.TournamentDto
import cy.volleybolley.games.data.dto.TournamentPreviewDto
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.domain.model.Tournament
import cy.volleybolley.games.domain.model.TournamentPreview

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
    courtId = courtId ?: 0,
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
    teams = teams.toPlayersData()
)

fun GamesResponse.GetTournamentDetails.toDomain(): Tournament = Tournament(
    tournamentId = tournamentId,
    isIndividual = isIndividual,
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
    maximumTeams = maximumTeams,
    teams = teams.toDomain(),
)

fun TournamentPreviewDto.toDomain(): TournamentPreview = TournamentPreview(
    tournamentId = tournamentId,
    host = host.toDomain(),
    location = location.toDomain(),
    message = message,
    startTime = startTime,
    endTime = endTime
)
