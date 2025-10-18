package cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen

import android.os.Build
import androidx.annotation.RequiresApi
import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.domain.model.Location
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import java.util.Date

data class BasicGameSetupScreenState @RequiresApi(Build.VERSION_CODES.O) constructor(
    val message: String = "",
    val placeCourt: Court = Court(
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
    //val date: Date = Calendar.getInstance().time, // Получаем текущую дату и время
    // val date: Date = getDateFor2025_10_20(), // Используем функцию для создания Date
    val date: LocalDate = LocalDate.of(2025, 10, 20), // Используем LocalDate  val date: LocalDate = LocalDate.now()
    val startTime: String = "02:00",
    val endTime: String  = "04:00",
    val gender: Gender  = Gender.Mix,
    val levels: List<Level> = listOf(Level.Light, Level.Medium, Level.Hard), //    val levels: Array<Int>
    val errorMessage: String? = null,
    val isLoading : Boolean = false     // Для загрузки (если необходимо)
 //   val isPickDateClicked: Boolean = false // нажата кнопка Pick Date, то есть показывается календарь
)  : UiState

/*fun getDateFor2025_10_20(): Date {
    val calendar = Calendar.getInstance()
    calendar.set(2025, Calendar.OCTOBER, 20) // ВНИМАНИЕ: Calendar.OCTOBER = 9 (январь = 0)
    return calendar.time
}*/

fun getDateFor2025_10_20(): Date {
    val calendar = Calendar.getInstance()
    calendar.set(2025, Calendar.OCTOBER, 20) // ВНИМАНИЕ: Calendar.OCTOBER = 9 (январь = 0)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.time
}

enum class Gender {
    Mix,
    Men,
    Women
}

enum class Level {
    Light,
    Medium,
    Hard,
    Pro
}
