package cy.volleybolley.core.presentation.ui.screens.profile.state

import cy.volleybolley.core.presentation.base.UiState

data class PersonalDataScreenState(
    val avatar: String? = null,
    val name: String = "",
    val surname: String = "",
    val gender: String = "",
    val dateOfBirth: String = "",
    val country: String = "",
    val city: String = "",
) : UiState
