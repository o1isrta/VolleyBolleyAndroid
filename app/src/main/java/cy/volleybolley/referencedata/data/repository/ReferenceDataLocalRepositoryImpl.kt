package cy.volleybolley.referencedata.data.repository

import android.util.Log
import cy.volleybolley.BuildConfig
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

    override suspend fun saveCountries(data: List<CountryLocalDto>): Boolean {
        runCatching {
            val jsonString = json.encodeToString(data)
            getFile(COUNTRIES_CACHE).writeText(jsonString)
        }.onFailure { exception ->
            if (BuildConfig.DEBUG) {
                Log.e(ERROR_TAG, "Save $COUNTRIES_CACHE error", exception)
            }
            return false
        }
        return true
    }

    override suspend fun loadCountries(): List<CountryLocalDto>? {
        return runCatching {
            val file = getFile(COUNTRIES_CACHE)

            if (file.exists()) {
                json.decodeFromString<List<CountryLocalDto>>(file.readText())
            } else {
                null
            }
        }.onFailure { exception ->
            if (BuildConfig.DEBUG) {
                Log.e(ERROR_TAG, "Load $COUNTRIES_CACHE error", exception)
            }
        }.getOrNull()
    }

    override suspend fun saveCurrencies(data: List<CurrencyLocalDto>): Boolean {
        val jsonString = json.encodeToString(data)
        runCatching {
            getFile(CURRENCIES_CACHE).writeText(jsonString)
        }.onFailure { exception ->
            if (BuildConfig.DEBUG) {
                Log.e(ERROR_TAG, "Save $CURRENCIES_CACHE error", exception)
            }
            return false
        }
        return true
    }

    override suspend fun loadCurrencies(): List<CurrencyLocalDto>? {
        return runCatching {
            val file = getFile(CURRENCIES_CACHE)

            if (file.exists()) {
                json.decodeFromString<List<CurrencyLocalDto>>(file.readText())
            } else {
                null
            }
        }.onFailure { exception ->
            if (BuildConfig.DEBUG) {
                Log.e(ERROR_TAG, "Load $CURRENCIES_CACHE error", exception)
            }
        }.getOrNull()
    }

    override suspend fun saveFaq(data: FaqLocalDto): Boolean {
        val jsonString = json.encodeToString(data)
        runCatching {
            getFile(FAQ_CACHE).writeText(jsonString)
        }.onFailure { exception ->
            if (BuildConfig.DEBUG) {
                Log.e(ERROR_TAG, "Save $FAQ_CACHE error", exception)
            }
            return false
        }
        return true
    }

    override suspend fun loadFaq(): FaqLocalDto? {
        return runCatching {
            val file = getFile(FAQ_CACHE)

            if (file.exists()) {
                json.decodeFromString<FaqLocalDto>(file.readText())
            } else {
                null
            }
        }.onFailure { exception ->
            if (BuildConfig.DEBUG) {
                Log.e(ERROR_TAG, "Load $FAQ_CACHE error", exception)
            }
        }.getOrNull()
    }

    private companion object {
        const val COUNTRIES_CACHE = "countries_cache"
        const val CURRENCIES_CACHE = "currencies_cache"
        const val FAQ_CACHE = "faq_cache"
        const val ERROR_TAG = "ReferenceDataCaching"
    }
}
