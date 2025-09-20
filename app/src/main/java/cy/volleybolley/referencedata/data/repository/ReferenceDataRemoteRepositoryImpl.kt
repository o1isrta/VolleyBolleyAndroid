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

class ReferenceDataRemoteRepositoryImpl(
    private val networkClient: NetworkClient<ReferenceDataRequest, ReferenceDataResponse>,
    private val localRepository: ReferenceDataLocalRepository
) : ReferenceDataRemoteRepository {
    override suspend fun getCountries(): VolleyResult<List<Country>, ErrorType> {
        val response = networkClient.getResponse(ReferenceDataRequest.CountriesRequest())

        return when (response.isSuccess) {
            true -> {
                val result =
                    (response.body as? ReferenceDataResponse.CountriesResponse)?.countries?.mapToDomain()

                result?.let {
                    if (localRepository.saveCountries(it.mapToLocalDto())) {
                        VolleyResult.Success(
                            localRepository.loadCountries()?.mapToDomain().orEmpty()
                        )
                    } else {
                        VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
                    }

                } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
            }

            false -> {
                VolleyResult.Failure(response.resultCode.mapToErrorType())
            }
        }
    }

    override suspend fun getCurrencies(): VolleyResult<List<Currency>, ErrorType> {
        val response = networkClient.getResponse(ReferenceDataRequest.CurrencyRequest())

        return when (response.isSuccess) {
            true -> {
                val result =
                    (response.body as? ReferenceDataResponse.CurrenciesResponse)?.currencies?.mapToDomain()

                result?.let {
                    localRepository.saveCurrencies(it.mapToLocalDto())
                    VolleyResult.Success(
                        localRepository.loadCurrencies()?.mapToDomain().orEmpty()
                    )
                } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
            }

            false -> {
                VolleyResult.Failure(response.resultCode.mapToErrorType())
            }
        }
    }

    override suspend fun getFaq(): VolleyResult<Faq, ErrorType> {
        val response = networkClient.getResponse(ReferenceDataRequest.FaqRequest())

        return when (response.isSuccess) {
            true -> {
                val result =
                    (response.body as? ReferenceDataResponse.FaqResponse)?.faqDto?.mapToDomain()

                result?.let {
                    localRepository.saveFaq(it.mapToLocalDto())
                    VolleyResult.Success(
                        localRepository.loadFaq()?.mapToDomain() ?: Faq("")
                    )
                } ?: VolleyResult.Failure(ErrorType.UNKNOWN_ERROR)
            }

            false -> {
                VolleyResult.Failure(response.resultCode.mapToErrorType())
            }
        }
    }
}
