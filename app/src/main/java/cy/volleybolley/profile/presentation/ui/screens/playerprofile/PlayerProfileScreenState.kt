package cy.volleybolley.profile.presentation.ui.screens.playerprofile

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.screens.createNewGame.createNewGameRepository.Gender
import cy.volleybolley.players.domain.model.PlayerDetail

sealed interface PlayerProfileScreenState : UiState {

    data object Loading : PlayerProfileScreenState

    data object Error : PlayerProfileScreenState

    data class ShowPlayerDetails(
        val playerDetail: PlayerDetail = PlayerDetail(
            id = -1,
            firstName = "No",
            lastName = "Name",
            avatarUrl = null,
            isFavorite = false,
            level = "LIGHT",
            gender = Gender.Men.displayText,
            latestActivity = listOf()
        ),
        val isLoadingFavStatus: Boolean = false
    ) : PlayerProfileScreenState

}

