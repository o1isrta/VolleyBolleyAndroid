package cy.volleybolley.core.presentation.ui.screens.games.mygames.manageplayers

import cy.volleybolley.core.presentation.base.BaseViewModel

class ManagePlayersViewModel :
    BaseViewModel<ManagePlayersState, ManagePlayersAction, ManagePlayersEffect>(
        initialState = ManagePlayersState()
    ) {

    override val tag: String = "ManagePlayersVM"

    override fun obtainEvent(event: ManagePlayersAction) {
        when (event) {
            ManagePlayersAction.ClickBack -> sendUiEffect(ManagePlayersEffect.NavigateBack)

            is ManagePlayersAction.RemovePlayer -> {
                // Вызов use-case удаления игрока и обновление состояния
            }

            ManagePlayersAction.Refresh -> {
                // Подтянуть актуальный список из домейна и обновить state
            }
        }
    }
}
