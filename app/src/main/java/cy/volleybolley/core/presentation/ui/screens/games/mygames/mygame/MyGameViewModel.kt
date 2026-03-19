package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygame

import cy.volleybolley.core.presentation.base.BaseViewModel

class MyGameViewModel :
    BaseViewModel<MyGameState, MyGameAction, MyGameEffect>(initialState = MyGameState()) {

    override fun obtainEvent(event: MyGameAction) {
        when (event) {
            MyGameAction.ClickBack -> sendUiEffect(MyGameEffect.NavigateBack)
            is MyGameAction.ClickMap -> sendUiEffect(MyGameEffect.OpenMap(event.location))

            // Заглушки
            MyGameAction.ClickInvite -> sendUiEffect(MyGameEffect.InvitePlayers)
            MyGameAction.ClickShare -> sendUiEffect(MyGameEffect.ShareLink)
            MyGameAction.ClickCancel -> sendUiEffect(MyGameEffect.CancelGame)

            is MyGameAction.DeletePlayer -> {
                // Домейн-удаление игрока, затем обновить state
            }

            MyGameAction.Refresh -> {
                // Подтянуть данные из домейна
            }
        }
    }
}
