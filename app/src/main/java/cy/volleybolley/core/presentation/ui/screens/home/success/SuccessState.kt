package cy.volleybolley.core.presentation.ui.screens.home.success

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.profile.domain.model.PaymentType
import kotlinx.serialization.Serializable
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class SuccessState(
    val event: SucceedGame,
) : UiState

@Serializable
data class SucceedGame(
    val id: Int,
    val type: SucceedGameType,
    val locationName: String,
    val locationPlace: String,
    val date: String,
    val time: String,
    val level: String,
    val playersInfo: String,
    val pricePerPerson: String,
    val paymentType: PaymentType,
    val paymentAccount: String?,
)

fun SucceedGame.toDeepLink(): String {
    val encodedType = URLEncoder.encode(type.toString(), StandardCharsets.UTF_8.toString())
    val encodedId = URLEncoder.encode(id.toString(), StandardCharsets.UTF_8.toString())
    return "volleybolley://invite/$encodedType/$encodedId"
}

sealed interface SucceedGameType {
    object CreatedGame : SucceedGameType
    object CreatedTournament : SucceedGameType
    object JoinedGame : SucceedGameType
    object JoinedTournament : SucceedGameType
}
