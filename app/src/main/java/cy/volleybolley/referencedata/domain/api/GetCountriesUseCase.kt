package cy.volleybolley.referencedata.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.domain.model.Country

interface GetCountriesUseCase {
    suspend fun execute(): VolleyResult<List<Country>, ErrorType>
}
