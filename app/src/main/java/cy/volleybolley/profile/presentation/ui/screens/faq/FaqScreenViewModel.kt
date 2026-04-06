package cy.volleybolley.profile.presentation.ui.screens.faq

import cy.volleybolley.R
import cy.volleybolley.core.domain.model.onFailure
import cy.volleybolley.core.domain.model.onSuccess
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.profile.presentation.ui.screens.faq.FaqScreenEffect.NavigateFromFaqScreen
import cy.volleybolley.profile.presentation.ui.screens.faq.FaqScreenEvent.OnBackFromFaqClick
import cy.volleybolley.profile.presentation.ui.screens.faq.FaqScreenEvent.RetryClick
import cy.volleybolley.referencedata.domain.api.GetFaqUseCase
import kotlinx.coroutines.flow.update

class FaqScreenViewModel(
    private val getFaqUseCase: GetFaqUseCase
) : BaseViewModel<FaqScreenState, FaqScreenEvent, FaqScreenEffect>(
    initialState = FaqScreenState.Loading
) {

    init {
        loadFaq()
    }

    override fun obtainEvent(event: FaqScreenEvent) {
        when (event) {
            OnBackFromFaqClick -> sendUiEffect(NavigateFromFaqScreen(null))
            RetryClick -> loadFaq()
        }
    }

    private fun loadFaq() {
        uiStateMutable.update { FaqScreenState.Loading }

        launchSafe(
            onError = {
                uiStateMutable.update { FaqScreenState.Error(R.string.something_went_wrong) }
            },
            getErrorLogMessage = { "Error loading FAQ: ${it.message}" }
        ) {
            getFaqUseCase.execute()
                .onSuccess { faq ->
                    val parsed = VolleyUiUtil.parseMarkdown(faq.faq)
                    uiStateMutable.update { FaqScreenState.Success(parsed) }
                }
                .onFailure { error ->
                    uiStateMutable.update { FaqScreenState.Error(R.string.something_went_wrong) }
                }
        }
    }
}
