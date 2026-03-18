package cy.volleybolley.auth.data.state

import cy.volleybolley.auth.domain.api.state.AuthStateHolder
import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthStateHolderImpl(
    initialAuthenticated: Boolean,
    initialPersonalData: PersonalData?
) : AuthStateHolder {

    private val _isAuthenticated = MutableStateFlow(initialAuthenticated)
    override val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    private val _personalData = MutableStateFlow(initialPersonalData)
    override val personalData: StateFlow<PersonalData?> = _personalData.asStateFlow()

    override fun setAuthenticated(isAuthenticated: Boolean) {
        _isAuthenticated.value = isAuthenticated
    }

    override fun setPersonalData(data: PersonalData?) {
        _personalData.value = data
    }

    override fun clear() {
        _isAuthenticated.value = false
        _personalData.value = null
    }
}
