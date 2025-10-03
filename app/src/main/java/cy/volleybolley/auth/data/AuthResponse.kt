package cy.volleybolley.auth.data

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val player: UserDto,
)
