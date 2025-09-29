package cy.volleybolley.referencedata.domain.api

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.domain.model.Country
import cy.volleybolley.referencedata.domain.model.Currency
import cy.volleybolley.referencedata.domain.model.Faq

interface ReferenceDataRemoteRepository {
    suspend fun getCountries(): VolleyResult<List<Country>, ErrorType>
    suspend fun getCurrencies(): VolleyResult<List<Currency>, ErrorType>
    suspend fun getFaq(): VolleyResult<Faq, ErrorType>
}
