package cy.volleybolley.core.presentation.ui.screens.profile.state

import cy.volleybolley.core.presentation.base.UiState

data class PersonalDataScreenState(
    val avatar: String? = null,
    val name: String = "",
    val surname: String = "",
    val genderId: Int = 0,
    val dateOfBirth: Long? = null,
    val country: String = "No country",
    val city: String = "No city",
    val countriesList: List<String> = emptyList(),
    val citiesList: List<String> = emptyList(),
    val buttonEnabled: Boolean = false,
) : UiState
