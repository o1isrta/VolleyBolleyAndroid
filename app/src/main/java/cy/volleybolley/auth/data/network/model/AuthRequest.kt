package cy.volleybolley.auth.data.network.model

import cy.volleybolley.auth.data.dto.GoogleAuthRequestBodyDto

sealed interface AuthRequest {

    data class Google(
        val path: String = "auth/google/login/",
        val body: GoogleAuthRequestBodyDto
    ) : AuthRequest

}
