package cy.volleybolley.players.data.repository

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.ui.GENDER_FEMALE
import cy.volleybolley.core.presentation.ui.GENDER_MALE
import cy.volleybolley.core.presentation.ui.LEVEL_HIGH
import cy.volleybolley.core.presentation.ui.LEVEL_LIGHT
import cy.volleybolley.core.presentation.ui.LEVEL_MEDIUM
import cy.volleybolley.core.presentation.ui.LEVEL_PRO
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.model.PlayerDetail
import cy.volleybolley.players.domain.repository.PlayersRepository

class MockPlayersRepositoryImpl : PlayersRepository {
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
        private const val ALEKSANDRA_ID = 11
        private const val MONIKA_ID = 12
        private const val JOHN_ID = 13
        private const val MARYA_ID = 14
        private const val SERGEY2_ID = 15
        private const val SVETLANA2_ID = 16
    }

    private val mockPlayers = listOf(
        Player(KRISTINA_ID, "Kristina", "Popova", null, true, LEVEL_MEDIUM, GENDER_FEMALE),
        Player(POLINA_ID, "Polina", "Vasylyeva", null, false, LEVEL_PRO, GENDER_FEMALE),
        Player(ANTON_ID, "Anton", "Ivanov", null, true, LEVEL_LIGHT, GENDER_MALE),
        Player(ALEKSANDR_ID, "Aleksandr", "Abramov", null, false, LEVEL_HIGH, GENDER_MALE),
        Player(MARK_ID, "Mark", "Johansen", null, true, LEVEL_MEDIUM, GENDER_MALE),
        Player(POL_ID, "Pol", "Johnson", null, false, LEVEL_MEDIUM, GENDER_MALE),
        Player(ANN_ID, "Ann", "Nissan", null, true, LEVEL_LIGHT, GENDER_FEMALE),
        Player(SERGEY_ID, "Sergey", "Krugovsky", null, false, LEVEL_HIGH, GENDER_MALE),
        Player(SVETLANA_ID, "Svetlana", "Markova", null, true, LEVEL_MEDIUM, GENDER_FEMALE,),
        Player(NIKITA_ID, "Nikita", "Hotko", null, true, LEVEL_MEDIUM, GENDER_MALE,),
        Player(ALEKSANDRA_ID, "Aleksandra", "Malinina", null, false, LEVEL_HIGH, GENDER_FEMALE),
        Player(MONIKA_ID, "Monika", "Dobson", null, true, LEVEL_MEDIUM, GENDER_FEMALE),
        Player(JOHN_ID, "John", "Johnson", null, false, LEVEL_HIGH, GENDER_MALE),
        Player(MARYA_ID, "Maria", "Kalinina", null, true, LEVEL_PRO, GENDER_FEMALE),
        Player(SERGEY2_ID, "Sergey", "Karp", null, false, LEVEL_MEDIUM, GENDER_MALE),
        Player(SVETLANA2_ID, "Svetlana", "Dubstsova", null, true, LEVEL_MEDIUM, GENDER_FEMALE,),

        /* Player(11, "Kristina", "Popova", null, true, LEVEL_MEDIUM, GENDER_FEMALE),
               Player(12, "Polina", "Vasylyeva", null, false, LEVEL_PRO, GENDER_FEMALE),
               Player(13, "Anton", "Ivanov", null, true, LEVEL_LIGHT, GENDER_MALE),
               Player(14, "Aleksandr", "Abramov", null, false, LEVEL_HIGH, GENDER_MALE),
               Player(15, "Maria", "Novak", null, false, LEVEL_PRO, GENDER_FEMALE),
               Player(16, "Kristina", "Popova", null, true, LEVEL_MEDIUM, GENDER_FEMALE),
               Player(17, "Polina", "Vasylyeva", null, false, LEVEL_PRO, GENDER_FEMALE),
               Player(18, "Anton", "Ivanov", null, true, LEVEL_LIGHT, GENDER_MALE),
               Player(19, "Aleksandr", "Abramov", null, false, LEVEL_HIGH, GENDER_MALE),
               Player(20, "Maria", "Novak", null, false, LEVEL_PRO, GENDER_FEMALE),
               Player(21, "Kristina", "Popova", null, true, LEVEL_MEDIUM, GENDER_FEMALE),
               Player(22, "Polina", "Vasylyeva", null, false, LEVEL_PRO, GENDER_FEMALE),
               Player(23, "Anton", "Ivanov", null, true, LEVEL_LIGHT, GENDER_MALE),
               Player(24, "Aleksandr", "Abramov", null, false, LEVEL_HIGH, GENDER_MALE),
               Player(25, "Maria", "Novak", null, false, LEVEL_PRO, GENDER_FEMALE),
               Player(26, "Kristina", "Popova", null, true, LEVEL_MEDIUM, GENDER_FEMALE),
               Player(27, "Polina", "Vasylyeva", null, false, LEVEL_PRO, GENDER_FEMALE),
               Player(28, "Anton", "Ivanov", null, true, LEVEL_LIGHT, GENDER_MALE),
               Player(29, "Aleksandr", "Abramov", null, false, LEVEL_HIGH, GENDER_MALE),
               Player(30, "Maria", "Novak", null, false, LEVEL_PRO, GENDER_FEMALE),
               Player(31, "Kristina", "Popova", null, true, LEVEL_MEDIUM, GENDER_FEMALE),
               Player(32, "Polina", "Vasylyeva", null, false, LEVEL_PRO, GENDER_FEMALE),
               Player(33, "Anton", "Ivanov", null, true, LEVEL_LIGHT, GENDER_MALE),
               Player(34, "Aleksandr", "Abramov", null, false, LEVEL_HIGH, GENDER_MALE),
               Player(35, "Maria", "Novak", null, false, LEVEL_PRO, GENDER_FEMALE)*/
    )

    override suspend fun getAllPlayers(): VolleyResult<List<Player>, ErrorType> =
        VolleyResult.Success(mockPlayers)

//    override suspend fun searchPlayers(query: String): VolleyResult<List<Player>, ErrorType> {
//        if (query.isNullOrEmpty()) return VolleyResult.Success(emptyList())
//        val filteredPlayers = mockPlayers.filter {
//            it.firstName.contains(query, ignoreCase = true) ||
//                it.lastName.contains(query, ignoreCase = true)
//        }
//        return VolleyResult.Success(filteredPlayers)
//    }

    override suspend fun getPlayerDetail(playerId: Int): VolleyResult<PlayerDetail, ErrorType> {
        return VolleyResult.Success(
            PlayerDetail(
                id = playerId,
                firstName = "Mock Name",
                lastName = "Mock LastName",
                avatarUrl = null,
                isFavorite = true,
                level = "Pro",
                gender = "Female",
                latestActivity = listOf()
            )
        )
    }

    override suspend fun addToFavorites(playerId: Int): VolleyResult<Player, ErrorType> {
        return VolleyResult.Success(mockPlayers.first())
    }

    override suspend fun removeFromFavorites(playerId: Int): VolleyResult<Unit, ErrorType> {
        return VolleyResult.Success(Unit)
    }

    // Эти три метода тут ни о чем, просто пришлось пока имплементить, потому что расширил интерфейс.
    override fun getCachedAllPlayers(): List<Player> = emptyList()

    override fun getCachedFavoritePlayers(): List<Player> = emptyList()

    override suspend fun getFavoritePlayers(): VolleyResult<List<Player>, ErrorType> =
        VolleyResult.Success(mockPlayers)
}
