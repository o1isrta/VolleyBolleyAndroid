package cy.volleybolley.core.presentation.ui.screens.createnewgame.basicGameSetupScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import cy.volleybolley.R
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository.CreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository.FakeCreateNewGameRepository
import cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository.GameData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

open class BasicGameSetupScreenViewModel(private val gameRepository: CreateNewGameRepository) :
    BaseViewModel<BasicGameSetupScreenState, BasicGameSetupScreenEvent, BasicGameSetupScreenEffect>(
        BasicGameSetupScreenState()
    ) {
    override val tag: String = "BasicGameSetupScreenViewModel"
    val str: String = R.string.time_picker.toString()
    private var timeChangeJob: Job? = null

    companion object {
        const val MAX_LENGTH = VolleyDimens.DIMEN_160
        const val DEBOUNCE_DELAY_300MS = 300L
        const val HOUR1 = 60
        const val HOUR2 = 240
        const val HOUR = 60
    }

    // флаг для показа календаря
    private val _showCalendar = MutableStateFlow(!isSameDay(uiStateMutable.value.date, LocalDate.now()))
    open val showCalendar: StateFlow<Boolean> = _showCalendar.asStateFlow()

    init {
        // Подписка на изменения GameData из репозитория
        viewModelScope.launch {
            gameRepository.gameData.collectLatest { gameDataFromRepo ->
                // Когда GameData в репозитории меняется, обновляем соответствующие части UI State
                uiStateMutable.update { currentState ->
                    currentState.copy( // Обновляем нужные поля из GameData
                        placeCourt = gameDataFromRepo.placeCourt,
                        date = gameDataFromRepo.date,
                        startTime = gameDataFromRepo.startTime,
                        finishTime = gameDataFromRepo.finishTime,
                        gender = gameDataFromRepo.gender,
                        levels = gameDataFromRepo.levels
                    )
                }
            }
        }
    }

    override fun obtainEvent(event: BasicGameSetupScreenEvent) {
        //   var timeChangeJob: Job? = null
        when (event) {
            is BasicGameSetupScreenEvent.MessageChanged -> messageChanged(event.text)
            BasicGameSetupScreenEvent.OnBackClicked -> onBackClicked()
            BasicGameSetupScreenEvent.OnChangeClick -> onChangeClick()
            is BasicGameSetupScreenEvent.DateSelected -> dateSelected(event.date)

            is BasicGameSetupScreenEvent.OnPickDateClicked -> {
                // показываем календарь при нажатии на pick Date
                _showCalendar.value = true // отображаем календарь, даже если дата сегодня
            }

            BasicGameSetupScreenEvent.OnTodayClicked -> {
                // Скрываем календарь при нажатии "Today" и устанавливаем сегодняшнюю дату
                uiStateMutable.value = uiStateMutable.value.copy(date = LocalDate.now())
                _showCalendar.value = false // скрываем календарь
            }

            is BasicGameSetupScreenEvent.StartTimeChanged -> startTimeChanged(event.time)
            is BasicGameSetupScreenEvent.FinishTimeChanged -> finishTimeChanged(event.time)

            is BasicGameSetupScreenEvent.GenderSelected -> {
                uiStateMutable.value = uiStateMutable.value.copy(gender = event.gender)
            }

            is BasicGameSetupScreenEvent.PlayerLevelSelected -> playerLevelSelected(event.levels)

            is BasicGameSetupScreenEvent.OnNextStepClick -> onNextStepClick()
        }
    }

    private fun messageChanged(text: String) {
        // ограничиваем длину в ViewModel — можно бы убрать ограничение в MessageField
        val limited = getLimitedText(MAX_LENGTH, text)
        uiStateMutable.value = uiStateMutable.value.copy(message = limited)
    }

    private fun onBackClicked() {
        sendUiEffect(BasicGameSetupScreenEffect.NavigateBack)
    }

    private fun onChangeClick() {
        // переход на экран CreatePlace при нажатии на кнопку Change
        sendUiEffect(BasicGameSetupScreenEffect.NavigateToCreatePlace)
    }

    private fun dateSelected(date: LocalDate) {
        // устанавливаем выбранную дату
        if (!date.isBefore(LocalDate.now())) {
            uiStateMutable.value = uiStateMutable.value.copy(date = date)
            // Скрываем календарь только если выбранная дата - сегодня
            if (isSameDay(date, LocalDate.now())) {
                _showCalendar.value = false
            }
        }
    }

    private fun startTimeChanged(time: VolleyTimeStamp?) {
        timeChangeJob?.cancel()
        viewModelScope.launch {
            Log.d(str, "ViewModel: Received OnEndTimeChanged event: ${time}")
            delay(DEBOUNCE_DELAY_300MS) // Дебаунс 300ms
            val newState = uiStateMutable.value.copy(
                startTime = time
            )
            uiStateMutable.value = newState
            Log.d(str, "ViewModel: New UI State: ${uiStateMutable.value}")
        }
    }

    private fun finishTimeChanged(time: VolleyTimeStamp?) {
        timeChangeJob?.cancel()
        viewModelScope.launch {
            Log.d(str, "ViewModel: Received OnEndTimeChanged event: ${time}")
            delay(DEBOUNCE_DELAY_300MS) // Дебаунс 300ms
            val newState = uiStateMutable.value.copy(
                finishTime = time
            )
            uiStateMutable.value = newState
            Log.d(str, "ViewModel: New UI State: ${uiStateMutable.value}")
        }
    }

    private fun playerLevelSelected(levels: Set<Level>) {
        if (levels.isEmpty()) {
            sendUiEffect(BasicGameSetupScreenEffect.ShowError(message = R.string.please_select_player_level.toString()))
        } else {
            uiStateMutable.value = uiStateMutable.value.copy(levels = levels)
        }
    }

    private fun onNextStepClick() {
        val message: String = validateData()
        if (message.isEmpty()) {
            nextStep()
        } else {
            sendUiEffect(
                BasicGameSetupScreenEffect.ShowError(message = message)
            )
        }
    }

    private fun nextStep() { // если accountNumber != Null, аккаунт существует
        launchSafe(
            dispatcher = Dispatchers.IO,
            getErrorLogMessage = { "Error: ${it.message ?: "Unknown error"}" },
            onError = { er ->
                sendUiEffect(
                    BasicGameSetupScreenEffect.ShowError(
                        er.message ?: "Failed to check account"
                    )
                )
            }
        ) {
            gameRepository.updateGameData { gameData ->
                gameData.copy(
                    placeCourt = uiState.value.placeCourt,
                    date = uiState.value.date,
                    startTime = uiState.value.startTime,
                    finishTime = uiState.value.finishTime,
                    gender = uiState.value.gender,
                    levels = uiState.value.levels
                )
            }
            sendUiEffect(BasicGameSetupScreenEffect.NavigateNextStep)
        }
    }

    /** Проверяет собранные на экране данные
     * */
    private fun validateData(): String {
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
        if (durationMinutes < HOUR1) {
            return "The game duration must be at least one hour."
        }
        if (durationMinutes > HOUR2) {
            return "The game duration must be no more than 4 hours."
        }
        return ""
    }

    /** Подсчет разницы во времени
     *
     */
    private fun calculateDurationMinutes(startTime: VolleyTimeStamp, endTime: VolleyTimeStamp): Int {
        val startTotalMinutes = (startTime.hour +
            (if (startTime.isAfternoon) {
                VolleyTimeStamp.AFTERNOON_VALUE
            } else {
                0
            })
            ) * HOUR +
            startTime.minutes

        val endTotalMinutes = (endTime.hour +
            (if (endTime.isAfternoon) {
                VolleyTimeStamp.AFTERNOON_VALUE
            } else {
                0
            })
            ) * HOUR +
            endTime.minutes

        return endTotalMinutes - startTotalMinutes
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
        if (Character.isHighSurrogate(input[end - 1])) {
            end -= 1
        }
        return input.substring(0, end)
    }

    //Вспомогательная ф-ция для сравнения дней
    fun isSameDay(date1: LocalDate, date2: LocalDate): Boolean {
        return date1 == date2
    }
}

// Специальный ViewModel для Preview
class BasicGameSetupScreenViewModelPreview : BasicGameSetupScreenViewModel(FakeCreateNewGameRepository(GameData())) {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _showCalendarPreview = MutableStateFlow(
        true
        //LocalDate.now() == LocalDate.of(2025,11,23)
    ) // чтобы видно было календарь - поставить сегодняшнюю дату

    @RequiresApi(Build.VERSION_CODES.O)
    override val showCalendar: StateFlow<Boolean> = _showCalendarPreview.asStateFlow()
}
