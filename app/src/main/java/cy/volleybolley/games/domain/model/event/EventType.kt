package cy.volleybolley.games.domain.model.event

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
enum class EventType {
    @Serializable
    GAME,
    @Serializable
    TOURNAMENT
}
