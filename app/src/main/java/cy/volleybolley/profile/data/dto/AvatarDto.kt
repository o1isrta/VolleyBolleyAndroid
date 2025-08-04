package cy.volleybolley.profile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AvatarDto(
    @SerialName("avatar") val avatar: String,
)
