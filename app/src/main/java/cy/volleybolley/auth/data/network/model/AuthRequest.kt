package cy.volleybolley.auth.data.network.model

sealed interface AuthRequest {
    class Google(val body: AuthRequestBodyDto) : AuthRequest {
        companion object {
            const val PATH = "/auth/google/login/"
        }
    }

    class Phone(val body: AuthRequestBodyDto) : AuthRequest {
        companion object {
            const val PATH = "/auth/phone-number/login/"
        }
    }

    class RefreshAccessToken(
        val body: RefreshAccessTokenRequestBodyDto
    ) : AuthRequest {
        companion object {
            const val PATH = "/auth/token/refresh/"
        }
    }
}
