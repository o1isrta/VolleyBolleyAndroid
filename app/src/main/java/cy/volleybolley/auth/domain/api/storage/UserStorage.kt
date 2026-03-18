package cy.volleybolley.auth.domain.api.storage

import cy.volleybolley.profile.domain.model.PersonalData

interface UserStorage {
    suspend fun savePersonalData(data: PersonalData)
    suspend fun getPersonalData(): PersonalData?
    suspend fun saveIsRegistered(isRegistered: Boolean)
    suspend fun getIsRegistered(): Boolean
    suspend fun clear()
}
