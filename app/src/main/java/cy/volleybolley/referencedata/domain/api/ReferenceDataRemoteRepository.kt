package cy.volleybolley.referencedata.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.domain.model.Country
import cy.volleybolley.referencedata.domain.model.Currency
import cy.volleybolley.referencedata.domain.model.Faq
import kotlinx.coroutines.flow.Flow

interface ReferenceDataRemoteRepository {
    suspend fun getCountries(): Flow<VolleyResult<List<Country>, ErrorType>>
    suspend fun getCurrencies(): Flow<VolleyResult<List<Currency>, ErrorType>>
    suspend fun getFaq(): Flow<VolleyResult<Faq, ErrorType>>
}
