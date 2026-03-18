package cy.volleybolley.auth.data

import cy.volleybolley.auth.domain.api.LoginDataRepository
import cy.volleybolley.auth.domain.api.RefreshTokenTimestampRepository
import cy.volleybolley.auth.domain.api.state.AuthStateHolder
import cy.volleybolley.auth.domain.api.storage.TokenStorage
import cy.volleybolley.auth.domain.api.storage.UserStorage
import cy.volleybolley.auth.domain.models.LoginData

class LoginDataRepositoryImpl(
    private val tokenStorage: TokenStorage,
    private val userStorage: UserStorage,
    private val refreshTokenTimestampRepository: RefreshTokenTimestampRepository,
    private val authStateHolder: AuthStateHolder
) : LoginDataRepository {

    override suspend fun saveLoginData(loginData: LoginData) {
        tokenStorage.saveAccessToken(loginData.accessToken)
        tokenStorage.saveRefreshToken(loginData.refreshToken)
        userStorage.savePersonalData(loginData.userPersonalData)
        userStorage.saveIsRegistered(loginData.isRegistered)
        refreshTokenTimestampRepository.saveRefreshTokenTimestamp(System.currentTimeMillis())

        authStateHolder.setAuthenticated(true)
        authStateHolder.setPersonalData(loginData.userPersonalData)
    }

    override suspend fun getAccessToken(): String? {
        return tokenStorage.getAccessToken()
    }

    override suspend fun getRefreshToken(): String? {
        return tokenStorage.getRefreshToken()
    }

    override suspend fun getIsRegistered(): Boolean {
        return userStorage.getIsRegistered()
    }

    override suspend fun clearAll() {
        tokenStorage.clearTokens()
        userStorage.clearUserData()
        refreshTokenTimestampRepository.clearRefreshTokenTimestamp()
        authStateHolder.clear()
    }
}
