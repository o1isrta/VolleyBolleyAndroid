package cy.volleybolley.core.presentation.ui.screens.di

import cy.volleybolley.core.presentation.ui.screens.home.success.SucceedGame
import cy.volleybolley.core.presentation.ui.screens.home.success.SuccessViewModel
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
//import cy.volleybolley.сore.domain.model.SucceedGame // Assuming SucceedGame is in this package

//val screensModule = module {
//    // Data
//
//    // Domain
//
//    // ViewModel
//    viewModel { (createdEvent: SucceedGame) ->
//        SuccessViewModel(createdEvent = createdEvent)
//    }
//}
val screensModule = module {
    viewModel { (succeedGameJson: String) ->
        val json = get<Json>()
        // Десериализуем JSON-строку обратно в объект SucceedGame
        val succeedGame = json.decodeFromString(SucceedGame.serializer(), succeedGameJson)
        // Передаем десериализованный объект в SuccessViewModel
        SuccessViewModel(createdEvent = succeedGame)
    }
}
