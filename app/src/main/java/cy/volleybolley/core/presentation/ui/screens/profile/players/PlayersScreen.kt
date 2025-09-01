package cy.volleybolley.core.presentation.ui.screens.profile.players

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun PlayersScreen(navController: NavHostController) {
    Button(onClick = { navController.popBackStack() }) {
        Text("Назад")
    }
}
