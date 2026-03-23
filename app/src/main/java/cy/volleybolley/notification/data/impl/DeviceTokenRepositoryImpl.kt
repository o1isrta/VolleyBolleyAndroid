package cy.volleybolley.notification.data.impl

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.notification.data.dto.UpdateDeviceTokenRequest
import cy.volleybolley.notification.data.network.firebase.DeviceTokenRequest
import cy.volleybolley.notification.data.network.firebase.DeviceTokenResponse
import cy.volleybolley.notification.domain.api.registration.DeviceTokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeviceTokenRepositoryImpl(
    private val networkClient: NetworkClient<DeviceTokenRequest, DeviceTokenResponse>
) : DeviceTokenRepository {

    override suspend fun updateToken(token: String): VolleyResult<Unit, ErrorType> = withContext(Dispatchers.IO) {
        val body = UpdateDeviceTokenRequest(token = token)
        val response = networkClient.getResponse(DeviceTokenRequest.UpdateToken(body = body))

        if (!response.isSuccess) {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        } else {
            VolleyResult.Success(Unit)
        }
    }
}
