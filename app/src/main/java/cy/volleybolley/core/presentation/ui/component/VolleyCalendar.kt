package cy.volleybolley.core.presentation.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import cy.volleybolley.core.presentation.ui.model.VolleyTypography
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale


@UiLibraryMarker
object VolleyCalendar {
    // Calendar Section

    @Composable
    fun CalendarSection(
        selectedDate: LocalDate,
        onDateSelected: (LocalDate) -> Unit,
        modifier: Modifier = Modifier
    ) {
        val today = LocalDate.now()
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth//.withMonth(1) // начало текущего года
        val endMonth = currentMonth.plusYears(2) //Календарь на 2 года вперед.
        val firstDayOfWeek = DayOfWeek.MONDAY    // val firstDayOfWeek = firstDayOfWeekFromLocale()

        val calendarState = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstDayOfWeek = firstDayOfWeek
        )
       // val coroutineScope = rememberCoroutineScope()

        // Прокрутка к месяцу selectedDate при первом рендеринге или изменении selectedDate
        LaunchedEffect(selectedDate) {
            calendarState.scrollToMonth(YearMonth.from(selectedDate))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                //.height(266.dp)
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
                        isSelectable = day.date >= today,
                        onDateSelected = {
                            onDateSelected(it)
                        }//(Date.from(it.atStartOfDay(ZoneId.systemDefault()).toInstant())) }
                    )
                },
//                monthHeader = { month ->
//                    MonthHeader(month, calendarState)
//                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    isSelectable: Boolean,
    onDateSelected: (LocalDate) -> Unit
) {
  //  val context = LocalContext.current
    Box(
        modifier = Modifier
            .aspectRatio(42.14f / 32.2f) // Сохраняем пропорции
           // .padding(3.dp)
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
            //isSelected -> VolleyColor.TextCalendarDark
            day.position == DayPosition.MonthDate && day.date == LocalDate.now() -> VolleyColor.OrangeHard
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
        }
    }
}

@Composable
fun MonthHeader(month: CalendarMonth, calendarState: CalendarState) {
   // val currentMonth = remember { mutableStateOf(month.yearMonth) }
    val coroutineScope = rememberCoroutineScope()
    val currentYear = YearMonth.now().year
    val currentYearMonth = YearMonth.now()

    // Проверяем, достигнуты ли границы
    val isPreviousMonthDisabled = month.yearMonth <= calendarState.startMonth //month.yearMonth.year <= currentYear && month.yearMonth <= currentYearMonth
    val isNextMonthDisabled = month.yearMonth >= calendarState.endMonth//month.yearMonth.year >= currentYear && month.yearMonth >= currentYearMonth
  //  val isPreviousYearDisabled = month.yearMonth <= calendarState.startMonth.minusYears(1)
    val isNextYearDisabled = month.yearMonth.plusYears(1) >= calendarState.endMonth


    val monthName = month.yearMonth.month.name.lowercase(Locale.ENGLISH).let {
        if (it.isNotEmpty()) {
            it.substring(0, 1).uppercase(Locale.ENGLISH) + it.substring(1)
        } else {
            it
        }
    }
        //val monthName = month.yearMonth.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())
        //.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

   // val monthName = month.yearMonth.month.name.lowercase(Locale.ENGLISH).replaceFirstChar {
   //     if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString()
    Row(
        modifier = Modifier
            .fillMaxWidth(),
           // .padding(horizontal = 12.dp),
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
                colorFilter = ColorFilter.tint(if (isPreviousMonthDisabled) VolleyColor.GreyDisabled else LocalContentColor.current)
            )
        }

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            VolleyText.ButtonText(
                text = "$monthName, ", // Используем отформатированное имя месяца
                //   text = "${month.yearMonth.month.name}, ${month.yearMonth.year}",
              //  modifier = Modifier.padding(horizontal = 16.dp)
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
                    //   text = "${month.yearMonth.month.name}, ${month.yearMonth.year}",
                    //  modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_5.dp))

                Image(
                    painter = painterResource(R.drawable.chevron_down),
                    contentDescription = "Next Year",
                    modifier = Modifier
                        .size(width = 14.dp, height = 7.dp),
                    colorFilter = ColorFilter.tint(if (isNextYearDisabled) VolleyColor.GreyDisabled else LocalContentColor.current)
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
                colorFilter = ColorFilter.tint(if (isNextMonthDisabled) VolleyColor.GreyDisabled else LocalContentColor.current)
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
                it.subList(1, it.size) + it.subList(
                    0,
                    1
                ) // Перемещаем воскресенье в конец, чтобы начиналось с понедельника

            }
        }
    }

    Row(modifier = Modifier            /*.padding(3.dp)*/) {
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
    val previewDate = remember { mutableStateOf(LocalDate.of(2025, 10, 17)) } // Начальная дата для preview

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
            .padding(20.dp)
    ) {
        VolleyCalendar.CalendarSection(
            selectedDate = previewDate.value,//LocalDate.of(2025, 10, 20),
            //onDateSelected = {}
            onDateSelected = { newDate -> previewDate.value = newDate } //  Обновляем previewDate при выборе новой даты
        )
    }
}

/*fun getDateFor2025_10_20(): Date {
    val calendar = Calendar.getInstance()
    calendar.set(2025, Calendar.OCTOBER, 20) // ВНИМАНИЕ: Calendar.OCTOBER = 9 (январь = 0)
    return calendar.time
}*/


  /*  fun DatePickerSection(
        selectedDate: Date,
        onDateSelected: (Date) -> Unit,
        modifier: Modifier = Modifier
    ) {
        // Контейнер для календаря
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(266.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(VolleyColor.White)
                    .padding(16.dp)
        ) {
            CalendarView(
                selectedDate = selectedDate,
                onDateSelected = onDateSelected
            )
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun CalendarView(
        selectedDate: Date?,
        onDateSelected: (Date) -> Unit
    ) {
        val currentDate = remember { Calendar.getInstance().time }
        val currentMonth = remember { YearMonth.now() }
        val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
        val monthState = rememberCalendarState(
            startMonth = currentMonth.minusMonths(12),
            endMonth = currentMonth.plusMonths(12),
            firstDayOfWeek = firstDayOfWeek,
        )
        val coroutineScope = rememberCoroutineScope()
        var selDate by remember { mutableStateOf(selectedDate) }

        HorizontalCalendar(
            state = monthState,
            dayContent = { day ->
                Day(day, selDate) { day ->
                    selDate = day.date
                    onDateSelected(selDate)
                }
            },
            monthHeader = { month ->
                MonthHeader(month, firstDayOfWeek)
            },
        )
    }

    @Composable
    fun Day(
        day: CalendarDay,
        selectedDate: Date,
        onClick: (CalendarDay) -> Unit
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
            *//*     .clickable {
                     onClick(day)
                 }*//*,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                textAlign = TextAlign.Center,
                color = if (isSameDay(day.date.toDate(), selectedDate)) Color.Blue else Color.Black
            )
        }
    }

    @Composable
    fun MonthHeader(month: CalendarMonth, firstDayOfWeek: DayOfWeek) {
        Row {
            val daysOfWeek = remember { getDaysOfWeek(firstDayOfWeek) }
            for (dayOfWeek in daysOfWeek) {
                Text(
                    textAlign = TextAlign.Center,
                    text = dayOfWeek.name.first().toString(),
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }

    fun getDaysOfWeek(firstDayOfWeek: DayOfWeek): Array<DayOfWeek> {
        val daysOfWeek = DayOfWeek.values()
        // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
        // Only necessary if firstDayOfWeek is not DayOfWeek.MONDAY which may not be the case.
        if (firstDayOfWeek != DayOfWeek.MONDAY) {
            val reorderedDaysOfWeek = daysOfWeek.copyOf()
            var current = 0
            // Find the firstDayOfWeek in the week.
            while (reorderedDaysOfWeek[current] != firstDayOfWeek) {
                current++
            }
            // Rotate the array so that firstDayOfWeek is at the start.
            for (i in 0 until current) {
                val temp = reorderedDaysOfWeek[i]
                reorderedDaysOfWeek[i] = reorderedDaysOfWeek[current + i]
                reorderedDaysOfWeek[current + i] = temp
            }
        }
        return daysOfWeek
    }

    fun isSameDay(date1: Date, date2: Date): Boolean {
        val calendar1 = Calendar.getInstance()
        calendar1.time = date1

        val calendar2 = Calendar.getInstance()
        calendar2.time = date2

        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
            calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
            calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
    }*/


