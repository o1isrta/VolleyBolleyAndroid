package cy.volleybolley.core.presentation.ui.screens.profile

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun FaqScreen(navController: NavHostController) {
    Button(onClick = { navController.popBackStack() }) {
        Text("Назад")
    }
}
