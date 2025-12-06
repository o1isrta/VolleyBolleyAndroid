package cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.ui.LEVEL_HIGH
import cy.volleybolley.core.presentation.ui.LEVEL_LIGHT
import cy.volleybolley.core.presentation.ui.LEVEL_MEDIUM
import cy.volleybolley.core.presentation.ui.LEVEL_PRO
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
        if(players.size < _gameData.value.maximumPlayers)
            _gameData.value = _gameData.value.copy(players = _gameData.value.players + players)
    }

    override fun removePlayerFromGame(playerIndex: Int) {
        val mutableList = _gameData.value.players.toMutableList()
        if (playerIndex in 0 until mutableList.size) {
            mutableList.removeAt(playerIndex)
            _gameData.value = _gameData.value.copy(players = mutableList.toList())
        }
    }

//    override suspend fun searchPlayers(query: String, favoritesOnly: Boolean): VolleyResult<List<Player>, ErrorType>/*List<Player>*/ {
//        // Имитация запроса к серверу
//        delay(500)
//        //TODO Здесь должен быть реальный запрос к API с использованием query и favoritesOnly
//        // return try {
//        //    val result = apiService.searchPlayers(query, favoritesOnly)
//        //   VolleyResult.Success(result)
//        // } catch (e: Exception) {
//        //    Log.e("SearchPlayersError", "Error fetching players: ${e.message}", e)
//        //  VolleyResult.Failure(ErrorType.SERVER_ERROR)
//        // }
//        // Заглушка для поиска: возвращаем список тестовых игроков, соответствующих запросу
//        val samplePlayers = listOf(
//            Player(1,"Kristina", "Popova", null, true, cy.volleybolley.core.presentation.ui.LEVEL_MEDIUM),
//            Player(2, "Polina", "Vasylyeva", null,false, cy.volleybolley.core.presentation.ui.LEVEL_PRO),
//            Player(3, "Anton", "Ivanov", null, true, cy.volleybolley.core.presentation.ui.LEVEL_LIGHT),
//            Player(4, "Aleksandr", "Abramov", null, false, cy.volleybolley.core.presentation.ui.LEVEL_HIGH),
//            Player(5, "Maria", "Novak", null, false, cy.volleybolley.core.presentation.ui.LEVEL_PRO)
//        )
//        val filteredPlayers = samplePlayers.filter { player ->
//            /*player.lastName.contains(query, ignoreCase = true) &&*/ (!favoritesOnly || player.isFavorite)
//            /* it.lastName.contains(query, ignoreCase = true) && (!favoritesOnly || it.isFavorite)*/
//        }
//        return VolleyResult.Success(filteredPlayers)
//    }

    override suspend fun saveGameDataToServer() : VolleyResult<Unit, ErrorType> {
        // Имитация сохранения на сервер
        delay(1000)
        // Здесь должен быть реальный код для сохранения данных на сервер
        // Здесь предположим, что ApiService возвращает VolleyResult
        // try {
        //  val result = apiService.saveGameData(gameData)
        // return VolleyResult.Success(Unit) // если saveGameData возвращает Unit
        //  } catch (e: Exception) {
        // Логируйте ошибку
        //  return VolleyResult.Failure(ErrorType.SERVER_ERROR) // или специфичный ErrorType
        //}
        println("Sending game data to the server $gameData")
        return VolleyResult.Success(Unit) // Mock successful save
    }

    /* Получение данных игры с сервера
* */
    override suspend fun getGameDataFromServer(): VolleyResult<GameData, ErrorType>  {
        // Имитация загрузки с сервера
        delay(500) // Имитация задержки при получении данных с сервера
        //try {
        // return VolleyResult.Success(apiService.getGameData())
        // } catch (e: Exception) {
        // return VolleyResult.Failure(ErrorType.SERVER_ERROR)
        //}

        // Mock Data
        val mockGameData = GameData(players = listOf(
            Player(1,"Kristina", "Popova", null, true, LEVEL_MEDIUM),
            Player(2, "Polina", "Vasylyeva", null,false, LEVEL_PRO),
            Player(3, "Anton", "Ivanov", null, true, LEVEL_LIGHT),
            Player(4, "Aleksandr", "Abramov", null, false, LEVEL_HIGH)
        ))
        return VolleyResult.Success(mockGameData)
    }

    /* Загрузить данные игры
    * */
       override suspend fun loadGameData() : VolleyResult<Unit, ErrorType> {
        //  уже возвращает VolleyResult.
        return when (val result = getGameDataFromServer()) {
            is VolleyResult.Success -> {
                _gameData.value = result.data
                VolleyResult.Success(Unit)
            }
            is VolleyResult.Failure -> {
                VolleyResult.Failure(result.error) // Пробросить ошибку дальше
            }
        }
//        val response = getGameDataFromServer()
//        _gameData.value = response
    }

    override suspend fun updateGameData(update: (GameData) -> GameData): VolleyResult<GameData, ErrorType> {
        return try {
            val updatedData = update(_gameData.value)
            _gameData.value = updatedData
            VolleyResult.Success(updatedData)
        } catch (e: Exception) {
            VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
        }
    }
}
