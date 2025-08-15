package cy.volleybolley.core.presentation.ui.screens.profile.personaldata

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.core.presentation.ui.navigation.NavMap

sealed interface PersonalDataScreenEffect : UiEffect {
    data class NavigateFromPersonalDataScreen(val route: NavMap?) : PersonalDataScreenEffect
}
