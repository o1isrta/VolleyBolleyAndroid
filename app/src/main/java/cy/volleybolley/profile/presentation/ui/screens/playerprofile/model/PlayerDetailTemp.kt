package cy.volleybolley.profile.presentation.ui.screens.playerprofile.model

data class PlayerDetailTemp(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String?,
    val isFavorite: Boolean,
    val level: String,
    val latestActivity: List<PlayerActivityTemp>
)
