package cy.volleybolley.core.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import cy.volleybolley.authorization.data.dto.UserDto

sealed interface ApiResponse {

    class BadResponse(
        val responseStatusCode: Int = -1,
        val message: String = "Something went wrong, dude. Shit happens..."
    ) : ApiResponse

    // Поля пока просто для примера, до уточнения API.
    @Serializable
    class AuthResponse(
        val someId: Int,
        val someToken: String,
    ): ApiResponse

    @Serializable
    class AuthorizationResponse(
        @SerialName("access_token") val accessToken: String,
        @SerialName("refresh_token") val refreshToken: String,
        val user: UserDto
    ): ApiResponse

}