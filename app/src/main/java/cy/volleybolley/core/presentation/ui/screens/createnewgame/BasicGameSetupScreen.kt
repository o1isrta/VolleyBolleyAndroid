package cy.volleybolley.core.presentation.ui.screens.createnewgame

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar.TopBar

@Composable
fun BasicGameSetupScreen(navController: NavHostController) {
    TitleWithBackArrow
    Button(onClick = { navController.popBackStack() }) {
        Text("Назад")
    }
}

@Preview
@Composable
private fun GameSetupScreenPreview() {
    val navController = rememberNavController() // Создаем моковый NavHostController
    Box {
        BasicGameSetupScreen(navController = navController)
    }
}
