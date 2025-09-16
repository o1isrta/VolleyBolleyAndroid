package cy.volleybolley.core.presentation.ui.screens.games.mygames.changeteam

import androidx.compose.runtime.mutableStateListOf
import cy.volleybolley.core.presentation.base.UiState

data class ChangeTeamState(
    val teams: List<TeamUi> = listOf(
        TeamUi(
            name = "Team 1",
            members = mutableStateListOf(
                MemberUi("Anton Ivanov", "H"),
                MemberUi("Aleksandr Abramov", "H")
            )
        ),
        TeamUi(
            name = "Team 2",
            members = mutableStateListOf(
                MemberUi("Anya Levan", "H"),
                MemberUi("Alina Lyubimova", "H")
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
                MemberUi("Tatiana Kalinina", "H"),
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
    val name: String?,   // null => Free spot
    val level: String?   // null => нет бейджа
)
