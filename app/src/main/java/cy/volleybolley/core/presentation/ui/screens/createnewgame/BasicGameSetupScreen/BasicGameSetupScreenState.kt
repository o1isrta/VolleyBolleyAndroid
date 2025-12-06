package cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen

import android.os.Build
import androidx.annotation.RequiresApi
import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository.Gender
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.domain.model.Location
import java.time.LocalDate

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
    val date: LocalDate = LocalDate.now(), //val date: LocalDate = LocalDate.of(2025, 10, 20), // Используем LocalDate  val date: LocalDate = LocalDate.now()
    val startTime: VolleyTimeStamp? = VolleyTimeStamp(2,0, true), // val startTime: String = "02:00",
    val finishTime: VolleyTimeStamp? = VolleyTimeStamp(4,0, true), // val endTime: String  = "04:00",
    val gender: Gender = Gender.Mix,
    val levels: Set<Level> = setOf(Level.Light, Level.Medium, Level.Hard), //    val levels: Array<Int>
    val isLoading : Boolean = false     // Для загрузки (если необходимо)
)  : UiState

//enum class Gender {
//    Mix,
//    Men,
//    Women
//}

