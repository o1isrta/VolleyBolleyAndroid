package cy.volleybolley.referencedata.di

import android.content.Context
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
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val referenceDataModule = module {
    single { Json { prettyPrint = true } }
    single {
        val context = get<Context>()
        context.cacheDir
    }

    single<NetworkClient<ReferenceDataRequest, ReferenceDataResponse>>(
        HttpClientQualifier.REFERENCE_DATA.qualifier
    ) {
        ReferenceDataNetworkClient()
    }

    single<ReferenceDataRemoteRepository> {
        ReferenceDataRemoteRepositoryImpl(
            networkClient = get(),
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
        GetCountriesUseCaseImpl(remoteRepository = get())
    }
    single<GetCurrenciesUseCase> {
        GetCurrenciesUseCaseImpl(remoteRepository = get())
    }
    single<GetFaqUseCase> {
        GetFaqUseCaseImpl(remoteRepository = get())
    }
}
