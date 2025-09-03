package cy.volleybolley.core.presentation.ui.screens.profile.playerprofile

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

@Composable
fun PlayerProfileScreen(
    navController: NavHostController,
    viewModel: PlayerProfileScreenViewModel,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

}

@Composable
private fun PlayerProfileScreen(
    state: PlayerProfileScreenState,
    effect: PlayerProfileScreenEffect?,
    navigateAction: (Int?) -> Unit,
    eventCallback: (PlayerProfileScreenEvent) -> Unit,
) {

}
