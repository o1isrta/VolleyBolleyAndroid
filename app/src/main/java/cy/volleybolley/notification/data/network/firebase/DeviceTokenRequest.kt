package cy.volleybolley.notification.data.network.firebase

import cy.volleybolley.notification.data.dto.UpdateDeviceTokenRequest

sealed interface DeviceTokenRequest {
    val accessToken: String?
        get() = null
    class UpdateToken(
        val path: String = FCM_PATH,
        val body: UpdateDeviceTokenRequest
    ) : DeviceTokenRequest

    companion object {
        private const val FCM_PATH = "/notifications/fcm-auth"
    }
}
