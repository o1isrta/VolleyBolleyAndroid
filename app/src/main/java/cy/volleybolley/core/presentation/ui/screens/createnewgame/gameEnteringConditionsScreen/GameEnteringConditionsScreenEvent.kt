package cy.volleybolley.core.presentation.ui.screens.createnewgame.gameEnteringConditionsScreen

import cy.volleybolley.core.presentation.base.UiEvent

sealed class GameEnteringConditionsScreenEvent : UiEvent {
    object OnPublicSelected :
        GameEnteringConditionsScreenEvent()    // нажатие на "Public" // data class OnPublicSelected(val privacy: Privacy) : GameEnteringConditionsScreenEvent() // нажатие на "Public"

    object OnPrivateSelected :
        GameEnteringConditionsScreenEvent()   // нажатие на "Private" = запрос на открытие экрана Privacy

    object OnBackClicked : GameEnteringConditionsScreenEvent()       // нажатие на стрелку "Назад"
    object OnAddPaymentClick : GameEnteringConditionsScreenEvent()   // Нажатие на кнопку "Add Payment"
    object OnSaveGameClick : GameEnteringConditionsScreenEvent()     // Нажатие на кнопку "Save game"
    object OnManagePlayersClick : GameEnteringConditionsScreenEvent() // Нажатие на кнопку Manage Players
    object CheckIfAccountExists : GameEnteringConditionsScreenEvent() // Проверка, существует ли аккаунт
    data class PerPersonChanged(val perPerson: String) :
        GameEnteringConditionsScreenEvent()        // Изменение стоимости (per Person)

    data class MaximumPlayersChanged(val maximumPersons: Int) :
        GameEnteringConditionsScreenEvent() // Увеличение или уменьшение maximumPlayers

    data class RemovePlayer(val index: Int) :
        GameEnteringConditionsScreenEvent()                   // нажатие на кнопку "Удалить игрока из игры"
}
