package cy.volleybolley.core.presentation.ui.screens.createnewgame.basicGameSetupScreen

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository.Gender
import java.time.LocalDate

sealed class BasicGameSetupScreenEvent : UiEvent {
    data object OnBackClicked : BasicGameSetupScreenEvent() // нажатие на стрелку "Назад"
    data object OnChangeClick : BasicGameSetupScreenEvent() // нажатие на кнопку "Change"
    data object OnPickDateClicked : BasicGameSetupScreenEvent() // Нажатие на кнопку pick date
    data object OnTodayClicked : BasicGameSetupScreenEvent() // Нажатие на кнопку Today
    data object OnNextStepClick : BasicGameSetupScreenEvent()
    data class MessageChanged(val text: String) : BasicGameSetupScreenEvent() // изменение сообщения
    data class StartTimeChanged(val time: VolleyTimeStamp?) : BasicGameSetupScreenEvent()
    data class FinishTimeChanged(val time: VolleyTimeStamp?) : BasicGameSetupScreenEvent()
    data class GenderSelected(val gender: Gender) : BasicGameSetupScreenEvent()
    data class PlayerLevelSelected(val levels: Set<Level>) : BasicGameSetupScreenEvent()
    data class DateSelected(val date: LocalDate) : BasicGameSetupScreenEvent() // подтверждение выбора даты в календаре
}

