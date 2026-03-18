package cy.volleybolley.registration.di

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.registration.data.RegistrationRepositoryImpl
import cy.volleybolley.registration.data.network.RegistrationNetworkClient
import cy.volleybolley.registration.data.network.model.RegistrationRequest
import cy.volleybolley.registration.data.network.model.RegistrationResponse
import cy.volleybolley.registration.domain.UserRegistrationUseCase
import cy.volleybolley.registration.domain.api.RegistrationRepository
import cy.volleybolley.registration.presentation.ui.screens.registration.RegistrationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val registrationModule = module {
    single<NetworkClient<RegistrationRequest, RegistrationResponse>>(HttpClientQualifier.REGISTRATION.qualifier) {
        RegistrationNetworkClient()
    }

    single<RegistrationRepository> {
        RegistrationRepositoryImpl(
            networkClient = get(HttpClientQualifier.REGISTRATION.qualifier)
        )
    }

    factory {
        UserRegistrationUseCase(repository = get())
    }

    viewModel {
        RegistrationViewModel(
            getCountriesUseCase = get(),
            userRegistrationUseCase = get(),
            getPersonalDataUseCase = get(),
            savePersonalDataUseCase = get()
        )
    }
}
