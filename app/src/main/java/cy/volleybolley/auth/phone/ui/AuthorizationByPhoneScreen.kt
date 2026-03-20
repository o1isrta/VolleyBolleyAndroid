package cy.volleybolley.auth.phone.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.auth.phone.ui.model.AuthorizationByPhoneEffect
import cy.volleybolley.auth.phone.ui.model.AuthorizationByPhoneEvent
import cy.volleybolley.auth.phone.ui.model.AuthorizationByPhoneState
import cy.volleybolley.core.presentation.ui.util.safeTopPadding
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthorizationByPhoneScreen(
    viewModel: AuthorizationByPhoneViewModel = koinViewModel(),
    paddingFromSystemUi: PaddingValues,
    onBackNavigationRequested: () -> Unit,
    onNavigateToRegistration: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    val context = LocalContext.current

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (val action = effect) {
            is AuthorizationByPhoneEffect.NavigateToRegistration -> onNavigateToRegistration()
            is AuthorizationByPhoneEffect.NavigateHome -> onNavigateToHome()
            is AuthorizationByPhoneEffect.ShowError -> {
                Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
            }
            null -> Unit
        }
    }

    AuthorizationByPhoneScreen(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        onBackNavigationRequested = onBackNavigationRequested,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Composable
fun AuthorizationByPhoneScreen(
    state: AuthorizationByPhoneState,
    paddingFromSystemUi: PaddingValues,
    onBackNavigationRequested: () -> Unit,
    eventCallback: (AuthorizationByPhoneEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .safeTopPadding(extraTopPadding = paddingFromSystemUi.calculateTopPadding() + 8.dp)
            .fillMaxSize()
    ) {
        AuthorizationByPhoneContent(
            state = state,
            onBackNavigationRequested = onBackNavigationRequested,
            eventCallback = eventCallback,
        )
    }
}
