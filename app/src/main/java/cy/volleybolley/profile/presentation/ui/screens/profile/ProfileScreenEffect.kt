package cy.volleybolley.profile.presentation.ui.screens.profile

import cy.volleybolley.core.presentation.base.UiEffect

sealed interface ProfileScreenEffect : UiEffect {
    data object NavigateToPlayers : ProfileScreenEffect
    data object NavigateToPersonalData : ProfileScreenEffect
    data object NavigateToPayments : ProfileScreenEffect
    data object NavigateToFaq : ProfileScreenEffect
    data object NavigateToAbout : ProfileScreenEffect
    data object NavigateToAuthorization : ProfileScreenEffect

    data class ShowLogoutDialog(
        val onPositiveButtonClick: () -> Unit,
        val onNegativeButtonClick: () -> Unit,
    ) : ProfileScreenEffect

    data class ShowDeleteAccountDialog(
        val onPositiveButtonClick: () -> Unit,
        val onNegativeButtonClick: () -> Unit,
    ) : ProfileScreenEffect
}
