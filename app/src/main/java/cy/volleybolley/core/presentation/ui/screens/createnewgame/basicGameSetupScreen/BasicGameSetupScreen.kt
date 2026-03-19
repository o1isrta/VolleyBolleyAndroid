package cy.volleybolley.core.presentation.ui.screens.createnewgame.basicGameSetupScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleyMessageTextField
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.VolleyTextFieldAttribute
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyCalendar
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository.Gender
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.domain.model.Location
import org.koin.compose.viewmodel.koinViewModel
import java.time.LocalDate

@Composable
fun BasicGameSetupScreen(
    paddingFromSystemUi: PaddingValues,
    onNavigateToNextStep: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: BasicGameSetupScreenViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    val showCalendar by viewModel.showCalendar.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val resources = LocalResources.current

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            is BasicGameSetupScreenEffect.NavigateBack -> onNavigateBack()
            is BasicGameSetupScreenEffect.NavigateNextStep -> onNavigateToNextStep()
            is BasicGameSetupScreenEffect.ShowErrorMessage -> {
                Toast.makeText(context, "Error: ${currentEffect.message}", Toast.LENGTH_SHORT).show()
            }

            is BasicGameSetupScreenEffect.ShowErrorMessageById -> {
                val errorMessage = resources.getString(currentEffect.messageId)
                Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
            }
            null -> {}
        }
    }

    BasicGameSetupScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        showCalendar = showCalendar,
        isSameDay = { date1, date2 -> viewModel.isSameDay(date1, date2) },
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
private fun BasicGameSetupScreen(
    state: BasicGameSetupScreenState,
    paddingFromSystemUi: PaddingValues,
    showCalendar: Boolean,
    isSameDay: (LocalDate, LocalDate) -> Boolean,
    eventCallback: (BasicGameSetupScreenEvent) -> Unit
) {
    val scrollState = rememberSaveable(saver = ScrollState.Saver) {
        ScrollState(0)
    }

    if (state.isLoading) {
        VolleySimpleComponent.LoadingIndicator()
    } else {
        Column(
            modifier = Modifier.padding(paddingFromSystemUi)
        ) {
            VolleyContainersRootTransparent.TransparentContainer(modifier = Modifier.padding(8.dp)) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    Spacer(modifier = Modifier.size(size = 20.dp))

                    TitleSection(
                        onBackClicked = { eventCallback(BasicGameSetupScreenEvent.OnBackClicked) }
                    )

                    Spacer(modifier = Modifier.size(size = 16.dp))
                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        MessageSection(
                            message = state.message,
                            onMessageChanged = { eventCallback(BasicGameSetupScreenEvent.MessageChanged(it)) }
                        )

                        Spacer(modifier = Modifier.size(size = 16.dp))
                        VolleySimpleComponent.DividerLine()
                        Spacer(modifier = Modifier.size(size = 16.dp))

                        PlaceSection(
                            placeCourt = state.placeCourt,
                            onChangeClick = { eventCallback(BasicGameSetupScreenEvent.OnChangeClick) }
                        )

                        Spacer(modifier = Modifier.size(size = 16.dp))
                        VolleySimpleComponent.DividerLine()
                        Spacer(modifier = Modifier.size(size = 16.dp))

                        DateSection(
                            date = state.date,
                            showCalendar = showCalendar,
                            isSameDay = isSameDay,
                            onTodayClicked = { eventCallback(BasicGameSetupScreenEvent.OnTodayClicked) },
                            onPickDateClicked = { eventCallback(BasicGameSetupScreenEvent.OnPickDateClicked) },
                            onDateSelected = { eventCallback(BasicGameSetupScreenEvent.DateSelected(it)) }
                        )

                        TimeSection(
                            startTime = state.startTime,
                            finishTime = state.finishTime,
                            onStartTimeChanged = { eventCallback(BasicGameSetupScreenEvent.StartTimeChanged(it)) },
                            onFinishTimeChanged = { eventCallback(BasicGameSetupScreenEvent.FinishTimeChanged(it)) }
                        )

                        Spacer(modifier = Modifier.size(size = 16.dp))
                        VolleySimpleComponent.DividerLine()
                        Spacer(modifier = Modifier.size(size = 16.dp))

                        GenderSection(
                            gender = state.gender,
                            onGenderSelected = { eventCallback(BasicGameSetupScreenEvent.GenderSelected(it)) }
                        )

                        Spacer(modifier = Modifier.size(size = 16.dp))
                        VolleySimpleComponent.DividerLine()
                        Spacer(modifier = Modifier.size(size = 16.dp))

                        LevelSection(
                            levels = state.levels,
                            onPlayerLevelSelected = { eventCallback(BasicGameSetupScreenEvent.PlayerLevelSelected(it)) }
                        )

                        Spacer(modifier = Modifier.size(size = 16.dp))
                        NextButtonSection(
                            modifier = Modifier
                                .height(44.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(),
                            onNextStepClick = { eventCallback(BasicGameSetupScreenEvent.OnNextStepClick) }
                        )

                        Spacer(modifier = Modifier.size(size = 16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun TitleSection(onBackClicked: () -> Unit) {
    TitleWithBackArrow(
        title = stringResource(R.string.create_a_game),
        modifier = Modifier.fillMaxWidth(),
        onBackClick = onBackClicked
    )
}

@Composable
private fun MessageSection(message: String, onMessageChanged: (String) -> Unit) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.your_message),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White
    )

    Spacer(modifier = Modifier.size(size = 16.dp))
    VolleyMessageTextField.MessageField(
        hint = stringResource(R.string.leave_a_note_for_players),
        textInput = message,
        modifier = Modifier.height(106.dp),
        actionToTransferContent = onMessageChanged
    )
}

@Composable
private fun PlaceSection(
    placeCourt: Court,
    onChangeClick: () -> Unit
) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.place),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White
    )

    Spacer(modifier = Modifier.size(size = 12.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(44.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(R.drawable.ic_mark_yellow),
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(size = 8.dp))
            Column(horizontalAlignment = Alignment.Start) {
                VolleyText.BodyBold(
                    text = placeCourt.location.courtName,
                    modifier = Modifier,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = VolleyColor.White
                )
                VolleyText.BodyLight(
                    text = placeCourt.location.locationName,
                    modifier = Modifier,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = VolleyColor.White
                )
            }
        }

        VolleyButton.ActiveGradientButton(
            modifier = Modifier,
            text = stringResource(R.string.change),
            onClick = onChangeClick
        )
    }
}

@Composable
private fun DateSection(
    date: LocalDate,
    showCalendar: Boolean,
    isSameDay: (LocalDate, LocalDate) -> Boolean,
    onTodayClicked: () -> Unit,
    onPickDateClicked: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.date),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White
    )

    Spacer(modifier = Modifier.size(size = 12.dp))
    VolleyButton.GroupButtonsForDate2(
        checkId = if (isSameDay(date, LocalDate.now())) 1 else 2,
        modifier = Modifier,
        onSelected = { position ->
            when (position) {
                1 -> onTodayClicked()
                2 -> onPickDateClicked()
            }
        }
    )
    Spacer(modifier = Modifier.size(size = 10.dp))

    if (showCalendar) {
        VolleyCalendar.GameCalendar(
            selectedDate = date,
            onDateSelected = onDateSelected
        )
        Spacer(modifier = Modifier.size(size = 8.dp))
    }
}

@Composable
private fun TimeSection(
    startTime: VolleyTimeStamp?,
    finishTime: VolleyTimeStamp?,
    onStartTimeChanged: (VolleyTimeStamp?) -> Unit,
    onFinishTimeChanged: (VolleyTimeStamp?) -> Unit
) {
    VolleyText.BodyBold(
        text = stringResource(R.string.game_duration),
        modifier = Modifier,
        color = VolleyColor.White
    )

    Spacer(modifier = Modifier.size(size = 8.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        VolleyText.BodyRegular(
            text = stringResource(R.string.from),
            modifier = Modifier,
            color = VolleyColor.White
        )

        Spacer(modifier = Modifier.size(size = 8.dp))
        VolleyTextFieldAttribute.DurationFieldWithArrows(
            inputTime = startTime
        ) { time ->
            onStartTimeChanged(time)
        }

        Spacer(modifier = Modifier.size(size = 8.dp))
        VolleyText.BodyRegular(
            text = stringResource(R.string.to),
            modifier = Modifier,
            color = VolleyColor.White
        )

        Spacer(modifier = Modifier.size(size = 8.dp))
        VolleyTextFieldAttribute.DurationFieldWithArrows(
            inputTime = finishTime
        ) { time ->
            onFinishTimeChanged(time)
        }
    }
}

@Composable
private fun GenderSection(
    gender: Gender,
    onGenderSelected: (Gender) -> Unit
) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.gender),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White
    )

    Spacer(modifier = Modifier.size(size = 12.dp))
    VolleyButton.GroupButtonsForGender3(
        checkId = when (gender) {
            Gender.Mix -> 1
            Gender.Men -> 2
            Gender.Women -> 3
        },
        modifier = Modifier,
        onSelected = { position ->
            val selectedGender = when (position) {
                1 -> Gender.Mix
                2 -> Gender.Men
                3 -> Gender.Women
                else -> null
            }
            selectedGender?.let { onGenderSelected(it) }
        }
    )
}

@Composable
private fun LevelSection(
    levels: Set<Level>,
    onPlayerLevelSelected: (Set<Level>) -> Unit
) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.player_level),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White
    )

    Spacer(modifier = Modifier.size(size = 12.dp))
    VolleyButton.GroupButtonsForLevelMulti(
        modifier = Modifier,
        checkedLevels = levels,
        onSelected = onPlayerLevelSelected
    )
}

@Composable
private fun NextButtonSection(
    modifier: Modifier,
    onNextStepClick: () -> Unit
) {
    VolleyButton.ActiveButton(
        modifier = modifier,
        text = stringResource(R.string.next_step),
        onClick = onNextStepClick
    )
}

@Preview
@Composable
private fun BasicGameSetupScreenPreview() {
    val previewState = BasicGameSetupScreenState(
        placeCourt = Court(
            courtId = 1,
            price = "1$",
            description = "Karon Beach Club: Patak Rd, Mueang Phuket",
            location = Location(
                longitude = 55.0,
                latitude = 56.0,
                courtName = "Karon Beach Club",
                locationName = "Patak Rd, Mueang Phuket"
            ),
            contacts = listOf(),
            photo = "",
            tags = listOf()
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        BasicGameSetupScreen(
            state = previewState,
            paddingFromSystemUi = PaddingValues(0.dp),
            showCalendar = true,
            isSameDay = { _, _ -> false },
            eventCallback = {}
        )
    }
}
