package cy.volleybolley.authorization.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationBody(
    @SerialName("id_token") val idToken: String
)
