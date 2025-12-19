package cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository

import android.util.Log
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.ui.GENDER_FEMALE
import cy.volleybolley.core.presentation.ui.GENDER_MALE
import cy.volleybolley.core.presentation.ui.LEVEL_HIGH
import cy.volleybolley.core.presentation.ui.LEVEL_LIGHT
import cy.volleybolley.core.presentation.ui.LEVEL_MEDIUM
import cy.volleybolley.core.presentation.ui.LEVEL_PRO/**/
import cy.volleybolley.players.domain.model.Player
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.io.IOException

class CreateNewGameRepositoryImpl : CreateNewGameRepository {
    private val _gameData = MutableStateFlow(GameData())
    override val gameData: StateFlow<GameData> = _gameData // Expose as immutable StateFlow

    companion object {
        const val DEBOUNCE_DELAY_500MS = 500L
        const val DEBOUNCE_DELAY_1000MS = 1000L
        private const val KRISTINA_ID = 1
        private const val POLINA_ID = 2
        private const val ANTON_ID = 3
        private const val ALEKSANDR_ID = 4
    }

    override fun addPlayersToGame(players: List<Player>) {
        if (players.size < _gameData.value.maximumPlayers) {
            _gameData.value = _gameData.value.copy(players = _gameData.value.players + players)
        }
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
        delay(DEBOUNCE_DELAY_1000MS)
        return VolleyResult.Success(Unit)
    }

    /* Получение данных игры с сервера
* */
    override suspend fun getGameDataFromServer(): VolleyResult<GameData, ErrorType> {
        // Имитация загрузки с сервера
        delay(DEBOUNCE_DELAY_500MS) // Имитация задержки при получении данных с сервера

        // Mock Data
        val mockGameData = GameData(
            players = listOf(
                Player(KRISTINA_ID, "Kristina", "Popova", null, true, GENDER_FEMALE, LEVEL_MEDIUM),
                Player(POLINA_ID, "Polina", "Vasylyeva", null, false, GENDER_FEMALE, LEVEL_PRO),
                Player(ANTON_ID, "Anton", "Ivanov", null, true, GENDER_MALE, LEVEL_LIGHT),
                Player(ALEKSANDR_ID, "Aleksandr", "Abramov", null, false, GENDER_MALE, LEVEL_HIGH)
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
    }

    override suspend fun updateGameData(update: (GameData) -> GameData): VolleyResult<GameData, ErrorType> {
        return try {
            val updatedData = update(_gameData.value)
            _gameData.value = updatedData
            VolleyResult.Success(updatedData)
        } catch (e: IllegalArgumentException) {
            // Обработка некорректных данных (BAD_REQUEST)
            Log.e("CreateNewGameRepositoryImpl", "IllegalArgumentException during updateGameData", e)
            VolleyResult.Failure(ErrorType.BAD_REQUEST)
        } catch (e: NullPointerException) {
            // Обработка null-значений (BAD_REQUEST)
            Log.e("CreateNewGameRepositoryImpl", "NullPointerException during updateGameData", e)
            VolleyResult.Failure(ErrorType.BAD_REQUEST)
        } catch (e: SecurityException) {
            // Обработка ошибок безопасности (UNAUTHORIZED)
            Log.e("CreateNewGameRepositoryImpl", "SecurityException during updateGameData", e)
            VolleyResult.Failure(ErrorType.UNAUTHORIZED)
        } catch (e: IOException) {
            //  Обработка проблем с вводом-выводом (например, сетевые ошибки)
            Log.e("CreateNewGameRepositoryImpl", "IOException during updateGameData", e)
            VolleyResult.Failure(ErrorType.NETWORK_ERROR) //  Предположим, что у вас есть такой ErrorType
        } catch (e: RuntimeException) { // Или другой подкласс RuntimeException, подходящий для вашей ситуации
            Log.e("CreateNewGameRepositoryImpl", "RuntimeException during updateGameData", e)
            VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
        }
    }
}
