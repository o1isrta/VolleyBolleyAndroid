package cy.volleybolley.registration.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.domain.model.PersonalData

interface RegistrationRepository {
    suspend fun userRegistration(userData: PersonalData): VolleyResult<Unit, ErrorType>
}
