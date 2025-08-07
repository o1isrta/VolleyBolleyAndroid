package cy.volleybolley.referencedata.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface GetCountriesUseCase {
    fun execute(): Flow<VolleyResult<List<Country>, ErrorType>>
}
