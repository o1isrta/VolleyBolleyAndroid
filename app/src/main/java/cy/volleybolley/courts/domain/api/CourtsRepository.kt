package cy.volleybolley.courts.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.courts.domain.model.Court

interface CourtsRepository {
    suspend fun getCourts(searchQuery: String = ""): VolleyResult<List<Court>, ErrorType>
}