package cy.volleybolley.notification.data.network

sealed interface DeviceTokenResponse {
    object Success : DeviceTokenResponse
}
