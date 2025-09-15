package cy.volleybolley.referencedata.data.cache

import cy.volleybolley.referencedata.data.localdto.CountryLocalDto
import cy.volleybolley.referencedata.data.localdto.CurrencyLocalDto
import cy.volleybolley.referencedata.data.localdto.FaqLocalDto

interface ReferenceDataLocalRepository {
    suspend fun saveCountries(data: List<CountryLocalDto>): Boolean
    suspend fun loadCountries(): List<CountryLocalDto>?
    suspend fun saveCurrencies(data: List<CurrencyLocalDto>): Boolean
    suspend fun loadCurrencies(): List<CurrencyLocalDto>?
    suspend fun saveFaq(data: FaqLocalDto): Boolean
    suspend fun loadFaq(): FaqLocalDto?
}
