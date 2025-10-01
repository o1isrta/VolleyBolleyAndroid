package cy.volleybolley.notification.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyTypography
import cy.volleybolley.notification.presentation.ui.component.NotificationsScreenComponents.NotificationListContent
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotificationsScreen(
    navHostController: NavHostController,
    viewModel: NotificationsViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    val context = LocalContext.current

    LaunchedEffect(effect) {
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is NotificationsEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is NotificationsEffect.NavigateTo -> {
                    navHostController.navigate(effect.screen)
                }

                null -> {}
            }
        }
    }

    UiStateRenderer(
        isLoading = state.isLoading,
        error = state.error
    ) {
        NotificationListContent(
            notifications = state.notifications,
            navController = navHostController,
            onItemClick = { viewModel.obtainEvent(NotificationsEvent.OnNotificationClick(it)) }
        )
    }
}

@Composable
private fun NotificationsLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun NotificationsError(error: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = error,
            style = VolleyTypography.TitleLarge,
            color = VolleyColor.White
        )
    }
}

@Composable
private fun <T> UiStateRenderer(
    isLoading: Boolean,
    error: T?,
    onRenderError: @Composable (T) -> Unit = { NotificationsError(it.toString()) },
    content: @Composable () -> Unit
) {
    when {
        isLoading -> NotificationsLoading()
        error != null -> onRenderError(error)
        else -> content()
    }
}
