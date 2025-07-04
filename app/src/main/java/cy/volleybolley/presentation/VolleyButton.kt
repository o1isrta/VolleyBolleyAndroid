package cy.volleybolley.presentation

import android.provider.DocumentsContract
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cy.volleybolley.Root

object VolleyButton {
    @Composable
    fun ActiveButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = false,
        text: String,
        isLoading: Boolean = false
    ) {
        Button(
            enabled = enabled,
            modifier = modifier,
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = VolleyColor.GreyDisabled,
                containerColor = VolleyColor.YellowPro,
                disabledContentColor = VolleyColor.White
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = VolleyColor.BlackText)
            } else {
                Text(
                    text = text.uppercase(),
                    color = if (enabled) {
                        VolleyColor.BlackText
                    } else {
                        VolleyColor.White
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewActiveButton() {
    Root {
        VolleyButton.ActiveButton(
            modifier = Modifier.padding(it).padding(horizontal = 24.dp).fillMaxWidth(),
            text = "Next step",
            onClick = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewLoadingActiveButton() {
    Root {
        VolleyButton.ActiveButton(
            modifier = Modifier.padding(it).padding(horizontal = 24.dp).fillMaxWidth(),
            text = "Next step",
            isLoading = true,
            onClick = {}
        )
    }
}
