package cy.volleybolley.core.presentation.ui.screens.games.upcominggames

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun UpcomingTourneyDetailsScreen(navController: NavHostController) {
    Button(onClick = { navController.popBackStack() }) {
        Text("Назад")
    }
}
