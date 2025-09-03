package cy.volleybolley.core.presentation.ui.screens.profile.playerprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.exceptions.domerrors.NamespaceError
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.core.presentation.ui.screens.profile.playerprofile.PlayerProfileScreenEffect.NavigateFromPlayerDetailScreen
import cy.volleybolley.core.presentation.ui.screens.profile.playerprofile.PlayerProfileScreenEvent.ClickOnBackFromPlayerDetails
import cy.volleybolley.core.presentation.ui.screens.profile.playerprofile.model.PlayerActivityTemp
import cy.volleybolley.core.presentation.ui.screens.profile.playerprofile.model.PlayerDetailTemp
import cy.volleybolley.courts.domain.model.Location

@Composable
fun PlayerProfileScreen(
    navController: NavHostController,
    viewModel: PlayerProfileScreenViewModel,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

}

@Composable
private fun PlayerProfileScreen(
    state: PlayerProfileScreenState,
    effect: PlayerProfileScreenEffect?,
    userHoursOffset: Int,
    navigateAction: (Int?) -> Unit,
    eventCallback: (PlayerProfileScreenEvent) -> Unit,
) {
    val scrollState = rememberScrollState()

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
                title = "${state.playerDetail.firstName} ${state.playerDetail.lastName}",
                modifier = Modifier.fillMaxWidth(),
                onBackClick = { eventCallback(ClickOnBackFromPlayerDetails) }
            )

            Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                VolleyAvatar.CircularAvatar(
                    avatar = state.playerDetail.avatarUrl,
                    size = VolleyDimens.DIMEN_100.dp
                )

                Spacer(Modifier.height(VolleyDimens.DIMEN_4.dp))

                VolleyText.BodyBoldGradient(
                    text = state.playerDetail.level,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                )
            }

            Spacer(Modifier.height(VolleyDimens.DIMEN_12.dp))

            if (state.playerDetail.latestActivity.isNotEmpty()) {
                VolleyText.BodyBold(
                    text = stringResource(R.string.latest_activity),
                    color = VolleyColor.White,
                    maxLines = 1
                )
                Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))
                Column {
                    val countOfActivities = state.playerDetail.latestActivity.size
                    state.playerDetail.latestActivity.forEachIndexed { index, activity ->
                        PlayerActivityItem(
                            locationName = activity.courtLocation.locationName,
                            courtName = activity.courtLocation.courtName,
                            dateStamp = activity.eventTimestamp,
                            userHoursOffset = userHoursOffset,
                            onMapClick = {}
                        )

                        if (index < countOfActivities - 1) {
                            Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))
                        }
                    }
                }
            } else {
                VolleyText.BodyBold(
                    text = stringResource(R.string.player_profile_no_activity),
                    color = VolleyColor.White,
                    maxLines = 1
                )
            }

            Spacer(Modifier.height(VolleyDimens.DIMEN_12.dp))

            FavoriteManagementButton(
                isFavorite = state.playerDetail.isFavorite,
                onClick = {}
            )
        }
    }

    LaunchedEffect(effect) {
        when(effect) {
            is NavigateFromPlayerDetailScreen -> navigateAction(effect.playerIdWithChangedFavoriteStatus)
            null -> {}
        }
    }
}

@Composable
private fun PlayerActivityItem(
    locationName: String,
    courtName: String,
    dateStamp: String,
    userHoursOffset: Int,
    onMapClick: () -> Unit,
) {
    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = VolleyDimens.DIMEN_16,
    ) {
        Column(
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_16.dp, VolleyDimens.DIMEN_8.dp)
        ) {
            VolleyText.BodyRegular(
                text = VolleyUiUtil.parseTimeStringToActivityDateString(
                    utcString = dateStamp,
                    hoursOffset = userHoursOffset,
                ),
                color = VolleyColor.White,
                maxLines = 1
            )
            Spacer(Modifier.height(VolleyDimens.DIMEN_8.dp))
            LocationSection(
                locationName = locationName,
                courtName = courtName,
                onMapClick = onMapClick
            )
        }
    }
}

@Composable
private fun LocationSection(
    locationName: String,
    courtName: String,
    onMapClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_pointer_map),
            contentDescription = null,
            tint = VolleyColor.OrangeHard
        )

        Spacer(Modifier.width(VolleyDimens.DIMEN_8.dp))

        Column(Modifier.weight(1f)) {
            VolleyText.BodyBold(
                text = courtName,
                color = VolleyColor.White,
                textAlign = TextAlign.Start,
                maxLines = 1
            )
            VolleyText.BodyLight(
                text = locationName,
                color = VolleyColor.White,
                textAlign = TextAlign.Start,
                maxLines = 2
            )
        }

        Spacer(Modifier.width(VolleyDimens.DIMEN_8.dp))

        VolleyButton.ActiveButtonMap(
            text = stringResource(R.string.map),
            onClick = onMapClick
        )
    }
}

@Composable
private fun FavoriteManagementButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onClick: () -> Unit,
) {
    val gradientBrush = remember {
        Brush.verticalGradient(
            colors = listOf(
                VolleyColor.YellowForGradient,
                VolleyColor.GreenForGradient
            )
        )
    }

    val buttonText = stringResource(
        if (isFavorite) R.string.unfavorite else R.string.add_to_favorites
    ).uppercase()
    val backgroundColor = if (isFavorite) Color.Transparent else VolleyColor.YellowPro
    val textColor = if (isFavorite) VolleyColor.White else VolleyColor.TextDark
    val shape = RoundedCornerShape(VolleyDimens.DIMEN_16.dp)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(VolleyDimens.DIMEN_44.dp)
            .background(
                color = backgroundColor,
                shape = shape
            )
            .border(
                width = VolleyDimens.DIMEN_1.dp,
                brush = gradientBrush,
                shape = shape
            )
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = onClick
            )
    ) {
        VolleyText.ButtonText(
            text = buttonText,
            color = textColor,
            maxLines = 1,

        )
    }
}

@Preview
@Composable
private fun PreviewPlayerProfileScreen() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            val state = PlayerProfileScreenState(
                playerDetail = PlayerDetailTemp(
                    id = 3,
                    firstName = "Some",
                    lastName = "Player",
                    avatarUrl = null,
                    isFavorite = false,
                    level = "HARD",
                    latestActivity = listOf(
                        PlayerActivityTemp(
                            eventTimestamp = "2025-08-16T14:23:45Z",
                            courtLocation = Location(
                                longitude = 37.6156,
                                latitude = 55.7536,
                                courtName = "Спорт-площадка 18",
                                locationName = "Московкая область, г. Химки"
                            )
                        ),
                        PlayerActivityTemp(
                            eventTimestamp = "2025-07-14T20:23:45Z",
                            courtLocation = Location(
                                longitude = 37.6194,
                                latitude = 55.7523,
                                courtName = "Арена Восток",
                                locationName = "Казань"
                            )
                        )
                    )
                )
            )

            PlayerProfileScreen(
                state = state,
                effect = null,
                userHoursOffset = 3,
                navigateAction = {},
                eventCallback = {}
            )
        }
    }
}
