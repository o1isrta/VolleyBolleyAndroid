package cy.volleybolley.profile.presentation.ui.screens.profile

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
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
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyProgress
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.NavigateToAbout
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.NavigateToAuthorization
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.NavigateToFaq
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.NavigateToPayments
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.NavigateToPersonalData
import cy.volleybolley.profile.presentation.ui.screens.profile.ProfileScreenEffect.NavigateToPlayers
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    paddingFromSystemUi: PaddingValues,
    onNavigateToPlayers: () -> Unit,
    onNavigateToPersonalData: () -> Unit,
    onNavigateToPayments: () -> Unit,
    onNavigateToFaq: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToAuthorization: () -> Unit,
    viewModel: ProfileScreenViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            NavigateToPlayers -> onNavigateToPlayers()
            NavigateToPersonalData -> onNavigateToPersonalData()
            NavigateToPayments -> onNavigateToPayments()
            NavigateToFaq -> onNavigateToFaq()
            NavigateToAbout -> onNavigateToAbout()
            NavigateToAuthorization -> onNavigateToAuthorization()
            is ShowLogoutDialog -> {}
            is ShowDeleteAccountDialog -> {}
            null -> Unit
        }
    }

    ProfileScreen(
        state = state,
        effect = effect,
        eventCallback = { event -> viewModel.obtainEvent(event) },
        modifier = Modifier.padding(paddingFromSystemUi)
    )
}

@Stable
@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileScreenState,
    effect: ProfileScreenEffect?,
    eventCallback: (ProfileScreenEvent) -> Unit,
) {
    Box(modifier = modifier) {
        Column {
            VolleyContainersRootTransparent.TransparentContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
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
                    .padding(28.dp, 20.dp)
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

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(VolleyColor.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                VolleyProgress.CircularProgress()
            }
        }
    }

    if (effect is ShowLogoutDialog) {
        ProfileDialog(
            text = stringResource(R.string.log_out_question),
            onConfirm = effect.onPositiveButtonClick,
            onDismiss = effect.onNegativeButtonClick
        )
    }

    if (effect is ShowDeleteAccountDialog) {
        ProfileDialog(
            text = stringResource(R.string.delete_account_question),
            onConfirm = effect.onPositiveButtonClick,
            onDismiss = effect.onNegativeButtonClick
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
                    horizontal = 8.dp,
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
            .padding(0.dp, 16.dp)
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
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
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

                    Spacer(Modifier.width(8.dp))

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
                state = ProfileScreenState(),
                effect = null,
                eventCallback = {}
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_9_PRO)
@Composable
private fun PreviewProfileDialog() {
    RootContainerForPreview {
        ProfileDialog(
            text = stringResource(R.string.log_out_question),
            onDismiss = {},
            onConfirm = {},
        )
    }
}
