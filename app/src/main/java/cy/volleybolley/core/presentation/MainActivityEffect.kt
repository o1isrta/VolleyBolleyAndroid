package cy.volleybolley.core.presentation

import cy.volleybolley.core.presentation.base.UiEffect

sealed class MainActivityEffect : UiEffect {
    object RequestNotificationPermission : MainActivityEffect()
}
