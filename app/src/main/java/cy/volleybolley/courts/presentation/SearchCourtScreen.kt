package cy.volleybolley.courts.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import cy.volleybolley.core.presentation.ui.screens.courts.CourtsComponents.CourtMapListSwitcherScreen
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchCourtScreen(
    navHostController: NavHostController,
    viewModel: SearchCourtViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var isMapSelected by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is SearchCourtEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is SearchCourtEffect.NavigateToGameCreation -> {
                    navHostController.navigate("create_game") // Дальнейшее создание игры
                }

                is SearchCourtEffect.NavigateBack -> {
                    navHostController.popBackStack()
                }

                null -> {}
            }
        }
        viewModel.obtainEvent(SearchCourtEvent.LoadSearchCourt)
    }

    CourtListContent(
        isMapSelected = isMapSelected,
        onTabSelected = { isMapSelected = it },
        state = state,
        onEvent = { event -> viewModel.obtainEvent(event) }
    )
}

@Composable
private fun CourtListContent(
    isMapSelected: Boolean,
    onTabSelected: (Boolean) -> Unit,
    state: SearchCourtState,
    onEvent: (SearchCourtEvent) -> Unit,
) {
    CourtMapListSwitcherScreen(
        state = state,
        onEvent = onEvent,
        isMapSelected = isMapSelected,
        onTabSelected = onTabSelected,
        modifier = Modifier.fillMaxSize()
    )
}
