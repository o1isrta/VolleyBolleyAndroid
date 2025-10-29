package cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

open class BasicGameSetupScreenViewModel :
    BaseViewModel<BasicGameSetupScreenState, BasicGameSetupScreenEvent, BasicGameSetupScreenEffect>(
        BasicGameSetupScreenState()
    ) {
    override val tag: String = "BasicGameSetupScreenViewModel"

    companion object {
        val MAX_LENGTH = VolleyDimens.DIMEN_160
    }
    // флаг для показа календаря
    private val _showCalendar = MutableStateFlow( !isSameDay(uiStateMutable.value.date, LocalDate.now()))
    open val showCalendar: StateFlow<Boolean> = _showCalendar.asStateFlow()

//    private val _effectError = MutableStateFlow<BasicGameSetupScreenEffect?>(null)
//    val effectError: StateFlow<BasicGameSetupScreenEffect?> = _effectError

    init {
        // проверяем, есть  ли аккаунт
        //obtainEvent(BasicGameSetupScreenEvent.)
    }

    override fun obtainEvent(event: BasicGameSetupScreenEvent) {
        var timeChangeJob: Job? = null
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
            is BasicGameSetupScreenEvent.DateSelected -> {
                // устанавливаем выбранную дату
                if (!event.date.isBefore(LocalDate.now())) {
                    uiStateMutable.value = uiStateMutable.value.copy(date = event.date)
                    // Скрываем календарь только если выбранная дата - сегодня
                    if (isSameDay(event.date, LocalDate.now())) {
                        _showCalendar.value = false
                    }
                }
            }
            is BasicGameSetupScreenEvent.OnPickDateClicked -> {
                // показываем календарь при нажатии на pick Date
                _showCalendar.value = true // отображаем календарь, даже если дата сегодня
            }
            BasicGameSetupScreenEvent.OnTodayClicked -> {
                // Скрываем календарь при нажатии "Today" и устанавливаем сегодняшнюю дату
                uiStateMutable.value = uiStateMutable.value.copy(date = LocalDate.now()/*, isPickDateClicked = false*/)
                _showCalendar.value = false // скрываем календарь
            }
            is BasicGameSetupScreenEvent.StartTimeChanged -> {
                timeChangeJob?.cancel()
                timeChangeJob = viewModelScope.launch {
                    Log.d("TimePicker", "ViewModel: Received OnEndTimeChanged event: ${event.time}")
                    delay(300) // Дебаунс 300ms
                    val newState = uiStateMutable.value.copy(
                        startTime = event.time//,
                       // errorMessage = validateTimes(event.time, uiStateMutable.value.endTime)
                    )
                    uiStateMutable.value = newState
                    Log.d("TimePicker", "ViewModel: New UI State: ${uiStateMutable.value}")
                    // validateTimes(newState.startTime, newState.endTime)
                }
            }
            is BasicGameSetupScreenEvent.FinishTimeChanged -> {
                timeChangeJob?.cancel()
                timeChangeJob = viewModelScope.launch {
                    Log.d("TimePicker", "ViewModel: Received OnEndTimeChanged event: ${event.time}")
                    delay(300) // Дебаунс 300ms
                    val newState = uiStateMutable.value.copy(
                        finishTime = event.time//,
                     //   errorMessage = validateTimes(uiStateMutable.value.startTime, event.time)
                    )
                    uiStateMutable.value = newState
                    Log.d("TimePicker", "ViewModel: New UI State: ${uiStateMutable.value}")
                    //  validateTimes(newState.startTime, newState.endTime)
                }
            }
            is BasicGameSetupScreenEvent.OnNextStepClick -> {
                val message: String = validateData()
                if (message.isEmpty()) {
                    sendUiEffect(BasicGameSetupScreenEffect.NavigateNextStep)
                }
                else {
                    sendUiEffect(BasicGameSetupScreenEffect.ShowError(message = message))
                }
            }
            is BasicGameSetupScreenEvent.GenderSelected -> {
                uiStateMutable.value = uiStateMutable.value.copy(gender = event.gender)
            }
            is BasicGameSetupScreenEvent.PlayerLevelSelected -> {
                if(event.levels.isEmpty())
                    sendUiEffect(BasicGameSetupScreenEffect.ShowError(message = "Please, select player level!"))
                else
                    uiStateMutable.value = uiStateMutable.value.copy(levels = event.levels)
            }
        }
    }

    private fun validateData() : String{
        val startTime = uiStateMutable.value.startTime
        val finishTime = uiStateMutable.value.finishTime

        if (startTime == null || finishTime == null) {
            return "Please select both start and end times." // Или другое сообщение об ошибке
        }
        if (finishTime.compareTo(startTime) <= 0) {
            return "The end time of the game must be after the start time."
        }
        // Рассчитываем длительность игры в минутах
        val durationMinutes = calculateDurationMinutes(startTime, finishTime)

        // Проверяем, чтобы длительность игры была не меньше часа (60 минут) и не больше 4 часов (240 минут)
        if (durationMinutes < 60) {
            return "The game duration must be at least one hour."
        }
        if (durationMinutes > 240) {
            return "The game duration must be no more than 4 hours."
        }
        return ""
    }

    // Подсчет разницы во времени
    private fun calculateDurationMinutes(startTime: VolleyTimeStamp, endTime: VolleyTimeStamp): Int {
        val startTotalMinutes = (startTime.hour + if (startTime.isAfternoon) VolleyTimeStamp.AFTERNOON_VALUE else 0) * 60 + startTime.minutes
        val endTotalMinutes = (endTime.hour + if (endTime.isAfternoon) VolleyTimeStamp.AFTERNOON_VALUE else 0) * 60 + endTime.minutes
        return endTotalMinutes - startTotalMinutes
    }

//    private fun validateTimes(start: VolleyTimeStamp, end: VolleyTimeStamp){
//        if (end.compareTo(start) < 0) { // Если end меньше start
//            viewModelScope.launch {
//                _effectError.value = BasicGameSetupScreenEffect.ShowSnackbar("End time must be after start time")
//            }
//        } else {
//            viewModelScope.launch {
//                _effectError.value = BasicGameSetupScreenEffect.ClearSnackbar
//            }
//        }
//    }

//    public fun clearErrorEffect() {
//        _effectError.value = null
//    }

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
    fun isSameDay(date1: LocalDate, date2: LocalDate): Boolean {
        return date1 == date2
//        val calendar1 = Calendar.getInstance()
//        calendar1.time = date1
//        val calendar2 = Calendar.getInstance()
//        calendar2.time = date2
//
//        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
//            calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
//            calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
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

// Специальный ViewModel для Preview
class BasicGameSetupScreenViewModelPreview : BasicGameSetupScreenViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _showCalendarPreview = MutableStateFlow(LocalDate.now() != LocalDate.of(2025, 10, 23))  // Пример
    @RequiresApi(Build.VERSION_CODES.O)
    override val showCalendar: StateFlow<Boolean> = _showCalendarPreview.asStateFlow()


}
