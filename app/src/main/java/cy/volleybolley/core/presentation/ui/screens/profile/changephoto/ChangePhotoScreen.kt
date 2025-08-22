package cy.volleybolley.core.presentation.ui.screens.profile.changephoto

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChangePhotoScreen(
    avatarString: String? = null,
    navController: NavHostController,
    viewModel: ChangePhotoScreenViewModel = koinViewModel(),
    ) {
    Button(onClick = { navController.popBackStack() }) {
        Text("Назад")
    }
}
