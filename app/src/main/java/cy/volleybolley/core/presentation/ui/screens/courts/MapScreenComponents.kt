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
import cy.volleybolley.core.presentation.ui.screens.courts.ListScreenComponents.CourtDetailsContent
import cy.volleybolley.core.presentation.ui.screens.courts.ListScreenComponents.CourtImageWithTags
import cy.volleybolley.core.presentation.ui.screens.courts.ListScreenComponents.DistanceContainer
import cy.volleybolley.core.presentation.ui.screens.courts.MapScreenComponents.MapScreenContent
import cy.volleybolley.courts.domain.model.Court

object MapScreenComponents {
    private const val DEFAULT_LAT = 7.8804
    private const val DEFAULT_LNG = 98.3923
    private const val DEFAULT_ZOOM = 14f
    private val DEFAULT_LAT_LNG = LatLng(DEFAULT_LAT, DEFAULT_LNG)

    @SuppressLint("MissingPermission")
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun MapScreen(
        modifier: Modifier = Modifier,
        courts: List<Court> = emptyList(),
        selectedCourt: Court? = null,
        userLocation: LatLng? = null,
        showDetails: Boolean = false,
        onMapClick: () -> Unit,
        onCourtClick: (Court) -> Unit,
        onCourtChoose: (Court) -> Unit,
        onCourtDetailsClick: (Court) -> Unit,
        onUserLocationUpdate: (LatLng) -> Unit,
        onUserLocationDenied: () -> Unit,
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
                                    onUserLocationUpdate(LatLng(it.latitude, it.longitude))
                                } ?: onUserLocationDenied()
                            }
                    }

                    else -> locationPermissionState.launchPermissionRequest()
                }
            }
        }

        MapScreenContent(
            courts = courts,
            selectedCourt = selectedCourt,
            userLocation = userLocation,
            showDetails = showDetails,
            onMapClick = onMapClick,
            onCourtClick = onCourtClick,
            onCourtChoose = onCourtChoose,
            onCourtDetailsClick = onCourtDetailsClick,
            modifier = modifier
        )
    }

    @Composable
    fun MapScreenContent(
        modifier: Modifier = Modifier,
        courts: List<Court> = emptyList(),
        selectedCourt: Court? = null,
        userLocation: LatLng? = null,
        showDetails: Boolean = false,
        onMapClick: () -> Unit,
        onCourtClick: (Court) -> Unit,
        onCourtChoose: (Court) -> Unit,
        onCourtDetailsClick: (Court) -> Unit,
    ) {
        val context = LocalContext.current
        val defaultLatLng = DEFAULT_LAT_LNG
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(userLocation ?: defaultLatLng, DEFAULT_ZOOM)
        }
        Box(modifier = modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(zoomControlsEnabled = false),
                onMapClick = { onMapClick() }
            ) {
                userLocation?.let { location ->
                    Marker(
                        state = MarkerState(position = location),
                        title = "You are here",
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                    )
                }

                courts.forEach { court ->
                    Marker(
                        state = MarkerState(position = court.location.toLatLng()),
                        title = court.location.courtName,
                        icon = court.getMarkerIcon(context, selectedCourt),
                        onClick = {
                            onCourtClick(court)
                            true
                        }
                    )
                }
            }

            selectedCourt?.let { court ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        val modifier = Modifier
                            .padding(VolleyDimens.DIMEN_8.dp)

                        if (showDetails) {
                            CourtMapItemDetail(
                                court = court,
                                onClick = onMapClick,
                                onChooseCourt = { onCourtChoose(court) },
                                modifier = modifier
                            )
                        } else {
                            CourtMapItemWithButton(
                                court = court,
                                onClick = { onCourtDetailsClick(court) },
                                onChooseCourt = { onCourtChoose(court) },
                                modifier = modifier
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CourtMapItem(
    modifier: Modifier = Modifier,
    court: Court,
    onClick: () -> Unit,
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

            DistanceContainer(
                modifier = Modifier.align(Alignment.CenterVertically),
                distance = "Nearest"
            )
        }
    }
}

@Composable
private fun CourtMapItemWithButton(
    modifier: Modifier,
    court: Court,
    onClick: () -> Unit,
    onChooseCourt: () -> Unit,
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
            CourtActionButtons(onClickDetails = onClick, onChooseCourt = onChooseCourt)
        }
    }
}

@Composable
private fun CourtActionButtons(
    modifier: Modifier = Modifier,
    onClickDetails: () -> Unit,
    onChooseCourt: () -> Unit,
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
            CourtImageWithTags(
                photoUrl = court.photo,
                tags = court.tags
            )
            CourtDetailsContent(
                modifier = Modifier.padding(top = VolleyDimens.DIMEN_16.dp),
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
        MapScreenContent(
            courts = CourtsMockData.sampleCourts,
            selectedCourt = selectedCourt,
            userLocation = CourtsMockData.LAT_LNG_PHUKET,
            showDetails = false,
            onMapClick = {},
            onCourtClick = { },
            onCourtChoose = {},
            onCourtDetailsClick = { }
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
