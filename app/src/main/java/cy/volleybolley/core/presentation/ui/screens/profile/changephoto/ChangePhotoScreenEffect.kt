package cy.volleybolley.core.presentation.ui.screens.profile.changephoto

import cy.volleybolley.core.presentation.base.UiEffect
import cy.volleybolley.core.presentation.ui.navigation.NavMap

sealed interface ChangePhotoScreenEffect : UiEffect{
    data class NavigateFromChangePhotoScreen(val route: NavMap?) : ChangePhotoScreenEffect
}
