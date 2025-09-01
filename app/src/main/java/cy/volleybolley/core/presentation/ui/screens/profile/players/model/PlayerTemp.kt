package cy.volleybolley.core.presentation.ui.screens.profile.players.model

data class PlayerTemp(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String?,
    val isFavorite: Boolean,
    val level: String
)
