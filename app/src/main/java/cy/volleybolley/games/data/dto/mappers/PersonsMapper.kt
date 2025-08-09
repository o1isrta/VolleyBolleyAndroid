package cy.volleybolley.games.data.dto.mappers

import cy.volleybolley.games.data.dto.HostDto
import cy.volleybolley.games.data.dto.PlayerShortDto
import cy.volleybolley.games.data.dto.PlayersDto
import cy.volleybolley.games.data.dto.TeamDto
import cy.volleybolley.games.domain.model.Host
import cy.volleybolley.games.domain.model.PlayerShort
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

fun List<TeamDto>.toDomain(): List<Team> = this.map { it.toDomain() }

fun TeamDto.toDomain(): Team = Team(
    teamId = teamId,
    players = TODO()
)

fun List<Team>.toData(): List<TeamDto> = this.map { it.toData() }

fun Team.toData(): TeamDto = TeamDto(
    teamId = teamId,
    players = TODO()
)

fun List<Team>.toPlayersData(): PlayersDto = PlayersDto(this.map { it.toPlayersData() })

fun Team.toPlayersData(): Int = this.teamId ?: 0
