package cy.volleybolley.core.presentation.ui.screens.createnewgame.CreateNewGameRepository

import cy.volleybolley.core.presentation.ui.model.Level
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.players.domain.model.Player
import java.time.LocalDate

//private const val LEVEL_HIGH = "H"
//private const val LEVEL_LIGHT = "L"
//private const val LEVEL_MEDIUM = "M"
//private const val LEVEL_PRO = "P"

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
    val startTime: VolleyTimeStamp? = VolleyTimeStamp(2,0, true),
    val finishTime: VolleyTimeStamp? = VolleyTimeStamp(4,0, true),
    val gender: Gender = Gender.Mix,
    val levels: Set<Level> = setOf(Level.Light, Level.Medium, Level.Hard),
    // получаем с экрана GameEnteringConditionsScreen:
    val maximumPlayers: Int = 8,
    val selectedPrivacy: Privacy = Privacy.Public,
    val perPerson: String = "5.0",
    val accountNumber: String? = null,  // номер аккаунта, если есть
    // получаем с экрана PrivacyOptionsScreen, показываем на экране GameEnteringConditionsScreen,
    // редактируем на обоих экранах (на GameEnteringConditionsScreen можем удалять игроков, на PrivacyOptionsScreen - добавлять, удалять)
    val players: List<Player> = emptyList() // игроки, выбранные для игры
//        listOf(
//        Player(1,"Kristina", "Popova", null, true, cy.volleybolley.core.presentation.ui.LEVEL_MEDIUM),
//        Player(2, "Polina", "Vasylyeva", null,false, cy.volleybolley.core.presentation.ui.LEVEL_PRO),
//        Player(3, "Anton", "Ivanov", null, true, cy.volleybolley.core.presentation.ui.LEVEL_LIGHT),
//        Player(4, "Aleksandr", "Abramov", null, false, cy.volleybolley.core.presentation.ui.LEVEL_HIGH)
//)
)
   enum class Privacy {
        Public,
        Private
    }

//// по образу MemberUi из 62 ветки, из файла ChangeTeamScreen.kt
//        data class PlayerUI(
//        val name: String,
//        val level: String
//    )

enum class Gender {
    Mix,
    Men,
    Women
}
