package cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.core.presentation.ui.navigation.SearchCourtRoute
import cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen.GameEnteringConditionsScreenEvent
import cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen.Privacy
import kotlinx.coroutines.flow.collectLatest
import java.util.Date
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Calendar

@Composable
fun BasicGameSetupScreen(navController: NavHostController,
                         viewModel: BasicGameSetupScreenViewModel = viewModel()) { // = BasicGameSetupScreenViewModel ()
    val scrollState = rememberScrollState() //Состояние скролла
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel.uiEffect) { // подписываемся на Effect
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is BasicGameSetupScreenEffect.NavigateToCreatePlace -> {
                   // Toast.makeText(context, "Нажали на Create", Toast.LENGTH_SHORT).show()
                   navController.navigate(SearchCourtRoute)
                }
                BasicGameSetupScreenEffect.NavigateBack -> {
                    navController.popBackStack()
                }
                //Обработка всех возможных случаев
                else -> {
                    // Handle unexpected effect or do nothing.  Log it!
                    Log.w("BasicGameSetupScreen", "Unhandled effect: $effect")
                }
            }
        }
    }
// Overlay для отображения индикатора загрузки
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator() // Или другой индикатор загрузки
        }
    } else {  // Отображаем основной контент, только если не загружается
        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = VolleyDimens.DIMEN_32,
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = VolleyDimens.DIMEN_20.dp)
            ) {
                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

                TitleWithBackArrow(
                    title = stringResource(R.string.create_a_game),
                    modifier = Modifier.fillMaxWidth(),
                    onBackClick = {viewModel.obtainEvent(BasicGameSetupScreenEvent.OnBackClicked)}
                )

                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)

                ) {
                    VolleyText.TitleMedium(
                        text = stringResource(R.string.your_message),
                        modifier = Modifier.fillMaxWidth(),
                        color = VolleyColor.White
                    )

                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

                    VolleyMessageTextField.MessageField(
                        hint = stringResource(R.string.leave_a_note_for_players),
                        textInput = state.message,
                        modifier = Modifier.height(106.dp)
                    ) { }

                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

                    VolleySimpleComponent.DividerLine()// HorizontalLine()

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
                            modifier = Modifier
                                .weight(1f), // Важно!  Занимает только часть доступного пространства,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_mark_yellow),
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
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
                            modifier = Modifier,//.height(VolleyDimens.DIMEN_44.dp),
                            text = "Create",
                            onClick = {viewModel.obtainEvent(BasicGameSetupScreenEvent.OnCreateClick)}
                        )
                    }

                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                    VolleySimpleComponent.DividerLine() //HorizontalLine()
                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

                    VolleyText.TitleMedium(
                        text = stringResource(R.string.date),
                        modifier = Modifier.fillMaxWidth(),
                        color = VolleyColor.White
                    )

                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

                    val todayDate: Date = Date()
                    val isPickDateSelected = !isSameDay(state.date, todayDate)

                    VolleyButton.GroupButtonsForDate2(
                        checkId = if (isSameDay(state.date, Date())) 1 else 2,
                        modifier = Modifier,
                        onSelected = { position ->
                            val selectedDate: Date? = when (position) {
                                1 -> Date() // Сегодня
                                2 -> {
                                    // При выборе "Pick Date" не устанавливаем дату сразу,
                                    // а показываем календарь для выбора
                                    // Оставляем текущую дату state.date как временную
                                    state.date
                                }
                                else -> null // Обработка некорректной позиции
                            }

                            selectedDate?.let { date ->
                                viewModel.obtainEvent(BasicGameSetupScreenEvent.OnDateSelected(date))
                            } ?: run {
                                // Обработка нераспознанной позиции
                                Log.e("BasicGameSetupScreen", "Нераспознанная позиция кнопки даты: $position")
                            }
                        }
                    )
                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_10.dp))

                    // здесь будет календарь
                    /*     Box(
             modifier = Modifier
                 .fillMaxWidth()
                 .height(266.dp)
                 .clip(RoundedCornerShape(32.dp)) // Задаем скругление углов
                 .background(VolleyColor.White) // Цвет прямоугольника
          )
         Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
         */
                    // Календарь показывается только если выбрана кнопка "Pick Date"
                    if (isPickDateSelected) {
                        DatePickerSection(
                            selectedDate = state.date,
                            onDateSelected = { selectedDate ->
                                viewModel.obtainEvent(BasicGameSetupScreenEvent.OnDateSelected(selectedDate))
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
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
                            inputTime = VolleyTimeStamp(
                                14,
                                0,
                                true
                            )
                        ) { }

                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))

                        VolleyText.BodyRegular(
                            text = stringResource(R.string.to),
                            modifier = Modifier,
                            color = VolleyColor.White
                        )

                        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))

                        VolleyTextFieldAttribute.DurationFieldWithArrows(
                            inputTime = VolleyTimeStamp(
                                15,
                                0,
                                true
                            )
                        ) { }
                    }

                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
                    VolleySimpleComponent.DividerLine() //HorizontalLine()
                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

                     VolleyText.TitleMedium(
                        text = stringResource(R.string.gender),
                        modifier = Modifier.fillMaxWidth(),
                        color = VolleyColor.White
                    )

                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

                    VolleyButton.GroupButtonsForGender3(
                        modifier = Modifier,
                        onSelected = {}
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

                    VolleyButton.GroupButtonsForLevel(
                        checkId = 3,
                        modifier = Modifier,
                        onSelected = {}
                    )
                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

                    VolleyButton.ActiveButton(
                        modifier = Modifier
                            // .padding(VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_16.dp)
                            .height(44.dp)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(),
                        text = stringResource(R.string.next_game),
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))
                }
            }
        }
    }
    /* }*/

}

fun isSameDay(date1: Date, date2: Date): Boolean {
    val calendar1 = Calendar.getInstance().apply { time = date1 }
    val calendar2 = Calendar.getInstance().apply { time = date2 }

    return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
        calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
}

/*@Composable
fun BasicGameSetupScreenContent(
) {
    Column(
        modifier = Modifier
            .padding(horizontal = VolleyDimens.DIMEN_20.dp)
    ) {
        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

        TitleWithBackArrow(
            title = stringResource(R.string.create_a_game),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

        VolleyText.TitleMedium(
            text = stringResource(R.string.your_message),
            modifier = Modifier.fillMaxWidth(),
            color = VolleyColor.White
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

        VolleyMessageTextField.MessageField(
            hint = stringResource(R.string.leave_a_note_for_players),
            textInput = "",
            modifier = Modifier
        ) { }

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

        VolleySimpleComponent.DividerLine()// HorizontalLine()

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
                modifier = Modifier
                    .weight(1f), // Важно!  Занимает только часть доступного пространства,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_mark_yellow),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    VolleyText.BodyBold(
                        text = "Karon Beach Club",
                        modifier = Modifier,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = VolleyColor.White
                    )
                    VolleyText.BodyLight(
                        text = "Patak Rd, Mueang Phuket",
                        modifier = Modifier,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = VolleyColor.White
                    )
                }
            }

            VolleyButton.ActiveGradientButton(
                modifier = Modifier,//.height(VolleyDimens.DIMEN_44.dp),
                text = "Create"
            ) { }
        }

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

        VolleySimpleComponent.DividerLine() //HorizontalLine()

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

        VolleyText.TitleMedium(
            text = stringResource(R.string.date),
            modifier = Modifier.fillMaxWidth(),
            color = VolleyColor.White
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

        VolleyButton.GroupButtonsForDate2(
            checkId = 1,
            modifier = Modifier,
            onSelected = {}
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_10.dp))

        // здесь будет календарь
       *//*     Box(
    modifier = Modifier
        .fillMaxWidth()
        .height(266.dp)
        .clip(RoundedCornerShape(32.dp)) // Задаем скругление углов
        .background(VolleyColor.White) // Цвет прямоугольника
 )
Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
*//*
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
                inputTime = VolleyTimeStamp(
                    14,
                    0,
                    true
                )
            ) { }

            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))

            VolleyText.BodyRegular(
                text = stringResource(R.string.to),
                modifier = Modifier,
                color = VolleyColor.White
            )

            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))

            VolleyTextFieldAttribute.DurationFieldWithArrows(
                inputTime = VolleyTimeStamp(
                    15,
                    0,
                    true
                )
            ) { }
        }

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
        VolleySimpleComponent.DividerLine() //HorizontalLine()
        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

        VolleyText.TitleMedium(
            text = stringResource(R.string.gender),
            modifier = Modifier.fillMaxWidth(),
            color = VolleyColor.White
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

        VolleyButton.GroupButtonsForGender3(
            modifier = Modifier,
            onSelected = {}
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

        VolleyButton.GroupButtonsForLevel(
            checkId = 3,
            modifier = Modifier,
            onSelected = {}
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))
        
        VolleyButton.ActiveButton(
            modifier = Modifier
               // .padding(VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_16.dp)
                .height(44.dp)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            text = stringResource(R.string.next_game),
            onClick = {}
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))
    }
}*/


@Preview
@Composable
private fun BasicGameSetupScreenPreview() {
    val navController = rememberNavController() // Создаем моковый NavHostController
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        BasicGameSetupScreen(navController = navController)
    }
}
