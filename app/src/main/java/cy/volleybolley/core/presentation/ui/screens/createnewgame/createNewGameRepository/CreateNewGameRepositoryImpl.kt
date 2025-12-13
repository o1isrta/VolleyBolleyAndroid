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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateNewGameRepositoryImpl : CreateNewGameRepository {
    private val _gameData = MutableStateFlow(GameData())
    override val gameData: StateFlow<GameData> = _gameData // Expose as immutable StateFlow

    override fun addPlayersToGame(players: List<Player>) {
        if (players.size < _gameData.value.maximumPlayers)
            _gameData.value = _gameData.value.copy(players = _gameData.value.players + players)
    }

    override fun removePlayerFromGame(playerIndex: Int) {
        val mutableList = _gameData.value.players.toMutableList()
        if (playerIndex in 0 until mutableList.size) {
            mutableList.removeAt(playerIndex)
            _gameData.value = _gameData.value.copy(players = mutableList.toList())
        }
    }

    override suspend fun saveGameDataToServer(): VolleyResult<Unit, ErrorType> {
        // Имитация сохранения на сервер
        delay(1000)
        return VolleyResult.Success(Unit)
    }

    /* Получение данных игры с сервера
* */
    override suspend fun getGameDataFromServer(): VolleyResult<GameData, ErrorType> {
        // Имитация загрузки с сервера
        delay(500) // Имитация задержки при получении данных с сервера
        //try {
        // return VolleyResult.Success(apiService.getGameData())
        // } catch (e: Exception) {
        // return VolleyResult.Failure(ErrorType.SERVER_ERROR)
        //}

        // Mock Data
        val mockGameData = GameData(
            players = listOf(
                Player(1, "Kristina", "Popova", null, true, GENDER_FEMALE, LEVEL_MEDIUM),
                Player(2, "Polina", "Vasylyeva", null, false, GENDER_FEMALE, LEVEL_PRO),
                Player(3, "Anton", "Ivanov", null, true, GENDER_MALE, LEVEL_LIGHT),
                Player(4, "Aleksandr", "Abramov", null, false, GENDER_MALE, LEVEL_HIGH)
            )
        )
        return VolleyResult.Success(mockGameData)
    }

    /* Загрузить данные игры
    * */
    override suspend fun loadGameData(): VolleyResult<Unit, ErrorType> {
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
