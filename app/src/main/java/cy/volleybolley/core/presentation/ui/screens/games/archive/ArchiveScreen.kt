package cy.volleybolley.core.presentation.ui.screens.games.archive

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText

@Composable
fun ArchiveScreen(navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        VolleyText.TitleLarge(
            text = "ARCHIVE SCREEN",
            color = VolleyColor.White
        )
        Button(onClick = { navController.popBackStack() }) {
            Text("Назад")
        }
    }
}
