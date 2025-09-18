package cy.volleybolley.core.presentation.ui.screens.findagame

import cy.volleybolley.core.presentation.base.BaseViewModel

class JoinTheGameViewModel :
    BaseViewModel<JoinTheGameState, JoinTheGameEvent, JoinTheGameEffect>(JoinTheGameState("")) {

    override val tag: String = TAG
    override fun obtainEvent(event: JoinTheGameEvent) {
        TODO()
    }

    companion object {
        val TAG = JoinTheGameViewModel::class.simpleName ?: "JoinTheGameViewModel"
    }
}
