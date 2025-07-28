package cy.volleybolley.referencedata.data.repository

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.data.network.model.mapToErrorType
import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.data.dto.mapToDomain
import cy.volleybolley.referencedata.data.network.ReferenceDataRequest
import cy.volleybolley.referencedata.data.network.ReferenceDataResponse
import cy.volleybolley.referencedata.data.network.mapToDomain
import cy.volleybolley.referencedata.domain.api.ReferenceDataRemoteRepository
import cy.volleybolley.referencedata.domain.model.Country
import cy.volleybolley.referencedata.domain.model.Currency
import cy.volleybolley.referencedata.domain.model.Faq
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReferenceDataRemoteRepositoryImpl(private val networkClient: NetworkClient<ReferenceDataRequest, ReferenceDataResponse>) :
    ReferenceDataRemoteRepository {
    override fun getCountries(): Flow<VolleyResult<List<Country>, ErrorType>> = flow {
        val response = networkClient.getResponse(ReferenceDataRequest.CountriesRequest())

        when (response.isSuccess) {
            true -> {
                emit(
                    VolleyResult.Success(
                        (response.body as ReferenceDataResponse.CountriesResponse).countries.map { dto -> dto.mapToDomain() }
                    )
                )
                // и кладем result в кеш (LocalRepository)
            }

            false -> {
                emit(
                    VolleyResult.Failure(response.resultCode.mapToErrorType())
                )
                // В противном случае подгружаем страны из кеша
                // Если и в памяти нет, то тогда отправляем ошибку
            }
        }
    }

    override fun getCurrency(): Flow<VolleyResult<Currency, ErrorType>> = flow {
        val response = networkClient.getResponse(ReferenceDataRequest.CurrencyRequest())

        when (response.isSuccess) {
            true -> {
                emit(
                    VolleyResult.Success(
                        (response.body as ReferenceDataResponse.CurrencyResponse).mapToDomain()
                    )
                )
                // и не забываем положить result в кеш (LocalRepository)
            }

            false -> {
                emit(VolleyResult.Failure(response.resultCode.mapToErrorType()))
                // В противном случае подгружаем валюту из кеша
                // Если и в памяти нет, то тогда отправляем ошибку
            }
        }
    }

    override fun getFaq(): Flow<VolleyResult<Faq, ErrorType>> = flow {
        val response = networkClient.getResponse(ReferenceDataRequest.FaqRequest())

        when (response.isSuccess) {
            true -> {
                emit(
                    VolleyResult.Success(
                        (response.body as ReferenceDataResponse.FaqResponse).mapToDomain()
                    )
                )
                // и не забываем положить result в кеш (LocalRepository)
            }

            false -> {
                emit(VolleyResult.Failure(response.resultCode.mapToErrorType()))
                // В противном случае подгружаем faq из кеша
                // Если и в памяти нет, то тогда отправляем ошибку
            }
        }
    }
}
