package cy.volleybolley.core.presentation.ui.screens.createnewtourney

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.core.presentation.ui.screens.createNewGame.model.Gender
import java.time.LocalDate

sealed interface BasicTourneySetupEvent : UiEvent {
    data object OnBackClicked : BasicTourneySetupEvent
    data object OnChangeClick : BasicTourneySetupEvent
    data object OnPickDateClicked : BasicTourneySetupEvent
    data object OnTodayClicked : BasicTourneySetupEvent
    data object OnNextStepClick : BasicTourneySetupEvent
    data class MessageChanged(val text: String) : BasicTourneySetupEvent
    data class StartTimeChanged(val time: VolleyTimeStamp?) : BasicTourneySetupEvent
    data class FinishTimeChanged(val time: VolleyTimeStamp?) : BasicTourneySetupEvent
    data class GenderSelected(val gender: Gender) : BasicTourneySetupEvent
    data class PlayerLevelSelected(val levels: Set<Level>) : BasicTourneySetupEvent
    data class DateSelected(val date: LocalDate) : BasicTourneySetupEvent
    data class TourneyTypeSelected(val tourneyType: TourneyType) : BasicTourneySetupEvent
}
