package cy.volleybolley.notification.domain.impl

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.notification.domain.api.registration.DeviceTokenRepository
import cy.volleybolley.notification.domain.api.registration.SendDeviceTokenUseCase

class SendDeviceTokenUseCaseImpl(
    private val repository: DeviceTokenRepository
) : SendDeviceTokenUseCase {
    override suspend fun updateToken(token: String): VolleyResult<Unit, ErrorType> {
        return repository.updateToken(token)
    }
}
