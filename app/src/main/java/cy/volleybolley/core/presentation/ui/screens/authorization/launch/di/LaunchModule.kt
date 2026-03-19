package cy.volleybolley.core.presentation.ui.screens.authorization.launch.di

import cy.volleybolley.core.presentation.ui.screens.authorization.launch.LaunchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val launchModule = module {
    viewModel {
        LaunchViewModel(
            getRefreshTokenUseCase = get(),
            getIsRegisteredUseCase = get(),
            getCountriesUseCase = get()
        )
    }
}
