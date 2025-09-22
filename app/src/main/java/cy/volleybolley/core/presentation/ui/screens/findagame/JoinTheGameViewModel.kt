package cy.volleybolley.core.presentation.ui.screens.findagame

import cy.volleybolley.core.presentation.base.BaseViewModel

class JoinTheGameViewModel :
    BaseViewModel<JoinTheGameState, JoinTheGameEvent, JoinTheGameEffect>(JoinTheGameState("")) {

    override val tag: String = JoinTheGameViewModel::class.simpleName ?: ""

    override fun obtainEvent(event: JoinTheGameEvent) {
        when (event) {
            JoinTheGameEvent.OnBack -> TODO()
            JoinTheGameEvent.OnJoinGame -> TODO()
            JoinTheGameEvent.OnMap -> TODO()
            JoinTheGameEvent.Refresh -> TODO()
        }
    }
}
