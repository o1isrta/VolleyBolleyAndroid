package cy.volleybolley.core.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class PlayerResponse {
    @Serializable
    data class PlayerList(
        val players: List<PlayerDto>
    ) : PlayerResponse()

    @Serializable
    data class PlayerDetail(
        val player: PlayerDtoDetail
    ) : PlayerResponse()

    data object Empty : PlayerResponse()
}

@Serializable
data class PlayerDto(
    @SerialName("player_id") val id: Int,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("is_favorite") val isFavorite: Boolean,
    val level: String
)

@Serializable
data class PlayerDtoDetail(
    @SerialName("player_id") val id: Int,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("is_favorite") val isFavorite: Boolean,
    val level: String,
    @SerialName("latest_activity") val latestActivity: List<ActivityDto>
)

@Serializable
data class ActivityDto(
    @SerialName("event_timestamp") val eventTimestamp: String,
    @SerialName("court_location") val courtLocation: LocationDto
)

@Serializable
data class LocationDto(
    val longitude: Double,
    val latitude: Double,
    @SerialName("court_name") val courtName: String,
    @SerialName("location_name") val locationName: String
)

