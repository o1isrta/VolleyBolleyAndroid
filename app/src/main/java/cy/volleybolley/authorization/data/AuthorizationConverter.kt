package cy.volleybolley.authorization.data

import cy.volleybolley.core.data.network.model.ApiResponse.AuthorizationResponse
import cy.volleybolley.authorization.data.dto.UserDto
import cy.volleybolley.authorization.domain.model.AuthorizationResult
import cy.volleybolley.authorization.domain.model.User

fun UserDto.toUser() = User(
    userId = userId,
    isRegistered = isRegistered,
    avatar = avatar,
    firstName = firstName,
    lastName = lastName,
    genderId = genderId,
    dateOfBirth = dateOfBirth,
    levelId = levelId,
    countryId = countryId,
    cityId = cityId
)

fun AuthorizationResponse.toAuthorizationResult() = AuthorizationResult(
    accessToken = accessToken,
    refreshToken = refreshToken,
    user = user.toUser()
)