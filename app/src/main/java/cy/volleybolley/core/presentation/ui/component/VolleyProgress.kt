package cy.volleybolley.core.presentation.ui.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import cy.volleybolley.core.presentation.ui.model.VolleyColor

object VolleyProgress {
    @Stable
    @Composable
    fun CircularProgress(modifier: Modifier = Modifier) {
        CircularProgressIndicator(modifier = modifier, color = VolleyColor.YellowPro)
    }
}
