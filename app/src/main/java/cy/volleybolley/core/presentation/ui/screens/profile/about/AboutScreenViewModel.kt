package cy.volleybolley.core.presentation.ui.screens.profile.about

import cy.volleybolley.core.presentation.base.BaseViewModel

class AboutScreenViewModel(

) : BaseViewModel<AboutScreenState, AboutScreenEvent, AboutScreenEffect>(
    initialState = AboutScreenState(),
) {
    override val tag: String = TAG

    override fun obtainEvent(event: AboutScreenEvent) {

    }

    companion object {
        val TAG: String = AboutScreenViewModel::class.simpleName ?: "AboutScreenViewModel"
    }
}
