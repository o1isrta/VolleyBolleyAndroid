package cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen

import cy.volleybolley.core.presentation.base.UiEvent
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import java.time.LocalDate

sealed class BasicGameSetupScreenEvent  : UiEvent {
    //    data class PrivacySelected(val privacy: Privacy) : GameEnteringConditionsScreenEvent()
    //    object CheckIfAccountExists : GameEnteringConditionsScreenEvent() // Проверка, существует ли аккаунт
    object OnBackClicked : BasicGameSetupScreenEvent()     // нажатие на стрелку "Назад"
    data class MessageChanged(val text: String) : BasicGameSetupScreenEvent() // изменение сообщения
    object OnChangeClick  : BasicGameSetupScreenEvent()     // нажатие на кнопку "Create"
    data class OnDateSelected(val date: LocalDate) : BasicGameSetupScreenEvent() // пользователь подстверждает выбор даты в календаре
    object OnPickDateClicked : BasicGameSetupScreenEvent() // Нажатие на кнопку pick date
    object OnTodayClicked : BasicGameSetupScreenEvent() // Нажатие на кнопку Today
    data class OnStartTimeChanged(val time: VolleyTimeStamp?) : BasicGameSetupScreenEvent()
    data class OnFinishTimeChanged(val time: VolleyTimeStamp?) : BasicGameSetupScreenEvent()
    object OnNextStepClick : BasicGameSetupScreenEvent()
}

