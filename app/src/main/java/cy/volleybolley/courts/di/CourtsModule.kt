package cy.volleybolley.courts.di

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.courts.data.CourtsRepositoryImpl
import cy.volleybolley.courts.data.network.CourtsNetworkClient
import cy.volleybolley.courts.data.network.CourtsRequest
import cy.volleybolley.courts.data.network.CourtsResponse
import cy.volleybolley.courts.domain.CourtsUseCaseImpl
import cy.volleybolley.courts.domain.api.CourtsRepository
import cy.volleybolley.courts.domain.api.CourtsUseCase
import org.koin.dsl.module

val courtsModule = module {
    // Data
    single<NetworkClient<CourtsRequest, CourtsResponse>>(HttpClientQualifier.COURTS.qualifier) {
        CourtsNetworkClient()
    }
    single<CourtsRepository>(HttpClientQualifier.COURTS.qualifier) { CourtsRepositoryImpl(networkClient = get()) }

    // Domain
    single<CourtsUseCase> { CourtsUseCaseImpl(repository = get()) }

    // ViewModel

}
