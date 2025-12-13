package cy.volleybolley.core.presentation.ui.screens.createnewgame.createNewGameRepository

import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.players.domain.model.Player
import java.time.LocalDate

data class GameData(
    // получаем с экрана BasicGameSetupScreen:
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
    val date: LocalDate = LocalDate.now(),
    val startTime: VolleyTimeStamp? = VolleyTimeStamp(2, 0, true),
    val finishTime: VolleyTimeStamp? = VolleyTimeStamp(4, 0, true),
    val gender: Gender = Gender.Mix,
    val levels: Set<Level> = setOf(Level.Light, Level.Medium, Level.Hard),
    // получаем с экрана GameEnteringConditionsScreen:
    val maximumPlayers: Int = 8,
    val perPerson: String = "5.0",
    val accountNumber: String? = null,  // номер аккаунта, если есть
    // получаем с экрана PrivacyOptionsScreen, показываем на экране GameEnteringConditionsScreen,
    // редактируем на обоих экранах (на GameEnteringConditionsScreen можем удалять игроков, на PrivacyOptionsScreen - добавлять, удалять)
    val players: List<Player> = emptyList() // игроки, выбранные для игры
)

enum class Privacy {
    Public,
    Private
}

enum class Gender {
    Mix,
    Men,
    Women
}
