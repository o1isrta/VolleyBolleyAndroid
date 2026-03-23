package cy.volleybolley.notification.data.dto

import cy.volleybolley.notification.utils.Constants.ANDROID_SYSTEM
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDeviceTokenRequest(
    @SerialName("token") val token: String?,
    @SerialName("platform") val platform: String = ANDROID_SYSTEM
)
