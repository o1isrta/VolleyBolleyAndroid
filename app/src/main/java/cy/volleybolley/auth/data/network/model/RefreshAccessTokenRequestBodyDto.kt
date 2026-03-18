package cy.volleybolley.auth.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RefreshAccessTokenRequestBodyDto(
    @SerialName("refresh_token") val refreshToken: String
)
