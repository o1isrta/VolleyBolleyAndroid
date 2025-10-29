package cy.volleybolley.courts.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cy.volleybolley.core.presentation.ui.component.VolleyProgress.CircularProgress
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText

@Composable
fun <T> UiStateRenderer(
    isLoading: Boolean,
    error: T?,
    onRenderError: @Composable (T) -> Unit = { ErrorState(it.toString()) },
    content: @Composable () -> Unit
) {
    when {
        isLoading -> LoadingState()
        error != null -> onRenderError(error)
        else -> content()
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgress()
    }
}

@Composable
private fun ErrorState(error: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        VolleyText.TitleLarge(text = error, color = VolleyColor.White)
    }
}
