package cy.volleybolley.core.presentation.ui.screens.profile.faq

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.core.presentation.ui.screens.profile.faq.FaqScreenEffect.NavigateFromFaqScreen
import cy.volleybolley.core.presentation.ui.screens.profile.faq.FaqScreenEvent.OnBackFromFaqClick
import kotlinx.coroutines.flow.update

class FaqScreenViewModel : BaseViewModel<FaqScreenState, FaqScreenEvent, FaqScreenEffect>(
    initialState = FaqScreenState()
) {
    init {
        // Mock init
        _uiState.update { FaqScreenState(faqText = VolleyUiUtil.parseMarkdown(VolleyUiUtil.MOCK_FAQ)) }
    }

    override val tag: String = TAG

    override fun obtainEvent(event: FaqScreenEvent) {
        when (event) {
            OnBackFromFaqClick -> sendUiEffect(NavigateFromFaqScreen(null))
        }
    }

    companion object {
        val TAG = FaqScreenViewModel::class.simpleName ?: "FaqScreenViewModel"
    }
}
