package cy.volleybolley.core.presentation.ui.screens.findagame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.TransparentContainer
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.ui.theme.VolleybolleyTheme

@Composable
fun JoinTheGameScreen(navController: NavHostController) {
    Button(onClick = { navController.popBackStack() }) {
        Text("Назад")
    }
}

@Composable
private fun JoinTheGameScreen() {
    TransparentContainer() {

    }

}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=375dp,height=812dp"
)
@Composable
private fun JoinTheGameScreenPreview() {
    VolleybolleyTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            JoinTheGameScreen()
        }
    }
}

