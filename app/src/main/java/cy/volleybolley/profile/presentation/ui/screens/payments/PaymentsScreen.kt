package cy.volleybolley.profile.presentation.ui.screens.payments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.profile.domain.model.Payment
import cy.volleybolley.profile.domain.model.PaymentType
import cy.volleybolley.profile.presentation.ui.screens.payments.PaymentsScreenEffect.NavigateFromPaymentsScreen
import cy.volleybolley.profile.presentation.ui.screens.payments.PaymentsScreenEvent.ClickOnBackFromPayments
import cy.volleybolley.profile.presentation.ui.screens.payments.PaymentsScreenEvent.ClickOnPaymentsItem
import cy.volleybolley.profile.presentation.ui.screens.payments.PaymentsScreenEvent.ClickOnPaymentsItemCheckBox

@Composable
fun PaymentsScreen(
    navController: NavHostController,
    viewModel: PaymentsScreenViewModel,
    paddingFromSystemUi: PaddingValues,
) {
    viewModel.handleBackPayments()
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    PaymentsScreen(
        state = state,
        effect = effect,
        navigateAction = { route ->
            route?.let {
                navController.navigate(it)
            } ?: navController.popBackStack()
        },
        eventCallback = { event -> viewModel.obtainEvent(event) },
        modifier = Modifier.padding(paddingFromSystemUi)
    )
}

@Composable
private fun PaymentsScreen(
    modifier: Modifier = Modifier,
    state: PaymentsScreenState,
    effect: PaymentsScreenEffect?,
    navigateAction: (NavMap?) -> Unit,
    eventCallback: (PaymentsScreenEvent) -> Unit,
) {
    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = VolleyDimens.DIMEN_32,
        modifier = modifier
            .fillMaxWidth()
            .padding(VolleyDimens.DIMEN_8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_20.dp)
        ) {
            VolleySimpleComponent.TitleWithBackArrow(
                title = stringResource(R.string.payments),
                modifier = Modifier.fillMaxWidth(),
                onBackClick = { eventCallback(ClickOnBackFromPayments) }
            )

            Spacer(Modifier.height(VolleyDimens.DIMEN_8.dp))

            Column(Modifier.verticalScroll(rememberScrollState())) {
                Spacer(Modifier.height(VolleyDimens.DIMEN_8.dp))

                GetPaymentItemByType(
                    payments = state.payments,
                    itemType = PaymentType.THAIBANK,
                    onTitleClick = { eventCallback(ClickOnPaymentsItem(it)) },
                    onCheckBoxClick = { eventCallback(ClickOnPaymentsItemCheckBox(it)) }
                )

                PaymentsDivider()

                GetPaymentItemByType(
                    payments = state.payments,
                    itemType = PaymentType.CASH,
                    onTitleClick = { eventCallback(ClickOnPaymentsItem(it)) },
                    onCheckBoxClick = { eventCallback(ClickOnPaymentsItemCheckBox(it)) }
                )

                PaymentsDivider()

                GetPaymentItemByType(
                    payments = state.payments,
                    itemType = PaymentType.REVOLUT,
                    onTitleClick = { eventCallback(ClickOnPaymentsItem(it)) },
                    onCheckBoxClick = { eventCallback(ClickOnPaymentsItemCheckBox(it)) }
                )
            }
        }
    }

    LaunchedEffect(effect) {
        when (effect) {
            is NavigateFromPaymentsScreen -> navigateAction(effect.route)
            null -> {}
        }
    }
}

@Composable
private fun GetPaymentItemByType(
    payments: List<Payment>,
    itemType: PaymentType,
    onTitleClick: (PaymentType) -> Unit,
    onCheckBoxClick: (PaymentType) -> Unit,
) {
    payments.find { it.type == itemType }?.let { payment ->
        PaymentsComponent(
            checkBoxEnabled = if (itemType == PaymentType.CASH) true else payment.account.isNotEmpty(),
            checkBoxIsChecked = payment.isPreferred,
            title = stringResource(itemType.getSimpleName()),
            onTitleClick = { onTitleClick(itemType) },
            onCheckBoxClick = { onCheckBoxClick(itemType) }

        )
    } ?: PaymentsComponent(
        checkBoxEnabled = false,
        checkBoxIsChecked = false,
        title = stringResource(itemType.getSimpleName()),
        onTitleClick = { onTitleClick(itemType) },
        onCheckBoxClick = {}
    )
}

@Stable
@Composable
private fun PaymentsComponent(
    checkBoxEnabled: Boolean,
    checkBoxIsChecked: Boolean,
    title: String,
    onTitleClick: () -> Unit,
    onCheckBoxClick: () -> Unit,
) {
    val painter = painterResource(
        if (checkBoxIsChecked) R.drawable.ic_payment_checkbox_fill else R.drawable.ic_payment_checkbox_empty
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        VolleyText.BodyRegular(
            text = title,
            color = VolleyColor.White,
            maxLines = 1,
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = VolleyDimens.DIMEN_0.dp,
                    top = VolleyDimens.DIMEN_0.dp,
                    end = VolleyDimens.DIMEN_8.dp,
                    bottom = VolleyDimens.DIMEN_0.dp
                )
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = onTitleClick
                )
        )

        Image(
            painter = painter,
            alpha = if (checkBoxEnabled) 1f else 0.25f,
            contentDescription = null,
            modifier = Modifier
                .clickable(
                    enabled = checkBoxEnabled,
                    interactionSource = null,
                    indication = null,
                    onClick = onCheckBoxClick
                )
        )
    }
}

@Composable
private fun PaymentsDivider() {
    VolleySimpleComponent.DividerLine(
        modifier = Modifier
            .fillMaxWidth()
            .padding(VolleyDimens.DIMEN_0.dp, VolleyDimens.DIMEN_14.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewPaymentsScreen() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            val state = PaymentsScreenState()
            PaymentsScreen(
                state = state,
                effect = null,
                navigateAction = {},
                eventCallback = {},
            )
        }
    }
}
