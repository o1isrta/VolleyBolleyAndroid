package cy.volleybolley.core.presentation.ui.screens.createnewgame.basicGameSetupScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.time.LocalDate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleyMessageTextField
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.VolleyTextFieldAttribute
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyCalendar
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.SearchCourtRoute
import cy.volleybolley.core.presentation.ui.navigation.GameEnteringConditionsRoute
import cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository.Gender

@Composable
fun BasicGameSetupScreen(
    navController: NavHostController,
    viewModel: BasicGameSetupScreenViewModel = viewModel(),
    paddingFromSystemUi: PaddingValues
) {
    val scrollState = rememberScrollState()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    ObserveUiEffects(viewModel, navController, context)

    // Отображение контента
    ContentDisplay(state, scrollState, paddingFromSystemUi, viewModel)
}

@Composable
private fun ObserveUiEffects(
    viewModel: BasicGameSetupScreenViewModel,
    navController: NavHostController,
    context: Context
) {
    LaunchedEffect(viewModel.uiEffect) { // подписываемся на Effect
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is BasicGameSetupScreenEffect.NavigateToCreatePlace -> {
                    navController.navigate(SearchCourtRoute)
                }

                BasicGameSetupScreenEffect.NavigateBack -> {
                    navController.popBackStack()
                }

                is BasicGameSetupScreenEffect.NavigateNextStep -> {
                    navController.navigate(GameEnteringConditionsRoute)
                }

                is BasicGameSetupScreenEffect.ShowError -> {
                    Toast.makeText(context, "Error: ${effect.message}", Toast.LENGTH_SHORT).show()
                }

                else -> { // Обработка всех возможных случаев
                    Log.w("BasicGameSetupScreen", "Unhandled effect: $effect")
                }
            }
        }
    }
}

@Composable
private fun ContentDisplay(
    state: BasicGameSetupScreenState,
    scrollState: ScrollState,
    paddingFromSystemUi: PaddingValues,
    viewModel: BasicGameSetupScreenViewModel
) {
    if (state.isLoading) {
        VolleySimpleComponent.LoadingIndicator()
    } else {
        Column(
            modifier = Modifier.padding(paddingFromSystemUi)
        ) {
            VolleyContainersRootTransparent.TransparentContainer(
                cornerRadius = VolleyDimens.DIMEN_32,
                modifier = Modifier
                    .padding(
                        VolleyDimens.DIMEN_8.dp,
                        VolleyDimens.DIMEN_8.dp,
                        VolleyDimens.DIMEN_8.dp,
                        VolleyDimens.DIMEN_16.dp
                    )
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = VolleyDimens.DIMEN_20.dp)
                ) {
                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

                    TitleSection(viewModel)

                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        MessageSection(state, viewModel)

                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                        VolleySimpleComponent.DividerLine()
                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

                        PlaceSection(state, viewModel)

                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                        VolleySimpleComponent.DividerLine()
                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

                        DateSection(state, viewModel)

                        TimeSection(state, viewModel)

                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                        VolleySimpleComponent.DividerLine()
                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

                        GenderSection(state, viewModel)

                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                        VolleySimpleComponent.DividerLine()
                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

                        LevelSection(state, viewModel)

                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                        NextButtonSection(
                            Modifier
                                .height(44.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(),
                            viewModel
                        )

                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                    }
                }
            }
        }
    }
}

// Секции для ContentDisplay
@Composable
private fun TitleSection(viewModel: BasicGameSetupScreenViewModel) {
    TitleWithBackArrow(
        title = stringResource(R.string.create_a_game),
        modifier = Modifier.fillMaxWidth(),
        onBackClick = {
            viewModel.obtainEvent(
                BasicGameSetupScreenEvent.OnBackClicked
            )
        }
    )
}

@Composable
private fun MessageSection(state: BasicGameSetupScreenState, viewModel: BasicGameSetupScreenViewModel) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.your_message),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White
    )

    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
    VolleyMessageTextField.MessageField(
        hint = stringResource(R.string.leave_a_note_for_players),
        textInput = state.message,
        modifier = Modifier.height(VolleyDimens.DIMEN_106.dp),
        actionToTransferContent = { newMessage ->
            viewModel.obtainEvent(
                BasicGameSetupScreenEvent.MessageChanged(newMessage)
            )
        }
    )
}

@Composable
private fun PlaceSection(
    state: BasicGameSetupScreenState,
    viewModel: BasicGameSetupScreenViewModel
) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.place),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White
    )

    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(VolleyDimens.DIMEN_44.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f), // Важно. Занимает только часть доступного пространства,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(R.drawable.ic_mark_yellow),
                contentDescription = null
            )

            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
            Column(horizontalAlignment = Alignment.Start) {
                VolleyText.BodyBold(
                    text = state.placeCourt.location.courtName,
                    modifier = Modifier,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = VolleyColor.White
                )
                VolleyText.BodyLight(
                    text = state.placeCourt.location.locationName,
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
            onClick = {
                viewModel.obtainEvent(
                    BasicGameSetupScreenEvent.OnChangeClick
                )
            }
        )
    }
}

@Composable
private fun DateSection(
    state: BasicGameSetupScreenState,
    viewModel: BasicGameSetupScreenViewModel
) {
    val showCalendar = viewModel.showCalendar.collectAsState().value

    VolleyText.TitleMedium(
        text = stringResource(R.string.date),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White
    )

    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))
    val str: String = R.string.basic_game_setup_screen.toString()
    VolleyButton.GroupButtonsForDate2(
        checkId = if (viewModel.isSameDay(state.date, LocalDate.now())) 1 else 2,
        modifier = Modifier,
        onSelected = { position ->
            when (position) {
                1 -> { // Сегодня
                    viewModel.obtainEvent(BasicGameSetupScreenEvent.OnTodayClicked)
                }

                2 -> { // Выбрать Дату (Pick Date)
                    viewModel.obtainEvent(BasicGameSetupScreenEvent.OnPickDateClicked)
                }

                else -> { // Обработка нераспознанной позиции
                    Log.e(str, "Unrecognized date button position: $position")
                }
            }
        }
    )
    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_10.dp))

    // Календарь показывается только если выбрана кнопка "Pick Date"
    if (showCalendar) { // Используем флаг из ViewModel
        VolleyCalendar.GameCalendar(
            selectedDate = state.date,
            onDateSelected = { selectedDate ->
                viewModel.obtainEvent(
                    BasicGameSetupScreenEvent.DateSelected(
                        selectedDate
                    )
                )
            }
        )
        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
    }
}

@Composable
private fun TimeSection(
    state: BasicGameSetupScreenState,
    viewModel: BasicGameSetupScreenViewModel
) {
    VolleyText.BodyBold(
        text = stringResource(R.string.game_duration),
        modifier = Modifier,
        color = VolleyColor.White
    )

    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
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

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
        VolleyTextFieldAttribute.DurationFieldWithArrows(
            inputTime = state.startTime
        ) { time ->
            viewModel.obtainEvent(
                BasicGameSetupScreenEvent.StartTimeChanged(time)
            )
        }

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
        VolleyText.BodyRegular(
            text = stringResource(R.string.to),
            modifier = Modifier,
            color = VolleyColor.White
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
        VolleyTextFieldAttribute.DurationFieldWithArrows(
            inputTime = state.finishTime
        ) { time ->
            viewModel.obtainEvent(
                BasicGameSetupScreenEvent.FinishTimeChanged(time)
            )
        }
    }
}

@Composable
private fun GenderSection(
    state: BasicGameSetupScreenState,
    viewModel: BasicGameSetupScreenViewModel
) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.gender),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White
    )

    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))
    VolleyButton.GroupButtonsForGender3(
        checkId = when (state.gender) {
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
                else -> null // Обработка некорректной позиции
            }
            selectedGender?.let { gender ->
                viewModel.obtainEvent(BasicGameSetupScreenEvent.GenderSelected(gender))
            } ?: run { // Обработка нераспознанной позиции
                Log.e("BasicGameSetupScreen", "Unrecognized position: $position")
            }
        }
    )
}

@Composable
private fun LevelSection(
    state: BasicGameSetupScreenState,
    viewModel: BasicGameSetupScreenViewModel
) {
    VolleyText.TitleMedium(
        text = stringResource(R.string.player_level),
        modifier = Modifier.fillMaxWidth(),
        color = VolleyColor.White
    )

    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))
    VolleyButton.GroupButtonsForLevelMulti(
        modifier = Modifier,
        checkedLevels = state.levels,
        onSelected = { levels ->
            viewModel.obtainEvent(
                BasicGameSetupScreenEvent.PlayerLevelSelected(levels)
            )
        }
    )
}

@Composable
private fun NextButtonSection(
    modifier: Modifier,
    viewModel: BasicGameSetupScreenViewModel
) {
    VolleyButton.ActiveButton(
        modifier = modifier,
        text = stringResource(R.string.next_step),
        onClick = {
            viewModel.obtainEvent(
                BasicGameSetupScreenEvent.OnNextStepClick
            )
        }
    )
}

@Preview
@Composable
private fun BasicGameSetupScreenPreview() {
    val navController = rememberNavController()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        BasicGameSetupScreen(
            viewModel = BasicGameSetupScreenViewModelPreview(),
            navController = navController,
            paddingFromSystemUi = PaddingValues(0.dp)
        )
    }
}
