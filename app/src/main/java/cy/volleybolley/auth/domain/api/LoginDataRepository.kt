package cy.volleybolley.auth.domain.api

import cy.volleybolley.profile.domain.model.PersonalData

interface LoginDataRepository {
    // Токены
    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun clearTokens()

    // Персональные данные
    suspend fun savePersonalData(personalData: PersonalData)
    suspend fun getPersonalData(): PersonalData?
    suspend fun updatePersonalData(personalData: PersonalData)
    suspend fun clearPersonalData()

    // Полная очистка
    suspend fun clearAll()
}
