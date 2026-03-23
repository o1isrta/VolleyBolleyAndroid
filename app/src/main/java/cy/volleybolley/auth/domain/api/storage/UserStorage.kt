package cy.volleybolley.auth.domain.api.storage

import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.coroutines.flow.Flow

interface UserStorage {
    suspend fun savePersonalData(data: PersonalData)
    suspend fun getPersonalData(): PersonalData?
    fun getPersonalDataFlow(): Flow<PersonalData?>
    suspend fun saveIsRegistered(isRegistered: Boolean)
    suspend fun getIsRegistered(): Boolean
    suspend fun clear()
}
