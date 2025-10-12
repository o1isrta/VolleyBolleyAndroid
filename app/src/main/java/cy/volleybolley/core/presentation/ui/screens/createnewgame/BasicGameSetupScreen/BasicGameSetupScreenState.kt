package cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen

import cy.volleybolley.core.presentation.base.UiState
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.domain.model.Location
import java.time.LocalDate
import java.util.Date

data class BasicGameSetupScreenState(
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
    val date: Date = Date(),
    val startTime: String = "02:00",
    val endTime: String  = "02:00",
    val gender: Gender  = Gender.Mix,
    val levels: List<Level> = listOf(Level.Light, Level.Medium, Level.Hard), //    val levels: Array<Int>
    val errorMessage: String? = null,
    val isLoading : Boolean = false     // Для загрузки (если необходимо)
 //   val isPickDateClicked: Boolean = false // нажата кнопка Pick Date, то есть показывается календарь
)  : UiState

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
