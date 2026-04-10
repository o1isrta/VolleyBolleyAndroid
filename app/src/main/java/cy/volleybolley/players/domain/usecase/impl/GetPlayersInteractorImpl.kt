package cy.volleybolley.players.domain.usecase.impl

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.players.domain.model.Player
import cy.volleybolley.players.domain.repository.PlayersRepository
import cy.volleybolley.players.domain.usecase.GetAllPlayersUseCase
import cy.volleybolley.players.domain.usecase.GetFavoritePlayersUseCase
import cy.volleybolley.players.domain.usecase.GetPlayersInteractor

class GetPlayersInteractorImpl(
    private val getAllPlayersUseCase: GetAllPlayersUseCase,
    private val getFavoritePlayersUseCase: GetFavoritePlayersUseCase,
    private val repository: PlayersRepository,
) : GetPlayersInteractor {

    override suspend fun updatePlayers(showAllPlayers: Boolean): VolleyResult<List<Player>, ErrorType> {
        return if (showAllPlayers) getAllPlayersUseCase() else getFavoritePlayersUseCase()
    }

    override fun fetchCachedPlayers(): List<Player> = repository.getCachedAllPlayers()

    override fun fetchCachedFavoritePlayers(): List<Player> = repository.getCachedFavoritePlayers()
}
