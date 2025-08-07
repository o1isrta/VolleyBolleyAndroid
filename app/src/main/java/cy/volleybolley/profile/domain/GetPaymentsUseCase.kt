package cy.volleybolley.profile.domain

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.domain.api.ProfileRepository
import cy.volleybolley.profile.domain.model.Payment

class GetPaymentsUseCase(
    private val repository: ProfileRepository,
) {
    suspend fun execute(): VolleyResult<List<Payment>, ErrorType> {
        return repository.getPayments()
    }
}
