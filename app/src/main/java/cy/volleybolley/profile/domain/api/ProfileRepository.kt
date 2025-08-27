package cy.volleybolley.profile.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.domain.model.Payment
import cy.volleybolley.profile.domain.model.PersonalData

interface ProfileRepository {
    suspend fun getPersonalData(): VolleyResult<PersonalData, ErrorType>
    suspend fun getPayments(): VolleyResult<List<Payment>, ErrorType>
    suspend fun updatePersonalData(data: PersonalData): VolleyResult<Unit, ErrorType>
    suspend fun updatePayments(payments: List<Payment>): VolleyResult<Unit, ErrorType>
    suspend fun updateAvatar(uriString: String?): VolleyResult<String, ErrorType>
    suspend fun deleteProfile(): VolleyResult<Unit, ErrorType>
}
