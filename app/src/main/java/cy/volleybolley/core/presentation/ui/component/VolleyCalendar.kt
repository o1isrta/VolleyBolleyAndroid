package cy.volleybolley.core.presentation.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.component.model.UiLibraryMarker
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit
import java.util.Locale

@UiLibraryMarker
object VolleyCalendar { // Calendar Section
    private const val WIDTH = 319f
    private const val HEIGHT = 266f
    const val WIDTH_DAY = 42.14f
    const val HEIGHT_DAY = 32.2f
    private const val MONTH_6 = 6L
    const val MONTH_1 = 1L
    private const val YEAR_2 = 2L
    const val YEAR_1 = 1L
    const val COEFF = 2
    val SPECIFIC_DATE = LocalDate.of(2025, 10, 22)

    @Composable
    fun CalendarSection(
        selectedDate: LocalDate,
        onDateSelected: (LocalDate) -> Unit,
        startMonth: YearMonth = YearMonth.now(), // Дефолтное значение: текущий месяц
        endMonth: YearMonth = YearMonth.now().plusYears(YEAR_2), // Дефолтное значение: 2 года вперед
        isDaySelectable: ((LocalDate) -> Boolean)? = null // Предикат, который определяет, можно ли выбрать день.
        // True - можно выбрать, False - нельзя.
    ) {
        val today = LocalDate.now()
        // Если isDaySelectable не задан, используем дефолтное поведение
        val actualIsDaySelectable: (LocalDate) -> Boolean = isDaySelectable ?: { date ->
            date >= today // Дефолтное поведение: можно выбрать только от сегодняшнего дня и позже
        }
        val firstDayOfWeek = DayOfWeek.MONDAY

        val calendarState = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstDayOfWeek = firstDayOfWeek
        )

        // Прокрутка к месяцу selectedDate при первом рендеринге или изменении selectedDate
        LaunchedEffect(selectedDate) {
            calendarState.scrollToMonth(YearMonth.from(selectedDate))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(WIDTH / HEIGHT) // Сохраняем пропорции
                .background(VolleyColor.White, RoundedCornerShape(VolleyDimens.DIMEN_32.dp))
                .padding(VolleyDimens.DIMEN_12.dp)
        ) {
            // Заголовок месяца
            MonthHeader(month = calendarState.firstVisibleMonth, calendarState = calendarState)

            // Заголовок дней недели
            DaysOfWeekHeader()

            HorizontalCalendar(
                state = calendarState,
                dayContent = { day ->
                    Day(
                        day = day,
                        isSelected = selectedDate == day.date,
                        isToday = day.date == today, //  Передаем isToday
                        // Используем actualIsDaySelectable для определения, можно ли выбрать день
                        // Также проверяем, что день относится к текущему месяцу.
                        isSelectable = actualIsDaySelectable(day.date),
                        onDateSelected = { onDateSelected(it) }
                    )
                },
                modifier = Modifier.weight(1f)
            )
        }
    }

    @Composable
    fun GameCalendar(
        selectedDate: LocalDate,
        onDateSelected: (LocalDate) -> Unit
    ) {
        val today = LocalDate.now()
        CalendarSection(
            selectedDate = selectedDate,
            onDateSelected = onDateSelected,
            startMonth = YearMonth.now(),
            endMonth = YearMonth.now().plusMonths(MONTH_1),
            isDaySelectable = { date -> date >= today && date <= today.plus(MONTH_1, ChronoUnit.MONTHS) }
        )
    }

    @Composable
    fun TournamentCalendar(
        selectedDate: LocalDate,
        onDateSelected: (LocalDate) -> Unit
    ) {
        val today = LocalDate.now()
        CalendarSection(
            selectedDate = selectedDate,
            onDateSelected = onDateSelected,
            startMonth = YearMonth.now(),
            endMonth = YearMonth.now().plusMonths(MONTH_6),
            isDaySelectable = { date -> date >= today && date <= today.plus(MONTH_6, ChronoUnit.MONTHS) }
        )
    }
}

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    isToday: Boolean,
    isSelectable: Boolean, // Этот параметр теперь используется для кликабельности и внешнего вида
    onDateSelected: (LocalDate) -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(VolleyCalendar.WIDTH_DAY / VolleyCalendar.HEIGHT_DAY) // Сохраняем пропорции
            // Делаем недоступными дни, которые раньше текущей даты
            .then(
                if (isSelectable) {
                    Modifier.clickable {
                        onDateSelected(day.date)
                    }
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        val contentColor = when {
            // Выбираемый день
            day.position == DayPosition.MonthDate && isSelectable -> VolleyColor.TextCalendarDark
            // Невыбираемый день текущего месяца
            day.position == DayPosition.MonthDate && !isSelectable -> VolleyColor.GreyDisabled
            else -> VolleyColor.TextCalendarLightGrey // Дни другого месяца
        }

        val backgroundColor = if (isSelected) {
            Brush.verticalGradient(
                colors = listOf(
                    VolleyColor.YellowForGradient,
                    VolleyColor.GreenForGradient
                )
            )
        } else {
            null
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape) // Овал для выделенной даты
                .background(backgroundColor ?: SolidColor(Color.Transparent)),
            contentAlignment = Alignment.Center
        ) {
            VolleyText.BodyRegular(
                text = day.date.dayOfMonth.toString(),
                color = contentColor,
                textAlign = TextAlign.Center
            )
            if (isToday) {
                GradientBorder(
                    borderWidth = VolleyDimens.DIMEN_2.dp,
                    gradientColors = listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient)
                )
            }
        }
    }
}

@Composable
fun GradientBorder(borderWidth: Dp, gradientColors: List<Color>) {
    val strokeWidthPx = with(LocalDensity.current) { borderWidth.toPx() }
    val cornerRadius = VolleyDimens.DIMEN_16.dp // Здесь задаем радиус скругления углов (16dp)
    val cornerRadiusPx = with(LocalDensity.current) { cornerRadius.toPx() }
    val offset = strokeWidthPx / VolleyCalendar.COEFF

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRoundRect(
            brush = Brush.linearGradient(colors = gradientColors),
            topLeft = Offset(offset, offset),
            size = Size(
                size.width - VolleyCalendar.COEFF * offset,
                size.height - VolleyCalendar.COEFF * offset
            ),
            cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx),
            style = Stroke(width = strokeWidthPx)
        )
    }
}

@Composable
fun MonthHeader(month: CalendarMonth, calendarState: CalendarState) {
    val coroutineScope = rememberCoroutineScope()

    // Проверяем, достигнуты ли границы
    val isPreviousMonthDisabled =
        month.yearMonth <= calendarState.startMonth
    val isNextMonthDisabled =
        month.yearMonth >= calendarState.endMonth
    val isNextYearDisabled = month.yearMonth.plusYears(VolleyCalendar.YEAR_1) >= calendarState.endMonth

    val monthName = month.yearMonth.month.name.lowercase(Locale.ENGLISH).let {
        if (it.isNotEmpty()) {
            it.substring(0, 1).uppercase(Locale.ENGLISH) + it.substring(1)
        } else {
            it
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                if (!isPreviousMonthDisabled) {
                    coroutineScope.launch {
                        calendarState.scrollToMonth(month.yearMonth.minusMonths(VolleyCalendar.MONTH_1))
                    }
                }
            },
            enabled = !isPreviousMonthDisabled
        ) {
            Image(
                painter = painterResource(R.drawable.chevron_left),
                contentDescription = "Previous Month",
                modifier = Modifier.size(width = VolleyDimens.DIMEN_7.dp, height = VolleyDimens.DIMEN_14.dp),
                colorFilter = ColorFilter.tint(
                    if (isPreviousMonthDisabled)
                        VolleyColor.GreyDisabled
                    else
                        VolleyColor.Black
                )
            )
        }

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            VolleyText.ButtonText(
                text = "$monthName, ", // Используем отформатированное имя месяца
                color = VolleyColor.TextCalendarDark
            )
            Row(
                modifier = Modifier
                    .clickable {
                        if (!isNextYearDisabled) {
                            coroutineScope.launch {
                                calendarState.scrollToMonth(month.yearMonth.plusYears(VolleyCalendar.YEAR_1))
                            }
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                VolleyText.ButtonText(
                    text = "${month.yearMonth.year}", // Используем отформатированное имя месяца
                )
                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_5.dp))

                Image(
                    painter = painterResource(R.drawable.chevron_down),
                    contentDescription = "Next Year",
                    modifier = Modifier
                        .size(width = VolleyDimens.DIMEN_14.dp, height = VolleyDimens.DIMEN_7.dp),
                    colorFilter = ColorFilter.tint(
                        if (isNextYearDisabled)
                            VolleyColor.GreyDisabled
                        else VolleyColor.Black
                    )
                )
            }
        }

        IconButton(
            onClick = {
                if (!isNextMonthDisabled) {
                    coroutineScope.launch {
                        calendarState.scrollToMonth(month.yearMonth.plusMonths(VolleyCalendar.MONTH_1))
                    }
                }
            },
            enabled = !isNextMonthDisabled
        ) {
            Image(
                painter = painterResource(R.drawable.chevron_right),
                contentDescription = "Next Month",
                modifier = Modifier.size(width = VolleyDimens.DIMEN_7.dp, height = VolleyDimens.DIMEN_14.dp),
                colorFilter = ColorFilter.tint(
                    if (isNextMonthDisabled)
                        VolleyColor.GreyDisabled
                    else VolleyColor.Black
                )
            )
        }
    }
}

// Заголовки столбцов (дни недели)
@Composable
fun DaysOfWeekHeader() {
    val daysOfWeek = remember {
        DateFormatSymbols.getInstance(Locale.ENGLISH).shortWeekdays.toList().let {
            it.subList(1, it.size).let { // отбрасываем первый пустой элемент
                // Перемещаем воскресенье в конец, чтобы начиналось с понедельника
                it.subList(1, it.size) + it.subList(0, 1)
            }
        }
    }

    Row(modifier = Modifier) {
        daysOfWeek.forEach { dayOfWeek ->
            val formattedDayOfWeek =
                dayOfWeek.replaceFirstChar {
                    if (it.isLowerCase())
                        it.titlecase(Locale.getDefault())
                    else it.toString()
                }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(VolleyCalendar.WIDTH_DAY / VolleyCalendar.HEIGHT_DAY),
                contentAlignment = Alignment.Center
            ) {
                VolleyText.BodyRegular(
                    text = formattedDayOfWeek,
                    color = VolleyColor.TextCalendarDark
                )
            }
        }
    }

}

@Preview
@Composable
private fun CalendarSectionPreview() {
    val previewDate = remember { mutableStateOf(VolleyCalendar.SPECIFIC_DATE) } // Начальная дата для preview

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
            .padding(20.dp)
    ) {
        VolleyCalendar.CalendarSection(
            selectedDate = previewDate.value, // LocalDate.of(2025, 10, 20),
            onDateSelected = { newDate -> previewDate.value = newDate } //  Обновляем previewDate при выборе новой даты
        )
    }
}
