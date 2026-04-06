package cy.volleybolley.profile.presentation.ui.screens.personaldata

import cy.volleybolley.auth.domain.api.usecase.GetPersonalDataUseCase
import cy.volleybolley.core.domain.model.onFailure
import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.navigation.ChangePhotoRoute
import cy.volleybolley.core.util.VolleyLog
import cy.volleybolley.profile.domain.UpdatePersonalDataUseCase
import cy.volleybolley.profile.domain.model.PersonalData
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEffect.NavigateFromPersonalDataScreen
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEffect.ShowToast
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.CitySelect
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.CountrySelect
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.DateSelect
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.GenderSelect
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.NameChanged
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.OnAvatarEditClick
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.OnBackFromPersonalDataClick
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.OnUpdateButtonClick
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.SurnameChanged
import cy.volleybolley.profile.presentation.ui.screens.personaldata.mapper.toPersonalData
import cy.volleybolley.profile.presentation.ui.screens.personaldata.mapper.withCountriesToState
import cy.volleybolley.profile.presentation.ui.screens.personaldata.model.BackAvatarHolder
import cy.volleybolley.referencedata.domain.api.GetCountriesUseCase
import cy.volleybolley.referencedata.domain.model.Country
import kotlinx.coroutines.flow.update

class PersonalDataScreenViewModel(
    private val backAvatarHolder: BackAvatarHolder,
    private val getPersonalDataUseCase: GetPersonalDataUseCase,
    private val updatePersonalDataUseCase: UpdatePersonalDataUseCase,
    private val getCountriesUseCase: GetCountriesUseCase,
) : BaseViewModel<PersonalDataScreenState, PersonalDataScreenEvent, PersonalDataScreenEffect>(
    initialState = PersonalDataScreenState()
) {
    private var originState: PersonalDataScreenState = uiState.value

    init {
        initScreenState()
    }

    override fun obtainEvent(event: PersonalDataScreenEvent) {
        when (event) {
            OnBackFromPersonalDataClick -> sendUiEffect(
                NavigateFromPersonalDataScreen(null)
            )

            OnAvatarEditClick -> sendUiEffect(
                NavigateFromPersonalDataScreen(
                    ChangePhotoRoute(avatarUrl = originState.avatar)
                )
            )

            OnUpdateButtonClick -> onUpdateClick()

            is NameChanged -> {
                uiStateMutable.update { checkStateForButtonEnabled(it.copy(name = event.newName)) }
            }

            is SurnameChanged -> {
                uiStateMutable.update { checkStateForButtonEnabled(it.copy(surname = event.newSurname)) }
            }

            is GenderSelect -> {
                uiStateMutable.update { checkStateForButtonEnabled(it.copy(genderId = event.genderId)) }
            }

            is DateSelect -> {
                uiStateMutable.update { checkStateForButtonEnabled(it.copy(dateOfBirthMillis = event.date)) }
            }

            is CountrySelect -> onCountrySelected(event.country)

            is CitySelect -> {
                uiStateMutable.update { checkStateForButtonEnabled(it.copy(selectedCity = event.city)) }
            }
        }
    }

    fun handleBackAvatar() {
        backAvatarHolder.getAvatarChanges()?.let { avatarValue ->
            val newAvatar = avatarValue.ifEmpty { null }
            originState = originState.copy(avatar = newAvatar)
            uiStateMutable.update { it.copy(avatar = newAvatar) }
            backAvatarHolder.clearBackAvatar()
        }
    }

    private fun onUpdateClick() {
        launchSafe(
            getErrorLogMessage = {
                "PersonalDataScreen >>> onUpdateClick() >>> error: ${it.message}"
            },
            onError = { sendUiEffect(ShowToast("Update is failure with error: ${it.message}")) }
        ) {
            uiStateMutable.update { it.copy(isLoading = true) }
            val newPersonalData = uiState.value.toPersonalData()
            updatePersonalDataUseCase.execute(newPersonalData)
                .onSuccess {
                    originState = uiState.value.copy(buttonEnabled = false, isLoading = false)
                    uiStateMutable.update { originState }
                    sendUiEffect(ShowToast("Update is successful"))
                }
                .onFailure { error ->
                    VolleyLog.e(tag, "PersonalDataScreen >>> updatePersonalDataUseCase(): $error")
                    sendUiEffect(ShowToast("Update is failure"))

                }
        }
    }

    private fun onCountrySelected(country: Country) {
        uiStateMutable.update {
            checkStateForButtonEnabled(
                it.copy(
                    selectedCountry = country,
                    selectedCity = null,
                    cityList = country.cities
                )
            )
        }
    }

    private fun initScreenState() {
        launchSafe(
            getErrorLogMessage = {
                "PersonalDataScreen >>> initScreenState() >>> countries + personal data: ${it.message}"
            },
            onError = { sendUiEffect(ShowToast("Data init is failure with error: ${it.message}")) }
        ) {
            val personalData = getPersonalDataUseCase.execute()
            getCountriesUseCase.execute()
                .onSuccess { countries ->
                    uiStateMutable.update {
                        handleAndSetOriginStateOnInit(personalData, countries)
                    }
                }
                .onFailure { error ->
                    VolleyLog.e(tag, "PersonalDataScreen >>> getCountriesUseCase(): $error")
                    sendUiEffect(ShowToast("Get countries is failure"))
                    uiStateMutable.update {
                        handleAndSetOriginStateOnInit(personalData)
                    }
                }
        }
    }

    private fun handleAndSetOriginStateOnInit(
        personalData: PersonalData?,
        countries: List<Country>? = null
    ): PersonalDataScreenState {
        originState = personalData
            .withCountriesToState(
                countries = countries,
                stateForButtonEnabledFlag = originState
            )
        return originState
    }

    private fun checkStateForButtonEnabled(newState: PersonalDataScreenState): PersonalDataScreenState {
        return newState.copy(
            buttonEnabled = newState.isPersonalDataNotEmpty() && originState.isPersonalDataChanged(newState)
        )
    }
}
