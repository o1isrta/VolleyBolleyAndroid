package cy.volleybolley.referencedata.domain.impl

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.domain.api.GetCountriesUseCase
import cy.volleybolley.referencedata.domain.api.ReferenceDataRemoteRepository
import cy.volleybolley.referencedata.domain.model.Country
import kotlinx.coroutines.flow.Flow

class GetCountriesUseCaseImpl(private val remoteRepository: ReferenceDataRemoteRepository) :
    GetCountriesUseCase {
    override fun execute(): Flow<VolleyResult<List<Country>, ErrorType>> {
        return remoteRepository.getCountries()
    }
}
