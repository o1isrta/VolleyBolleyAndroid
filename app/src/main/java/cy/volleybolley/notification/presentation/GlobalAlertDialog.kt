package cy.volleybolley.notification.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText.BodyBold
import cy.volleybolley.core.presentation.ui.model.VolleyText.ButtonText
import cy.volleybolley.core.presentation.ui.model.VolleyText.TitleLarge
import cy.volleybolley.core.presentation.ui.model.state.data.DialogData
import cy.volleybolley.ui.theme.VolleybolleyTheme

@Composable
fun GlobalAlertDialog(
    dialog: DialogData,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = VolleyColor.Turquoise,
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                TitleLarge(
                    text = dialog.title,
                    color = VolleyColor.White
                )
            }
        },
        text = {
            BodyBold(
                text = dialog.message,
                color = VolleyColor.White
            )
        },
        confirmButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(VolleyDimens.DIMEN_065)
                    .clip(RoundedCornerShape(VolleyDimens.DIMEN_16))
                    .background(VolleyColor.YellowPro)
                    .clickable(onClick = onConfirm)
                    .padding(vertical = VolleyDimens.DIMEN_12.dp),
                contentAlignment = Alignment.Center
            ) {
                ButtonText(
                    text = "ENABLED",
                    color = VolleyColor.TextDark
                )
            }
        },
        dismissButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(VolleyDimens.DIMEN_032)
                    .clip(RoundedCornerShape(VolleyDimens.DIMEN_16))
                    .border(
                        VolleyDimens.DIMEN_1.dp,
                        VolleyColor.YellowPro,
                        RoundedCornerShape(VolleyDimens.DIMEN_16)
                    )
                    .clickable(onClick = onDismiss)
                    .padding(vertical = VolleyDimens.DIMEN_12.dp),
                contentAlignment = Alignment.Center
            ) {
                ButtonText(
                    text = "SKIP",
                    color = VolleyColor.White
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewGlobalAlertDialog() {
    val dummyDialog = DialogData(
        title = stringResource(R.string.notifications),
        message = stringResource(R.string.notifications_alert_dialog),
        onConfirm = {},
        onDismiss = {}
    )

    VolleybolleyTheme {
        GlobalAlertDialog(
            dialog = dummyDialog,
            onConfirm = {},
            onDismiss = {}
        )
    }
}
