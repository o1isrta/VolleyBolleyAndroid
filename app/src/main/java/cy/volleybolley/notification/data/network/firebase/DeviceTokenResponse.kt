package cy.volleybolley.notification.data.network.firebase

sealed interface DeviceTokenResponse {
    object Success : DeviceTokenResponse
}
