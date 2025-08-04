package cy.volleybolley.core.domain.players.model

data class PlayerDetail(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String?,
    val isFavorite: Boolean,
    val level: String,
    val latestActivity: List<Activity>
)

data class Activity(
    val eventTimestamp: String,
    val courtLocation: Location
)

data class Location(
    val longitude: Double,
    val latitude: Double,
    val courtName: String,
    val locationName: String
)
