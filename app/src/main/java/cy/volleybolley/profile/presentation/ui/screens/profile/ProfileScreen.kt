package cy.volleybolley.profile.presentation.ui.screens.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
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
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.NavigateFromProfileScreen
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.ShowDeleteAccountDialog
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.ShowLogoutDialog
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnAboutClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnDeleteAccountClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnFaqClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnLogoutClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnPaymentsClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnPersonalDataClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnPlayersClick
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEvent.OnSupportClick
import cy.volleybolley.profile.presentation.ui.screens.profile.model.ProfileDialogType
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileScreenViewModel = koinViewModel(),
    paddingFromSystemUi: PaddingValues,
    finisher: () -> Unit,
) {
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    ProfileScreen(
        effect = effect,
        navigateAction = { route ->
            navController.navigate(route)
        },
        eventCallback = { event -> viewModel.obtainEvent(event) },
        modifier = Modifier.padding(paddingFromSystemUi)
    )

    BackHandler { finisher() }
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    effect: ProfileScreenEffect?,
    navigateAction: (NavMap) -> Unit,
    eventCallback: (ProfileScreenEvent) -> Unit,
) {
    var typeOfDialog: ProfileDialogType? by remember { mutableStateOf(null) }

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
                    painter = painterResource(R.drawable.ic_ball),
                    title = stringResource(R.string.profile_players_component),
                ) { eventCallback(OnPlayersClick) }

                ProfileComponentDivider()

                ProfileComponent(
                    painter = painterResource(R.drawable.ic_personal_data),
                    title = stringResource(R.string.profile_personal_data_component),
                ) { eventCallback(OnPersonalDataClick) }

                ProfileComponentDivider()

                ProfileComponent(
                    painter = painterResource(R.drawable.ic_payments),
                    title = stringResource(R.string.profile_payments_component),
                ) { eventCallback(OnPaymentsClick) }

                ProfileComponentDivider()

                ProfileComponent(
                    painter = painterResource(R.drawable.ic_support),
                    title = stringResource(R.string.profile_support_component),
                ) { eventCallback(OnSupportClick) }

                ProfileComponentDivider()

                ProfileComponent(
                    painter = painterResource(R.drawable.ic_faq),
                    title = stringResource(R.string.profile_faq_component),
                ) { eventCallback(OnFaqClick) }

                ProfileComponentDivider()

                ProfileComponent(
                    painter = painterResource(R.drawable.ic_about),
                    title = stringResource(R.string.profile_about_component),
                ) { eventCallback(OnAboutClick) }

                ProfileComponentDivider()

                ProfileComponent(
                    painter = painterResource(R.drawable.ic_logout),
                    title = stringResource(R.string.profile_logout_component),
                ) { eventCallback(OnLogoutClick) }
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
                color = VolleyColor.White,
                modifier = Modifier
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = { eventCallback(OnDeleteAccountClick) }
                    )
            )
        }
    }

    LaunchedEffect(effect) {
        when (effect) {
            is NavigateFromProfileScreen -> navigateAction(effect.route)

            is ShowLogoutDialog -> {
                val actualDialogType = ProfileDialogType.LOGOUT.apply {
                    setPositiveAction(effect.onPositiveButtonClick)
                }
                typeOfDialog = actualDialogType
            }

            is ShowDeleteAccountDialog -> {
                val actualDialogType = ProfileDialogType.DELETE.apply {
                    setPositiveAction(effect.onPositiveButtonClick)
                }
                typeOfDialog = actualDialogType
            }

            null -> Unit
        }
    }

    typeOfDialog?.let { dialogType ->
        ProfileDialog(
            text = dialogType.dialogText,
            onDismiss = { typeOfDialog = null },
            onConfirm = dialogType.action
        )
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
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = onComponentClick
            )
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = VolleyColor.White
        )

        VolleyText.BodyRegular(
            text = title,
            color = VolleyColor.White,
            maxLines = 1,
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = VolleyDimens.DIMEN_8.dp,
                    vertical = 0.dp
                )
        )
    }
}

@Composable
private fun ProfileComponentDivider() {
    VolleySimpleComponent.DividerLine(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, VolleyDimens.DIMEN_16.dp)
    )
}

@Composable
private fun ProfileDialog(
    text: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
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

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    VolleyButton.OutlinedActiveButton(
                        text = stringResource(R.string.yes),
                        onClick = onConfirm,
                        modifier = Modifier
                            .weight(1f)
                    )

                    Spacer(Modifier.width(VolleyDimens.DIMEN_8.dp))

                    VolleyButton.ActiveButton(
                        text = stringResource(R.string.no),
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun PreviewProfileScreen() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            ProfileScreen(
                effect = null,
                navigateAction = {}
            ) { }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewProfileDialog() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            ProfileDialog(
                text = stringResource(R.string.log_out_question),
                onDismiss = {},
                onConfirm = {},
            )
        }
    }
}
