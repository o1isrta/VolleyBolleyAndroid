package cy.volleybolley.profile.presentation.ui.screens.faq

import androidx.annotation.StringRes
import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.profile.presentation.ui.screens.faq.model.FaqString

sealed interface FaqScreenState : UiState {
    data object Loading : FaqScreenState
    data class Success(val faqText: List<FaqString>) : FaqScreenState
    data class Error(@StringRes val messageResId: Int) : FaqScreenState
}
