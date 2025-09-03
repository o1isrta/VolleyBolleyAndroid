package cy.volleybolley.core.presentation.ui.screens.profile.playerprofile.model

data class PlayerDetailTemp(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String?,
    val isFavorite: Boolean,
    val level: String,
    val latestActivity: List<PlayerActivityTemp>
)
