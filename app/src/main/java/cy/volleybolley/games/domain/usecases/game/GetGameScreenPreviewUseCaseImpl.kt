package cy.volleybolley.games.domain.usecases.game

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.games.domain.api.GamesRepository
import cy.volleybolley.games.domain.api.game.GetGameScreenPreviewUseCase
import cy.volleybolley.games.domain.model.event.Preview

class GetGameScreenPreviewUseCaseImpl(private val repository: GamesRepository) : GetGameScreenPreviewUseCase {
    override suspend fun getPreview(): VolleyResult<Preview, ErrorType> {
        return repository.getPreview()
    }
}
