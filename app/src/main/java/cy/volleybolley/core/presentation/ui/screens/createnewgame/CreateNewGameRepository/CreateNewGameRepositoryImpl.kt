package cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository

import cy.volleybolley.core.presentation.ui.LEVEL_MEDIUM
import cy.volleybolley.players.domain.model.Player
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateNewGameRepositoryImpl : CreateNewGameRepository {
    //private var gameData: GameData = GameData()
    private val _gameData = MutableStateFlow(GameData())
    override val gameData: StateFlow<GameData> = _gameData // Expose as immutable StateFlow

   // private val apiService: ApiService = TODO() //Предположим, что у нас есть интерфейс ApiService для запросов, его нужно передать в конструкторе

//    init {
//        loadGameData()
//    }

//    override suspend fun updateGameData(gameData: GameData) {
//        _gameData.value = gameData // Update the internal state, triggering updates
//            //this.gameData = gameData
//    }
    override fun addPlayersToGame(players: List<Player>) {
        _gameData.value = _gameData.value.copy(players = _gameData.value.players + players)
    }
    override fun removePlayerFromGame(playerIndex: Int) {
        val mutableList = _gameData.value.players.toMutableList()
        mutableList.removeAt(playerIndex)
        _gameData.value = _gameData.value.copy(players = mutableList.toList())
    }

    override suspend fun searchPlayers(query: String, favoritesOnly: Boolean): List<Player> {
        // Имитация запроса к серверу
        delay(500)
        //TODO Здесь должен быть реальный запрос к API с использованием query и favoritesOnly
        //val result = apiService.searchPlayers(query, favoritesOnly)
        //return result
        return listOf(
            Player(1,"Kristina", "Popova", null, true, LEVEL_MEDIUM),
            Player(2, "Polina", "Vasylyeva", null,false, cy.volleybolley.core.presentation.ui.LEVEL_PRO),
            Player(3, "Anton", "Ivanov", null, true, cy.volleybolley.core.presentation.ui.LEVEL_LIGHT),
            Player(4, "Aleksandr", "Abramov", null, false, cy.volleybolley.core.presentation.ui.LEVEL_HIGH)
        ) //emptyList()
    }

    override suspend fun saveGameDataToServer() {
        // Имитация сохранения на сервер
        delay(1000)
        // Здесь должен быть реальный код для сохранения данных на сервер
        //val jsonString = Json.encodeToString(gameData)
        //return apiService.saveGameData(jsonString)

            //apiService.saveGameData(Json.encodeToString(gameData))
        println("Sending game data to the server $gameData")
    }

    /* Получение данных игры с сервера
* */
    override suspend fun getGameDataFromServer(): GameData {
        // Имитация загрузки с сервера
        delay(500) // Имитация задержки при получении данных с сервера
        //val response = apiService.getGameData()
        MutableStateFlow(GameData(
            return GameData(players = listOf(
                Player(1,"Kristina", "Popova", null, true, LEVEL_MEDIUM),
                Player(2, "Polina", "Vasylyeva", null,false, cy.volleybolley.core.presentation.ui.LEVEL_PRO),
                Player(3, "Anton", "Ivanov", null, true, cy.volleybolley.core.presentation.ui.LEVEL_LIGHT),
                Player(4, "Aleksandr", "Abramov", null, false, cy.volleybolley.core.presentation.ui.LEVEL_HIGH)
            )
            )))
        //return response
    }

    /* Загрузить данные игры
    * */
       override suspend fun loadGameData(){
        val response = getGameDataFromServer()
        _gameData.value = response
    }
}
