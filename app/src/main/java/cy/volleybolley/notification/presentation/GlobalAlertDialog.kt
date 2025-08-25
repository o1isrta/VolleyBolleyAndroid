package cy.volleybolley.notification.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.DialogBlackout)
                .clickable(enabled = true, onClick = {}),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = VolleyDimens.DIMEN_8.dp)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(VolleyDimens.DIMEN_24.dp))
                    .background(VolleyColor.Turquoise)
                    .padding(VolleyDimens.DIMEN_20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleLarge(
                    text = dialog.title,
                    color = VolleyColor.White,
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

                BodyBold(
                    text = dialog.message,
                    color = VolleyColor.White,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_28.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(VolleyDimens.DIMEN_16.dp))
                            .border(
                                width = VolleyDimens.DIMEN_1.dp,
                                color = VolleyColor.YellowPro,
                                shape = RoundedCornerShape(VolleyDimens.DIMEN_16.dp)
                            )
                            .clickable(onClick = onDismiss)
                            .padding(vertical = VolleyDimens.DIMEN_12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ButtonText(
                            text = stringResource(R.string.skip),
                            color = VolleyColor.White
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(2f)
                            .clip(RoundedCornerShape(VolleyDimens.DIMEN_16.dp))
                            .background(VolleyColor.YellowPro)
                            .clickable(onClick = onConfirm)
                            .padding(vertical = VolleyDimens.DIMEN_12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ButtonText(
                            text = stringResource(R.string.enabled),
                            color = VolleyColor.TextDark
                        )
                    }
                }
            }
        }
    }
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
