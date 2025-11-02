package cy.volleybolley.auth.data.dto

import cy.volleybolley.auth.data.network.model.AuthResponse
import cy.volleybolley.auth.domain.models.LoginData
import cy.volleybolley.profile.domain.model.PersonalData

fun UserDto.toPersonalData(): PersonalData {
    return PersonalData(
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        gender = gender ?: "",
        birthDate = dateOfBirth ?: "",
        level = level ?: "",
        avatar = avatar,
        countryId = country ?: -1,
        cityId = city ?: -1,
    )
}

fun AuthResponse.GoogleResponse.toDomain(): LoginData {
    return LoginData(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userPersonalData = playerUser.toPersonalData()
    )
}
