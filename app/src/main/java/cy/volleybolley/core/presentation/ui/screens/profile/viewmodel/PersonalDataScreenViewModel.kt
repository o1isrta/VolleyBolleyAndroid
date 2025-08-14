package cy.volleybolley.core.presentation.ui.screens.profile.viewmodel

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.navigation.ChangePhotoRoute
import cy.volleybolley.core.presentation.ui.screens.profile.effect.PersonalDataScreenEffect
import cy.volleybolley.core.presentation.ui.screens.profile.effect.PersonalDataScreenEffect.NavigateOnOtherScreen
import cy.volleybolley.core.presentation.ui.screens.profile.event.PersonalDataScreenEvent
import cy.volleybolley.core.presentation.ui.screens.profile.event.PersonalDataScreenEvent.CitySelect
import cy.volleybolley.core.presentation.ui.screens.profile.event.PersonalDataScreenEvent.CountrySelect
import cy.volleybolley.core.presentation.ui.screens.profile.event.PersonalDataScreenEvent.DateSelect
import cy.volleybolley.core.presentation.ui.screens.profile.event.PersonalDataScreenEvent.GenderSelect
import cy.volleybolley.core.presentation.ui.screens.profile.event.PersonalDataScreenEvent.NameChanged
import cy.volleybolley.core.presentation.ui.screens.profile.event.PersonalDataScreenEvent.OnAvatarEditClick
import cy.volleybolley.core.presentation.ui.screens.profile.event.PersonalDataScreenEvent.OnBackFromProfileClick
import cy.volleybolley.core.presentation.ui.screens.profile.event.PersonalDataScreenEvent.SurnameChanged
import cy.volleybolley.core.presentation.ui.screens.profile.state.PersonalDataScreenState
import cy.volleybolley.profile.domain.GetPersonalDataUseCase
import cy.volleybolley.profile.domain.UpdatePersonalDataUseCase

class PersonalDataScreenViewModel(
    private val getPersonalDataUseCase: GetPersonalDataUseCase,
    private val updatePersonalDataUseCase: UpdatePersonalDataUseCase,
) : BaseViewModel<PersonalDataScreenState, PersonalDataScreenEvent, PersonalDataScreenEffect>(
    initialState = PersonalDataScreenState()
){
    override val tag: String = TAG

    override fun obtainEvent(event: PersonalDataScreenEvent) {
        when(event) {
            OnBackFromProfileClick -> { NavigateOnOtherScreen(null) }
            OnAvatarEditClick -> { NavigateOnOtherScreen(ChangePhotoRoute) }
            is NameChanged -> {}
            is SurnameChanged -> {}
            is GenderSelect -> {}
            is DateSelect -> {}
            is CountrySelect -> {}
            is CitySelect -> {}
        }

    }

    companion object {
        val TAG: String = PersonalDataScreenViewModel::class.simpleName ?: "PersonalDataScreenViewModel"
    }
}
