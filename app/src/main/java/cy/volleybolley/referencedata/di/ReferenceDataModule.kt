package cy.volleybolley.referencedata.di

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.referencedata.data.cache.ReferenceDataLocalRepository
import cy.volleybolley.referencedata.data.network.ReferenceDataNetworkClient
import cy.volleybolley.referencedata.data.network.ReferenceDataRequest
import cy.volleybolley.referencedata.data.network.ReferenceDataResponse
import cy.volleybolley.referencedata.data.repository.ReferenceDataLocalRepositoryImpl
import cy.volleybolley.referencedata.data.repository.ReferenceDataRemoteRepositoryImpl
import cy.volleybolley.referencedata.domain.api.GetCountriesUseCase
import cy.volleybolley.referencedata.domain.api.GetCurrenciesUseCase
import cy.volleybolley.referencedata.domain.api.GetFaqUseCase
import cy.volleybolley.referencedata.domain.api.ReferenceDataRemoteRepository
import cy.volleybolley.referencedata.domain.impl.GetCountriesUseCaseImpl
import cy.volleybolley.referencedata.domain.impl.GetCurrenciesUseCaseImpl
import cy.volleybolley.referencedata.domain.impl.GetFaqUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val referenceDataModule = module {
    single {
        androidContext().cacheDir
    }

    single<NetworkClient<ReferenceDataRequest, ReferenceDataResponse>>(
        HttpClientQualifier.REFERENCE_DATA.qualifier
    ) {
        ReferenceDataNetworkClient(lazyHttpClient = inject(HttpClientQualifier.NO_ACCESS_TOKEN.qualifier))
    }

    single<ReferenceDataRemoteRepository> {
        ReferenceDataRemoteRepositoryImpl(
            networkClient = get(HttpClientQualifier.REFERENCE_DATA.qualifier),
            localRepository = get()
        )
    }
    single<ReferenceDataLocalRepository> {
        ReferenceDataLocalRepositoryImpl(
            json = get(),
            cacheDir = get()
        )
    }

    single<GetCountriesUseCase> {
        GetCountriesUseCaseImpl(
            remoteRepository = get(),
            localRepository = get()
        )
    }
    single<GetCurrenciesUseCase> {
        GetCurrenciesUseCaseImpl(
            remoteRepository = get(),
            localRepository = get()
        )
    }
    single<GetFaqUseCase> {
        GetFaqUseCaseImpl(
            remoteRepository = get(),
            localRepository = get()
        )
    }
}
