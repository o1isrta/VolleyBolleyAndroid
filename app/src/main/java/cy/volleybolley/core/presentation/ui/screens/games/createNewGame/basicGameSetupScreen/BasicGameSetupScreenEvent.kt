package cy.volleybolley.core.presentation.ui.screens.games.createNewGame.basicGameSetupScreen

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.core.presentation.ui.screens.games.createNewGame.model.GameGender
import java.time.LocalDate

sealed interface BasicGameSetupScreenEvent : UiEvent {
    object OnBackClicked : BasicGameSetupScreenEvent
    object OnChangeClick : BasicGameSetupScreenEvent
    object OnPickDateClicked : BasicGameSetupScreenEvent
    object OnTodayClicked : BasicGameSetupScreenEvent
    object OnNextStepClick : BasicGameSetupScreenEvent
    class MessageChanged(val text: String) : BasicGameSetupScreenEvent
    class StartTimeChanged(val time: VolleyTimeStamp?) : BasicGameSetupScreenEvent
    class FinishTimeChanged(val time: VolleyTimeStamp?) : BasicGameSetupScreenEvent
    class GenderSelected(val gender: GameGender) : BasicGameSetupScreenEvent
    class PlayerLevelSelected(val levels: Set<Level>) : BasicGameSetupScreenEvent
    class DateSelected(val date: LocalDate) : BasicGameSetupScreenEvent
}
