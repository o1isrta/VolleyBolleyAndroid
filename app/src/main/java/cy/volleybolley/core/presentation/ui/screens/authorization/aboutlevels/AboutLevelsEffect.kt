package cy.volleybolley.core.presentation.ui.screens.authorization.aboutlevels

import cy.volleybolley.core.presentation.base.UiEffect

sealed class AboutLevelsEffect : UiEffect {
    object NavigateBack : AboutLevelsEffect()
}
