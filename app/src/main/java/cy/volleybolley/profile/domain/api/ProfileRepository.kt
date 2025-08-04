package cy.volleybolley.profile.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.profile.domain.model.Payment
import cy.volleybolley.profile.domain.model.PersonalData

interface ProfileRepository {
    suspend fun getPersonalData(accessToken: String? = null): VolleyResult<PersonalData, ErrorType>
    suspend fun getPayments(accessToken: String? = null): VolleyResult<List<Payment>, ErrorType>
    suspend fun updatePersonalData(accessToken: String? = null, data: PersonalData): VolleyResult<Unit, ErrorType>
    suspend fun updatePayments(accessToken: String? = null, payments: List<Payment>): VolleyResult<Unit, ErrorType>
    suspend fun updateAvatar(accessToken: String? = null, avatarBase64String: String): VolleyResult<String, ErrorType>
    suspend fun deleteProfile(accessToken: String? = null): VolleyResult<Unit, ErrorType>
}
