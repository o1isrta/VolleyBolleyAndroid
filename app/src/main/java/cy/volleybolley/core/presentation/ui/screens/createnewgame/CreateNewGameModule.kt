package cy.volleybolley.core.presentation.ui.screens.createnewgame

import cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen.BasicGameSetupScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.CreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.CreateNewGameRepositoryImpl
import cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen.GameEnteringConditionsScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewgame.PrivacyOptionsScreen.PrivacyOptionsScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val createNewGameModule = module {
    single<CreateNewGameRepository> { CreateNewGameRepositoryImpl() }
    viewModel { BasicGameSetupScreenViewModel(get()) }
    viewModel { GameEnteringConditionsScreenViewModel(get()) }
    viewModel { PrivacyOptionsScreenViewModel(get(), get()) }
}

