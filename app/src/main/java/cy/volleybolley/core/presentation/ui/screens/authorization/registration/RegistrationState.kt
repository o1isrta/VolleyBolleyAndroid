package cy.volleybolley.core.presentation.ui.screens.authorization.registration

import cy.volleybolley.auth.data.dto.UserDto
import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.referencedata.domain.model.City
import cy.volleybolley.referencedata.domain.model.Country

data class RegistrationState(
    val user: UserDto? = null,
    val name: String = "",
    val surname: String = "",
    val gender: Int = 1,
    val level: Int = 1,
    val dateOfBirthMillis: Long? = null,
    val country: String = "",
    val selectedCountry: Country? = null,
    val countryList: List<Country> = emptyList(),
    val selectedCity: City? = null,
    val cityList: List<City> = emptyList(),
    val isBtnRegistrationEnabled: Boolean = false,
    val isLoading: Boolean = false
) : UiState
