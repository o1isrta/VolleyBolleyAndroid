package cy.volleybolley.profile.presentation.ui.screens.playerprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.PlayerProfileScreenEffect.NavigateFromPlayerDetailScreen
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.PlayerProfileScreenEvent.ClickOnActivityMapButton
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.PlayerProfileScreenEvent.ClickOnBackFromPlayerDetails
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.PlayerProfileScreenEvent.ClickOnFavoriteManagementButton
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.model.PlayerActivityTemp
import cy.volleybolley.profile.presentation.ui.screens.playerprofile.model.PlayerDetailTemp
import cy.volleybolley.profile.presentation.ui.screens.players.model.BackPlayerIdHolder

@Composable
fun PlayerProfileScreen(
    onNavigateBack: (Int?) -> Unit,
    viewModel: PlayerProfileScreenViewModel,
    paddingFromSystemUi: PaddingValues,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            is PlayerProfileScreenEffect.NavigateFromPlayerDetailScreen -> {
                onNavigateBack(currentEffect.playerIdWithChangedFavoriteStatus)
            }
            null -> {}
        }
    }

    PlayerProfileScreen(
        state = state,
        userHoursOffset = 3, // пока нет ручек для хранения профиля пользователя
        eventCallback = { event -> viewModel.obtainEvent(event) },
        modifier = Modifier.padding(paddingFromSystemUi)
    )
}

@Composable
private fun PlayerProfileScreen(
    modifier: Modifier = Modifier,
    state: PlayerProfileScreenState,
    userHoursOffset: Int,
    eventCallback: (PlayerProfileScreenEvent) -> Unit,
) {
    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = 32,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            VolleySimpleComponent.TitleWithBackArrow(
                title = "${state.playerDetail.firstName} ${state.playerDetail.lastName}",
                modifier = Modifier.fillMaxWidth(),
                onBackClick = { eventCallback(ClickOnBackFromPlayerDetails) }
            )

            Spacer(Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                VolleyAvatar.CircularAvatar(
                    avatar = state.playerDetail.avatarUrl,
                    size = 100.dp
                )

                Spacer(Modifier.height(4.dp))

                VolleyText.BodyBoldGradient(
                    text = state.playerDetail.level,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                )
            }

            Spacer(Modifier.height(12.dp))

            if (state.playerDetail.latestActivity.isNotEmpty()) {
                VolleyText.BodyBold(
                    text = stringResource(R.string.latest_activity),
                    color = VolleyColor.White,
                    maxLines = 1
                )
                Spacer(Modifier.height(16.dp))

                LazyColumn {
                    val countOfActivities = state.playerDetail.latestActivity.size
                    itemsIndexed(state.playerDetail.latestActivity) { index, activity ->
                        PlayerActivityItem(
                            locationName = activity.courtLocation.locationName,
                            courtName = activity.courtLocation.courtName,
                            dateStamp = activity.eventTimestamp,
                            userHoursOffset = userHoursOffset,
                            onMapClick = { eventCallback(ClickOnActivityMapButton) }
                        )

                        if (index < countOfActivities - 1) {
                            Spacer(Modifier.height(16.dp))
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

            Spacer(Modifier.height(12.dp))

            FavoriteManagementButton(
                isFavorite = state.playerDetail.isFavorite,
                onClick = { eventCallback(ClickOnFavoriteManagementButton(!state.playerDetail.isFavorite)) }
            )
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
        cornerRadius = 16,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp, 8.dp)
        ) {
            VolleyText.BodyRegular(
                text = VolleyUiUtil.parseTimeStringToActivityDateString(
                    utcString = dateStamp,
                    hoursOffset = userHoursOffset,
                ),
                color = VolleyColor.White,
                maxLines = 1
            )
            Spacer(Modifier.height(8.dp))
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

        Spacer(Modifier.width(8.dp))

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

        Spacer(Modifier.width(8.dp))

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
    val buttonText = stringResource(
        if (isFavorite) R.string.unfavorite else R.string.add_to_favorites
    ).uppercase()
    val backgroundColor = if (isFavorite) Color.Transparent else VolleyColor.YellowPro
    val textColor = if (isFavorite) VolleyColor.White else VolleyColor.TextDark
    val shape = RoundedCornerShape(16.dp)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(
                color = backgroundColor,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = VolleyColor.YellowPro,
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
                            eventTimestamp = "2025-08-16T14:30:45Z",
                            courtLocation = Location(
                                longitude = 37.6156,
                                latitude = 55.7536,
                                courtName = "Спорт-площадка 18",
                                locationName = "Московкая область, г. Химки"
                            )
                        ),
                        PlayerActivityTemp(
                            eventTimestamp = "2025-07-14T23:23:45Z",
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
                userHoursOffset = 3,
                eventCallback = {}
            )
        }
    }
}
