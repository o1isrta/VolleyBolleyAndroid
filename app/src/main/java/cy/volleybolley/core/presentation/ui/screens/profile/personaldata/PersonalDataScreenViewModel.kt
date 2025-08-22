package cy.volleybolley.core.presentation.ui.screens.profile.personaldata

import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.core.presentation.ui.navigation.ChangePhotoRoute
import cy.volleybolley.core.presentation.ui.screens.profile.personaldata.PersonalDataScreenEvent.CitySelect
import cy.volleybolley.core.presentation.ui.screens.profile.personaldata.PersonalDataScreenEvent.CountrySelect
import cy.volleybolley.core.presentation.ui.screens.profile.personaldata.PersonalDataScreenEvent.DateSelect
import cy.volleybolley.core.presentation.ui.screens.profile.personaldata.PersonalDataScreenEvent.GenderSelect
import cy.volleybolley.core.presentation.ui.screens.profile.personaldata.PersonalDataScreenEvent.NameChanged
import cy.volleybolley.core.presentation.ui.screens.profile.personaldata.PersonalDataScreenEvent.OnAvatarEditClick
import cy.volleybolley.core.presentation.ui.screens.profile.personaldata.PersonalDataScreenEvent.OnBackFromPersonalDataClick
import cy.volleybolley.core.presentation.ui.screens.profile.personaldata.PersonalDataScreenEvent.OnUpdateButtonClick
import cy.volleybolley.core.presentation.ui.screens.profile.personaldata.PersonalDataScreenEvent.SurnameChanged
import cy.volleybolley.core.presentation.ui.screens.profile.personaldata.model.GenderType
import cy.volleybolley.profile.domain.GetPersonalDataUseCase
import cy.volleybolley.profile.domain.UpdatePersonalDataUseCase
import cy.volleybolley.profile.domain.model.PersonalData
import kotlinx.coroutines.flow.update

class PersonalDataScreenViewModel(
    private val getPersonalDataUseCase: GetPersonalDataUseCase,
    private val updatePersonalDataUseCase: UpdatePersonalDataUseCase,
) : BaseViewModel<PersonalDataScreenState, PersonalDataScreenEvent, PersonalDataScreenEffect>(
    initialState = PersonalDataScreenState()
){
    private lateinit var originState: PersonalDataScreenState
    // Mock PersonalData
    val personalData = PersonalData(
        firstName = "Anonymous",
        lastName = "Nemislimus",
        gender = "MALE",
        birthDate = "1987-03-23",
        level = "LIGHT",
        countryId = 2,
        cityId = 202,
        avatar = "https://cdn.fishki.net/upload/post/2021/03/29/3682461/gallery/tn/wil-hughes-troll-face.jpg"
    )

    init {
        // getState()
        launchSafe(getErrorLogMessage = { "PersonalDataScreen >> init: ${it.message}" }) {
            originState = personalData.toState()
            _uiState.update { originState }
        }
    }

    override val tag: String = TAG

    override fun obtainEvent(event: PersonalDataScreenEvent) {
        when(event) {
            OnBackFromPersonalDataClick -> sendUiEffect(
                PersonalDataScreenEffect.NavigateFromPersonalDataScreen(null)
            )

            OnAvatarEditClick -> sendUiEffect(
                PersonalDataScreenEffect.NavigateFromPersonalDataScreen(
                    ChangePhotoRoute(avatarUrl = originState.avatar)
                )
            )

            OnUpdateButtonClick -> {
                launchSafe(getErrorLogMessage = { "PersonalDataScreen >> Update button: ${it.message}" }) {
                    val newPersonalData = uiState.value.toPersonalData()
                    updatePersonalDataUseCase.execute(newPersonalData)
                        .onSuccess {
                            getPersonalDataUseCase.execute()
                                .onSuccess { personalData ->
                                    originState = personalData.toState()
                                    _uiState.update { originState }
                                }
                        }
                }
            }

            is NameChanged -> {
                _uiState.update { checkStateForButtonEnabled(it.copy(name = event.newName)) }
            }

            is SurnameChanged -> {
                _uiState.update { checkStateForButtonEnabled(it.copy(surname = event.newSurname)) }
            }

            is GenderSelect -> {
                _uiState.update { checkStateForButtonEnabled(it.copy(genderId = event.genderId)) }
            }

            is DateSelect -> {
                _uiState.update { checkStateForButtonEnabled(it.copy(dateOfBirth = event.date)) }
            }

            is CountrySelect -> {}

            is CitySelect -> {}
        }

    }

    // Неполный метод, потом дописать как будет реализовано API
    private fun getState() {
        launchSafe(getErrorLogMessage = { "PersonalDataScreen >> getState: ${it.message}" }) {
            getPersonalDataUseCase.execute()
                .onSuccess {
                    originState = it.toState()
                    _uiState.update { originState }
                }
        }
    }

    // Неполный метод, потом дописать как будет реализовано API
    private fun PersonalData.toState(): PersonalDataScreenState {
        return PersonalDataScreenState(
            avatar = avatar,
            name = firstName,
            surname = lastName,
            genderId = GenderType.Companion.getIdByStringValue(gender),
            dateOfBirth = VolleyUiUtil.convertTextDateToMillis(
                VolleyUiUtil.DATE_OF_BIRTH_PATTERN_FOR_SERVER,
                birthDate
            ),
        )
    }

    private fun PersonalDataScreenState.toPersonalData(): PersonalData {
        return PersonalData(
            firstName = name,
            lastName = surname,
            gender = GenderType.Companion.getNameValueById(genderId),
            birthDate = dateOfBirth?.let {
                VolleyUiUtil.convertMillisToTextDate(
                    VolleyUiUtil.DATE_OF_BIRTH_PATTERN_FOR_SERVER,
                    it
                )
            } ?: "2000-12-31",
            level = "",
            countryId = -1,
            cityId = -1,
            avatar = avatar
        )
    }

    private fun checkStateForButtonEnabled(newState: PersonalDataScreenState): PersonalDataScreenState {
        val checkState = if (newState.buttonEnabled) newState.copy(buttonEnabled = false) else newState
        return if (checkState == originState) originState else newState.copy(buttonEnabled = true)
    }

    companion object {
        val TAG: String = PersonalDataScreenViewModel::class.simpleName ?: "PersonalDataScreenViewModel"
    }
}
