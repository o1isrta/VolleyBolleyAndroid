package cy.volleybolley.games.data.dto.mappers

import cy.volleybolley.games.data.dto.HostDto
import cy.volleybolley.games.data.dto.PlayerShortDto
import cy.volleybolley.games.data.dto.PlayersDto
import cy.volleybolley.games.data.dto.TeamDto
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.domain.model.Host
import cy.volleybolley.games.domain.model.PlayerShort
import cy.volleybolley.games.domain.model.Preview
import cy.volleybolley.games.domain.model.Team

fun HostDto.toDomain(): Host = Host(
    id = id,
    name = "$firstName $lastName",
    avatar = avatar,
    level = level
)

fun PlayerShortDto.toDomain(): PlayerShort = PlayerShort(
    playerId = playerId,
    name = "$firstName $lastName",
    level = level,
    avatar = avatar,
)

fun PlayerShort.toData(): PlayerShortDto = PlayerShortDto(
    playerId = playerId,
    firstName = name?.split(" ")[0],
    lastName = name?.split(" ")[1],
    level = level,
    avatar = avatar
)

fun TeamDto.toDomain(): Team = Team(
    teamId = teamId,
    players = players.toDomain()
)

fun Team.toData(): TeamDto = TeamDto(
    teamId = teamId,
    players = players.toData()
)

fun GamesResponse.GetPreview.toDomain(): Preview = Preview(
    upcomingGame = upcomingGame,
    invites = invites
)

fun List<PlayerShortDto>.toDomain(): List<PlayerShort> = this.map { it.toDomain() }

fun List<PlayerShort>.toData(): List<PlayerShortDto> = this.map { it.toData() }

fun List<PlayerShort>.toPlayersData():PlayersDto  = PlayersDto(this.map { it.playerId })

fun List<TeamDto>.toDomain(): List<Team> = this.map { it.toDomain() }

fun List<Team>.toData(): List<TeamDto> = this.map { it.toData() }

fun List<Team>.toPlayersData(): List<PlayersDto> {
    val players = mutableListOf<PlayersDto>()
    this.map {
        players.add(PlayersDto(it.players.toListInt()))
    }
    return players.toList()
}

fun List<PlayerShort>.toListInt(): List<Int> = this.map { it.playerId }

