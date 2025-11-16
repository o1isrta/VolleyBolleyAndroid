package cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository

interface CreateNewGameRepository {
    suspend fun getGameData(): GameData
    suspend fun updateGameData(gameData: GameData)
    suspend fun saveGameDataToServer(gameData: GameData)
}

// заглушка для preview
class FakeCreateNewGameRepository : CreateNewGameRepository {
    // Override все методы интерфейса CreateNewGameRepository и предоставьте заглушки.
    // Простой пример:

    override suspend fun getGameData(): GameData {
        return GameData()
    }

    override suspend fun updateGameData(gameData: GameData) {

    }

    override suspend fun saveGameDataToServer(gameData: GameData) {

    }

}
