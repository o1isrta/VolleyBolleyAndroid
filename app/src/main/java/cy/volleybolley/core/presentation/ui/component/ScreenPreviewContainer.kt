package cy.volleybolley.core.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cy.volleybolley.core.presentation.ui.model.VolleyColor

@Composable
fun ScreenPreviewContainer(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = VolleyColor.TurquoiseDark),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
