package cy.volleybolley.profile.presentation.ui.screens.faq

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import kotlinx.coroutines.flow.update

class FaqScreenViewModel : BaseViewModel<FaqScreenState, FaqScreenEvent, FaqScreenEffect>(
    initialState = FaqScreenState()
) {
    init {
        // Mock init
        uiStateMutable.update { FaqScreenState(faqText = VolleyUiUtil.parseMarkdown(VolleyUiUtil.MOCK_FAQ)) }
    }

    override val tag: String = TAG

    override fun obtainEvent(event: FaqScreenEvent) {
        when (event) {
            FaqScreenEvent.OnBackFromFaqClick -> sendUiEffect(FaqScreenEffect.NavigateFromFaqScreen(null))
        }
    }

    companion object {
        val TAG = FaqScreenViewModel::class.simpleName ?: "FaqScreenViewModel"
    }
}
