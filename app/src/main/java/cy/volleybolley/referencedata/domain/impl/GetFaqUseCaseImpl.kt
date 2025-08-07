package cy.volleybolley.referencedata.domain.impl

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.domain.api.ReferenceDataRemoteRepository
import cy.volleybolley.referencedata.domain.model.Faq
import cy.volleybolley.referencedata.domain.api.GetFaqUseCase
import kotlinx.coroutines.flow.Flow

class GetFaqUseCaseImpl(private val remoteRepository: ReferenceDataRemoteRepository) : GetFaqUseCase {
    override fun execute(): Flow<VolleyResult<Faq, ErrorType>> {
        return remoteRepository.getFaq()
    }
}
