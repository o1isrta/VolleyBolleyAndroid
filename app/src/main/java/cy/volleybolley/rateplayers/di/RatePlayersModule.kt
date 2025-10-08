package cy.volleybolley.rateplayers.di

import cy.volleybolley.rateplayers.RatePlayersViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val ratePlayersModule = module {
    viewModel { (eventId: Int, eventType: String) ->
        RatePlayersViewModel(
            eventId = eventId,
            eventType = eventType,
        )
    }
}
