package cy.volleybolley.profile.presentation.ui.screens.personaldata

import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyMocks
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.core.presentation.ui.navigation.ChangePhotoRoute
import cy.volleybolley.profile.domain.GetPersonalDataUseCase
import cy.volleybolley.profile.domain.UpdatePersonalDataUseCase
import cy.volleybolley.profile.domain.model.PersonalData
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEffect.NavigateFromPersonalDataScreen
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.CitySelect
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.CountrySelect
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.DateSelect
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.GenderSelect
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.NameChanged
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.OnAvatarEditClick
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.OnBackFromPersonalDataClick
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.OnUpdateButtonClick
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.SurnameChanged
import cy.volleybolley.profile.presentation.ui.screens.personaldata.model.BackAvatarHolder
import cy.volleybolley.profile.presentation.ui.screens.personaldata.model.GenderType
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
        // getCountriesList() - сперва подтягиваем страны в originState
        originState = originState.copy(countryList = VolleyMocks.countries)

        // getState() - затем сохраняем персональные данные в originState
        launchSafe(getErrorLogMessage = { "PersonalDataScreen >> init: ${it.message}" }) {
            originState = VolleyMocks.mockPersonalData.addToState()
            uiStateMutable.update { originState }
        }
    }

    override val tag: String = PersonalDataScreenViewModel::class.simpleName ?: "PersonalDataScreenViewModel"

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
            val newAvatar = if (avatarValue.isEmpty()) null else avatarValue
            originState = originState.copy(avatar = newAvatar)
            uiStateMutable.update { it.copy(avatar = newAvatar) }
            backAvatarHolder.clearBackAvatar()
        }
    }

    private fun onUpdateClick() {
        launchSafe(
            getErrorLogMessage = { "PersonalDataScreen >> Update button: ${it.message}" },
            block = {
                val newPersonalData = uiState.value.toPersonalData()
                updatePersonalDataUseCase.execute(newPersonalData)
                    .onSuccess {
                        getPersonalDataUseCase.execute()
                            .onSuccess { personalData ->
                                originState = personalData.addToState()
                                uiStateMutable.update { originState }
                            }
                    }
            }
        )
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

    private fun PersonalData.addToState(): PersonalDataScreenState {
        val countryById = originState.countryList.find { it.id == countryId }
        return PersonalDataScreenState(
            avatar = avatar,
            name = firstName,
            surname = lastName,
            levelHolder = level,
            genderId = GenderType.Companion.getIdByStringValue(gender),
            dateOfBirthMillis = VolleyUiUtil.convertTextDateToMillis(
                VolleyUiUtil.DATE_OF_BIRTH_PATTERN_FOR_SERVER,
                birthDate
            ),
            selectedCountry = countryById,
            selectedCity = countryById?.cities?.find { it.id == cityId },
            countryList = originState.countryList,
            cityList = countryById?.cities ?: emptyList(),
            buttonEnabled = originState.buttonEnabled
        )
    }

    private fun PersonalDataScreenState.toPersonalData(): PersonalData {
        return PersonalData(
            firstName = name,
            lastName = surname,
            gender = GenderType.Companion.getNameValueById(genderId),
            birthDate = dateOfBirthMillis?.let {
                VolleyUiUtil.convertMillisToTextDate(
                    VolleyUiUtil.DATE_OF_BIRTH_PATTERN_FOR_SERVER,
                    it
                )
            } ?: "2000-12-31",
            level = levelHolder,
            countryId = selectedCountry?.id ?: -1,
            cityId = selectedCity?.id ?: -1,
            avatar = avatar
        )
    }

    private fun checkStateForButtonEnabled(newState: PersonalDataScreenState): PersonalDataScreenState {
        val checkState = if (newState.buttonEnabled) newState.copy(buttonEnabled = false) else newState
        return if (checkState == originState) originState else newState.copy(buttonEnabled = true)
    }
}
