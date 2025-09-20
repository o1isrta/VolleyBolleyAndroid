package cy.volleybolley.core.presentation.ui.screens.games.mygames

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.ArchiveRoute

@Composable
fun MyGamesScreen(
    navController: NavHostController,
    finisher: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        VolleyText.TitleLarge(
            text = "MY GAMES SCREEN",
            color = VolleyColor.White
        )
        Button(onClick = { navController.popBackStack() }) {
            Text("Назад")
        }
        Button(onClick = { navController.navigate(ArchiveRoute) }) {
            Text("Go to Archive")
        }
    }

    BackHandler { finisher() }
}
