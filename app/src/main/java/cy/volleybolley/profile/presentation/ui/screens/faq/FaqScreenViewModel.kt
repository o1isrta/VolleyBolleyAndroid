package cy.volleybolley.profile.presentation.ui.screens.faq

import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.profile.presentation.ui.screens.faq.FaqScreenEffect.NavigateFromFaqScreen
import cy.volleybolley.profile.presentation.ui.screens.faq.FaqScreenEvent.OnBackFromFaqClick
import kotlinx.coroutines.flow.update

class FaqScreenViewModel : BaseViewModel<FaqScreenState, FaqScreenEvent, FaqScreenEffect>(
    initialState = FaqScreenState()
) {
    init {
        // Mock init
        uiStateMutable.update { FaqScreenState(faqText = VolleyUiUtil.parseMarkdown(VolleyUiUtil.MOCK_FAQ)) }
    }

    override val tag: String = FaqScreenViewModel::class.simpleName ?: "FaqScreenViewModel"

    override fun obtainEvent(event: FaqScreenEvent) {
        when (event) {
            OnBackFromFaqClick -> sendUiEffect(NavigateFromFaqScreen(null))
        }
    }
}
