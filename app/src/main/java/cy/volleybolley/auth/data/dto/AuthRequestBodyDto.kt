package cy.volleybolley.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestBodyDto(
    @SerialName("id_token") val idToken: String
)
