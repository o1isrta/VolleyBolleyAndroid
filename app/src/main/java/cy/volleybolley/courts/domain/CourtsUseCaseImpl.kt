package cy.volleybolley.courts.domain

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.courts.domain.api.CourtsRepository
import cy.volleybolley.courts.domain.api.CourtsUseCase
import cy.volleybolley.courts.domain.model.Court

class CourtsUseCaseImpl(
    val repository: CourtsRepository,
) : CourtsUseCase {

    override suspend fun getCourts(searchQuery: String?): VolleyResult<List<Court>, ErrorType> {
        return repository.getCourts(searchQuery)
    }
}
