package cy.volleybolley.core.presentation.ui.screens.createnewgame

import cy.volleybolley.core.presentation.ui.screens.createnewgame.basicGameSetupScreen.BasicGameSetupScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository.CreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository.CreateNewGameRepositoryImpl
import cy.volleybolley.core.presentation.ui.screens.createnewgame.gameEnteringConditionsScreen.GameEnteringConditionsScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewgame.privacyOptionsScreen.PrivacyOptionsScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.BasicTourneySetupScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewtourney.TourneyEnteringConditionsScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val createNewGameModule = module {
    single<CreateNewGameRepository> { CreateNewGameRepositoryImpl() }

    // Game creation ViewModels
    viewModel { BasicGameSetupScreenViewModel(get()) }
    viewModel { GameEnteringConditionsScreenViewModel(get()) }
    viewModel { PrivacyOptionsScreenViewModel(get(), get()) }

    // Tournament creation ViewModels
    viewModel { BasicTourneySetupScreenViewModel(get()) }
    viewModel { TourneyEnteringConditionsScreenViewModel(get()) }
}

