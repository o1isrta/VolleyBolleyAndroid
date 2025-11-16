package cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository

class CreateNewGameRepositoryImpl : CreateNewGameRepository {
    private var gameData: GameData = GameData()

    override suspend fun getGameData(): GameData {
        return gameData
    }

    override suspend fun updateGameData(gameData: GameData) {
        this.gameData = gameData
    }

    override suspend fun saveGameDataToServer(gameData: GameData) {
        // TODO: сохранение на сервер
        println("Saving game data to server: $gameData")
    }
}
