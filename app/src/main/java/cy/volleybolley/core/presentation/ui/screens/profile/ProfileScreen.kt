package cy.volleybolley.core.presentation.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.screens.profile.effect.ProfileScreenEffect
import cy.volleybolley.core.presentation.ui.screens.profile.event.ProfileScreenEvent
import cy.volleybolley.core.presentation.ui.screens.profile.state.ProfileScreenState

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    Button(onClick = { navController.popBackStack() }) {
        Text("Назад")
    }
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileScreenState,
    effect: ProfileScreenEffect?,
    eventCallback: (ProfileScreenEvent) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = VolleyDimens.DIMEN_32,
            modifier = Modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(VolleyDimens.DIMEN_20.dp)
            ) {
                ProfileComponent(
                    painter = painterResource(R.drawable.ic_players),
                    title = stringResource(R.string.profile_players_component),
                ) { }

                ComponentDivider()

                ProfileComponent(
                    painter = painterResource(R.drawable.ic_personal_data),
                    title = stringResource(R.string.profile_personal_data_component),
                ) { }

                ComponentDivider()

                ProfileComponent(
                    painter = painterResource(R.drawable.ic_payments),
                    title = stringResource(R.string.profile_payments_component),
                ) { }

                ComponentDivider()

                ProfileComponent(
                    painter = painterResource(R.drawable.ic_support),
                    title = stringResource(R.string.profile_support_component),
                ) { }

                ComponentDivider()

                ProfileComponent(
                    painter = painterResource(R.drawable.ic_faq),
                    title = stringResource(R.string.profile_faq_component),
                ) { }

                ComponentDivider()

                ProfileComponent(
                    painter = painterResource(R.drawable.ic_about),
                    title = stringResource(R.string.profile_about_component),
                ) { }

                ComponentDivider()

                ProfileComponent(
                    painter = painterResource(R.drawable.ic_logout),
                    title = stringResource(R.string.profile_logout_component),
                ) { }
            }
        }

        Box(
            contentAlignment = Alignment.BottomStart,
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_28.dp, VolleyDimens.DIMEN_20.dp)
                .weight(1f)
        ) {
            VolleyText.BodyLight(
                text = stringResource(R.string.delete_account),
                modifier = Modifier
                    .clickable {}
            )
        }
    }
}

@Stable
@Composable
private fun ProfileComponent(
    painter: Painter,
    title: String,
    onComponentClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onComponentClick() }
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
        )

        VolleyText.BodyRegular(
            text = title,
            modifier = Modifier.padding(
                horizontal = VolleyDimens.DIMEN_8.dp,
                vertical = 0.dp
            )
        )
    }
}

@Composable
private fun ComponentDivider() {
    Spacer(
        modifier = Modifier
            .padding(0.dp, VolleyDimens.DIMEN_16.dp)
            .height(1.dp)
            .background(VolleyColor.ProfileDivider)
    )
}
