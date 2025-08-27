package cy.volleybolley.core.presentation.ui.screens.authorization

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.LaunchRoute
import cy.volleybolley.core.presentation.ui.navigation.OnboardingRoute
import kotlinx.coroutines.delay

private const val LAUNCH_DELAY_MILLIS = 3_000L

@Composable
fun LaunchScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_launch), // твой png фон
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            VolleyText.LogoDisplay(
                text = stringResource(id = R.string.volleybolley),
                color = VolleyColor.White
            )
        }
    }

    // Через 3 сек переход дальше и удаление Launch из backstack
    LaunchedEffect(Unit) {
        delay(LAUNCH_DELAY_MILLIS)
        navController.navigate(OnboardingRoute) {
            popUpTo(LaunchRoute) { inclusive = true }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LaunchScreenPreview() {
    LaunchScreen(navController = rememberNavController())
}
