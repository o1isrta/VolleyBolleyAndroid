package cy.volleybolley.auth.data

import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.RefreshTokenTimestampStorage
import cy.volleybolley.auth.domain.api.storage.TokenStorage
import cy.volleybolley.auth.domain.api.storage.UserStorage
import cy.volleybolley.auth.domain.models.LoginData

class LoginDataRepositoryImpl(
    private val tokenStorage: TokenStorage,
    private val userStorage: UserStorage,
    private val refreshTokenTimestampStorage: RefreshTokenTimestampStorage
) : LoginDataRepository {

    override suspend fun saveLoginData(loginData: LoginData) {
        tokenStorage.saveAccessToken(loginData.accessToken)
        tokenStorage.saveRefreshToken(loginData.refreshToken)
        userStorage.savePersonalData(loginData.userPersonalData)
        userStorage.saveIsRegistered(loginData.isRegistered)
        refreshTokenTimestampStorage.saveRefreshTokenTimestamp(System.currentTimeMillis())
    }

    override fun getAccessToken(): String? {
        return tokenStorage.getAccessToken()
    }

    override fun getRefreshToken(): String? {
        return tokenStorage.getRefreshToken()
    }

    override suspend fun getIsRegistered(): Boolean {
        return userStorage.getIsRegistered()
    }

    override suspend fun clearAll() {
        tokenStorage.clear()
        userStorage.clear()
        refreshTokenTimestampStorage.clear()
    }
}
