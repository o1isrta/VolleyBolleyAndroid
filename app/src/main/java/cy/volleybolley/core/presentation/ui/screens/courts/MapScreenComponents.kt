package cy.volleybolley.core.presentation.ui.screens.courts

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.screens.courts.CourtDetailComponents.CourtMapItemDetail
import cy.volleybolley.core.presentation.ui.screens.courts.CourtMapItems.CourtMapItemWithButton
import cy.volleybolley.core.presentation.ui.screens.courts.MapScreenComponents.MapScreenContent
import cy.volleybolley.courts.presentation.model.CourtUi

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
        courts: List<CourtUi> = emptyList(),
        selectedCourt: CourtUi? = null,
        userLocation: LatLng? = null,
        showDetails: Boolean = false,
        onMapClick: () -> Unit,
        onCourtClick: (CourtUi) -> Unit,
        onCourtChoose: (CourtUi) -> Unit,
        onCourtDetailsClick: (CourtUi) -> Unit,
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
        courts: List<CourtUi> = emptyList(),
        selectedCourt: CourtUi? = null,
        userLocation: LatLng? = null,
        showDetails: Boolean = false,
        onMapClick: () -> Unit,
        onCourtClick: (CourtUi) -> Unit,
        onCourtChoose: (CourtUi) -> Unit,
        onCourtDetailsClick: (CourtUi) -> Unit,
    ) {
        val context = LocalContext.current
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                DEFAULT_LAT_LNG,
                DEFAULT_ZOOM
            )
        }
        var hasCenteredOnUser by rememberSaveable { mutableStateOf(false) }
        LaunchedEffect(userLocation) {
            if (userLocation != null && !hasCenteredOnUser) {
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngZoom(
                        userLocation,
                        DEFAULT_ZOOM
                    )
                )
                hasCenteredOnUser = true
            }
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
                        title = stringResource(R.string.start_position),
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
                            .padding(8.dp)

                        if (showDetails) {
                            CourtMapItemDetail(
                                court = court,
                                onChooseCourt = { onCourtChoose(court) },
                                modifier = modifier,
                                onClickDetails = { onCourtDetailsClick(court) }
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
