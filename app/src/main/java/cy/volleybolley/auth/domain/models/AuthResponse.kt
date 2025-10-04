package cy.volleybolley.auth.domain.models

import cy.volleybolley.auth.data.UserDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
    val player: UserDto,
)
