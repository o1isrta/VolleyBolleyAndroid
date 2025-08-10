package cy.volleybolley.referencedata.data.repository

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.data.cache.ReferenceDataLocalRepository
import cy.volleybolley.referencedata.data.mapper.mapToDomain
import cy.volleybolley.referencedata.data.mapper.mapToLocalDto
import cy.volleybolley.referencedata.data.network.ReferenceDataRequest
import cy.volleybolley.referencedata.data.network.ReferenceDataResponse
import cy.volleybolley.referencedata.domain.api.ReferenceDataRemoteRepository
import cy.volleybolley.referencedata.domain.model.Country
import cy.volleybolley.referencedata.domain.model.Currency
import cy.volleybolley.referencedata.domain.model.Faq
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReferenceDataRemoteRepositoryImpl(
    private val networkClient: NetworkClient<ReferenceDataRequest, ReferenceDataResponse>,
    private val localRepository: ReferenceDataLocalRepository
) : ReferenceDataRemoteRepository {
    override suspend fun getCountries(): Flow<VolleyResult<List<Country>, ErrorType>> = flow {
        val response = networkClient.getResponse(ReferenceDataRequest.CountriesRequest())

        when (response.isSuccess) {
            true -> {
                val result =
                    (response.body as? ReferenceDataResponse.CountriesResponse)?.countries?.mapToDomain()
                result?.let {
                    localRepository.saveCountries(it.mapToLocalDto())
                    emit(VolleyResult.Success(it))
                } ?: emit(VolleyResult.Failure(ErrorType.UNKNOWN_ERROR))
            }

            false -> {
                val countriesFromCache = localRepository.loadCountries()
                countriesFromCache?.let {
                    emit(
                        VolleyResult.Success(countriesFromCache.mapToDomain())
                    )
                } ?: emit(VolleyResult.Failure(response.resultCode.mapToErrorType()))

            }
        }
    }

    override suspend fun getCurrencies(): Flow<VolleyResult<List<Currency>, ErrorType>> = flow {
        val response = networkClient.getResponse(ReferenceDataRequest.CurrencyRequest())

        when (response.isSuccess) {
            true -> {
                val result =
                    (response.body as? ReferenceDataResponse.CurrenciesResponse)?.currencies?.mapToDomain()

                result?.let {
                    localRepository.saveCurrencies(result.mapToLocalDto())
                    emit(VolleyResult.Success(result))
                } ?: emit(VolleyResult.Failure(ErrorType.UNKNOWN_ERROR))
            }

            false -> {
                val currenciesFromCache = localRepository.loadCurrencies()

                currenciesFromCache?.let {
                    emit(VolleyResult.Success(currenciesFromCache.mapToDomain()))
                } ?: emit(VolleyResult.Failure(response.resultCode.mapToErrorType()))
            }
        }
    }

    override suspend fun getFaq(): Flow<VolleyResult<Faq, ErrorType>> = flow {
        val response = networkClient.getResponse(ReferenceDataRequest.FaqRequest())

        when (response.isSuccess) {
            true -> {
                val result =
                    (response.body as? ReferenceDataResponse.FaqResponse)?.faqDto?.mapToDomain()

                result?.let {
                    localRepository.saveFaq(result.mapToLocalDto())
                    emit(VolleyResult.Success(result))
                } ?: emit(VolleyResult.Failure(ErrorType.UNKNOWN_ERROR))
            }

            false -> {
                val faqFromCache = localRepository.loadFaq()

                faqFromCache?.let {
                    emit(VolleyResult.Success(faqFromCache.mapToDomain()))
                } ?: emit(VolleyResult.Failure(response.resultCode.mapToErrorType()))
            }
        }
    }
}
