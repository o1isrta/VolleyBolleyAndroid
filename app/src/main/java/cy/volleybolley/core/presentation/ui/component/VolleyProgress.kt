package cy.volleybolley.core.presentation.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens

object VolleyProgress {
    @Stable
    @Composable
    fun CircularProgress(modifier: Modifier = Modifier) {
        CircularProgressIndicator(
            modifier = modifier.size(VolleyDimens.DIMEN_40.dp),
            color = VolleyColor.YellowPro
        )
    }
}
