package cy.volleybolley.core.presentation.ui.screens.games.mygames.changeteam

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateListOf
import cy.volleybolley.R
import cy.volleybolley.core.presentation.base.UiState

private const val LEVEL_HIGH = "H"

data class ChangeTeamState(
    val teams: List<TeamUi> = listOf(
        TeamUi(
            nameRes = R.string.team_one,
            members = mutableStateListOf(
                MemberUi("Anton Ivanov", LEVEL_HIGH),
                MemberUi("Aleksandr Abramov", LEVEL_HIGH)
            )
        ),
        TeamUi(
            nameRes = R.string.team_two,
            members = mutableStateListOf(
                MemberUi("Anya Levan", LEVEL_HIGH),
                MemberUi("Alina Lyubimova", LEVEL_HIGH)
            )
        ),
        TeamUi(
            nameRes = R.string.team_three,
            members = mutableStateListOf(
                MemberUi(null, null),
                MemberUi(null, null)
            )
        ),
        TeamUi(
            nameRes = R.string.team_four,
            members = mutableStateListOf(
                MemberUi("Tatiana Kalinina", LEVEL_HIGH),
                MemberUi(null, null)
            )
        )
    ),
    val selectedTeam: Int = 0
) : UiState

data class TeamUi(
    @StringRes val nameRes: Int,
    val members: MutableList<MemberUi>
)

data class MemberUi(
    val name: String?, // null => Free spot
    val level: String? // null => нет бейджа
)
