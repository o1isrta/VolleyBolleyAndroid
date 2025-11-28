package cy.volleybolley.profile.presentation.ui.screens.profile

import cy.volleybolley.core.presentation.base.UiEvent

sealed interface ProfileScreenEvent : UiEvent {
    data object OnPlayersClick : ProfileScreenEvent
    data object OnPersonalDataClick : ProfileScreenEvent
    data object OnPaymentsClick : ProfileScreenEvent
    data object OnSupportClick : ProfileScreenEvent
    data object OnFaqClick : ProfileScreenEvent
    data object OnAboutClick : ProfileScreenEvent
    data object OnLogoutClick : ProfileScreenEvent
    data object OnDeleteAccountClick : ProfileScreenEvent
}
