package cy.volleybolley.profile.presentation.ui.screens.enterpaymentdata

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.RootContainerForPreview
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.profile.domain.model.PaymentType
import cy.volleybolley.profile.presentation.ui.screens.enterpaymentdata.EnterPaymentDataScreenEffect.NavigateFromEnterPaymentDataScreen
import cy.volleybolley.profile.presentation.ui.screens.enterpaymentdata.EnterPaymentDataScreenEffect.ShowInfoDialog
import cy.volleybolley.profile.presentation.ui.screens.enterpaymentdata.EnterPaymentDataScreenEvent.AccountTextChanged
import cy.volleybolley.profile.presentation.ui.screens.enterpaymentdata.EnterPaymentDataScreenEvent.ClickOnBackFromEnterPaymentData
import cy.volleybolley.profile.presentation.ui.screens.enterpaymentdata.EnterPaymentDataScreenEvent.OnSaveButtonClick
import cy.volleybolley.profile.presentation.ui.screens.payments.model.BackPaymentsHolder

@Composable
fun EnterPaymentDataScreen(
    onNavigateBack: (String?) -> Unit,
    viewModel: EnterPaymentDataScreenViewModel,
    paddingFromSystemUi: PaddingValues,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            is EnterPaymentDataScreenEffect.NavigateFromEnterPaymentDataScreen -> {
                onNavigateBack(currentEffect.updatedPaymentsJsonString)
            }

            is EnterPaymentDataScreenEffect.ShowInfoDialog -> {}
            null -> {}
        }
    }

    EnterPaymentDataScreen(
        screenPaymentType = viewModel.originPaymentType,
        state = state,
        showDialog = effect is EnterPaymentDataScreenEffect.ShowInfoDialog,
        dialogEffect = effect as? EnterPaymentDataScreenEffect.ShowInfoDialog,
        eventCallback = { event -> viewModel.obtainEvent(event) },
        modifier = Modifier.padding(paddingFromSystemUi)
    )
}

@Composable
private fun EnterPaymentDataScreen(
    modifier: Modifier = Modifier,
    screenPaymentType: PaymentType,
    state: EnterPaymentDataScreenState,
    showDialog: Boolean,
    dialogEffect: EnterPaymentDataScreenEffect.ShowInfoDialog?,
    eventCallback: (EnterPaymentDataScreenEvent) -> Unit,
) {
    val headerValue = stringResource(screenPaymentType.getSimpleName())

    VolleyContainersRootTransparent.TransparentContainer(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            VolleySimpleComponent.TitleWithBackArrow(
                title = headerValue,
                modifier = Modifier.fillMaxWidth(),
                onBackClick = { eventCallback(ClickOnBackFromEnterPaymentData) }
            )
            Spacer(Modifier.height(8.dp))

            Column(Modifier.verticalScroll(rememberScrollState())) {
                Spacer(Modifier.height(8.dp))

                OutsideHint(screenPaymentType)

                Spacer(Modifier.height(8.dp))

                PaymentAccField(
                    text = state.accountValue,
                    paymentType = screenPaymentType,
                    actionToTransferContent = { newText -> eventCallback(AccountTextChanged(newText)) }
                )

                Spacer(Modifier.height(16.dp))

                VolleyButton.ActiveButton(
                    enabled = state.buttonEnabled,
                    text = stringResource(R.string.save),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) { eventCallback(OnSaveButtonClick) }
            }
        }
    }

    if (showDialog && dialogEffect != null) {
        EnterPaymentDataDialog(
            text = stringResource(R.string.payment_changes_done),
            onDone = dialogEffect.onDoneButtonClick,
            onDismiss = dialogEffect.onDismissClick
        )
    }
}

@Composable
private fun PaymentAccField(
    modifier: Modifier = Modifier,
    text: String,
    paymentType: PaymentType,
    actionToTransferContent: (String) -> Unit,
) {
    val hint = when (paymentType) {
        PaymentType.REVOLUT -> stringResource(R.string.enter_payment_revolut_hint)
        PaymentType.CASH -> ""
        PaymentType.THAIBANK -> stringResource(R.string.enter_payment_thaibank_hint)
        PaymentType.UNKNOWN -> ""
    }

    val showPrefix = when (paymentType) {
        PaymentType.REVOLUT -> true
        PaymentType.CASH -> false
        PaymentType.THAIBANK -> false
        PaymentType.UNKNOWN -> false
    }

    VolleyTextFieldGradient.PaymentAccountTextField(
        modifier = modifier,
        text = text,
        hint = hint,
        showPrefix = showPrefix,
        actionToTransferContent = actionToTransferContent
    )
}

@Stable
@Composable
private fun OutsideHint(
    paymentType: PaymentType,
) {
    val text = when (paymentType) {
        PaymentType.REVOLUT -> stringResource(R.string.user_name)
        PaymentType.CASH -> ""
        PaymentType.THAIBANK -> stringResource(R.string.enter_account_id)
        PaymentType.UNKNOWN -> ""
    }

    VolleyText.BodyRegular(
        text = text,
        color = VolleyColor.White,
        maxLines = 1,
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun EnterPaymentDataDialog(
    text: String,
    onDismiss: () -> Unit,
    onDone: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(true, true, false)
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp),
            shape = RoundedCornerShape(32.dp),
            colors = cardColors(containerColor = VolleyColor.Turquoise)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                VolleyText.BodyBold(
                    text = text,
                    textAlign = TextAlign.Center,
                    color = VolleyColor.White
                )

                Spacer(Modifier.height(12.dp))

                VolleyButton.ActiveButton(
                    text = stringResource(R.string.done),
                    onClick = {
                        onDismiss()
                        onDone()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_9_PRO)
@Composable
private fun PreviewEnterPaymentDataScreen() {
    RootContainerForPreview {
        val stateEmpty = EnterPaymentDataScreenState()
        val stateRevolut = EnterPaymentDataScreenState("ManManus", true)

        Column {
            EnterPaymentDataScreen(
                screenPaymentType = PaymentType.REVOLUT,
                state = stateEmpty,
                showDialog = false,
                dialogEffect = null,
                eventCallback = {}
            )
            Spacer(Modifier.height(30.dp))
            EnterPaymentDataScreen(
                screenPaymentType = PaymentType.REVOLUT,
                state = stateRevolut,
                showDialog = false,
                dialogEffect = null,
                eventCallback = {}
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_9_PRO)
@Composable
private fun PreviewEnterPaymentDataScreen2() {
    RootContainerForPreview {
        val stateEmpty = EnterPaymentDataScreenState()
        val stateThai = EnterPaymentDataScreenState("564 789 4544", true)

        Column {
            EnterPaymentDataScreen(
                screenPaymentType = PaymentType.THAIBANK,
                state = stateEmpty,
                showDialog = false,
                dialogEffect = null,
                eventCallback = {}
            )
            Spacer(Modifier.height(30.dp))
            EnterPaymentDataScreen(
                screenPaymentType = PaymentType.THAIBANK,
                state = stateThai,
                showDialog = false,
                dialogEffect = null,
                eventCallback = {}
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_9_PRO)
@Composable
private fun PreviewEnterPaymentDataDialog() {
    RootContainerForPreview {
        Column {
            EnterPaymentDataDialog(
                text = stringResource(R.string.payment_changes_done),
                onDismiss = {},
                onDone = {}
            )
        }
    }
}
