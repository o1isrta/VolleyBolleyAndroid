package cy.volleybolley.auth.domain.api

import cy.volleybolley.auth.domain.models.LoginData

interface LoginDataRepository {
    suspend fun saveLoginData(loginData: LoginData)
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    suspend fun getIsRegistered(): Boolean
    suspend fun clearAll()
}
