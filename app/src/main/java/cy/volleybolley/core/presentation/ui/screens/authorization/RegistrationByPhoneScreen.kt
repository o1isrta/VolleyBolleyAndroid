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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cy.volleybolley.core.presentation.ui.navigation.RegistrationRoute

@Composable
fun RegistrationByPhoneScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            navController.navigate(RegistrationRoute)
        }) {
            Text("Go to Registration Screen")
        }
    }
}

@Preview
@Composable
fun RegistrationByPhoneScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(8.dp))
        Button(onClick = {

        }) {
            Text("Go to Registration Screen")
        }
    }
}
