package cy.volleybolley.registration.domain

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.domain.model.PersonalData
import cy.volleybolley.registration.domain.api.RegistrationRepository

class UserRegistrationUseCase(
    private val repository: RegistrationRepository,
) {
    suspend fun execute(userData: PersonalData): VolleyResult<Unit, ErrorType> {
        return repository.userRegistration(userData)
    }
}
