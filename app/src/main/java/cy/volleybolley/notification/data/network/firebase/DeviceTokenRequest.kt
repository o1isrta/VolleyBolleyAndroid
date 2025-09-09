package cy.volleybolley.notification.data.network.firebase

import cy.volleybolley.notification.data.dto.UpdateDeviceTokenRequest

sealed interface DeviceTokenRequest {
    class UpdateToken(
        val path: String = "/api/fcm-token",
        val body: UpdateDeviceTokenRequest
    ) : DeviceTokenRequest
}
