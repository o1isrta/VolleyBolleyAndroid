package cy.volleybolley.core.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.component.VolleyTitleBar.VolleyArrowBackTitleBar
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyTypography

object VolleyTitleBar {
    @Composable
    fun VolleyArrowBackTitleBar(title: String, modifier: Modifier = Modifier, onBackClick: () -> Unit) {
        Box(
            modifier = modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBackClick) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back"
                    )
                }
            }

            Text(
                text = title,
                color = Color.White,
                style = VolleyTypography.TitleLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewVolleyArrowBackTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(VolleyColor.TurquoiseDark),
    ) {
        MaterialTheme {
            VolleyArrowBackTitleBar(
                title = stringResource(R.string.notifications),
                onBackClick = {}
            )
        }
    }
}
