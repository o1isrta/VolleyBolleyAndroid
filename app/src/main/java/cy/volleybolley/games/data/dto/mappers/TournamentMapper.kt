package cy.volleybolley.games.data.dto.mappers

import cy.volleybolley.courts.data.dto.toDomain
import cy.volleybolley.games.data.dto.CreateTournamentDto
import cy.volleybolley.games.data.dto.PlayersDto
import cy.volleybolley.games.data.dto.TournamentPreviewDto
import cy.volleybolley.games.data.dto.mappers.toDomain
import cy.volleybolley.games.data.network.TournamentsResponse
import cy.volleybolley.games.domain.model.event.tournament.CreateTournament
import cy.volleybolley.games.domain.model.event.tournament.CreatedTournament
import cy.volleybolley.games.domain.model.event.Event
import cy.volleybolley.games.domain.model.event.EventType
import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails
import kotlin.collections.map

fun CreateTournament.toData(): CreateTournamentDto = CreateTournamentDto(
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
    teams = teams.map { PlayersDto(it) }
)

fun TournamentsResponse.CreateTournament.toDomain(): CreatedTournament = CreatedTournament(
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
    teams = teams.toDomainShort()
)

fun TournamentsResponse.GetTournamentDetails.toDomain(): TournamentDetails = TournamentDetails(
    tournamentId = tournamentId,
    isIndividual = isIndividual,
    tournamentType = gameType,
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
    teams = teams.map { it.toDomain() },
)

fun TournamentPreviewDto.toDomain(): Event = Event(
    id = tournamentId,
    type = EventType.TOURNAMENT,
    host = host.toDomain(),
    location = location.toDomain(),
    message = message,
    startTime = startTime,
    endTime = endTime
)
