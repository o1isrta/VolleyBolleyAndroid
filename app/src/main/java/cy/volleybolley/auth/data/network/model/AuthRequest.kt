package cy.volleybolley.auth.data.network.model

sealed interface AuthRequest {
    class Google(
        val path: String = "/auth/google/login/",
        val body: AuthRequestBodyDto
    ) : AuthRequest

    class Phone(
        val path: String = "/auth/phone-number/login/",
        val body: AuthRequestBodyDto
    ) : AuthRequest

    class RefreshAccessToken(
        val path: String = "/auth/token/refresh/",
        val body: RefreshAccessTokenRequestBodyDto
    ) : AuthRequest
}
