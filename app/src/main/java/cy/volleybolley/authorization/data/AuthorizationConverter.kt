package cy.volleybolley.authorization.data

import cy.volleybolley.authorization.data.dto.AuthorizationResponse
import cy.volleybolley.authorization.data.dto.PlayerDto
import cy.volleybolley.authorization.domain.model.AuthorizationResult
import cy.volleybolley.authorization.domain.model.Player

fun PlayerDto.toPlayer() = Player(
    playerId = playerId,
    avatar = avatar,
    firstName = firstName,
    lastName = lastName,
    gender = gender,
    dateOfBirth = dateOfBirth,
    level = level,
    country = country,
    city = city
)

fun AuthorizationResponse.toAuthorizationResult() = AuthorizationResult(
    accessToken = accessToken,
    refreshToken = refreshToken,
    isRegistered = isRegistered,
    player = player.toPlayer()
)
