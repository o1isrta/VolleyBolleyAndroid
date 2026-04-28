package cy.volleybolley.core.presentation.ui.screens.games.createNewGame.basicGameSetupScreen

import androidx.lifecycle.viewModelScope
import cy.volleybolley.R
import cy.volleybolley.core.presentation.base.BaseViewModel
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.core.presentation.ui.screens.games.createNewGame.CreateGameSharedViewModel
import cy.volleybolley.core.presentation.ui.screens.games.createNewGame.model.GameData
import cy.volleybolley.core.presentation.ui.screens.games.createNewGame.model.GameGender
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class BasicGameSetupScreenViewModel(
    private val createGameSharedViewModel: CreateGameSharedViewModel,
) : BaseViewModel<BasicGameSetupScreenState, BasicGameSetupScreenEvent, BasicGameSetupScreenEffect>(
    initialState = BasicGameSetupScreenState()
) {
    init {
        viewModelScope.launch {
            createGameSharedViewModel.gameData.collectLatest { gameData ->
                uiStateMutable.update { currentState ->
                    currentState.copy(
                        //message = gameData.message,
                        placeCourt = gameData.placeCourt, // ?: GameData().placeCourt,
                        date = gameData.date, // ?: LocalDate.now(),
                        startTime = gameData.startTime,
                        finishTime = gameData.finishTime,
                        gender = gameData.gender, // ?: GameGender.Mix,
                        levels = gameData.levels, //?: setOf(Level.Light, Level.Medium, Level.Hard),
                        showCalendar = gameData.showCalendar //?: false // showCalendar = gameData?.date != LocalDate.now()
                    )
                }
            }
        }
    }

    override fun obtainEvent(event: BasicGameSetupScreenEvent) {
        when (event) {
            is BasicGameSetupScreenEvent.MessageChanged -> messageChanged(event.text)
            is BasicGameSetupScreenEvent.OnBackClicked -> onBackClicked()
            is BasicGameSetupScreenEvent.OnChangeClick -> onChangeClick()
            is BasicGameSetupScreenEvent.DateSelected -> dateSelected(event.date)
            is BasicGameSetupScreenEvent.OnPickDateClicked -> onPickDateClicked()
            is BasicGameSetupScreenEvent.OnTodayClicked -> onTodayClicked()
            is BasicGameSetupScreenEvent.StartTimeChanged -> startTimeChanged(event.time)
            is BasicGameSetupScreenEvent.FinishTimeChanged -> finishTimeChanged(event.time)
            is BasicGameSetupScreenEvent.GenderSelected -> genderSelected(event.gender)
            is BasicGameSetupScreenEvent.PlayerLevelSelected -> playerLevelSelected(event.levels)
            is BasicGameSetupScreenEvent.OnNextStepClick -> onNextStepClick()
        }
    }

/*    private fun updateGameData(update: (GameData) -> GameData) {
        viewModelScope.launch {
            createGameSharedViewModel.updateGameData(update)
        }
    }*/

    private fun messageChanged(text: String) {
        val limited = getLimitedText(MAX_LENGTH, text)
        uiStateMutable.update { it.copy(message = limited) }
        // updateGameData { it.copy(message = limited) }
    }

    private fun onBackClicked() {
        sendUiEffect(BasicGameSetupScreenEffect.NavigateBack)
    }

    private fun onChangeClick() {
        sendUiEffect(BasicGameSetupScreenEffect.NavigateBack)
    }

    private fun onPickDateClicked() {
        uiStateMutable.update { it.copy(showCalendar = true) }
        // updateGameData { it.copy(showCalendar = true) }
    }

    private fun onTodayClicked() {
        uiStateMutable.update { it.copy(date = LocalDate.now(), showCalendar = false) }
        // updateGameData { it.copy(date = LocalDate.now(), showCalendar = false) }
    }

    private fun dateSelected(date: LocalDate) {
        if (!date.isBefore(LocalDate.now())) {
            uiStateMutable.update {
                it.copy(
                    date = date,
                    showCalendar = if (date == LocalDate.now()) false else it.showCalendar
                )
            }
          /*  updateGameData {
                it.copy(
                    date = date,
                    showCalendar = if (date == LocalDate.now()) false else it.showCalendar
                )
            }*/
        }
    }

    private fun startTimeChanged(time: VolleyTimeStamp?) {
        uiStateMutable.update { it.copy(startTime = time) }
       // updateGameData { it.copy(startTime = time) }
    }

    private fun finishTimeChanged(time: VolleyTimeStamp?) {
        uiStateMutable.update { it.copy(finishTime = time) }
       // updateGameData { it.copy(finishTime = time) }
    }

    private fun genderSelected(gender: GameGender) {
        uiStateMutable.update { it.copy(gender = gender) }
       // updateGameData { it.copy(gender = gender) }
    }

    private fun playerLevelSelected(levels: Set<Level>) {
/*        if (levels.isEmpty()) {
            sendUiEffect(BasicGameSetupScreenEffect.ShowErrorMessageById(R.string.please_select_player_level))
        } else {
            uiStateMutable.update { it.copy(levels = levels) }
        }*/
        if(!levels.isEmpty()) {
            uiStateMutable.update { it.copy(levels = levels) }
        }
    }

    private fun onNextStepClick() {
        val messageId = validateData()
        if (messageId < 0) {
            nextStep()
        } else {
            sendUiEffect(BasicGameSetupScreenEffect.ShowErrorMessageById(messageId))
        }
    }

    private fun nextStep() {
        launchSafe(
            getErrorLogMessage = { "Error: ${it.message ?: "Unknown error"}" },
            onError = { er ->
                sendUiEffect(
                    BasicGameSetupScreenEffect.ShowErrorMessage(er.message ?: "Failed to check account")
                )
            }
        ) {
            createGameSharedViewModel.updateGameData { gameData ->
                gameData.copy(
                    message = uiState.value.message,
                    placeCourt = uiState.value.placeCourt,
                    date = uiState.value.date,
                    startTime = uiState.value.startTime,
                    finishTime = uiState.value.finishTime,
                    gender = uiState.value.gender,
                    levels = uiState.value.levels,
                    showCalendar = uiState.value.showCalendar
                )
            }
            sendUiEffect(BasicGameSetupScreenEffect.NavigateNextStep)
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
                    durationMinutes < MINIMUM_GAME_DURATION_MINUTES -> R.string.game_duration_at_least_1_hour
                    durationMinutes > MAXIMUM_GAME_DURATION_MINUTES -> R.string.game_duration_no_more_than_4_hours
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

    private companion object {
        const val MAX_LENGTH = 160
        const val MINIMUM_GAME_DURATION_MINUTES = 60
        const val MAXIMUM_GAME_DURATION_MINUTES = 240
        const val HOUR = 60
    }
}
