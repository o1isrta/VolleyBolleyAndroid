package cy.volleybolley.core.presentation.ui.screens.createNewGame.basicGameSetupScreen

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.core.presentation.ui.screens.createNewGame.createNewGameRepository.Gender
import java.time.LocalDate

sealed interface BasicGameSetupScreenEvent : UiEvent {
    data object OnBackClicked : BasicGameSetupScreenEvent
    data object OnChangeClick : BasicGameSetupScreenEvent
    data object OnPickDateClicked : BasicGameSetupScreenEvent
    data object OnTodayClicked : BasicGameSetupScreenEvent
    data object OnNextStepClick : BasicGameSetupScreenEvent
    data class MessageChanged(val text: String) : BasicGameSetupScreenEvent
    data class StartTimeChanged(val time: VolleyTimeStamp?) : BasicGameSetupScreenEvent
    data class FinishTimeChanged(val time: VolleyTimeStamp?) : BasicGameSetupScreenEvent
    data class GenderSelected(val gender: Gender) : BasicGameSetupScreenEvent
    data class PlayerLevelSelected(val levels: Set<Level>) : BasicGameSetupScreenEvent
    data class DateSelected(val date: LocalDate) : BasicGameSetupScreenEvent
}

