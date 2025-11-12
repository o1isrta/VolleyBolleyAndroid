package cy.volleybolley.registration.di

import cy.volleybolley.core.data.network.api.NetworkClient
import cy.volleybolley.core.di.HttpClientQualifier
import cy.volleybolley.core.presentation.ui.screens.authorization.registration.RegistrationViewModel
import cy.volleybolley.registration.data.RegistrationRepositoryImpl
import cy.volleybolley.registration.data.network.RegistrationNetworkClient
import cy.volleybolley.registration.data.network.model.RegistrationRequest
import cy.volleybolley.registration.data.network.model.RegistrationResponse
import cy.volleybolley.registration.domain.UserRegistrationUseCase
import cy.volleybolley.registration.domain.api.RegistrationRepository
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val registrationModule = module {
    // Data
    single<NetworkClient<RegistrationRequest, RegistrationResponse>>(HttpClientQualifier.REGISTRATION.qualifier) {
        RegistrationNetworkClient()
    }

    single<RegistrationRepository> {
        RegistrationRepositoryImpl(
            networkClient = get(HttpClientQualifier.REGISTRATION.qualifier)
        )
    }

    // Domain
    factory { UserRegistrationUseCase(repository = get()) }

    // ViewModels
    viewModel { (userData: String) ->
        RegistrationViewModel(
            getCountriesUseCase = get(),
            userRegistrationUseCase = get(),
            json = get(),
            userData = userData
        )
    }
}
