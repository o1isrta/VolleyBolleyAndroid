package cy.volleybolley.auth.domain.impl

import cy.volleybolley.auth.domain.TokensInteractor
import cy.volleybolley.auth.domain.TokensRepository

class TokensInteractorImpl(private val repository: TokensRepository) : TokensInteractor {
    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        repository.saveTokens(accessToken, refreshToken)
    }

    override suspend fun getAccessToken(): String? {
        return repository.getAccessToken()
    }

    override suspend fun getRefreshToken(): String? {
        return repository.getRefreshToken()
    }

    override suspend fun clearTokens() {
        repository.clearTokens()
    }
}
