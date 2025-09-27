package cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen

import cy.volleybolley.core.presentation.base.UiEvent

sealed class GameEnteringConditionsScreenEvent : UiEvent{
    data class PrivacySelected(val privacy: Privacy) : GameEnteringConditionsScreenEvent()
    object CheckIfAccountExists: GameEnteringConditionsScreenEvent() // Проверка, существует ли аккаунт
}
