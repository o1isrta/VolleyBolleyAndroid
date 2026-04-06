package cy.volleybolley.core.presentation.ui.screens.createnewtourney

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import cy.volleybolley.core.presentation.ui.model.DateOption
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.core.presentation.ui.screens.createNewGame.model.Gender
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.domain.model.Location
import org.koin.compose.viewmodel.koinViewModel
import java.time.LocalDate

@Composable
fun BasicTourneySetupScreen(
    paddingFromSystemUi: PaddingValues,
    onNavigateToSearchCourt: () -> Unit,
    onNavigateToNextStep: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: BasicTourneySetupViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    val showCalendar by viewModel.showCalendar.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            is BasicTourneySetupEffect.NavigateToCreatePlace -> onNavigateToSearchCourt()

            is BasicTourneySetupEffect.NavigateBack -> onNavigateBack()

            is BasicTourneySetupEffect.NavigateNextStep -> onNavigateToNextStep()

            is BasicTourneySetupEffect.ShowErrorMessage -> {
                Toast.makeText(context, "Error: ${currentEffect.message}", Toast.LENGTH_SHORT).show()
            }

            is BasicTourneySetupEffect.ShowErrorMessageById -> {
                val errorMessage = context.getString(currentEffect.messageId)
                Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
            }

            null -> {}
        }
    }

    BasicTourneySetupScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        showCalendar = showCalendar,
        isSameDay = { date1, date2 -> viewModel.isSameDay(date1, date2) },
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
private fun BasicTourneySetupScreen(
    state: BasicTourneySetupState,
    paddingFromSystemUi: PaddingValues,
    showCalendar: Boolean,
    isSameDay: (LocalDate, LocalDate) -> Boolean,
    eventCallback: (BasicTourneySetupEvent) -> Unit
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
            VolleyContainersRootTransparent.TransparentContainer(
                modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    TitleWithBackArrow(
                        title = stringResource(R.string.create_a_tourney),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        onBackClick = { eventCallback(BasicTourneySetupEvent.OnBackClicked) }
                    )

                    Column(
                        modifier = Modifier.verticalScroll(scrollState)
                    ) {
                        MessageSection(
                            message = state.message,
                            onMessageChanged = { eventCallback(BasicTourneySetupEvent.MessageChanged(it)) }
                        )

                        VolleySimpleComponent.DividerLine(
                            Modifier.padding(top = 16.dp)
                        )

                        PlaceSection(
                            placeCourt = state.placeCourt,
                            onChangeClick = { eventCallback(BasicTourneySetupEvent.OnChangeClick) }
                        )

                        VolleySimpleComponent.DividerLine(
                            Modifier.padding(top = 16.dp)
                        )

                        DateSection(
                            date = state.date,
                            showCalendar = showCalendar,
                            isSameDay = isSameDay,
                            onTodayClicked = { eventCallback(BasicTourneySetupEvent.OnTodayClicked) },
                            onPickDateClicked = { eventCallback(BasicTourneySetupEvent.OnPickDateClicked) },
                            onDateSelected = { eventCallback(BasicTourneySetupEvent.DateSelected(it)) }
                        )

                        TimeSection(
                            startTime = state.startTime,
                            finishTime = state.finishTime,
                            onStartTimeChanged = { eventCallback(BasicTourneySetupEvent.StartTimeChanged(it)) },
                            onFinishTimeChanged = { eventCallback(BasicTourneySetupEvent.FinishTimeChanged(it)) }
                        )

                        VolleySimpleComponent.DividerLine(
                            Modifier.padding(top = 16.dp)
                        )

                        TourneyTypeSection(
                            tourneyType = state.tourneyType,
                            onTourneyTypeSelected = { eventCallback(BasicTourneySetupEvent.TourneyTypeSelected(it)) }
                        )

                        VolleySimpleComponent.DividerLine(
                            Modifier.padding(top = 16.dp)
                        )

                        GenderSection(
                            gender = state.gender,
                            onGenderSelected = { eventCallback(BasicTourneySetupEvent.GenderSelected(it)) }
                        )

                        VolleySimpleComponent.DividerLine(
                            Modifier.padding(top = 16.dp)
                        )

                        LevelSection(
                            levels = state.levels,
                            onPlayerLevelSelected = { eventCallback(BasicTourneySetupEvent.PlayerLevelSelected(it)) }
                        )

                        VolleyButton.ActiveButton(
                            modifier = Modifier
                                .padding(top = 16.dp, bottom = 20.dp)
                                .height(44.dp)
                                .fillMaxWidth(),
                            text = stringResource(R.string.next_step),
                            onClick = { eventCallback(BasicTourneySetupEvent.OnNextStepClick) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MessageSection(
    message: String,
    onMessageChanged: (String) -> Unit
) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.your_message),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        color = VolleyColor.White
    )

    VolleyMessageTextField.MessageField(
        modifier = Modifier
            .padding(top = 16.dp)
            .height(106.dp),
        hint = stringResource(R.string.leave_a_note_for_players),
        textInput = message,
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        color = VolleyColor.White
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .height(44.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(R.drawable.ic_mark_yellow),
                contentDescription = null
            )

            Column(
                modifier = Modifier.padding(start = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                VolleyText.BodyBold(
                    text = placeCourt.location.courtName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = VolleyColor.White
                )
                VolleyText.BodyLight(
                    text = placeCourt.location.locationName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = VolleyColor.White
                )
            }
        }

        VolleyButton.ActiveGradientButton(
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
    val selectedOption = if (isSameDay(date, LocalDate.now())) DateOption.Today else DateOption.PickDate

    VolleyText.TitleMedium(
        text = stringResource(R.string.date),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        color = VolleyColor.White
    )

    VolleyButton.SingleChoiceButtonGroup(
        items = DateOption.entries,
        selected = selectedOption,
        label = { it.displayText },
        showRightIcon = { it.showRightIcon },
        modifier = Modifier.padding(top = 12.dp),
        onSelect = { option ->
            when (option) {
                DateOption.Today -> onTodayClicked()
                DateOption.PickDate -> onPickDateClicked()
            }
        }
    )

    if (showCalendar) {
        Box(modifier = Modifier.padding(top = 10.dp)) {
            VolleyCalendar.GameCalendar(
                selectedDate = date,
                onDateSelected = onDateSelected
            )
        }
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
        modifier = Modifier.padding(top = 8.dp),
        color = VolleyColor.White
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        VolleyText.BodyRegular(
            text = stringResource(R.string.from),
            color = VolleyColor.White
        )

        VolleyTextFieldAttribute.DurationFieldWithArrows(
            modifier = Modifier.padding(start = 8.dp),
            inputTime = startTime,
            actionForSaveTime = onStartTimeChanged
        )

        VolleyText.BodyRegular(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(R.string.to),
            color = VolleyColor.White
        )

        VolleyTextFieldAttribute.DurationFieldWithArrows(
            modifier = Modifier.padding(start = 8.dp),
            inputTime = finishTime,
            actionForSaveTime = onFinishTimeChanged
        )
    }
}

@Composable
private fun TourneyTypeSection(
    tourneyType: TourneyType,
    onTourneyTypeSelected: (TourneyType) -> Unit
) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.tourney_type),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        color = VolleyColor.White
    )

    VolleyButton.SingleChoiceButtonGroup(
        items = TourneyType.entries,
        selected = tourneyType,
        label = { it.displayText },
        modifier = Modifier.padding(top = 12.dp),
        onSelect = onTourneyTypeSelected
    )
}

@Composable
private fun GenderSection(
    gender: Gender,
    onGenderSelected: (Gender) -> Unit
) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.gender),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        color = VolleyColor.White
    )

    VolleyButton.SingleChoiceButtonGroup(
        items = Gender.entries,
        selected = gender,
        label = { it.displayText },
        modifier = Modifier.padding(top = 12.dp),
        onSelect = onGenderSelected
    )
}

@Composable
private fun LevelSection(
    levels: Set<cy.volleybolley.core.presentation.ui.model.Level>,
    onPlayerLevelSelected: (Set<cy.volleybolley.core.presentation.ui.model.Level>) -> Unit
) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.player_level),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        color = VolleyColor.White
    )

    VolleyButton.MultiChoiceButtonGroup(
        items = Level.entries,
        selected = levels,
        label = { it.displayText },
        modifier = Modifier.padding(top = 12.dp),
        paddingValues = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
        onSelect = onPlayerLevelSelected
    )
}

@Preview
@Composable
private fun BasicTourneySetupScreenPreview() {
    val previewState = BasicTourneySetupState(
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
        BasicTourneySetupScreen(
            state = previewState,
            paddingFromSystemUi = PaddingValues(0.dp),
            showCalendar = true,
            isSameDay = { _, _ -> false },
            eventCallback = {}
        )
    }
}
