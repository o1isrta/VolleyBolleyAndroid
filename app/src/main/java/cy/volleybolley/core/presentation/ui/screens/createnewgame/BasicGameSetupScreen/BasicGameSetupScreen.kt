package cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleyMessageTextField
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.VolleyTextFieldAttribute
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.SearchCourtRoute
import kotlinx.coroutines.flow.collectLatest
import androidx.lifecycle.viewmodel.compose.viewModel
import cy.volleybolley.core.presentation.ui.component.VolleyCalendar
import cy.volleybolley.core.presentation.ui.navigation.GameEnteringConditionsRoute
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.Gender
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BasicGameSetupScreen(
    navController: NavHostController,
    viewModel: BasicGameSetupScreenViewModel = viewModel(),
    paddingFromSystemUi: PaddingValues
) {
    val scrollState = rememberScrollState() //Состояние скролла
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val showCalendar = viewModel.showCalendar.collectAsState().value

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

                else -> { //Обработка всех возможных случаев
                    Log.w("BasicGameSetupScreen", "Unhandled effect: $effect")
                }
            }
        }
    }
    if (state.isLoading) { // Overlay для отображения индикатора загрузки
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator() // Или другой индикатор загрузки
        }
    } else { // Отображаем основной контент, только если не загружается
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
                    TitleWithBackArrow(
                        title = stringResource(R.string.create_a_game),
                        modifier = Modifier.fillMaxWidth(),
                        onBackClick = { viewModel.obtainEvent(BasicGameSetupScreenEvent.OnBackClicked) }
                    )

                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        VolleyText.TitleMedium(
                            text = stringResource(R.string.your_message),
                            modifier = Modifier.fillMaxWidth(),
                            color = VolleyColor.White
                        )

                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                        VolleyMessageTextField.MessageField(
                            hint = stringResource(R.string.leave_a_note_for_players),
                            textInput = state.message,
                            modifier = Modifier.height(106.dp),
                            actionToTransferContent = { newMessage ->
                                viewModel.obtainEvent(BasicGameSetupScreenEvent.MessageChanged(newMessage))
                            }
                        )

                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                        VolleySimpleComponent.DividerLine()
                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

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
                                modifier = Modifier.weight(1f), // Важно!  Занимает только часть доступного пространства,
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
                                text = "Create",
                                onClick = { viewModel.obtainEvent(BasicGameSetupScreenEvent.OnChangeClick) }
                            )
                        }

                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                        VolleySimpleComponent.DividerLine()
                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

                        VolleyText.TitleMedium(
                            text = stringResource(R.string.date),
                            modifier = Modifier.fillMaxWidth(),
                            color = VolleyColor.White
                        )

                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))
                        VolleyButton.GroupButtonsForDate2(
                            checkId = if (viewModel.isSameDay(state.date, LocalDate.now())) 1 else 2,
                            modifier = Modifier,
                            onSelected = { position ->
                                when (position) {
                                    1 -> { // Сегодня
                                        viewModel.obtainEvent(BasicGameSetupScreenEvent.OnTodayClicked)
                                    }

                                    2 -> {  //Выбрать Дату (Pick Date)
                                        viewModel.obtainEvent(BasicGameSetupScreenEvent.OnPickDateClicked)
                                    }

                                    else -> { // Обработка нераспознанной позиции
                                        Log.e("BasicGameSetupScreen", "Нераспознанная позиция кнопки даты: $position")
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_10.dp))

                        // Календарь показывается только если выбрана кнопка "Pick Date"
                        if (showCalendar) {  // Используем флаг из ViewModel
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
                            ) { time -> viewModel.obtainEvent(BasicGameSetupScreenEvent.StartTimeChanged(time)) }

                            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
                            VolleyText.BodyRegular(
                                text = stringResource(R.string.to),
                                modifier = Modifier,
                                color = VolleyColor.White
                            )

                            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
                            VolleyTextFieldAttribute.DurationFieldWithArrows(
                                inputTime = state.finishTime
                            ) { time -> viewModel.obtainEvent(BasicGameSetupScreenEvent.FinishTimeChanged(time)) }
                        }

                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                        VolleySimpleComponent.DividerLine()
                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

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
                                    Log.e("BasicGameSetupScreen", "Нераспознанная позиция: $position")
                                }
                            }
                        )

                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                        VolleySimpleComponent.DividerLine() //HorizontalLine()
                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

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
                                viewModel.obtainEvent(BasicGameSetupScreenEvent.PlayerLevelSelected(levels))
                            }
                        )

                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                        VolleyButton.ActiveButton(
                            modifier = Modifier
                                .height(44.dp)
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(),
                            text = stringResource(R.string.next_step),
                            onClick = {
                                viewModel.obtainEvent(BasicGameSetupScreenEvent.OnNextStepClick)
                            }
                        )
                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                    }
                }
            }
        }
    }
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
            navController = navController, paddingFromSystemUi = PaddingValues(0.dp)
        )
    }
}

