package cy.volleybolley.core.presentation.ui.screens.authorization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cy.volleybolley.core.presentation.ui.navigation.SignUpRoute

@Composable
fun OnboardingScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to OnboardingScreen")
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            navController.navigate(SignUpRoute)
        }) {
            Text("Go to Sign Up Screen")
        }
    }
}
