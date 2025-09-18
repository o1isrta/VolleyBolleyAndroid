package cy.volleybolley.core.presentation.ui.screens.home.success

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.profile.domain.model.PaymentType
import kotlinx.serialization.Serializable
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class SuccessState(
    val event: CreatedEvent,
) : UiState

@Serializable
data class CreatedEvent(
    val id: Int,
    val type: EventType,
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

fun CreatedEvent.toDeepLink(): String {
    val encodedType = URLEncoder.encode(type.name, StandardCharsets.UTF_8.toString())
    val encodedId = URLEncoder.encode(id.toString(), StandardCharsets.UTF_8.toString())
    return "volleybolley://invite/$encodedType/$encodedId"
}

enum class EventType {
    GAME,
    TOURNAMENT
}
