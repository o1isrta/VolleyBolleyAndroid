package cy.volleybolley.auth.domain.api.state

import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.coroutines.flow.StateFlow

interface AuthStateHolder {
    val isAuthenticated: StateFlow<Boolean>
    val personalData: StateFlow<PersonalData?>

    fun setAuthenticated(isAuthenticated: Boolean)
    fun setPersonalData(data: PersonalData?)
    fun clear()
}
