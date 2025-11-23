package cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository

import cy.volleybolley.players.domain.model.Player
import kotlinx.coroutines.flow.StateFlow

interface CreateNewGameRepository {
    val gameData: StateFlow<GameData>
     //suspend fun updateGameData(gameData: GameData)
    fun addPlayersToGame(players: List<Player>)
    fun removePlayerFromGame(playerIndex: Int)
    suspend fun searchPlayers(query: String, favoritesOnly: Boolean): List<Player>
    suspend fun saveGameDataToServer()
    suspend fun getGameDataFromServer(): GameData
    suspend fun loadGameData()
}

// заглушка для preview
class FakeCreateNewGameRepository(override val gameData: StateFlow<GameData>) : CreateNewGameRepository {
    // Override все методы интерфейса CreateNewGameRepository и предоставьте заглушки.

//    override suspend fun updateGameData(gameData: GameData) {
//
//    }
    override fun addPlayersToGame(players: List<Player>) {
    }
    override fun removePlayerFromGame(playerIndex: Int) {
    }
    override suspend fun searchPlayers(query: String, favoritesOnly: Boolean): List<Player> {
        return emptyList()
    }
    override suspend fun saveGameDataToServer() {
    }
    override suspend fun getGameDataFromServer(): GameData {
        return GameData()
    }
    override suspend fun loadGameData() {
    }
}
