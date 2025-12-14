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

    private val mockPlayers = listOf(
        Player(1, "Kristina", "Popova", null, true, LEVEL_MEDIUM, GENDER_FEMALE),
        Player(2, "Polina", "Vasylyeva", null, false, LEVEL_PRO, GENDER_FEMALE),
        Player(3, "Anton", "Ivanov", null, true, LEVEL_LIGHT, GENDER_MALE),
        Player(4, "Aleksandr", "Abramov", null, false, LEVEL_HIGH, GENDER_MALE),
        Player(5, "Maria", "Novak", null, false, LEVEL_PRO, GENDER_FEMALE),
      /*  Player(6, "Kristina", "Popova", null, true, LEVEL_MEDIUM, GENDER_FEMALE),
        Player(7, "Polina", "Vasylyeva", null, false, LEVEL_PRO, GENDER_FEMALE),
        Player(8, "Anton", "Ivanov", null, true, LEVEL_LIGHT, GENDER_MALE),
        Player(9, "Aleksandr", "Abramov", null, false, LEVEL_HIGH, GENDER_MALE),
        Player(10, "Maria", "Novak", null, false, LEVEL_PRO, GENDER_FEMALE),
      */ /* Player(11, "Kristina", "Popova", null, true, LEVEL_MEDIUM, GENDER_FEMALE),
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

    override suspend fun searchPlayers(query: String): VolleyResult<List<Player>, ErrorType> {
        if (query.isNullOrEmpty()) return VolleyResult.Success(emptyList())
        val filteredPlayers = mockPlayers.filter {
            it.firstName.contains(query, ignoreCase = true) ||
                it.lastName.contains(query, ignoreCase = true)
        }
        return VolleyResult.Success(filteredPlayers)
    }

    override suspend fun getPlayerDetail(playerId: Int): VolleyResult<PlayerDetail, ErrorType> {
        // Реализуйте моковую реализацию для деталей игрока, если это нужно
        TODO("Not yet implemented")
    }

    override suspend fun addToFavorites(playerId: Int): VolleyResult<Player, ErrorType> {
        // Реализуйте моковую реализацию для добавления в избранное, если это нужно
        TODO("Not yet implemented")
    }

    override suspend fun removeFromFavorites(playerId: Int): VolleyResult<Unit, ErrorType> {
        // Реализуйте моковую реализацию для удаления из избранного, если это нужно
        TODO("Not yet implemented")
    }

}
