package cy.volleybolley.core.presentation.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cy.volleybolley.core.presentation.ui.component.model.UiLibraryMarker
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import java.time.YearMonth
import java.util.Calendar
import java.util.Date
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale

@UiLibraryMarker
object VolleyCalendar {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun DatePickerSection(
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
            initialVisibleMonth = currentMonth,
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
            /*     .clickable {
                     onClick(day)
                 }*/,
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
    }
}

