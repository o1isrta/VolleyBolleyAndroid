package cy.volleybolley.auth.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AuthRequestBodyDto(
    @SerialName("id_token")
    val idToken: String
)
