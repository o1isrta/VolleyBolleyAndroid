package cy.volleybolley.core.presentation.ui.screens.createNewGame.basicGameSetupScreen

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.RootContainerForPreview
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleyMessageTextField
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.VolleyTextFieldAttribute
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyCalendar
import cy.volleybolley.core.presentation.ui.component.VolleyProgress
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
fun BasicGameSetupScreen(
    paddingFromSystemUi: PaddingValues,
    onNavigateToNextStep: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: BasicGameSetupScreenViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    val context = LocalContext.current
    val resources = LocalResources.current

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            is BasicGameSetupScreenEffect.NavigateBack -> onNavigateBack()
            is BasicGameSetupScreenEffect.NavigateNextStep -> onNavigateToNextStep()
            is BasicGameSetupScreenEffect.ShowErrorMessage -> {
                Toast.makeText(context, currentEffect.message, Toast.LENGTH_SHORT).show()
            }
            is BasicGameSetupScreenEffect.ShowErrorMessageById -> {
                val errorMessage = resources.getString(currentEffect.messageId)
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
            null -> {}
        }
    }

    BasicGameSetupScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Stable
@Composable
private fun BasicGameSetupScreen(
    state: BasicGameSetupScreenState,
    paddingFromSystemUi: PaddingValues,
    eventCallback: (BasicGameSetupScreenEvent) -> Unit
) {
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            VolleyProgress.CircularProgress()
        }
    } else {
        Column(
            modifier = Modifier.padding(paddingFromSystemUi)
        ) {
            VolleyContainersRootTransparent.TransparentContainer(modifier = Modifier.padding(8.dp)) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    TitleWithBackArrow(
                        title = stringResource(R.string.create_a_game),
                        modifier = Modifier.padding(top = 20.dp).fillMaxWidth(),
                        onBackClick = { eventCallback(BasicGameSetupScreenEvent.OnBackClicked) }
                    )

                    Column(modifier = Modifier.padding(top = 16.dp).verticalScroll(rememberScrollState())) {
                        MessageSection(
                            message = state.message,
                            onMessageChanged = { eventCallback(BasicGameSetupScreenEvent.MessageChanged(it)) }
                        )

                        VolleySimpleComponent.DividerLine(modifier = Modifier.padding(vertical = 16.dp))

                        PlaceSection(
                            placeCourt = state.placeCourt,
                            onChangeClick = { eventCallback(BasicGameSetupScreenEvent.OnChangeClick) }
                        )

                        VolleySimpleComponent.DividerLine(modifier = Modifier.padding(vertical = 16.dp))

                        DateSection(
                            date = state.date,
                            showCalendar = state.showCalendar,
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

                        VolleySimpleComponent.DividerLine(modifier = Modifier.padding(vertical = 16.dp))

                        GenderSection(
                            gender = state.gender,
                            onGenderSelected = { eventCallback(BasicGameSetupScreenEvent.GenderSelected(it)) }
                        )

                        VolleySimpleComponent.DividerLine(modifier = Modifier.padding(vertical = 16.dp))

                        LevelSection(
                            levels = state.levels,
                            onPlayerLevelSelected = { eventCallback(BasicGameSetupScreenEvent.PlayerLevelSelected(it)) }
                        )

                        VolleyButton.ActiveButton(
                            modifier = Modifier.padding(vertical = 16.dp).height(44.dp).fillMaxWidth(),
                            text = stringResource(R.string.next_step),
                            onClick = { eventCallback(BasicGameSetupScreenEvent.OnNextStepClick) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MessageSection(message: String, onMessageChanged: (String) -> Unit) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.your_message),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White
    )
    VolleyMessageTextField.MessageField(
        hint = stringResource(R.string.leave_a_note_for_players),
        textInput = message,
        modifier = Modifier.padding(top = 16.dp).height(106.dp),
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
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(top = 12.dp)
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
            modifier = Modifier,
            text = stringResource(R.string.change),
            onClick = onChangeClick
        )
    }
}

@Stable
@Composable
private fun DateSection(
    date: LocalDate,
    showCalendar: Boolean,
    onTodayClicked: () -> Unit,
    onPickDateClicked: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    val selectedOption = if (date == LocalDate.now()) DateOption.Today else DateOption.PickDate

    VolleyText.TitleMedium(
        text = stringResource(R.string.date),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White
    )

    Spacer(modifier = Modifier.size(size = 12.dp))
    VolleyButton.SingleChoiceButtonGroup(
        items = DateOption.entries,
        selected = selectedOption,
        label = { it.displayText },
        showRightIcon = { it.showRightIcon },
        modifier = Modifier,
        onSelect = { option ->
            when (option) {
                DateOption.Today -> onTodayClicked()
                DateOption.PickDate -> onPickDateClicked()
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
    VolleyButton.SingleChoiceButtonGroup(
        items = Gender.entries,
        selected = gender,
        label = { it.displayText },
        onSelect = onGenderSelected
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
    VolleyButton.MultiChoiceButtonGroup(
        items = Level.entries,
        selected = levels,
        label = { it.displayText },
        modifier = Modifier,
        paddingValues = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
        onSelect = onPlayerLevelSelected
    )
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_9_PRO)
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
        ),
        showCalendar = true
    )

    RootContainerForPreview {
        BasicGameSetupScreen(
            state = previewState,
            paddingFromSystemUi = it,
            eventCallback = {}
        )
    }
}
