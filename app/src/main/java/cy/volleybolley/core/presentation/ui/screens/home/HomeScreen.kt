package cy.volleybolley.core.presentation.ui.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    navController: NavHostController,
    finisher: () -> Unit,
) {
    Button(onClick = { navController.popBackStack() }) {
        Text("Назад")
    }

    BackHandler { finisher() }
}
