package cy.volleybolley.core.presentation.ui.screens.home.success

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.profile.domain.model.PaymentType

data class SuccessState (
    val event: String = "",
): UiState

data class SuccessfulEvent(
    val id: Int,
    val type: String,
    val courtId: Int,
    val message: String,
    val startTime: String,
    val endTime: String,
    val gender: String,
    val levels: List<String>,
    val isPrivate: Boolean = false,
    val maximumPlayers: Int,
    val pricePerPerson: String,
    val paymentType: PaymentType,
    val paymentAccount: String?,
    val currencyType: String,
    val players: List<Int>,
)
