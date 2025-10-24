package cy.volleybolley.courts.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
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
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
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
import cy.volleybolley.courts.domain.model.Location
import cy.volleybolley.courts.presentation.ListScreenComponents.CourtDetailsContent
import cy.volleybolley.courts.presentation.ListScreenComponents.DistanceContainer

object MapScreenComponents {
    @SuppressLint("MissingPermission")
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun MapScreen(
        state: CourtState,
        onEvent: (CourtEvent) -> Unit,
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
                                    onEvent(CourtEvent.UpdateUserLocation(LatLng(it.latitude, it.longitude)))
                                } ?: onEvent(CourtEvent.DeniedUserLocation)
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
}

@Composable
private fun MapScreenContent(
    state: CourtState,
    onEvent: (CourtEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val defaultLatLng = LatLng(7.8804, 98.3923)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(state.userLocation ?: defaultLatLng, 14f)
    }
    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false),
            onMapClick = { onEvent(CourtEvent.ClickOnMap) }
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
                        onEvent(CourtEvent.ClickOnCourtMarker(court))
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
                        onClick = { onEvent(CourtEvent.ClickOnMap) },
                        onChooseCourt = { onEvent(CourtEvent.ClickOnChooseCourt(court)) },
                        modifier = modifier
                    )
                } else {
                    CourtMapItemWithButton(
                        court = court,
                        onClick = { onEvent(CourtEvent.ClickOnCourtDetails(court)) },
                        onChooseCourt = { onEvent(CourtEvent.ClickOnChooseCourt(court)) },
                        modifier = modifier
                    )
                }
            }
        }
    }
}

private fun Location.toLatLng() = LatLng(latitude, longitude)

private fun Court.getMarkerIcon(context: Context, selectedCourt: Court?): BitmapDescriptor {
    return if (this.courtId == selectedCourt?.courtId) {
        context.drawableToBitmapDescriptor(R.drawable.ic_pin_map)
    } else {
        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
    }
}

private fun Context.drawableToBitmapDescriptor(@DrawableRes drawableRes: Int): BitmapDescriptor {
    val drawable = ContextCompat.getDrawable(this, drawableRes)!!
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
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

            DistanceContainer(
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

            CourtDetailsContent(
                court = court,
                onChooseCourt = onChooseCourt
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMapScreenContent() {
    var selectedCourt by remember { mutableStateOf(MockData.sampleCourts[0]) }

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
        court = MockData.sampleCourts[0],
        onClick = {},
        onChooseCourt = {},
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewCourtMapItemDetail() {
    CourtMapItemDetail(
        court = MockData.sampleCourts[1],
        onClick = {},
        onChooseCourt = {}
    )
}
