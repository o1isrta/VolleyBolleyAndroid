package cy.volleybolley.core.presentation.ui.screens.courts

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.TransparentContainer
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ActiveButton
import cy.volleybolley.core.presentation.ui.component.VolleyButton.OutlinedActiveButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.presentation.SearchCourtEvent
import cy.volleybolley.courts.presentation.SearchCourtState

object MapScreenComponents {
    private const val DEFAULT_LAT = 7.8804
    private const val DEFAULT_LNG = 98.3923
    private const val DEFAULT_ZOOM = 14f
    private val DEFAULT_LAT_LNG = LatLng(DEFAULT_LAT, DEFAULT_LNG)
    @SuppressLint("MissingPermission")
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun MapScreen(
        state: SearchCourtState,
        onEvent: (SearchCourtEvent) -> Unit,
        modifier: Modifier = Modifier
    ) {
        val context = LocalContext.current
        val fusedLocationClient = remember {
            LocationServices.getFusedLocationProviderClient(context)
        }
        val locationPermissionState = rememberPermissionState(
            permission = Manifest.permission.ACCESS_FINE_LOCATION
        )
        var locationRequested by remember { mutableStateOf(false) }

        LaunchedEffect(locationPermissionState.status) {
            if (!locationRequested) {
                locationRequested = true
                when {
                    locationPermissionState.status.isGranted -> {
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location ->
                                location?.let {
                                    onEvent(SearchCourtEvent.UpdateUserLocation(LatLng(it.latitude, it.longitude)))
                                } ?: onEvent(SearchCourtEvent.DeniedUserLocation)
                            }
                    }

                    else -> locationPermissionState.launchPermissionRequest()
                }
            }
        }

        MapScreenContent(
            state = state,
            onEvent = onEvent,
            modifier = modifier
        )
    }

    @Composable
    private fun MapScreenContent(
        state: SearchCourtState,
        onEvent: (SearchCourtEvent) -> Unit,
        modifier: Modifier = Modifier
    ) {
        val context = LocalContext.current
        val defaultLatLng = DEFAULT_LAT_LNG
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(state.userLocation ?: defaultLatLng, DEFAULT_ZOOM)
        }
        Box(modifier = modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(zoomControlsEnabled = false),
                onMapClick = { onEvent(SearchCourtEvent.ClickOnMap) }
            ) {
                state.userLocation?.let { userLocation ->
                    Marker(
                        state = MarkerState(position = userLocation),
                        title = "You are here",
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                    )
                }

                state.courts.forEach { court ->
                    Marker(
                        state = MarkerState(position = court.location.toLatLng()),
                        title = court.location.courtName,
                        icon = court.getMarkerIcon(context, state.selectedCourt),
                        onClick = {
                            onEvent(SearchCourtEvent.ClickOnSearchCourtMarker(court))
                            true
                        }
                    )
                }
            }

            state.selectedCourt?.let { court ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    val modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(VolleyDimens.DIMEN_8.dp)

                    if (state.showDetails) {
                        CourtMapItemDetail(
                            court = court,
                            onClick = { onEvent(SearchCourtEvent.ClickOnMap) },
                            onChooseCourt = { onEvent(SearchCourtEvent.ClickOnChooseSearchCourt(court)) },
                            modifier = modifier
                        )
                    } else {
                        CourtMapItemWithButton(
                            court = court,
                            onClick = { onEvent(SearchCourtEvent.ClickOnSearchCourtDetails(court)) },
                            onChooseCourt = { onEvent(SearchCourtEvent.ClickOnChooseSearchCourt(court)) },
                            modifier = modifier
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CourtMapItem(
    court: Court,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_navigation),
                contentDescription = stringResource(id = R.string.navigation),
                tint = VolleyColor.OrangeHard,
                modifier = Modifier
                    .padding(end = VolleyDimens.DIMEN_10.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                VolleyText.BodyBold(
                    text = court.location.courtName,
                    color = VolleyColor.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                VolleyText.BodyLight(
                    text = court.location.locationName,
                    color = VolleyColor.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            ListScreenComponents.DistanceContainer(
                modifier = Modifier.align(Alignment.CenterVertically),
                distance = "Nearest"
            )
        }
    }
}

@Composable
private fun CourtMapItemWithButton(
    court: Court,
    onClick: () -> Unit,
    onChooseCourt: () -> Unit,
    modifier: Modifier,
) {
    TransparentContainer(
        modifier = modifier
            .background(VolleyColor.TurquoiseDark)
            .wrapContentHeight(),
        mainContainerAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_20.dp),
            verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_16.dp)
        ) {
            CourtMapItem(
                court = court,
                onClick = onClick,
            )
            CourtActionButtons(onChooseCourt = onChooseCourt, onClickDetails = onClick)
        }
    }
}

@Composable
private fun CourtActionButtons(
    onClickDetails: () -> Unit,
    onChooseCourt: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        ActiveButton(
            modifier = Modifier.weight(2f),
            enabled = true,
            text = stringResource(R.string.choose_this_court),
            onClick = onChooseCourt
        )
        OutlinedActiveButton(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.details),
            onClick = onClickDetails
        )
    }
}

@Composable
private fun CourtMapItemDetail(
    court: Court,
    onClick: () -> Unit,
    onChooseCourt: () -> Unit,
    modifier: Modifier = Modifier
) {
    TransparentContainer(
        modifier = modifier
            .background(VolleyColor.TurquoiseDark)
            .wrapContentHeight(),
        mainContainerAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(VolleyDimens.DIMEN_20.dp)
        ) {
            CourtMapItem(
                court = court,
                onClick = onClick
            )

            ListScreenComponents.CourtDetailsContent(
                court = court,
                onChooseCourt = onChooseCourt
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMapScreenContent() {
    var selectedCourt by remember { mutableStateOf(CourtsMockData.sampleCourts[0]) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.GreyDisabled),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🗺 Fake Google Map", color = VolleyColor.GreyDark)
        }
        CourtMapItemWithButton(
            court = selectedCourt,
            onClick = {},
            onChooseCourt = {},
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(VolleyDimens.DIMEN_8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCourtMapItemWithButton() {
    CourtMapItemWithButton(
        court = CourtsMockData.sampleCourts[0],
        onClick = {},
        onChooseCourt = {},
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewCourtMapItemDetail() {
    CourtMapItemDetail(
        court = CourtsMockData.sampleCourts[1],
        onClick = {},
        onChooseCourt = {}
    )
}
