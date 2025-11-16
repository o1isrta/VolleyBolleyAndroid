package cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import java.time.LocalDate

sealed class BasicGameSetupScreenEvent  : UiEvent {
    object OnBackClicked : BasicGameSetupScreenEvent()     // нажатие на стрелку "Назад"
    data class MessageChanged(val text: String) : BasicGameSetupScreenEvent() // изменение сообщения
    object OnChangeClick  : BasicGameSetupScreenEvent()     // нажатие на кнопку "Change"
    data class DateSelected(val date: LocalDate) : BasicGameSetupScreenEvent() // пользователь подстверждает выбор даты в календаре
    object OnPickDateClicked : BasicGameSetupScreenEvent() // Нажатие на кнопку pick date
    object OnTodayClicked : BasicGameSetupScreenEvent() // Нажатие на кнопку Today
    data class StartTimeChanged(val time: VolleyTimeStamp?) : BasicGameSetupScreenEvent()
    data class FinishTimeChanged(val time: VolleyTimeStamp?) : BasicGameSetupScreenEvent()
    object OnNextStepClick : BasicGameSetupScreenEvent()
    data class GenderSelected(val gender: Gender) : BasicGameSetupScreenEvent()
    data class PlayerLevelSelected(val levels: Set<Level>) : BasicGameSetupScreenEvent()
}

