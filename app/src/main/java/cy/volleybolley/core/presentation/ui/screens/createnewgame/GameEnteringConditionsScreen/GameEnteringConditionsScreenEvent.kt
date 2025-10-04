package cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen

import cy.volleybolley.core.presentation.base.UiEvent

sealed class GameEnteringConditionsScreenEvent : UiEvent {
    data class PrivacySelected(val privacy: Privacy) : GameEnteringConditionsScreenEvent()
    object CheckIfAccountExists : GameEnteringConditionsScreenEvent() // Проверка, существует ли аккаунт
    data class PerPersonChanged(val perPerson: String) :
        GameEnteringConditionsScreenEvent() // Изменение стоимости (per Person)

    class MaximumPlayersChanged(val maximumPersons: Int) :
        GameEnteringConditionsScreenEvent() // Увеличение или уменьшение maximumPlayers

    object OnBackClicked : GameEnteringConditionsScreenEvent()     // нажатие на стрелку "Назад"
    object OnAddPaymentClick : GameEnteringConditionsScreenEvent() // Нажатие на кнопку "Add Payment"
    object OnSaveGameClick : GameEnteringConditionsScreenEvent() // Нажатие на кнопку "Save game"
    class RemovePlayer(val index: Int) :
        GameEnteringConditionsScreenEvent() // нажатие на кнопку "Удалить игрока из игры"

    object OpenPrivacyOptions : GameEnteringConditionsScreenEvent() // запрос на открытие экрана Privacy
    data class PlayersSelected(val players: List<PlayerUI>) :
        GameEnteringConditionsScreenEvent() // результат из Privacy screen
}
