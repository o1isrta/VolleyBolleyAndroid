package cy.volleybolley.auth.data.network.model

import cy.volleybolley.auth.data.dto.UserDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthResponse {
    @Serializable
    class GoogleResponse(
        @SerialName("access_token") val accessToken: String,
        @SerialName("refresh_token") val refreshToken: String,
        @SerialName("player") val playerUser: UserDto,
    ) : AuthResponse
}
