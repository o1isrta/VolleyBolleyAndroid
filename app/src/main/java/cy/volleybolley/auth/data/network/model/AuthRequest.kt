package cy.volleybolley.auth.data.network.model

import cy.volleybolley.auth.data.dto.GoogleAuthRequestBodyDto
import cy.volleybolley.auth.data.dto.RefreshAccessTokenRequestBodyDto

sealed interface AuthRequest {

    data class Google(
        val path: String = "auth/google/login/",
        val body: GoogleAuthRequestBodyDto
    ) : AuthRequest

    data class RefreshAccessToken(
        val path: String = "auth/token/refresh/",
        val body: RefreshAccessTokenRequestBodyDto
    ) : AuthRequest

}
