package cy.volleybolley.referencedata.domain.impl

import cy.volleybolley.core.domain.model.ErrorType
import cy.volleybolley.core.domain.model.VolleyResult
import cy.volleybolley.referencedata.data.cache.ReferenceDataLocalRepository
import cy.volleybolley.referencedata.data.mapper.mapToDomain
import cy.volleybolley.referencedata.domain.api.GetCountriesUseCase
import cy.volleybolley.referencedata.domain.api.ReferenceDataRemoteRepository
import cy.volleybolley.referencedata.domain.model.Country

class GetCountriesUseCaseImpl(
    private val remoteRepository: ReferenceDataRemoteRepository,
    private val localRepository: ReferenceDataLocalRepository
) : GetCountriesUseCase {
    override suspend fun execute(): VolleyResult<List<Country>, ErrorType> {
        when (val resultFromWeb = remoteRepository.getCountries()) {
            is VolleyResult.Success -> {
                return resultFromWeb
            }

            is VolleyResult.Failure -> {
                val countriesFromCache = localRepository.loadCountries()
                countriesFromCache?.let {
                    return VolleyResult.Success(
                        countriesFromCache.mapToDomain()
                    )
                } ?: return resultFromWeb
            }
        }
    }
}
