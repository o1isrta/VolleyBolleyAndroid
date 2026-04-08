package cy.volleybolley.core.presentation.ui.screens.games.state


import cy.volleybolley.core.presentation.ui.screens.games.createNewGame.CreateGameSharedViewModel
import cy.volleybolley.core.presentation.ui.screens.games.createNewGame.basicGameSetupScreen.BasicGameSetupScreenViewModel
import cy.volleybolley.core.presentation.ui.screens.games.createNewGame.gameConditionsScreen.GameConditionsViewModel
import cy.volleybolley.core.presentation.ui.screens.games.createNewGame.privacyOptionsScreen.PrivacyOptionsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val createNewGameModule = module {
    single<GameDataManager> { GameDataManagerImpl(get()) }

    single<CreateGameSharedViewModel> {
        CreateGameSharedViewModel(
            dataManager = get()
        )
    }

    // Screen ViewModels
    viewModel { BasicGameSetupScreenViewModel(get()) }
    viewModel { GameConditionsViewModel(get()) }
    viewModel { PrivacyOptionsViewModel(get(), get()) }

    // Tournament creation ViewModels
   // viewModel { BasicTourneySetupViewModel(get()) }
   // viewModel { TourneyEnteringConditionsScreenViewModel(get()) }
}
