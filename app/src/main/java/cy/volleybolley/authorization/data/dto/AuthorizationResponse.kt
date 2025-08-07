package cy.volleybolley.authorization.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface AuthorizationResponse {
    @Serializable
    class AuthResponse(
        @SerialName("access_token") val accessToken: String,
        @SerialName("refresh_token") val refreshToken: String,
        @SerialName("is_registered") val isRegistered: Boolean,
        val player: PlayerDto
    ): AuthorizationResponse

    object RegistrationResponse : AuthorizationResponse
}

