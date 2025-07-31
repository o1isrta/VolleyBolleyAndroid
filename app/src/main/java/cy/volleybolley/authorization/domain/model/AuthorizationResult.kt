package cy.volleybolley.authorization.domain.model

data class AuthorizationResult(
    val accessToken: String,
    val refreshToken: String,
    val isRegistered: Boolean,
    val player: Player
)
