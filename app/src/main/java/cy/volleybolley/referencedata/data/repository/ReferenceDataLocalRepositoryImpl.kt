package cy.volleybolley.referencedata.data.repository

import cy.volleybolley.referencedata.data.cache.ReferenceDataLocalRepository
import cy.volleybolley.referencedata.data.localdto.CountryLocalDto
import cy.volleybolley.referencedata.data.localdto.CurrencyLocalDto
import cy.volleybolley.referencedata.data.localdto.FaqLocalDto
import kotlinx.serialization.json.Json
import java.io.File

class ReferenceDataLocalRepositoryImpl(private val cacheDir: File, private val json: Json) :
    ReferenceDataLocalRepository {

    private fun getFile(key: String): File {
        return File(cacheDir, "$key.json")
    }

    override suspend fun saveCountries(data: List<CountryLocalDto>) {
        val jsonString = json.encodeToString(data)
        getFile(COUNTRIES_CACHE).writeText(jsonString)
    }

    override suspend fun loadCountries(): List<CountryLocalDto>? {
        val file = getFile(COUNTRIES_CACHE)
        return if (file.exists()) {
            json.decodeFromString(file.readText())
        } else {
            null
        }
    }

    override suspend fun saveCurrencies(data: List<CurrencyLocalDto>) {
        val jsonString = json.encodeToString(data)
        getFile(CURRENCIES_CACHE).writeText(jsonString)
    }

    override suspend fun loadCurrencies(): List<CurrencyLocalDto>? {
        val file = getFile(CURRENCIES_CACHE)
        return if (file.exists()) {
            json.decodeFromString(file.readText())
        } else {
            null
        }
    }

    override suspend fun saveFaq(data: FaqLocalDto) {
        val jsonString = json.encodeToString(data)
        getFile(FAQ_CACHE).writeText(jsonString)
    }

    override suspend fun loadFaq(): FaqLocalDto? {
        val file = getFile(FAQ_CACHE)
        return if (file.exists()) {
            json.decodeFromString(file.readText())
        } else {
            null
        }
    }

    companion object {
        private const val COUNTRIES_CACHE = "countries_cache"
        private const val CURRENCIES_CACHE = "currencies_cache"
        private const val FAQ_CACHE = "faq_cache"
    }
}
