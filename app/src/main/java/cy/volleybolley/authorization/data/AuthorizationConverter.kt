package cy.volleybolley.authorization.data

import cy.volleybolley.authorization.data.dto.AuthorizationResponse
import cy.volleybolley.authorization.data.dto.PlayerDto
import cy.volleybolley.authorization.data.dto.PlayerRegistrationBody
import cy.volleybolley.authorization.domain.model.AuthorizationResult
import cy.volleybolley.authorization.domain.model.Player
import cy.volleybolley.authorization.domain.model.RegistrationData

fun PlayerDto.toPlayer() = Player(
    playerId = playerId,
    avatar = avatar,
    firstName = firstName,
    lastName = lastName,
    gender = gender,
    dateOfBirth = dateOfBirth,
    level = level,
    countryId = countryId,
    cityId = cityId
)

fun AuthorizationResponse.AuthResponse.toAuthorizationResult() = AuthorizationResult(
    accessToken = accessToken,
    refreshToken = refreshToken,
    isRegistered = isRegistered,
    player = player.toPlayer()
)

fun RegistrationData.toPlayerRegistrationBody() = PlayerRegistrationBody(
    firstName = firstName,
    lastName = lastName,
    gender = gender,
    dateOfBirth = birthDate,
    level = level,
    countryId = countryId,
    cityId = cityId
)
