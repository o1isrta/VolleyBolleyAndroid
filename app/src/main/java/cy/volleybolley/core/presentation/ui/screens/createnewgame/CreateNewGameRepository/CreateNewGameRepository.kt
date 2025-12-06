package cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.ui.LEVEL_HIGH
import cy.volleybolley.core.presentation.ui.LEVEL_LIGHT
import cy.volleybolley.core.presentation.ui.LEVEL_MEDIUM
import cy.volleybolley.core.presentation.ui.LEVEL_PRO
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.usecase.SearchPlayersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface CreateNewGameRepository {
    val gameData: StateFlow<GameData>
     //suspend fun updateGameData(gameData: GameData)
    fun addPlayersToGame(players: List<Player>)
    fun removePlayerFromGame(playerIndex: Int)
   // suspend fun searchPlayers(query: String, favoritesOnly: Boolean): VolleyResult<List<Player>, ErrorType>
    suspend fun saveGameDataToServer(): VolleyResult<Unit, ErrorType>
    suspend fun getGameDataFromServer(): VolleyResult<GameData, ErrorType>
    suspend fun loadGameData(): VolleyResult<Unit, ErrorType>
    // Новый, более общий метод для обновления GameData
    // Он принимает лямбду, которая получает текущую GameData и возвращает измененную.
    // Это позволяет точечно изменять GameData, не передавая весь объект.
    suspend fun updateGameData(update: (GameData) -> GameData): VolleyResult<GameData, ErrorType>
}

// заглушка для preview
class FakeCreateNewGameRepository(initialGameData: GameData = GameData() // Возможность задать начальное состояние для предпросмотра
) : CreateNewGameRepository {
    // Для имитации StateFlow в репозитории
    private val _gameData = MutableStateFlow(initialGameData)
    override val gameData: StateFlow<GameData> = _gameData.asStateFlow()

    override suspend fun updateGameData(update: (GameData) -> GameData): VolleyResult<GameData, ErrorType> {
        val currentData = _gameData.value // Получаем текущее значение из MutableStateFlow
        val updatedData = update(currentData) // Применяем функцию обновления
        _gameData.value = updatedData // Устанавливаем новое значение
        return VolleyResult.Success(updatedData) // Всегда успешно в заглушке
    }

    // можно реализовать через updateGameData если хотите,
    override fun addPlayersToGame(players: List<Player>) {
        _gameData.update { current ->
            current.copy(players = current.players + players)
        }
    }
    // можно через updateGameData
    override fun removePlayerFromGame(playerIndex: Int) {
        _gameData.update { current ->
            val updatedPlayers = current.players.toMutableList().apply {
                if (playerIndex >= 0 && playerIndex < size) {
                    removeAt(playerIndex)
                }
            }
            current.copy(players = updatedPlayers)
        }
    }

//    override suspend fun searchPlayers(query: String, favoritesOnly: Boolean): VolleyResult<List<Player>, ErrorType> {
//        // Заглушка для поиска: возвращаем список тестовых игроков, соответствующих запросу
//        val samplePlayers = listOf(
//            Player(1,"Kristina", "Popova", null, true, cy.volleybolley.core.presentation.ui.LEVEL_MEDIUM),
//            Player(2, "Polina", "Vasylyeva", null,false, cy.volleybolley.core.presentation.ui.LEVEL_PRO),
//            Player(3, "Anton", "Ivanov", null, true, cy.volleybolley.core.presentation.ui.LEVEL_LIGHT),
//            Player(4, "Aleksandr", "Abramov", null, false, cy.volleybolley.core.presentation.ui.LEVEL_HIGH),
//            Player(4, "Maria", "Novak", null, false, cy.volleybolley.core.presentation.ui.LEVEL_PRO)
//        )
//        val filteredPlayers = samplePlayers.filter { player ->
//            player.lastName.contains(query, ignoreCase = true) && (!favoritesOnly || player.isFavorite)
//           /* it.lastName.contains(query, ignoreCase = true) && (!favoritesOnly || it.isFavorite)*/
//        }
//        return VolleyResult.Success(filteredPlayers)
//    }

    override suspend fun saveGameDataToServer(): VolleyResult<Unit, ErrorType> {
        // Просто имитируем успешное сохранение
        return VolleyResult.Success(Unit)
    }

    override suspend fun getGameDataFromServer(): VolleyResult<GameData, ErrorType> {
        // Возвращаем текущие данные (или заранее определенные для теста)
        return VolleyResult.Success(_gameData.value)
    }

    override suspend fun loadGameData(): VolleyResult<Unit, ErrorType> {
        // Имитируем загрузку и обновление внутренней StateFlow
//        val loadedData = GameData()
//        _gameData.value = loadedData
//        return VolleyResult.Success(loadedData)
        return when (val result = getGameDataFromServer()){
            is VolleyResult.Success -> {
                _gameData.value = result.data
                VolleyResult.Success(Unit)
            }
            is VolleyResult.Failure -> {
                VolleyResult.Failure(result.error) // Пробросить ошибку дальше
            }
        }
    }
}

class FakeSearchPlayersUseCase : SearchPlayersUseCase {
    override suspend fun invoke(query: String/*, favoritesOnly: Boolean*/): VolleyResult<List<Player>, ErrorType> {
        // Возвращаем тестовые данные
        val samplePlayers = listOf(
            Player(1, "Kristina", "Popova", null, true, LEVEL_MEDIUM),
            Player(2, "Polina", "Vasylyeva", null, false, LEVEL_PRO),
            Player(3, "Anton", "Ivanov", null, true, LEVEL_LIGHT),
            Player(4, "Aleksandr", "Abramov", null, false, LEVEL_HIGH),
            Player(5, "Maria", "Novak", null, false, LEVEL_PRO)
        )

        val filteredPlayers = samplePlayers.filter {player ->
            player.lastName.contains(query, ignoreCase = true) && (/* !favoritesOnly*|| */player.isFavorite)
        }
        return VolleyResult.Success(filteredPlayers)
    }
//        return VolleyResult.Success(
//            listOf(
//            Player(1,"Kristina", "Popova", null, true, cy.volleybolley.core.presentation.ui.LEVEL_MEDIUM),
//            Player(2, "Polina", "Vasylyeva", null,false, cy.volleybolley.core.presentation.ui.LEVEL_PRO),
//            Player(3, "Anton", "Ivanov", null, true, cy.volleybolley.core.presentation.ui.LEVEL_LIGHT),
//            Player(4, "Aleksandr", "Abramov", null, false, cy.volleybolley.core.presentation.ui.LEVEL_HIGH),
//            Player(5, "Maria", "Novak", null, false, cy.volleybolley.core.presentation.ui.LEVEL_PRO)
//        ))

}
