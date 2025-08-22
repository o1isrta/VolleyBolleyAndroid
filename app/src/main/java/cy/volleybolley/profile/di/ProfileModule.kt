package cy.volleybolley.profile.di

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.profile.data.ProfileRepositoryImpl
import cy.volleybolley.profile.data.network.ProfileNetworkClient
import cy.volleybolley.profile.data.network.model.ProfileRequest
import cy.volleybolley.profile.data.network.model.ProfileResponse
import cy.volleybolley.profile.domain.DeleteAvatarUseCase
import cy.volleybolley.profile.domain.DeleteProfileUseCase
import cy.volleybolley.profile.domain.GetPaymentsUseCase
import cy.volleybolley.profile.domain.GetPersonalDataUseCase
import cy.volleybolley.profile.domain.UpdateAvatarUseCase
import cy.volleybolley.profile.domain.UpdatePaymentsUseCase
import cy.volleybolley.profile.domain.UpdatePersonalDataUseCase
import cy.volleybolley.profile.domain.api.ProfileRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val profileModule = module {
    // Data
    single<NetworkClient<ProfileRequest, ProfileResponse>>(HttpClientQualifier.PROFILE.qualifier) {
        ProfileNetworkClient()
    }

    single<ProfileRepository> { ProfileRepositoryImpl(networkClient = get(named(HttpClientQualifier.PROFILE.value))) }

    // Domain
    factory { GetPersonalDataUseCase(repository = get()) }
    factory { GetPaymentsUseCase(repository = get()) }
    factory { UpdatePersonalDataUseCase(repository = get()) }
    factory { UpdatePaymentsUseCase(repository = get()) }
    factory { UpdateAvatarUseCase(repository = get()) }
    factory { DeleteProfileUseCase(repository = get()) }
    factory { DeleteAvatarUseCase(repository = get()) }
}
