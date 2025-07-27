package cy.volleybolley.referencedata.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.domain.model.Country
import cy.volleybolley.referencedata.domain.model.Currency
import cy.volleybolley.referencedata.domain.model.Faq
import kotlinx.coroutines.flow.Flow

interface ReferenceDataRemoteRepository {
    fun getCountries(): Flow<VolleyResult<List<Country>, ErrorType>>
    fun getCurrency(): Flow<VolleyResult<Currency, ErrorType>>
    fun getFaq(): Flow<VolleyResult<Faq, ErrorType>>
}
