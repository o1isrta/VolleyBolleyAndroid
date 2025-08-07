package cy.volleybolley.authorization.data.dto

import cy.volleybolley.authorization.domain.model.AuthorizationResult
import cy.volleybolley.authorization.domain.model.Player
import cy.volleybolley.authorization.domain.model.RegistrationData

fun PlayerDto.toDomain() = Player(
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

fun AuthorizationResponse.AuthResponse.toDomain() = AuthorizationResult(
    accessToken = accessToken,
    refreshToken = refreshToken,
    isRegistered = isRegistered,
    player = player.toDomain()
)

fun RegistrationData.toData() = PlayerRegistrationBody(
    firstName = firstName,
    lastName = lastName,
    gender = gender,
    dateOfBirth = birthDate,
    level = level,
    countryId = countryId,
    cityId = cityId
)
