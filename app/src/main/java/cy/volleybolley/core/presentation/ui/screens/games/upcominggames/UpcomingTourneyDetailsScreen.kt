package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyMocks
import cy.volleybolley.core.presentation.ui.navigation.JoinedPlayersRoute
import cy.volleybolley.core.presentation.ui.screens.games.upcominggames.dataholder.TournamentDetailsDataHolder
import org.koin.compose.koinInject

@Composable
fun UpcomingTourneyDetailsScreen(navController: NavHostController) {
    Row(modifier = Modifier.padding(VolleyDimens.DIMEN_8.dp)) {
        Button(onClick = { navController.popBackStack() }) {
            Text("Назад")
        }

        val holder: TournamentDetailsDataHolder = koinInject<TournamentDetailsDataHolder>()
        val key = holder.put(VolleyMocks.mockTournament)

        Button(
            onClick = {
                navController.navigate(
                    JoinedPlayersRoute(tournamentDetailsHolderKey = key)
                )
            }
        ) {
            Text("К списку игроков")
        }
    }
}
