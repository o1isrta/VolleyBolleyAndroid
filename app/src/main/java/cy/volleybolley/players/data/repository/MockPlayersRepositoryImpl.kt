package cy.volleybolley.players.data.repository

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.core.presentation.ui.LEVEL_HIGH
import cy.volleybolley.core.presentation.ui.LEVEL_LIGHT
import cy.volleybolley.core.presentation.ui.LEVEL_MEDIUM
import cy.volleybolley.core.presentation.ui.LEVEL_PRO
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.model.PlayerDetail
import cy.volleybolley.players.domain.repository.PlayersRepository


class MockPlayersRepositoryImpl : PlayersRepository {

    private val mockPlayers = listOf(
        Player(1, "Kristina", "Popova", null, true, LEVEL_MEDIUM),
        Player(2, "Polina", "Vasylyeva", null, false, LEVEL_PRO),
        Player(3, "Anton", "Ivanov", null, true, LEVEL_LIGHT),
        Player(4, "Aleksandr", "Abramov", null, false, LEVEL_HIGH),
        Player(5, "Maria", "Novak", null, false, LEVEL_PRO)
    )

    override suspend fun getAllPlayers(): VolleyResult<List<Player>, ErrorType> =
        VolleyResult.Success(mockPlayers)

    override suspend fun searchPlayers(query: String): VolleyResult<List<Player>, ErrorType> {
        if(query.isNullOrEmpty()) return VolleyResult.Success(emptyList())
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
