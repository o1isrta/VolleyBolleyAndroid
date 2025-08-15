package cy.volleybolley.core.presentation.ui.screens.profile.profile

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.core.presentation.ui.navigation.NavMap

sealed interface ProfileScreenEffect : UiEffect {
    data class NavigateFromProfileScreen(val route: NavMap) : ProfileScreenEffect

    data class ShowLogoutDialog(
        val onPositiveButtonClick: () -> Unit,
    ) : ProfileScreenEffect

    data class ShowDeleteAccountDialog(
        val onPositiveButtonClick: () -> Unit,
    ) : ProfileScreenEffect
}
