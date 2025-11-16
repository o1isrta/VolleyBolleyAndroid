package cy.volleybolley.profile.presentation.ui.screens.players.model

data class PlayerTemp(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String?,
    val isFavorite: Boolean,
    val level: String
)
