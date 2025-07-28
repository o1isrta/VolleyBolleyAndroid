package cy.volleybolley.referencedata.domain.impl

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.domain.api.ReferenceDataRemoteRepository
import cy.volleybolley.referencedata.domain.model.Currency
import cy.volleybolley.referencedata.domain.usecase.GetCurrencyUseCase
import kotlinx.coroutines.flow.Flow

class GetCurrencyUseCaseImpl(private val referenceDataRemoteRepository: ReferenceDataRemoteRepository) : GetCurrencyUseCase {
    override fun execute(): Flow<VolleyResult<Currency, ErrorType>> {
        return referenceDataRemoteRepository.getCurrency()
    }
}
