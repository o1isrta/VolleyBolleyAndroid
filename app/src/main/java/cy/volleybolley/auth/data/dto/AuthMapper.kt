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
        // Тут надо с беком обсудить, чтобы они тоже индексы присылали, ведь мы по словарям ищем страну и город
        // и на других экранах делаем именно так... Пока поставил mock
        countryId = 0,
        cityId = 0,
    )
}

fun AuthResponse.GoogleResponse.toDomain(): LoginData {
    return LoginData(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userPersonalData = playerUser.toPersonalData()
    )
}
