package cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.ui.GENDER_FEMALE
import cy.volleybolley.core.presentation.ui.GENDER_MALE
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

    fun addPlayersToGame(players: List<Player>)
    fun removePlayerFromGame(playerIndex: Int)

    suspend fun saveGameDataToServer(): VolleyResult<Unit, ErrorType>
    suspend fun getGameDataFromServer(): VolleyResult<GameData, ErrorType>
    suspend fun loadGameData(): VolleyResult<Unit, ErrorType>

    // Новый, более общий метод для обновления GameData
    // Он принимает лямбду, которая получает текущую GameData и возвращает измененную.
    // Это позволяет точечно изменять GameData, не передавая весь объект.
    suspend fun updateGameData(update: (GameData) -> GameData): VolleyResult<GameData, ErrorType>
}

// заглушка для preview
class FakeCreateNewGameRepository(
    initialGameData: GameData = GameData() // Возможность задать начальное состояние для предпросмотра
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
        return when (val result = getGameDataFromServer()) {
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

private const val KRISTINA_ID = 1
private const val POLINA_ID = 2
private const val ANTON_ID = 3
private const val ALEKSANDR_ID = 4
private const val MARIA_ID = 5

class FakeSearchPlayersUseCase : SearchPlayersUseCase {
    override suspend fun invoke(query: String): VolleyResult<List<Player>, ErrorType> {
        // Возвращаем тестовые данные
        val samplePlayers = listOf(
            Player(KRISTINA_ID, "Kristina", "Popova", null, true, GENDER_FEMALE, LEVEL_MEDIUM),
            Player(POLINA_ID, "Polina", "Vasylyeva", null, false, GENDER_FEMALE, LEVEL_PRO),
            Player(ANTON_ID, "Anton", "Ivanov", null, true, GENDER_MALE, LEVEL_LIGHT),
            Player(ALEKSANDR_ID, "Aleksandr", "Abramov", null, false, GENDER_MALE, LEVEL_HIGH),
            Player(MARIA_ID, "Maria", "Novak", null, false, GENDER_FEMALE, LEVEL_PRO)
        )

        val filteredPlayers = samplePlayers.filter { player ->
            player.lastName.contains(query, ignoreCase = true) && player.isFavorite
        }
        return VolleyResult.Success(filteredPlayers)
    }
}
