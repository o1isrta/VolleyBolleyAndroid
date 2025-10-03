package cy.volleybolley.auth.data

import cy.volleybolley.core.data.network.model.Response


class AuthRepositoryImpl(private val networkClient: AuthNetworkClient) {
    suspend fun loginWithGoogle(idToken: String): Response<AuthResponse> {
        return networkClient.getResponse(
            AuthRequestType.Google(GoogleAuthRequest(idToken))
        )
    }
}
