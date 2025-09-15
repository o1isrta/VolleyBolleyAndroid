package cy.volleybolley.referencedata.domain.impl

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.data.cache.ReferenceDataLocalRepository
import cy.volleybolley.referencedata.data.mapper.mapToDomain
import cy.volleybolley.referencedata.domain.api.GetCurrenciesUseCase
import cy.volleybolley.referencedata.domain.api.ReferenceDataRemoteRepository
import cy.volleybolley.referencedata.domain.model.Currency

class GetCurrenciesUseCaseImpl(
    private val remoteRepository: ReferenceDataRemoteRepository,
    private val localRepository: ReferenceDataLocalRepository
) :
    GetCurrenciesUseCase {
    override suspend fun execute(): VolleyResult<List<Currency>, ErrorType> {
        return when (val resultFromWeb = remoteRepository.getCurrencies()) {

            is VolleyResult.Success -> resultFromWeb
            is VolleyResult.Failure -> {
                localRepository.loadCurrencies()?.let { currenciesFromCache ->
                    VolleyResult.Success(
                        currenciesFromCache.mapToDomain()
                    )
                } ?: resultFromWeb
            }
        }
    }
}
