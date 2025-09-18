package cy.volleybolley.core.presentation.ui.screens.home.success

import cy.volleybolley.core.presentation.base.BaseViewModel

class SuccessViewModel() : BaseViewModel<SuccessState, SuccessEvent, SuccessEffect>(SuccessState()) {

    override val tag: String = "ggg"

    override fun obtainEvent(event: SuccessEvent) {
        TODO("Not yet implemented")
    }
}
