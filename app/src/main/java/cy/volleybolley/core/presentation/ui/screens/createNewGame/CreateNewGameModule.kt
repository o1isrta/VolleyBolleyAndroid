package cy.volleybolley.core.presentation.ui.screens.createNewGame

import cy.volleybolley.core.presentation.ui.screens.createNewGame.basicGameSetupScreen.BasicGameSetupScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.createNewGame.createNewGameRepository.CreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createNewGame.createNewGameRepository.CreateNewGameRepositoryImpl
import cy.volleybolley.core.presentation.ui.screens.createNewGame.gameConditions.GameConditionsViewModel
import cy.volleybolley.core.presentation.ui.screens.createNewGame.privacyOptionsScreen.PrivacyOptionsViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.BasicTourneySetupViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.TourneyEnteringConditionsScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val createNewGameModule = module {
    single<CreateNewGameRepository> { CreateNewGameRepositoryImpl() }

    // Game creation ViewModels
    viewModel { BasicGameSetupScreenViewModel(get()) }
    viewModel { GameConditionsViewModel(get()) }
    viewModel { PrivacyOptionsViewModel(get(), get()) }

    // Tournament creation ViewModels
    viewModel { BasicTourneySetupViewModel(get()) }
    viewModel { TourneyEnteringConditionsScreenViewModel(get()) }
}
