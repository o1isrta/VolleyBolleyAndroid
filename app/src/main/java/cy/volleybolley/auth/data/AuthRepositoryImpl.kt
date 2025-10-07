package cy.volleybolley.auth.data

import cy.volleybolley.auth.domain.AuthRepository
import cy.volleybolley.auth.domain.models.AuthResponse
import cy.volleybolley.core.data.network.model.Response

class AuthRepositoryImpl(private val networkClient: AuthNetworkClient) : AuthRepository {
    override suspend fun loginWithGoogle(idToken: String): Response<AuthResponse> {
        return networkClient.getResponse(
            AuthRequestType.Google(GoogleAuthRequest(idToken))
        )
    }
}
