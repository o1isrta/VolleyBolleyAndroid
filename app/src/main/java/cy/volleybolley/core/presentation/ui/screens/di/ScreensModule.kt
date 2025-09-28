package cy.volleybolley.core.presentation.ui.screens.di

import cy.volleybolley.core.presentation.ui.screens.home.success.SucceedGame
import cy.volleybolley.core.presentation.ui.screens.home.success.SuccessViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
//import cy.volleybolley.сore.domain.model.SucceedGame // Assuming SucceedGame is in this package

val screensModule = module {
    // Data

    // Domain

    // ViewModel
    viewModel { (createdEvent: SucceedGame) ->
        SuccessViewModel(createdEvent = createdEvent)
    }
}
