package cy.volleybolley.notification.domain.api.registration

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult

interface DeviceTokenRepository {
    suspend fun updateToken(
        token: String,
    ): VolleyResult<Unit, ErrorType>
}
