package cy.volleybolley.core.presentation.ui.screens.games.archive.pastgamescreen.model

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.screens.games.archive.util.provideMockGame
import cy.volleybolley.games.domain.model.event.game.GameDetails

sealed interface PastGameState : UiState {
    data object Loading : PastGameState
    data class Content(
        val game: GameDetails =
            provideMockGame(0)
    ) : PastGameState

    object Error : PastGameState
}
