package cy.volleybolley.core.presentation.ui.screens.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.LaunchRoute
import cy.volleybolley.core.presentation.ui.navigation.PersonalDataRoute
import cy.volleybolley.core.presentation.ui.navigation.PlayersRoute

@Composable
fun ProfileScreen(
    navController: NavHostController,
    finisher: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        VolleyText.TitleLarge(
            text = "PROFILE SCREEN",
            color = VolleyColor.White
        )
        Button(onClick = { navController.popBackStack() }) {
            Text("Назад")
        }
        Button(onClick = { navController.navigate(PlayersRoute) }) {
            Text("Go to Players")
        }
        Button(onClick = { navController.navigate(PersonalDataRoute) }) {
            Text("Go to Personal Data")
        }
        Spacer(Modifier.height(VolleyDimens.DIMEN_36.dp))
        Button(
            onClick = {
                navController.navigate(LaunchRoute){
                    popUpTo(LaunchRoute) { inclusive = true }
                }
            }
        ) {
            Text("Log Out (back to Launch)")
        }
    }

    BackHandler { finisher() }
}
