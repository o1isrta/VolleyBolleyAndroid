package cy.volleybolley.profile.presentation.ui.screens.personaldata

import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.core.presentation.ui.navigation.ChangePhotoRoute
import cy.volleybolley.profile.domain.GetPersonalDataUseCase
import cy.volleybolley.profile.domain.UpdatePersonalDataUseCase
import cy.volleybolley.profile.domain.model.PersonalData
import cy.volleybolley.profile.presentation.ui.screens.personaldata.model.BackAvatarHolder
import cy.volleybolley.profile.presentation.ui.screens.personaldata.model.GenderType
import kotlinx.coroutines.flow.update

class PersonalDataScreenViewModel(
    private val backAvatarHolder: BackAvatarHolder,
    private val getPersonalDataUseCase: GetPersonalDataUseCase,
    private val updatePersonalDataUseCase: UpdatePersonalDataUseCase,
) : BaseViewModel<PersonalDataScreenState, PersonalDataScreenEvent, PersonalDataScreenEffect>(
    initialState = PersonalDataScreenState()
) {
    private var originState: PersonalDataScreenState = uiState.value

    val mockPersonalData = PersonalData(
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
        // getState() пока нельзя тестировать - убрал
        launchSafe(getErrorLogMessage = { "PersonalDataScreen >> init: ${it.message}" }) {
            originState = mockPersonalData.toState()
            uiStateMutable.update { originState }
        }
    }

    override val tag: String = TAG

    override fun obtainEvent(event: PersonalDataScreenEvent) {
        when (event) {
            PersonalDataScreenEvent.OnBackFromPersonalDataClick -> sendUiEffect(
                PersonalDataScreenEffect.NavigateFromPersonalDataScreen(null)
            )

            PersonalDataScreenEvent.OnAvatarEditClick -> sendUiEffect(
                PersonalDataScreenEffect.NavigateFromPersonalDataScreen(
                    ChangePhotoRoute(avatarUrl = originState.avatar)
                )
            )

            PersonalDataScreenEvent.OnUpdateButtonClick -> {
                launchSafe(getErrorLogMessage = { "PersonalDataScreen >> Update button: ${it.message}" }) {
                    val newPersonalData = uiState.value.toPersonalData()
                    updatePersonalDataUseCase.execute(newPersonalData)
                        .onSuccess {
                            getPersonalDataUseCase.execute()
                                .onSuccess { personalData ->
                                    originState = personalData.toState()
                                    uiStateMutable.update { originState }
                                }
                        }
                }
            }

            is PersonalDataScreenEvent.NameChanged -> {
                uiStateMutable.update { checkStateForButtonEnabled(it.copy(name = event.newName)) }
            }

            is PersonalDataScreenEvent.SurnameChanged -> {
                uiStateMutable.update { checkStateForButtonEnabled(it.copy(surname = event.newSurname)) }
            }

            is PersonalDataScreenEvent.GenderSelect -> {
                uiStateMutable.update { checkStateForButtonEnabled(it.copy(genderId = event.genderId)) }
            }

            is PersonalDataScreenEvent.DateSelect -> {
                uiStateMutable.update { checkStateForButtonEnabled(it.copy(dateOfBirth = event.date)) }
            }

            is PersonalDataScreenEvent.CountrySelect -> {}

            is PersonalDataScreenEvent.CitySelect -> {}
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
