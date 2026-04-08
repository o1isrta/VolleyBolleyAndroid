package cy.volleybolley.core.presentation.ui.screens.createnewtourney

import androidx.lifecycle.viewModelScope
import cy.volleybolley.R
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
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

/*
open class BasicTourneySetupViewModel(
    private val gameRepository: CreateNewGameRepository
) : BaseViewModel<BasicTourneySetupState, BasicTourneySetupEvent, BasicTourneySetupEffect>(
    BasicTourneySetupState()
) {
    private var timeChangeJob: Job? = null
    private val _showCalendar = MutableStateFlow(false)
    open val showCalendar: StateFlow<Boolean> = _showCalendar.asStateFlow()

    init {
        viewModelScope.launch {
            _showCalendar.value = !isSameDay(uiState.value.date, LocalDate.now())
        }
        viewModelScope.launch {
            gameRepository.gameData.collectLatest { gameDataFromRepo ->
                uiStateMutable.update { currentState ->
                    currentState.copy(
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

    override fun obtainEvent(event: BasicTourneySetupEvent) {
        when (event) {
            is BasicTourneySetupEvent.MessageChanged -> messageChanged(event.text)
            is BasicTourneySetupEvent.OnBackClicked -> onBackClicked()
            is BasicTourneySetupEvent.OnChangeClick -> onChangeClick()
            is BasicTourneySetupEvent.DateSelected -> dateSelected(event.date)
            is BasicTourneySetupEvent.OnPickDateClicked -> {
                _showCalendar.value = true
            }
            is BasicTourneySetupEvent.OnTodayClicked -> {
                uiStateMutable.update { it.copy(date = LocalDate.now()) }
                _showCalendar.value = false
            }
            is BasicTourneySetupEvent.StartTimeChanged -> startTimeChanged(event.time)
            is BasicTourneySetupEvent.FinishTimeChanged -> finishTimeChanged(event.time)
            is BasicTourneySetupEvent.GenderSelected -> {
                uiStateMutable.update { it.copy(gender = event.gender) }
            }
            is BasicTourneySetupEvent.PlayerLevelSelected -> playerLevelSelected(event.levels)
            is BasicTourneySetupEvent.TourneyTypeSelected -> {
                uiStateMutable.update { it.copy(tourneyType = event.tourneyType) }
            }
            is BasicTourneySetupEvent.OnNextStepClick -> onNextStepClick()
        }
    }

    private fun messageChanged(text: String) {
        val limited = getLimitedText(MAX_LENGTH, text)
        uiStateMutable.update { it.copy(message = limited) }
    }

    private fun onBackClicked() {
        sendUiEffect(BasicTourneySetupEffect.NavigateBack)
    }

    private fun onChangeClick() {
        sendUiEffect(BasicTourneySetupEffect.NavigateToCreatePlace)
    }

    private fun dateSelected(date: LocalDate) {
        if (!date.isBefore(LocalDate.now())) {
            uiStateMutable.update { it.copy(date = date) }
            if (isSameDay(date, LocalDate.now())) {
                _showCalendar.value = false
            }
        }
    }

    private fun startTimeChanged(time: VolleyTimeStamp?) {
        timeChangeJob?.cancel()
        timeChangeJob = viewModelScope.launch {
            delay(DEBOUNCE_DELAY_300MS)
            uiStateMutable.update { it.copy(startTime = time) }
        }
    }

    private fun finishTimeChanged(time: VolleyTimeStamp?) {
        timeChangeJob?.cancel()
        timeChangeJob = viewModelScope.launch {
            delay(DEBOUNCE_DELAY_300MS)
            uiStateMutable.update { it.copy(finishTime = time) }
        }
    }

    private fun playerLevelSelected(levels: Set<Level>) {
        if (levels.isEmpty()) {
            sendUiEffect(BasicTourneySetupEffect.ShowErrorMessageById(R.string.please_select_player_level))
        } else {
            uiStateMutable.update { it.copy(levels = levels) }
        }
    }

    private fun onNextStepClick() {
        val messageId: Int = validateData()
        if (messageId < 0) {
            nextStep()
        } else {
            sendUiEffect(BasicTourneySetupEffect.ShowErrorMessageById(messageId = messageId))
        }
    }

    private fun nextStep() {
        launchSafe(
            dispatcher = Dispatchers.IO,
            getErrorLogMessage = { "Error: ${it.message ?: "Unknown error"}" },
            onError = { er ->
                sendUiEffect(
                    BasicTourneySetupEffect.ShowErrorMessage(er.message ?: "Failed to check account")
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
            sendUiEffect(BasicTourneySetupEffect.NavigateNextStep)
        }
    }

    private fun validateData(): Int {
        val startTime = uiState.value.startTime
        val finishTime = uiState.value.finishTime

        return when {
            startTime == null || finishTime == null -> R.string.please_select_both_start_and_end_times
            finishTime.compareTo(startTime) <= 0 -> R.string.end_time_of_the_game_must_be
            else -> {
                val durationMinutes = calculateDurationMinutes(startTime, finishTime)
                when {
                    durationMinutes < MINIMUM_TOURNEY_DURATION_MINUTES -> R.string.game_duration_at_least_1_hour
                    durationMinutes > MAXIMUM_TOURNEY_DURATION_MINUTES -> R.string.game_duration_no_more_than_4_hours
                    else -> -1
                }
            }
        }
    }

    private fun calculateDurationMinutes(startTime: VolleyTimeStamp, endTime: VolleyTimeStamp): Int {
        val startTotalMinutes = (startTime.hour +
            if (startTime.isAfternoon) VolleyTimeStamp.AFTERNOON_VALUE else 0) * HOUR +
            startTime.minutes

        val endTotalMinutes = (endTime.hour +
            if (endTime.isAfternoon) VolleyTimeStamp.AFTERNOON_VALUE else 0) * HOUR +
            endTime.minutes

        return endTotalMinutes - startTotalMinutes
    }

    private fun getLimitedText(maxLength: Int, input: String): String {
        return when {
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
    }

    fun isSameDay(date1: LocalDate, date2: LocalDate): Boolean {
        return date1 == date2
    }

    private companion object {
        const val MAX_LENGTH = 160
        const val DEBOUNCE_DELAY_300MS = 300L
        const val MINIMUM_TOURNEY_DURATION_MINUTES = 60
        const val MAXIMUM_TOURNEY_DURATION_MINUTES = 480
        const val HOUR = 60
    }
}
*/
