package cy.volleybolley.core.presentation.ui.screens.createnewgame

import cy.volleybolley.core.presentation.ui.screens.createnewgame.basicGameSetupScreen.BasicGameSetupScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository.CreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository.CreateNewGameRepositoryImpl
import cy.volleybolley.core.presentation.ui.screens.createnewgame.gameEnteringConditionsScreen.GameEnteringConditionsScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.createnewgame.privacyOptionsScreen.PrivacyOptionsScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val createNewGameModule = module {
    single<CreateNewGameRepository> { CreateNewGameRepositoryImpl() }
    viewModel { BasicGameSetupScreenViewModel(get()) }
    viewModel { GameEnteringConditionsScreenViewModel(get()) }
    viewModel { PrivacyOptionsScreenViewModel(get(), get()) }
}

