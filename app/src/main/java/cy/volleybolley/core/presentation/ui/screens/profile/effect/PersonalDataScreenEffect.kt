package cy.volleybolley.core.presentation.ui.screens.profile.effect

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.core.presentation.ui.navigation.NavMap

sealed interface PersonalDataScreenEffect : UiEffect {
    data class NavigateOnOtherScreen(val route: NavMap?) : PersonalDataScreenEffect

    data class ShowDialog(
        val onPositiveButtonClick: () -> Unit,
    ) : PersonalDataScreenEffect

}
