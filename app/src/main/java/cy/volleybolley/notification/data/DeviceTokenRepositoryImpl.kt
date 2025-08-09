package cy.volleybolley.notification.data

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.notification.data.dto.UpdateDeviceTokenRequest
import cy.volleybolley.notification.data.network.DeviceTokenRequest
import cy.volleybolley.notification.data.network.DeviceTokenResponse
import cy.volleybolley.notification.domain.api.registration.DeviceTokenRepository

class DeviceTokenRepositoryImpl(
    private val networkClient: NetworkClient<DeviceTokenRequest, DeviceTokenResponse>
) : DeviceTokenRepository {
    override suspend fun updateToken(token: String): VolleyResult<Unit, ErrorType> {
        val body = UpdateDeviceTokenRequest(
            token = token,
        )
        val response = networkClient.getResponse(DeviceTokenRequest.UpdateToken(body = body))

        if (!response.isSuccess) {
            return VolleyResult.Failure(response.resultCode.mapToErrorType())
        }

        return VolleyResult.Success(Unit)
    }
}
