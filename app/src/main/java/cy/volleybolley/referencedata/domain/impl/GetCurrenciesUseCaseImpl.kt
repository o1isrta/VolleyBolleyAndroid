package cy.volleybolley.referencedata.domain.impl

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.domain.api.GetCurrenciesUseCase
import cy.volleybolley.referencedata.domain.api.ReferenceDataRemoteRepository
import cy.volleybolley.referencedata.domain.model.Currency
import kotlinx.coroutines.flow.Flow

class GetCurrenciesUseCaseImpl(private val remoteRepository: ReferenceDataRemoteRepository) :
    GetCurrenciesUseCase {
    override fun execute(): Flow<VolleyResult<List<Currency>, ErrorType>> {
        return remoteRepository.getCurrencies()
    }
}
