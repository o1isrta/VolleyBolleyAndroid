package cy.volleybolley.core.presentation.ui.screens.profile.viewmodel

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.profile.effect.PersonalDataScreenEffect
import cy.volleybolley.core.presentation.ui.screens.profile.event.PersonalDataScreenEvent
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

    }

    companion object {
        val TAG: String = PersonalDataScreenViewModel::class.simpleName ?: "PersonalDataScreenViewModel"
    }
}
