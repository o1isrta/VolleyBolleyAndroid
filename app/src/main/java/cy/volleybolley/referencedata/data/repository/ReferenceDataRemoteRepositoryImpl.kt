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
    override fun getCountries(): Flow<VolleyResult<List<Country>, ErrorType>> = flow {
        val response = networkClient.getResponse(ReferenceDataRequest.CountriesRequest())

        when (response.isSuccess) {
            true -> {
                val result =
                    (response.body as ReferenceDataResponse.CountriesResponse).countries.mapToDomain()
                localRepository.saveCountries(result.mapToLocalDto())

                emit(
                    VolleyResult.Success(result)
                )
            }

            false -> {
                val countriesFromCache = localRepository.loadCountries()

                if (countriesFromCache != null) {
                    emit(
                        VolleyResult.Success(countriesFromCache.mapToDomain())
                    )
                } else {
                    emit(
                        VolleyResult.Failure(response.resultCode.mapToErrorType())
                    )
                }
            }
        }
    }

    override fun getCurrencies(): Flow<VolleyResult<List<Currency>, ErrorType>> = flow {
        val response = networkClient.getResponse(ReferenceDataRequest.CurrencyRequest())

        when (response.isSuccess) {
            true -> {
                val result =
                    (response.body as ReferenceDataResponse.CurrenciesResponse).currencies.mapToDomain()
                localRepository.saveCurrencies(result.mapToLocalDto())

                emit(
                    VolleyResult.Success(result)
                )
            }

            false -> {
                val currenciesFromCache = localRepository.loadCurrencies()

                if (currenciesFromCache != null) {
                    emit(VolleyResult.Success(currenciesFromCache.mapToDomain()))
                } else {
                    emit(VolleyResult.Failure(response.resultCode.mapToErrorType()))
                }
            }
        }
    }

    override fun getFaq(): Flow<VolleyResult<Faq, ErrorType>> = flow {
        val response = networkClient.getResponse(ReferenceDataRequest.FaqRequest())

        when (response.isSuccess) {
            true -> {
                val result =
                    (response.body as ReferenceDataResponse.FaqResponse).faqDto.mapToDomain()
                localRepository.saveFaq(result.mapToLocalDto())

                emit(
                    VolleyResult.Success(result)
                )
            }

            false -> {
                val faqFromCache = localRepository.loadFaq()

                if (faqFromCache != null) {
                    emit(VolleyResult.Success(faqFromCache.mapToDomain()))
                } else {
                    emit(VolleyResult.Failure(response.resultCode.mapToErrorType()))
                }
            }
        }
    }
}
