package cy.volleybolley.jointhegame.di

import cy.volleybolley.jointhegame.JoinTheGameViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val joinTheGameModule = module {
    viewModel { (gameId: Int) ->
        JoinTheGameViewModel(
            gameId = gameId,
            getGameDetailsUseCase = get(),
            joinGameUseCase = get(),
            resourceProvider = get()
        )
    }
}
