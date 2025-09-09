package cy.volleybolley.profile.presentation.ui.screens.faq

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.profile.presentation.ui.screens.faq.model.FaqString

data class FaqScreenState(
    val faqText: List<FaqString> = listOf()
) : UiState
