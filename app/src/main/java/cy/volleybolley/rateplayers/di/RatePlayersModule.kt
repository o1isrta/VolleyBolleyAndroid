package cy.volleybolley.rateplayers.di

import cy.volleybolley.games.domain.model.event.EventType
import cy.volleybolley.rateplayers.RatePlayersViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val ratePlayersModule = module {
    viewModel { (eventId: Int, eventType: EventType) ->
        RatePlayersViewModel(
            eventId = eventId,
            eventType = eventType,
            ratePlayersUseCase = get(),
            getPlayersToRateUseCase = get(),
        )
    }
}
