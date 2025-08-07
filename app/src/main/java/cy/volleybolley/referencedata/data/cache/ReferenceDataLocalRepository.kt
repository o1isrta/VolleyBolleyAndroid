package cy.volleybolley.referencedata.data.cache

import cy.volleybolley.referencedata.data.localdto.CountryLocalDto
import cy.volleybolley.referencedata.data.localdto.CurrencyLocalDto
import cy.volleybolley.referencedata.data.localdto.FaqLocalDto

interface ReferenceDataLocalRepository {
    fun saveCountries(data: List<CountryLocalDto>)
    fun loadCountries(): List<CountryLocalDto>?
    fun saveCurrencies(data: List<CurrencyLocalDto>)
    fun loadCurrencies(): List<CurrencyLocalDto>?
    fun saveFaq(data: FaqLocalDto)
    fun loadFaq(): FaqLocalDto?
}
