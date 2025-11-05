package cy.volleybolley.core.presentation.ui.screens.games.upcominggames.dataholder

import cy.volleybolley.games.domain.model.event.tournament.TournamentDetails
import java.util.UUID

class TournamentDetailsDataHolder {
    private val tournamentDetailsMap = mutableMapOf<String, TournamentDetails>()
    fun put(data: TournamentDetails): String {
        val id = UUID.randomUUID().toString()
        tournamentDetailsMap[id] = data
        return id
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(id: String): T = tournamentDetailsMap[id] as T
}
