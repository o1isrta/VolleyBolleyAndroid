package cy.volleybolley.core.presentation.ui.screens.authorization

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import cy.volleybolley.core.presentation.ui.navigation.OnboardingRoute
import kotlinx.coroutines.delay

private const val LAUNCH_DELAY_MILLIS = 1_500L

@Composable
fun LaunchScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }

    LaunchedEffect(Unit) {
        delay(LAUNCH_DELAY_MILLIS)
        navController.navigate(OnboardingRoute)
    }
}
