package cy.volleybolley.core.presentation.ui.screens.authorization.launch

import androidx.annotation.StringRes
import cy.volleybolley.core.presentation.base.UiState

sealed interface LaunchScreenState : UiState {
    data object Loading : LaunchScreenState
    data class Error(@StringRes val errorMessageRes: Int) : LaunchScreenState
}
