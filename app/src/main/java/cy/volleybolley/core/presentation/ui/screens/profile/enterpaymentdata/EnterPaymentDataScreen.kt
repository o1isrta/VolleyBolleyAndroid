package cy.volleybolley.core.presentation.ui.screens.profile.enterpaymentdata

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.screens.profile.enterpaymentdata.EnterPaymentDataScreenEffect.NavigateFromEnterPaymentDataScreen
import cy.volleybolley.core.presentation.ui.screens.profile.enterpaymentdata.EnterPaymentDataScreenEffect.ShowInfoDialog
import cy.volleybolley.core.presentation.ui.screens.profile.enterpaymentdata.EnterPaymentDataScreenEvent.AccountTextChanged
import cy.volleybolley.core.presentation.ui.screens.profile.enterpaymentdata.EnterPaymentDataScreenEvent.ClickOnBackFromEnterPaymentData
import cy.volleybolley.core.presentation.ui.screens.profile.enterpaymentdata.EnterPaymentDataScreenEvent.OnSaveButtonClick
import cy.volleybolley.core.presentation.ui.screens.profile.payments.model.BackPaymentsHolder
import cy.volleybolley.core.presentation.ui.screens.profile.enterpaymentdata.model.EnterPaymentDialogType
import cy.volleybolley.profile.domain.model.PaymentType

@Composable
fun EnterPaymentDataScreen(
    navController: NavHostController,
    viewModel: EnterPaymentDataScreenViewModel,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    EnterPaymentDataScreen(
        screenPaymentType = viewModel.originPaymentType,
        state = state,
        effect = effect,
        eventCallback = { event -> viewModel.obtainEvent(event) },
        navigateAction = { updatedPaymentJsonString ->
            updatedPaymentJsonString?.let {
                navController.previousBackStackEntry?.savedStateHandle?.set(BackPaymentsHolder.PAYMENTS_KEY, it)
                navController.popBackStack()
            } ?: navController.popBackStack()
        }
    )
}

@Composable
private fun EnterPaymentDataScreen(
    screenPaymentType: PaymentType,
    state: EnterPaymentDataScreenState,
    effect: EnterPaymentDataScreenEffect?,
    navigateAction: (String?) -> Unit,
    eventCallback: (EnterPaymentDataScreenEvent) -> Unit,
) {
    val scrollState = rememberScrollState()
    var dialogType: EnterPaymentDialogType? by remember { mutableStateOf(null) }
    val headerValue = screenPaymentType.getSimpleName()

    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = VolleyDimens.DIMEN_32,
        modifier = Modifier
            .fillMaxWidth()
            .padding(VolleyDimens.DIMEN_8.dp, 0.dp)
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_20.dp)
        ) {
            VolleySimpleComponent.TitleWithBackArrow(
                title = headerValue,
                modifier = Modifier.fillMaxWidth(),
                onBackClick = { eventCallback(ClickOnBackFromEnterPaymentData) }
            )

            Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))

            OutsideHint(screenPaymentType)

            Spacer(Modifier.height(VolleyDimens.DIMEN_8.dp))

            PaymentAccField(
                text = state.accountValue,
                paymentType = screenPaymentType,
                actionToTransferContent = { newText -> eventCallback(AccountTextChanged(newText)) }
            )

            Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))

            VolleyButton.ActiveButton(
                enabled = state.buttonEnabled,
                text = stringResource(R.string.save),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(VolleyDimens.DIMEN_44.dp)
            ) { eventCallback(OnSaveButtonClick) }
        }
    }

    LaunchedEffect(effect) {
        when (effect) {
            is NavigateFromEnterPaymentDataScreen -> navigateAction(effect.updatedPaymentsJsonString)
            is ShowInfoDialog -> {
                val definedDialog = EnterPaymentDialogType.SUCCESS.apply {
                    setDoneAction(effect.onDoneButtonClick)
                }
                dialogType = definedDialog
            }
            null -> {}
        }
    }

    dialogType?.let { type ->
        EnterPaymentDataDialog(
            text = type.dialogText,
            onDismiss = { dialogType = null },
            onDone = type.action
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
    val hint = when(paymentType) {
        PaymentType.REVOLUT -> stringResource(R.string.enter_payment_revolut_hint)
        PaymentType.CASH -> ""
        PaymentType.THAIBANK -> stringResource(R.string.enter_payment_thaibank_hint)
        PaymentType.UNKNOWN -> ""
    }

    val showPrefix = when(paymentType) {
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
    val text = when(paymentType) {
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
                .padding(VolleyDimens.DIMEN_8.dp),
            shape = RoundedCornerShape(VolleyDimens.DIMEN_32.dp),
            colors = cardColors(containerColor = VolleyColor.Turquoise)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(VolleyDimens.DIMEN_20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                VolleyText.BodyBold(
                    text = text,
                    textAlign = TextAlign.Center,
                    color = VolleyColor.White
                )

                Spacer(Modifier.height(VolleyDimens.DIMEN_12.dp))

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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewEnterPaymentDataScreen() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            val stateEmpty = EnterPaymentDataScreenState()
            val stateRevolut = EnterPaymentDataScreenState("ManManus", true)

            Column {
                EnterPaymentDataScreen(
                    screenPaymentType = PaymentType.REVOLUT,
                    state = stateEmpty,
                    effect = null,
                    eventCallback = {},
                    navigateAction = {}
                )
                Spacer(Modifier.height(30.dp))
                EnterPaymentDataScreen(
                    screenPaymentType = PaymentType.REVOLUT,
                    state = stateRevolut,
                    effect = null,
                    eventCallback = {},
                    navigateAction = {}
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewEnterPaymentDataScreen2() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            val stateEmpty = EnterPaymentDataScreenState()
            val stateThai = EnterPaymentDataScreenState("564 789 4544", true)

            Column {
                EnterPaymentDataScreen(
                    screenPaymentType = PaymentType.THAIBANK,
                    state = stateEmpty,
                    effect = null,
                    eventCallback = {},
                    navigateAction = {}
                )
                Spacer(Modifier.height(30.dp))
                EnterPaymentDataScreen(
                    screenPaymentType = PaymentType.THAIBANK,
                    state = stateThai,
                    effect = null,
                    eventCallback = {},
                    navigateAction = {}
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewEnterPaymentDataDialog() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            Column {
                EnterPaymentDataDialog(
                    text = stringResource(R.string.payment_changes_done),
                    onDismiss = {},
                    onDone = {}
                )
            }
        }
    }
}
