package cy.volleybolley.profile.domain

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.domain.api.ProfileRepository
import cy.volleybolley.profile.domain.model.Payment

class UpdatePaymentsUseCase(
    private val repository: ProfileRepository,
) {
    suspend fun execute(payments: List<Payment>): VolleyResult<Unit, ErrorType> {
        return repository.updatePayments(payments)
    }
}
