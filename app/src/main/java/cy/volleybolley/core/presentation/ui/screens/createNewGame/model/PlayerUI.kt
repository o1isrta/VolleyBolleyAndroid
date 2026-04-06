package cy.volleybolley.core.presentation.ui.screens.createNewGame.model

import cy.volleybolley.core.presentation.ui.model.Level

data class PlayerUi(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String?,
    val isFavorite: Boolean,
    val level: Level,
    val gender: Gender
) {
    val fullName: String = "$firstName $lastName"
    val levelDisplayText: String = level.displayText
    val genderDisplayText: String = gender.displayText
}
