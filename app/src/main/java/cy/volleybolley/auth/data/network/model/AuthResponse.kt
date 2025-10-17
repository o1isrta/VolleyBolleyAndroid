package cy.volleybolley.auth.data.network.model

import cy.volleybolley.auth.data.dto.UserDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthResponse {
    @Serializable
    class GoogleResponse(
        @SerialName("accessToken") val accessToken: String,
        @SerialName("refreshToken") val refreshToken: String,
        @SerialName("player") val playerUser: UserDto,
    ) : AuthResponse
}
