package cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
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
    suspend fun searchPlayers(query: String, favoritesOnly: Boolean): VolleyResult<List<Player>, ErrorType>
    suspend fun saveGameDataToServer(): VolleyResult<Unit, ErrorType>
    suspend fun getGameDataFromServer(): VolleyResult<GameData, ErrorType>
    suspend fun loadGameData(): VolleyResult<GameData, ErrorType>
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

    // 1. Реализуем новый метод updateGameData
    override suspend fun updateGameData(update: (GameData) -> GameData): VolleyResult<GameData, ErrorType> {
        val currentData = _gameData.value // Получаем текущее значение из MutableStateFlow
        val updatedData = update(currentData) // Применяем функцию обновления
        _gameData.value = updatedData // Устанавливаем новое значение
        return VolleyResult.Success(updatedData) // Всегда успешно в заглушке
    }

    // 2. addPlayersToGame - теперь можно реализовать через updateGameData если хотите,
    // или оставить как есть, если это отдельный метод. Для заглушки достаточно простейшей логики.
    override fun addPlayersToGame(players: List<Player>) {
        _gameData.update { current ->
            current.copy(players = current.players + players)
        }
    }
    // 3. removePlayerFromGame - тоже можно через updateGameData
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
    // 4. searchPlayers - теперь возвращает VolleyResult
    override suspend fun searchPlayers(query: String, favoritesOnly: Boolean): VolleyResult<List<Player>, ErrorType> {
        // Заглушка для поиска: возвращаем список тестовых игроков, соответствующих запросу
        val samplePlayers = listOf(
            Player(1,"Kristina", "Popova", null, true, cy.volleybolley.core.presentation.ui.LEVEL_MEDIUM),
            Player(2, "Polina", "Vasylyeva", null,false, cy.volleybolley.core.presentation.ui.LEVEL_PRO),
            Player(3, "Anton", "Ivanov", null, true, cy.volleybolley.core.presentation.ui.LEVEL_LIGHT),
            Player(4, "Aleksandr", "Abramov", null, false, cy.volleybolley.core.presentation.ui.LEVEL_HIGH),
            Player(4, "Maria", "Novak", null, false, cy.volleybolley.core.presentation.ui.LEVEL_PRO)
        )
        val filteredPlayers = samplePlayers.filter {
            it.lastName.contains(query, ignoreCase = true) && (!favoritesOnly || it.isFavorite)
        }
        return VolleyResult.Success(filteredPlayers)
    }
    // 5. saveGameDataToServer - теперь возвращает VolleyResult
    override suspend fun saveGameDataToServer(): VolleyResult<Unit, ErrorType> {
        // Просто имитируем успешное сохранение
        return VolleyResult.Success(Unit)
    }

    // 6. getGameDataFromServer - теперь возвращает VolleyResult
    override suspend fun getGameDataFromServer(): VolleyResult<GameData, ErrorType> {
        // Возвращаем текущие данные (или заранее определенные для теста)
        return VolleyResult.Success(_gameData.value)
    }

    // 7. loadGameData - теперь возвращает VolleyResult
    override suspend fun loadGameData(): VolleyResult<GameData, ErrorType> {
        // Имитируем загрузку и обновление внутренней StateFlow
        val loadedData = GameData()
        _gameData.value = loadedData
        return VolleyResult.Success(loadedData)
    }
}

class FakeSearchPlayersUseCase : SearchPlayersUseCase {
    override suspend fun invoke(query: String): VolleyResult<List<Player>, ErrorType> {
        // Возвращаем тестовые данные
        return VolleyResult.Success(
            listOf(
            Player(1,"Kristina", "Popova", null, true, cy.volleybolley.core.presentation.ui.LEVEL_MEDIUM),
            Player(2, "Polina", "Vasylyeva", null,false, cy.volleybolley.core.presentation.ui.LEVEL_PRO),
            Player(3, "Anton", "Ivanov", null, true, cy.volleybolley.core.presentation.ui.LEVEL_LIGHT),
            Player(4, "Aleksandr", "Abramov", null, false, cy.volleybolley.core.presentation.ui.LEVEL_HIGH),
            Player(5, "Maria", "Novak", null, false, cy.volleybolley.core.presentation.ui.LEVEL_PRO)
        ))
    }
}
