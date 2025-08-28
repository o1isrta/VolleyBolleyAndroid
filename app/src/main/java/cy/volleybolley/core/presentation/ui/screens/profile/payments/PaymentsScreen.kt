package cy.volleybolley.core.presentation.ui.screens.profile.payments

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun PaymentsScreen(navController: NavHostController) {
    Button(onClick = { navController.popBackStack() }) {
        Text("Назад")
    }
}
