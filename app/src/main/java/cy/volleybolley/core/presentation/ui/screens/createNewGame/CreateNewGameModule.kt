package cy.volleybolley.core.presentation.ui.screens.createNewGame

import cy.volleybolley.core.presentation.ui.screens.createNewGame.basicGameSetupScreen.BasicGameSetupScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.createNewGame.createNewGameRepository.CreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createNewGame.createNewGameRepository.CreateNewGameRepositoryImpl
import cy.volleybolley.core.presentation.ui.screens.createNewGame.gameConditions.GameConditionsViewModel
import cy.volleybolley.core.presentation.ui.screens.createNewGame.privacyOptionsScreen.PrivacyOptionsScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.BasicTourneySetupScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.TourneyEnteringConditionsScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val createNewGameModule = module {
    single<CreateNewGameRepository> { CreateNewGameRepositoryImpl() }

    // Game creation ViewModels
    viewModel { BasicGameSetupScreenViewModel(get()) }
    viewModel { GameConditionsViewModel(get()) }
    viewModel { PrivacyOptionsScreenViewModel(get(), get()) }

    // Tournament creation ViewModels
    viewModel { BasicTourneySetupScreenViewModel(get()) }
    viewModel { TourneyEnteringConditionsScreenViewModel(get()) }
}

