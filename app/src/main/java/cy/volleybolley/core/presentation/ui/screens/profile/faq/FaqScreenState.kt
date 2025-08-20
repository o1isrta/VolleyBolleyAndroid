package cy.volleybolley.core.presentation.ui.screens.profile.faq

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.screens.profile.faq.model.FaqString

data class FaqScreenState(
    val faqText: List<FaqString> = listOf()
) : UiState
