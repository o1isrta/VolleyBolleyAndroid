package cy.volleybolley.auth.data.network.model

import cy.volleybolley.auth.domain.models.LoginData
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
    @Serializable
    class RefreshAccessTokenResponse(
        @SerialName("access_token") val accessToken: String
    ) : AuthResponse
}

fun AuthResponse.GoogleResponse.toDomain(): LoginData {
    return LoginData(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userPersonalData = playerUser.toPersonalData(),
        isRegistered = playerUser.isRegistered
    )
}
