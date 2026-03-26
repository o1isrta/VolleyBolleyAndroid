package cy.volleybolley.profile.presentation.ui.screens.faq

import cy.volleybolley.core.domain.model.onFailure
import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.profile.presentation.ui.screens.faq.FaqScreenEffect.NavigateFromFaqScreen
import cy.volleybolley.profile.presentation.ui.screens.faq.FaqScreenEvent.OnBackFromFaqClick
import cy.volleybolley.referencedata.domain.api.GetFaqUseCase
import kotlinx.coroutines.flow.update

class FaqScreenViewModel(
    private val getFaqUseCase: GetFaqUseCase
) : BaseViewModel<FaqScreenState, FaqScreenEvent, FaqScreenEffect>(
    initialState = FaqScreenState()
) {

    init {
        loadFaq()
    }

    override fun obtainEvent(event: FaqScreenEvent) {
        when (event) {
            OnBackFromFaqClick -> sendUiEffect(NavigateFromFaqScreen(null))
        }
    }

    private fun loadFaq() {
        launchSafe(
            getErrorLogMessage = { "Error loading FAQ: ${it.message}" }
        ) {
            getFaqUseCase.execute()
                .onSuccess { faq ->
                    val parsed = VolleyUiUtil.parseMarkdown(faq.faq)

                    uiStateMutable.update {
                        it.copy(faqText = parsed)
                    }
                }
                .onFailure {
                    println("FAQ ERROR: $it")
                }
        }
    }
}
