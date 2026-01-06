package cy.volleybolley.core.presentation.ui.screens.createnewgame.basicGameSetupScreen

import android.os.Build
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

    private var timeChangeJob: Job? = null

    companion object {
        const val MAX_LENGTH = VolleyDimens.DIMEN_160
        const val DEBOUNCE_DELAY_300MS = 300L
        const val MINIMUM_GAME_DURATION_MINUTES = 60
        const val MAXIMUM_GAME_DURATION_MINUTES = 240
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
            delay(DEBOUNCE_DELAY_300MS) // Дебаунс 300ms
            uiStateMutable.value = uiStateMutable.value.copy(
                startTime = time
            )
        }
    }

    private fun finishTimeChanged(time: VolleyTimeStamp?) {
        timeChangeJob?.cancel()
        viewModelScope.launch {
            delay(DEBOUNCE_DELAY_300MS) // Дебаунс 300ms
            uiStateMutable.value = uiStateMutable.value.copy(
                finishTime = time
            )
        }
    }

    private fun playerLevelSelected(levels: Set<Level>) {
        if (levels.isEmpty()) {
            sendUiEffect(
                BasicGameSetupScreenEffect.ShowErrorMessageById(messageId = R.string.please_select_player_level)
            )
        } else {
            uiStateMutable.value = uiStateMutable.value.copy(levels = levels)
        }
    }

    private fun onNextStepClick() {
        val messageId: Int = validateData()
        if (messageId < 0) {
            nextStep()
        } else {
            sendUiEffect(
                BasicGameSetupScreenEffect.ShowErrorMessageById(messageId = messageId)
            )
        }
    }

    private fun nextStep() { // если accountNumber != Null, аккаунт существует
        launchSafe(
            dispatcher = Dispatchers.IO,
            getErrorLogMessage = { "Error: ${it.message ?: "Unknown error"}" },
            onError = { er ->
                sendUiEffect(
                    BasicGameSetupScreenEffect.ShowErrorMessage(
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
    private fun validateData(): Int {
        val startTime = uiStateMutable.value.startTime
        val finishTime = uiStateMutable.value.finishTime

        return when {
            startTime == null || finishTime == null -> R.string.please_select_both_start_and_end_times
            finishTime.compareTo(startTime) <= 0 -> R.string.end_time_of_the_game_must_be
            else -> {
                val durationMinutes = calculateDurationMinutes(startTime, finishTime)
                when {
                    durationMinutes < MINIMUM_GAME_DURATION_MINUTES -> R.string.game_duration_at_least_1_hour
                    durationMinutes > MAXIMUM_GAME_DURATION_MINUTES -> R.string.game_duration_no_more_than_4_hours
                    else -> -1
                }
            }
        }
    }

    /** Подсчет разницы во времени
     *
     */
    private fun calculateDurationMinutes(startTime: VolleyTimeStamp, endTime: VolleyTimeStamp): Int {
        val startTotalMinutes = (startTime.hour +
            if (startTime.isAfternoon) {
                VolleyTimeStamp.AFTERNOON_VALUE
            } else {
                0
            }
            ) * HOUR +
            startTime.minutes

        val endTotalMinutes = (endTime.hour +
            if (endTime.isAfternoon) {
                VolleyTimeStamp.AFTERNOON_VALUE
            } else {
                0
            }
            ) * HOUR +
            endTime.minutes

        return endTotalMinutes - startTotalMinutes
    }

    /**
     * Ограничивает текст по длине, не разрубая суррогатные пары.
     * maxLength — ожидаемый максимальный размер в кодовых единицах (Int).
     */
    private fun getLimitedText(maxLength: Int, input: String): String {
        val result = when {
            maxLength <= 0 -> ""
            input.length <= maxLength -> input
            else -> {
                var end = maxLength
                if (Character.isHighSurrogate(input[end - 1])) {
                    end--
                }
                input.substring(0, end)
            }
        }
        return result
    }

    // Вспомогательная ф-ция для сравнения дней
    fun isSameDay(date1: LocalDate, date2: LocalDate): Boolean {
        return date1 == date2
    }
}

// Специальный ViewModel для Preview
class BasicGameSetupScreenViewModelPreview : BasicGameSetupScreenViewModel(FakeCreateNewGameRepository(GameData())) {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _showCalendarPreview = MutableStateFlow(
        true
        // LocalDate.now() == LocalDate.of(2025,11,23)
    ) // чтобы видно было календарь - поставить сегодняшнюю дату

    @RequiresApi(Build.VERSION_CODES.O)
    override val showCalendar: StateFlow<Boolean> = _showCalendarPreview.asStateFlow()
}
