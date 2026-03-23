package cy.volleybolley.core.presentation.ui.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.tappableElement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@Stable
fun Modifier.safeTopPadding(extraTopPadding: Dp = 0.dp) = padding(
    top = WindowInsets.tappableElement.asPaddingValues().calculateTopPadding() + extraTopPadding
)
