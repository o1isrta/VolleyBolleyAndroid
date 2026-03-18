package cy.volleybolley.auth.phone.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.auth.phone.ui.model.AuthorizationByPhoneEvent
import cy.volleybolley.auth.phone.ui.model.AuthorizationByPhoneState

@Composable
fun PhoneInputBlock(
    state: AuthorizationByPhoneState,
    eventCallback: (AuthorizationByPhoneEvent) -> Unit
) {
    VolleyTextFieldGradient.PhoneTextField(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        text = state.phoneNumber,
        alertMessage = if (state.isPhoneNumberInputError) {
            stringResource(R.string.valid_phone_number)
        } else {
            ""
        },
        actionToTransferContent = { newValue ->
            eventCallback(AuthorizationByPhoneEvent.TypePhone(newValue))
        })

    VolleyButton.ActiveButton(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        enabled = state.isPhoneNumberValid,
        isLoading = state.isLoading && state.step == AuthorizationByPhoneState.Step.ENTER_PHONE,
        text = stringResource(R.string.send_code),
        onClick = {
            eventCallback(AuthorizationByPhoneEvent.SendCodeClicked)
        }
    )
}
