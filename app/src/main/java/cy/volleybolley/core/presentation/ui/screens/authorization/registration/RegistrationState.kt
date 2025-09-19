package cy.volleybolley.core.presentation.ui.screens.authorization.registration

import cy.volleybolley.core.presentation.base.UiState

data class RegistrationState(
    val name: String = "",
    val surname: String = "",
    val gender: Int = 1,
    val level: Int = 1,
    val dateOfBirthMillis: Long? = null,
    val country: String = "",
    val city: String = ""
) : UiState
