package cy.volleybolley.core.presentation.ui.screens.createnewtourney

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository.Gender
import java.time.LocalDate

sealed interface BasicTourneySetupScreenEvent : UiEvent {
    data object OnBackClicked : BasicTourneySetupScreenEvent
    data object OnChangeClick : BasicTourneySetupScreenEvent
    data object OnPickDateClicked : BasicTourneySetupScreenEvent
    data object OnTodayClicked : BasicTourneySetupScreenEvent
    data object OnNextStepClick : BasicTourneySetupScreenEvent
    data class MessageChanged(val text: String) : BasicTourneySetupScreenEvent
    data class StartTimeChanged(val time: VolleyTimeStamp?) : BasicTourneySetupScreenEvent
    data class FinishTimeChanged(val time: VolleyTimeStamp?) : BasicTourneySetupScreenEvent
    data class GenderSelected(val gender: Gender) : BasicTourneySetupScreenEvent
    data class PlayerLevelSelected(val levels: Set<Level>) : BasicTourneySetupScreenEvent
    data class DateSelected(val date: LocalDate) : BasicTourneySetupScreenEvent
    data class TourneyTypeSelected(val tourneyType: TourneyType) : BasicTourneySetupScreenEvent
}
