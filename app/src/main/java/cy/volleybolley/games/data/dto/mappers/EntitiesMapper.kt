package cy.volleybolley.games.data.dto.mappers

import cy.volleybolley.core.domain.model.LevelType
import cy.volleybolley.games.data.dto.HostDto
import cy.volleybolley.games.data.dto.PlayerShortDto
import cy.volleybolley.games.data.dto.PlayersDto
import cy.volleybolley.games.data.dto.RatePlayerDto
import cy.volleybolley.games.data.dto.RatePlayersDto
import cy.volleybolley.games.data.dto.ShortTeamDto
import cy.volleybolley.games.data.dto.TeamDto
import cy.volleybolley.games.data.network.GamesResponse
import cy.volleybolley.games.domain.model.entity.Host
import cy.volleybolley.games.domain.model.entity.PlayerShort
import cy.volleybolley.games.domain.model.entity.RatePlayer
import cy.volleybolley.games.domain.model.entity.ShortTeam
import cy.volleybolley.games.domain.model.entity.Team
import cy.volleybolley.games.domain.model.event.Preview

fun HostDto.toDomain(): Host = Host(
    id = id,
    name = "$firstName $lastName",
    avatar = avatar,
    level = LevelType.findByLevelName(level)
)

fun PlayerShortDto.toDomain(): PlayerShort = PlayerShort(
    playerId = playerId,
    name = "$firstName $lastName",
    level = LevelType.findByLevelName(level),
    avatar = avatar ?: "",
)

fun TeamDto.toDomain(): Team = Team(
    teamId = teamId ?: -1,
    players = players.toDomain()
)

fun ShortTeamDto.toDomain(): ShortTeam = ShortTeam(
    teamId = teamId ?: -1,
    players = players
)

fun GamesResponse.GetPreview.toDomain(): Preview = Preview(
    upcomingGame = upcomingGame,
    invites = invites
)

fun RatePlayer.toData(): RatePlayerDto = RatePlayerDto(
    playerId = playerId,
    levelChanged = levelChanged.name,
)

fun List<RatePlayer>.toData(): RatePlayersDto = RatePlayersDto(this.map { it.toData() })

fun List<PlayerShortDto>.toDomain(): List<PlayerShort> = this.map { it.toDomain() }

fun List<PlayerShort>.toPlayersData(): PlayersDto = PlayersDto(this.map { it.playerId })
