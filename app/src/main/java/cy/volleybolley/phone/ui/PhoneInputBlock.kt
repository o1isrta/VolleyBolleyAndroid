package cy.volleybolley.phone.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.phone.ui.presentation.model.AuthorizationByPhoneEvent
import cy.volleybolley.phone.ui.presentation.model.AuthorizationByPhoneState
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient
import cy.volleybolley.core.presentation.ui.component.VolleyButton

@Composable
fun PhoneInputBlock(
    state: AuthorizationByPhoneState,
    eventCallback: (AuthorizationByPhoneEvent) -> Unit
) {
    var phoneInput by rememberSaveable { mutableStateOf(state.phoneNumber) }

    LaunchedEffect(state.phoneNumber) {
        if (phoneInput != state.phoneNumber) {
            phoneInput = state.phoneNumber
        }
    }

    VolleyTextFieldGradient.PhoneTextField(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        text = phoneInput,
        alertMessage = if (state.isPhoneNumberInputError) {
            stringResource(R.string.valid_phone_number)
        } else {
            ""
        },
        actionToTransferContent = { newValue ->
            phoneInput = newValue
            eventCallback(AuthorizationByPhoneEvent.TypePhone(newValue))
        }
    )

    VolleyButton.ActiveButton(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        enabled = state.isPhoneNumberValid && !state.isLoading,
        text = stringResource(R.string.send_code),
        onClick = {
            eventCallback(AuthorizationByPhoneEvent.SendCodeClicked)
        }
    )
}
