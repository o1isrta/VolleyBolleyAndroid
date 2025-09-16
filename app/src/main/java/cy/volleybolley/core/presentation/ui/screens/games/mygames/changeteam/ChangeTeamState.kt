package cy.volleybolley.core.presentation.ui.screens.games.mygames.changeteam

import androidx.compose.runtime.mutableStateListOf
import cy.volleybolley.core.presentation.base.UiState

private const val LEVEL_HIGH = "H"

data class ChangeTeamState(
    val teams: List<TeamUi> = listOf(
        TeamUi(
            name = "Team 1",
            members = mutableStateListOf(
                MemberUi("Anton Ivanov", LEVEL_HIGH),
                MemberUi("Aleksandr Abramov", LEVEL_HIGH)
            )
        ),
        TeamUi(
            name = "Team 2",
            members = mutableStateListOf(
                MemberUi("Anya Levan", LEVEL_HIGH),
                MemberUi("Alina Lyubimova", LEVEL_HIGH)
            )
        ),
        TeamUi(
            name = "Team 3",
            members = mutableStateListOf(
                MemberUi(null, null),
                MemberUi(null, null)
            )
        ),
        TeamUi(
            name = "Team 4",
            members = mutableStateListOf(
                MemberUi("Tatiana Kalinina", LEVEL_HIGH),
                MemberUi(null, null)
            )
        )
    ),
    val selectedTeam: Int = 0
) : UiState

data class TeamUi(
    val name: String,
    val members: MutableList<MemberUi>
)

data class MemberUi(
    val name: String?,// null => Free spot
    val level: String?// null => нет бейджа
)
