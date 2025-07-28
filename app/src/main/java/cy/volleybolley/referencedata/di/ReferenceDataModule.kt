package cy.volleybolley.referencedata.di

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.referencedata.data.network.ReferenceDataNetworkClient
import cy.volleybolley.referencedata.data.network.ReferenceDataRequest
import cy.volleybolley.referencedata.data.network.ReferenceDataResponse
import cy.volleybolley.referencedata.data.repository.ReferenceDataRemoteRepositoryImpl
import cy.volleybolley.referencedata.domain.api.ReferenceDataRemoteRepository
import cy.volleybolley.referencedata.domain.impl.GetCountriesUseCaseImpl
import cy.volleybolley.referencedata.domain.impl.GetCurrencyUseCaseImpl
import cy.volleybolley.referencedata.domain.impl.GetFaqUseCaseImpl
import cy.volleybolley.referencedata.domain.usecase.GetCountriesUseCase
import cy.volleybolley.referencedata.domain.usecase.GetCurrencyUseCase
import cy.volleybolley.referencedata.domain.usecase.GetFaqUseCase
import org.koin.dsl.module

val referenceDataModule = module {
    single<NetworkClient<ReferenceDataRequest, ReferenceDataResponse>> { ReferenceDataNetworkClient() }
    single<ReferenceDataRemoteRepository> { ReferenceDataRemoteRepositoryImpl(get()) }
    // single<ReferenceDataLocalRepository<>> { ReferenceDataLocalRepositoryImpl(get()) }
    single<GetCountriesUseCase> { GetCountriesUseCaseImpl(get()) }
    single<GetCurrencyUseCase> { GetCurrencyUseCaseImpl(get()) }
    single<GetFaqUseCase> { GetFaqUseCaseImpl(get()) }
}
