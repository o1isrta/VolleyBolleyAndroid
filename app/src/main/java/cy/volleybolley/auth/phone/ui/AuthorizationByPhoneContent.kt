package cy.volleybolley.auth.phone.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.auth.phone.ui.VerifyCodeBlock
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar
import cy.volleybolley.auth.phone.ui.presentation.model.AuthorizationByPhoneEvent
import cy.volleybolley.auth.phone.ui.presentation.model.AuthorizationByPhoneState

@Stable
@Composable
fun AuthorizationByPhoneContent(
    state: AuthorizationByPhoneState,
    paddingFromSystemUI: PaddingValues,
    onBackNavigationRequested: () -> Unit,
    eventCallback: (AuthorizationByPhoneEvent) -> Unit,
) {
    VolleyContainersRootTransparent.TransparentContainer(
        modifier = Modifier
            .padding(
                top = paddingFromSystemUI.calculateTopPadding() + 8.dp,
                start = 8.dp,
                end = 8.dp,
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {

            VolleyTopBar.TopBarWithBackButton(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.registration),
                onBackNavigationRequested = onBackNavigationRequested
            )

            when (state.step) {
                AuthorizationByPhoneState.Step.ENTER_PHONE -> PhoneInputBlock(state, eventCallback)

                AuthorizationByPhoneState.Step.VERIFY_CODE -> VerifyCodeBlock(state, eventCallback)
            }
        }
    }
}
