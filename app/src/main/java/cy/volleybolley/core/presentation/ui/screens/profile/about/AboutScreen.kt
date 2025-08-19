package cy.volleybolley.core.presentation.ui.screens.profile.about

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import org.koin.androidx.compose.koinViewModel

@Composable
fun AboutScreen(
    navController: NavHostController,
    viewModel: AboutScreenViewModel = koinViewModel(),
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    AboutScreen(
        state = state,
        effect = effect,
        navigateAction = { route ->
            route?.let {
                navController.navigate(it)
            } ?: navController.popBackStack()
        },
        eventCallback = { event -> viewModel.obtainEvent(event)}
    )
}

@Composable
private fun AboutScreen(
    state: AboutScreenState,
    effect: AboutScreenEffect?,
    navigateAction: (NavMap?) -> Unit,
    eventCallback: (AboutScreenEvent) -> Unit,
) {

}
