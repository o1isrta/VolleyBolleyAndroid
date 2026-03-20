package cy.volleybolley.core.presentation.ui.screens.createNewGame.createNewGameRepository

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

class CreateNewGameRepositoryImpl : CreateNewGameRepository {
    private val _gameData = MutableStateFlow(GameData())
    override val gameData: StateFlow<GameData> = _gameData // Expose as immutable StateFlow

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
                Player(ALEKSANDR_ID, "Aleksandr", "Abramov", null, false, GENDER_MALE, LEVEL_HIGH),
                Player(MARK_ID, "Mark", "Yohansen", null, true, GENDER_MALE, LEVEL_MEDIUM),
                Player(POL_ID, "Pol", "Jhonson", null, false, GENDER_MALE, LEVEL_MEDIUM),
                Player(ANN_ID, "Ann", "Nissan", null, true, GENDER_FEMALE, LEVEL_LIGHT),
                Player(SERGEY_ID, "Sergey", "Krugovsky", null, false, GENDER_MALE, LEVEL_HIGH),
                Player(SVETLANA_ID, "Svetlana", "Markova", null, true, GENDER_FEMALE, LEVEL_MEDIUM),
                Player(NIKITA_ID, "Nikita", "Popov", null, false, GENDER_MALE, LEVEL_PRO)
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
        // заглушка
        val updatedData = update(_gameData.value)
        _gameData.value = updatedData
        return VolleyResult.Success(updatedData)

/*        val response = networkClient.getResponse(
            GRequest.UpdatePersonalData(
                body = actualChangesOnPersonalData?.toUpdateBody() ?: personalData.toUpdateBody()
            )
        )
        return if (response.isSuccess) {
            VolleyResult.Success(updatedData)
        } else {
            VolleyResult.Failure(response.resultCode.mapToErrorType())
        }*/
    }

    companion object {
        const val DEBOUNCE_DELAY_500MS = 500L
        const val DEBOUNCE_DELAY_1000MS = 1000L
        private const val KRISTINA_ID = 1
        private const val POLINA_ID = 2
        private const val ANTON_ID = 3
        private const val ALEKSANDR_ID = 4
        private const val MARK_ID = 5
        private const val POL_ID = 6
        private const val ANN_ID = 7
        private const val SERGEY_ID = 8
        private const val SVETLANA_ID = 9
        private const val NIKITA_ID = 10
    }
}
