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
object VolleyCalendar {   // Calendar Section

    @Composable
    fun CalendarSection(
        selectedDate: LocalDate,
        onDateSelected: (LocalDate) -> Unit,
        startMonth: YearMonth = YearMonth.now(),                            // Дефолтное значение: текущий месяц
        endMonth: YearMonth = YearMonth.now().plusYears(2),      // Дефолтное значение: 2 года вперед
        isDaySelectable: ((LocalDate) -> Boolean)? = null                   // Предикат, который определяет, можно ли выбрать день.
                                                                            // True - можно выбрать, False - нельзя.
    ) {
        val today = LocalDate.now()
        // Если isDaySelectable не задан, используем дефолтное поведение
        val actualIsDaySelectable: (LocalDate) -> Boolean = isDaySelectable ?: { date ->
            date >= today // Дефолтное поведение: можно выбрать только от сегодняшнего дня и позже
        }

        // val currentMonth = YearMonth.now()
        // val startMonth = currentMonth// начало текущего года
        // val endMonth = currentMonth.plusYears(2) //Календарь на 2 года вперед.
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
                .aspectRatio(319f / 266f) // Сохраняем пропорции
                .background(VolleyColor.White, RoundedCornerShape(32.dp))
                .padding(12.dp,12.dp,12.dp, 12.dp)
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
                        isSelectable = actualIsDaySelectable(day.date) /*&& day.position == DayPosition.MonthDate*/,
                        //isSelectable = day.date >= today,
                        onDateSelected = {onDateSelected(it) }
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
            endMonth = YearMonth.now().plusMonths(1),
            isDaySelectable = { date -> date >= today && date <= today.plus(1, ChronoUnit.MONTHS) }
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
            endMonth = YearMonth.now().plusMonths(6),
            isDaySelectable = { date -> date >= today && date <= today.plus(6, ChronoUnit.MONTHS) }
        )
    }
}

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    isToday: Boolean,
    isSelectable: Boolean,// Этот параметр теперь используется для кликабельности и внешнего вида
    onDateSelected: (LocalDate) -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(42.14f / 32.2f) // Сохраняем пропорции
            //Делаем недоступными дни, которые раньше текущей даты
            .then(
                if (/*day.position == DayPosition.MonthDate && */isSelectable) {
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
            //Сегодня
            day.position == DayPosition.MonthDate && isSelectable -> VolleyColor.TextCalendarDark // Выбираемый день
            day.position == DayPosition.MonthDate && !isSelectable -> VolleyColor.GreyDisabled // Невыбираемый день текущего месяца
            else -> VolleyColor.TextCalendarLightGrey // Дни другого месяца
//            day.position == DayPosition.MonthDate -> VolleyColor.TextCalendarDark
//            else -> VolleyColor.TextCalendarLightGrey
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
                    borderWidth = 2.dp,
                    gradientColors = listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient)
                )
            }
        }
    }
}

@Composable
fun GradientBorder(borderWidth: Dp, gradientColors: List<Color>) {
    val strokeWidthPx = with(LocalDensity.current) { borderWidth.toPx() }
    val cornerRadius = 16.dp // Здесь задаем радиус скругления углов (16dp)
    val cornerRadiusPx = with(LocalDensity.current) { cornerRadius.toPx() }
    val offset = strokeWidthPx / 2

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRoundRect(
            brush = Brush.linearGradient(colors = gradientColors),
            topLeft = Offset(offset, offset),
            size = Size(size.width - 2 * offset, size.height - 2 * offset),
            cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx),
            style = Stroke(width = strokeWidthPx)
        )
    }
}

@Composable
fun MonthHeader(month: CalendarMonth, calendarState: CalendarState) {
    val coroutineScope = rememberCoroutineScope()

    // Проверяем, достигнуты ли границы
    val isPreviousMonthDisabled = month.yearMonth <= calendarState.startMonth //month.yearMonth.year <= currentYear && month.yearMonth <= currentYearMonth
    val isNextMonthDisabled = month.yearMonth >= calendarState.endMonth//month.yearMonth.year >= currentYear && month.yearMonth >= currentYearMonth
    val isNextYearDisabled = month.yearMonth.plusYears(1) >= calendarState.endMonth

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
                        calendarState.scrollToMonth(month.yearMonth.minusMonths(1))
                    }
                }
                      },
            enabled = !isPreviousMonthDisabled
        ) {
            Image(
                painter = painterResource(R.drawable.chevron_left),
                contentDescription = "Previous Month",
                modifier = Modifier.size(width = 7.dp, height = 14.dp),
                colorFilter = ColorFilter.tint(if (isPreviousMonthDisabled) VolleyColor.GreyDisabled else VolleyColor.Black /*LocalContentColor.current*/)
            )
        }

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            VolleyText.ButtonText(
                text = "$monthName, ", // Используем отформатированное имя месяца
                color = VolleyColor.TextCalendarDark
            )
            Row(
                modifier = Modifier
                     .clickable {
                         if (!isNextYearDisabled) {
                             coroutineScope.launch {
                                 calendarState.scrollToMonth(month.yearMonth.plusYears(1))
                             }
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ){
                VolleyText.ButtonText(
                    text = "${month.yearMonth.year}", // Используем отформатированное имя месяца
                  )
                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_5.dp))

                Image(
                    painter = painterResource(R.drawable.chevron_down),
                    contentDescription = "Next Year",
                    modifier = Modifier
                        .size(width = 14.dp, height = 7.dp),
                    colorFilter = ColorFilter.tint(if (isNextYearDisabled) VolleyColor.GreyDisabled else VolleyColor.Black /*LocalContentColor.current*/)
                    )
            }
        }

        IconButton(
            onClick = {
                if (!isNextMonthDisabled) {
                    coroutineScope.launch {
                        calendarState.scrollToMonth(month.yearMonth.plusMonths(1))
                    }
                }
            },
            enabled = !isNextMonthDisabled //
        ) {
            Image(
                painter = painterResource(R.drawable.chevron_right),
                contentDescription = "Next Month",
                modifier = Modifier.size(width = 7.dp, height = 14.dp),
                colorFilter = ColorFilter.tint(if (isNextMonthDisabled) VolleyColor.GreyDisabled else VolleyColor.Black /*LocalContentColor.current*/)
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
                it.subList(1, it.size) + it.subList(0, 1) // Перемещаем воскресенье в конец, чтобы начиналось с понедельника
            }
        }
    }

    Row(modifier = Modifier) {
        daysOfWeek.forEach { dayOfWeek ->
            val formattedDayOfWeek = dayOfWeek.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(42.14f / 32.2f),
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
    val previewDate = remember { mutableStateOf(LocalDate.of(2025, 10, 22)) } // Начальная дата для preview

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
            .padding(20.dp)
    ) {
        VolleyCalendar.CalendarSection(
            selectedDate = previewDate.value,//LocalDate.of(2025, 10, 20),
            onDateSelected = { newDate -> previewDate.value = newDate } //  Обновляем previewDate при выборе новой даты
        )
    }
}

/*
@UiLibraryMarker
object VolleyCalendar {   // Calendar Section

    @Composable
    fun CalendarSection(
        selectedDate: LocalDate,
        onDateSelected: (LocalDate) -> Unit
    ) {
        val today = LocalDate.now()
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth// начало текущего года
        val endMonth = currentMonth.plusYears(2) //Календарь на 2 года вперед.
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
                .aspectRatio(319f / 266f) // Сохраняем пропорции
                .background(VolleyColor.White, RoundedCornerShape(32.dp))
                .padding(12.dp,12.dp,12.dp, 12.dp)
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
                        isSelectable = day.date >= today,
                        onDateSelected = {onDateSelected(it) }
                    )
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    isToday: Boolean,
    isSelectable: Boolean,
    onDateSelected: (LocalDate) -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(42.14f / 32.2f) // Сохраняем пропорции
            //Делаем недоступными дни, которые раньше текущей даты
            .then(
                if (day.position == DayPosition.MonthDate && isSelectable) {
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
            //Сегодня
            day.position == DayPosition.MonthDate -> VolleyColor.TextCalendarDark
            else -> VolleyColor.TextCalendarLightGrey
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
                    borderWidth = 2.dp,
                    gradientColors = listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient)
                )
            }
        }
    }
}

@Composable
fun GradientBorder(borderWidth: Dp, gradientColors: List<Color>) {
    val strokeWidthPx = with(LocalDensity.current) { borderWidth.toPx() }
    val cornerRadius = 16.dp // Здесь задаем радиус скругления углов (16dp)
    val cornerRadiusPx = with(LocalDensity.current) { cornerRadius.toPx() }
    val offset = strokeWidthPx / 2

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRoundRect(
            brush = Brush.linearGradient(colors = gradientColors),
            topLeft = Offset(offset, offset),
            size = Size(size.width - 2 * offset, size.height - 2 * offset),
            cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx),
            style = Stroke(width = strokeWidthPx)
        )
    }
}

@Composable
fun MonthHeader(month: CalendarMonth, calendarState: CalendarState) {
    val coroutineScope = rememberCoroutineScope()

    // Проверяем, достигнуты ли границы
    val isPreviousMonthDisabled = month.yearMonth <= calendarState.startMonth //month.yearMonth.year <= currentYear && month.yearMonth <= currentYearMonth
    val isNextMonthDisabled = month.yearMonth >= calendarState.endMonth//month.yearMonth.year >= currentYear && month.yearMonth >= currentYearMonth
    val isNextYearDisabled = month.yearMonth.plusYears(1) >= calendarState.endMonth

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
                        calendarState.scrollToMonth(month.yearMonth.minusMonths(1))
                    }
                }
                      },
            enabled = !isPreviousMonthDisabled
        ) {
            Image(
                painter = painterResource(R.drawable.chevron_left),
                contentDescription = "Previous Month",
                modifier = Modifier.size(width = 7.dp, height = 14.dp),
                colorFilter = ColorFilter.tint(if (isPreviousMonthDisabled) VolleyColor.GreyDisabled else VolleyColor.Black /*LocalContentColor.current*/)
            )
        }

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            VolleyText.ButtonText(
                text = "$monthName, ", // Используем отформатированное имя месяца
                color = VolleyColor.TextCalendarDark
            )
            Row(
                modifier = Modifier
                     .clickable {
                         if (!isNextYearDisabled) {
                             coroutineScope.launch {
                                 calendarState.scrollToMonth(month.yearMonth.plusYears(1))
                             }
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ){
                VolleyText.ButtonText(
                    text = "${month.yearMonth.year}", // Используем отформатированное имя месяца
                  )
                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_5.dp))

                Image(
                    painter = painterResource(R.drawable.chevron_down),
                    contentDescription = "Next Year",
                    modifier = Modifier
                        .size(width = 14.dp, height = 7.dp),
                    colorFilter = ColorFilter.tint(if (isNextYearDisabled) VolleyColor.GreyDisabled else VolleyColor.Black /*LocalContentColor.current*/)
                    )
            }
        }

        IconButton(
            onClick = {
                if (!isNextMonthDisabled) {
                    coroutineScope.launch {
                        calendarState.scrollToMonth(month.yearMonth.plusMonths(1))
                    }
                }
            }
        ) {
            Image(
                painter = painterResource(R.drawable.chevron_right),
                contentDescription = "Next Month",
                modifier = Modifier.size(width = 7.dp, height = 14.dp),
                colorFilter = ColorFilter.tint(if (isNextMonthDisabled) VolleyColor.GreyDisabled else VolleyColor.Black /*LocalContentColor.current*/)
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
                it.subList(1, it.size) + it.subList(0, 1) // Перемещаем воскресенье в конец, чтобы начиналось с понедельника
            }
        }
    }

    Row(modifier = Modifier) {
        daysOfWeek.forEach { dayOfWeek ->
            val formattedDayOfWeek = dayOfWeek.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(42.14f / 32.2f),
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
    val previewDate = remember { mutableStateOf(LocalDate.of(2025, 10, 22)) } // Начальная дата для preview

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
            .padding(20.dp)
    ) {
        VolleyCalendar.CalendarSection(
            selectedDate = previewDate.value,//LocalDate.of(2025, 10, 20),
            onDateSelected = { newDate -> previewDate.value = newDate } //  Обновляем previewDate при выборе новой даты
        )
    }
}
 */


