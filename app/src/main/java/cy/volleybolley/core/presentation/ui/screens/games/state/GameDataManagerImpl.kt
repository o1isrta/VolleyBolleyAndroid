package cy.volleybolley.core.presentation.ui.screens.games.state

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.ui.screens.games.createNewGame.model.GameData
import cy.volleybolley.core.presentation.ui.screens.games.createNewGame.model.toCreateGame
import cy.volleybolley.games.domain.api.GamesRepository

class GameDataManagerImpl(
    private val gamesRepository: GamesRepository
) : GameDataManager {

    override suspend fun saveGame(gameData: GameData): VolleyResult<Int, ErrorType> {
        val createGame = gameData.toCreateGame()

        return when (val result = gamesRepository.createGame(createGame)) {
            is VolleyResult.Success -> {
                VolleyResult.Success(result.data.gameId)
            }
            is VolleyResult.Failure -> {
                VolleyResult.Failure(result.error)
            }
        }
    }
}
