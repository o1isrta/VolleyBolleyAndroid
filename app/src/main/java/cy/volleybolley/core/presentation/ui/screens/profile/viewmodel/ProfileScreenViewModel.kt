package cy.volleybolley.core.presentation.ui.screens.profile.viewmodel

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.screens.profile.effect.ProfileScreenEffect
import cy.volleybolley.core.presentation.ui.screens.profile.event.ProfileScreenEvent
import cy.volleybolley.core.presentation.ui.screens.profile.state.ProfileScreenState

class ProfileScreenViewModel : BaseViewModel<ProfileScreenState, ProfileScreenEvent, ProfileScreenEffect>(
    initialState = ProfileScreenState("some_value")
){
    override val tag: String = TAG

    override fun obtainEvent(event: ProfileScreenEvent) {

    }

    companion object {
        const val TAG = "ProfileViewModelTag"
    }
}
