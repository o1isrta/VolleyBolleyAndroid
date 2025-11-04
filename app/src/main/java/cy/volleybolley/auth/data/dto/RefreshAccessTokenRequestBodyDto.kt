package cy.volleybolley.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshAccessTokenRequestBodyDto(
    @SerialName("refresh_token") val refreshToken: String
)
