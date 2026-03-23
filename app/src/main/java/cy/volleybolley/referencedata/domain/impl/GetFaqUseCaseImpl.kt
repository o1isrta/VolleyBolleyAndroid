package cy.volleybolley.referencedata.domain.impl

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.data.cache.ReferenceDataLocalRepository
import cy.volleybolley.referencedata.data.mapper.mapToDomain
import cy.volleybolley.referencedata.domain.api.GetFaqUseCase
import cy.volleybolley.referencedata.domain.api.ReferenceDataRemoteRepository
import cy.volleybolley.referencedata.domain.model.Faq

class GetFaqUseCaseImpl(
    private val remoteRepository: ReferenceDataRemoteRepository,
    private val localRepository: ReferenceDataLocalRepository
) : GetFaqUseCase {
    override suspend fun execute(): VolleyResult<Faq, ErrorType> {
        return when (val resultFromWeb = remoteRepository.getFaq()) {
            is VolleyResult.Success -> resultFromWeb
            is VolleyResult.Failure -> {
                localRepository.loadFaq()?.let { faqFromCache ->
                    VolleyResult.Success(
                        faqFromCache.mapToDomain()
                    )
                } ?: resultFromWeb
            }
        }
    }
}
