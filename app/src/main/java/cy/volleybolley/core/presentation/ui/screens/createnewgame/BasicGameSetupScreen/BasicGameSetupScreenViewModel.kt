package cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen

import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class BasicGameSetupScreenViewModel :
    BaseViewModel<BasicGameSetupScreenState, BasicGameSetupScreenEvent, BasicGameSetupScreenEffect>(
        BasicGameSetupScreenState()
    ) {
    override val tag: String = "BasicGameSetupScreenViewModel"

    companion object {
        val MAX_LENGTH = VolleyDimens.DIMEN_160
    }
    // флаг для показа календаря
    private val _showCalendar = MutableStateFlow(false)
    val showCalendar: StateFlow<Boolean> = _showCalendar.asStateFlow()

    init {
        // проверяем, есть  ли аккаунт
        //obtainEvent(BasicGameSetupScreenEvent.)
    }

    override fun obtainEvent(event: BasicGameSetupScreenEvent) {
        when (event) {
            is BasicGameSetupScreenEvent.MessageChanged -> {
                // ограничиваем длину в ViewModel — можно бы убрать ограничение в MessageField
                val limited = getLimitedText(MAX_LENGTH, event.text)
                uiStateMutable.value = uiStateMutable.value.copy(message = limited)
            }
            BasicGameSetupScreenEvent.OnBackClicked -> {
                sendUiEffect(BasicGameSetupScreenEffect.NavigateBack)
            }
            BasicGameSetupScreenEvent.OnChangeClick -> {
                // переход на экран CreatePlace при нажатии на кнопку Change
                sendUiEffect(BasicGameSetupScreenEffect.NavigateToCreatePlace)
            }
            is BasicGameSetupScreenEvent.OnDateSelected -> {
                // устанавливаем выбранную дату
                uiStateMutable.value = uiStateMutable.value.copy(date = event.date)
                // Скрываем календарь только если выбранная дата - сегодня
                _showCalendar.value = !isSameDay(event.date, Date())
            }
            is BasicGameSetupScreenEvent.OnPickDateClicked -> {
                // показываем календарь при нажатии на pick Date
                _showCalendar.value = true // отображаем календарь, даже если дата сегодня
            }
            BasicGameSetupScreenEvent.OnTodayClicked -> {
                // Скрываем календарь при нажатии "Today" и устанавливаем сегодняшнюю дату
                uiStateMutable.value = uiStateMutable.value.copy(date = Date()/*, isPickDateClicked = false*/)
                _showCalendar.value = false // скрываем календарь
            }
        }
    }

    /**
     * Ограничивает текст по длине, не разрубая суррогатные пары.
     * maxLength — ожидаемый максимальный размер в кодовых единицах (Int).
     */
    private fun getLimitedText(maxLength: Int, input: String): String {
        if (maxLength <= 0) return ""
        if (input.length <= maxLength) return input

        // Не разрезаем суррогатную пару: если на границе стоит high surrogate — сдвинуть на 1 влево
        var end = maxLength
        if (end > 0 && Character.isHighSurrogate(input[end - 1])) {
            end -= 1
        }
        return input.substring(0, end)
    }

    fun clearEffect() {
        viewModelScope.launch {
            uiEffectMutable.receive() // Получаем эффект, чтобы "очистить" его
        }
    }

    //Вспомогательная ф-ция для сравнения дней
    fun isSameDay(date1: Date, date2: Date): Boolean {
        val calendar1 = Calendar.getInstance()
        calendar1.time = date1
        val calendar2 = Calendar.getInstance()
        calendar2.time = date2

        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
            calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
            calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
    }

    /**
     * Возвращает "n / max" (сейчас используется для счётчика символов).
     *//*
    fun formatCounter(text: String, maxLength: Int): String =
        "${text.length} / $maxLength"

    *//**
     * Сколько символов осталось до лимита (>=0).
     *//*
    fun remaining(text: String, maxLength: Int): Int =
        (maxLength - text.length).coerceAtLeast(0)*/
}
