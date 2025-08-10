package cy.volleybolley.referencedata.data.cache

import cy.volleybolley.referencedata.data.localdto.CountryLocalDto
import cy.volleybolley.referencedata.data.localdto.CurrencyLocalDto
import cy.volleybolley.referencedata.data.localdto.FaqLocalDto

interface ReferenceDataLocalRepository {
    suspend fun saveCountries(data: List<CountryLocalDto>)
    suspend fun loadCountries(): List<CountryLocalDto>?
    suspend fun saveCurrencies(data: List<CurrencyLocalDto>)
    suspend fun loadCurrencies(): List<CurrencyLocalDto>?
    suspend fun saveFaq(data: FaqLocalDto)
    suspend fun loadFaq(): FaqLocalDto?
}
