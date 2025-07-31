package cy.volleybolley.authorization.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

 @Serializable
 class AuthorizationResponse(
     @SerialName("access_token") val accessToken: String,
     @SerialName("refresh_token") val refreshToken: String,
     @SerialName("is_registered") val isRegistered: Boolean,
     val player: PlayerDto
 )

