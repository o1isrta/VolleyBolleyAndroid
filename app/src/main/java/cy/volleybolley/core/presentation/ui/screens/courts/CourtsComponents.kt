package cy.volleybolley.core.presentation.ui.screens.courts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.component.VolleyButton.SliderButtonsMap
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.screens.courts.ListScreenComponents.ListContent
import cy.volleybolley.core.presentation.ui.screens.courts.MapScreenComponents.MapScreen
import cy.volleybolley.courts.domain.model.Court
import cy.volleybolley.courts.util.UiStateRenderer

object CourtsComponents {
    @Composable
    fun CourtMapListSwitcherScreen(
        modifier: Modifier = Modifier,
        courts: List<Court> = emptyList(),
        selectedCourt: Court? = null,
        userLocation: LatLng? = null,
        showDetails: Boolean = false,
        isLoading: Boolean = false,
        error: String? = null,
        isMapSelected: Boolean,
        onTabSelected: (Boolean) -> Unit,
        onBackNavigationRequested: () -> Unit = {},
        onCourtClick: (Court) -> Unit,
        onCourtChoose: (Court) -> Unit,
        onMapClick: () -> Unit,
        onCourtDetailsClick: (Court) -> Unit,
        onUserLocationUpdate: (LatLng) -> Unit,
        onUserLocationDenied: () -> Unit,
    ) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            UiStateRenderer(
                isLoading = isLoading,
                error = error
            ) {
                when {
                    isMapSelected -> {
                        MapScreen(
                            courts = courts,
                            selectedCourt = selectedCourt,
                            userLocation = userLocation,
                            showDetails = showDetails,
                            onMapClick = onMapClick,
                            onCourtClick = onCourtClick,
                            onCourtChoose = onCourtChoose,
                            onCourtDetailsClick = onCourtDetailsClick,
                            onUserLocationUpdate = onUserLocationUpdate,
                            onUserLocationDenied = onUserLocationDenied
                        )
                    }

                    else -> {
                        ListContent(
                            courts = courts,
                            selectedCourt = selectedCourt,
                            onClick = onCourtClick,
                            onChooseCourt = onCourtChoose,
                            modifier = Modifier.padding(top = VolleyDimens.DIMEN_40.dp)
                        )
                    }
                }
            }
            MapListTopBar(
                isMapSelected = isMapSelected,
                onTabSelected = onTabSelected,
                onBackNavigationRequested = onBackNavigationRequested,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = VolleyDimens.DIMEN_8.dp)
            )
        }
    }
}

@Composable
private fun MapListTopBar(
    isMapSelected: Boolean,
    onTabSelected: (Boolean) -> Unit,
    onBackNavigationRequested: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackNavigationRequested,
            modifier = Modifier.size(VolleyDimens.DIMEN_24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = stringResource(id = R.string.back),
                tint = if (isMapSelected) VolleyColor.Black else VolleyColor.White,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        SliderButtonsMap(
            checkId = if (isMapSelected) 1 else 2,
            onTabSelected = { tabId -> onTabSelected(tabId == 1) },
            modifier = Modifier
                .size(
                    width = VolleyDimens.DIMEN_204.dp,
                    height = VolleyDimens.DIMEN_32.dp
                )
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCourtMapListSwitcherScreen() {
    var isMapSelected by remember { mutableStateOf(false) }
    var selectedCourt by remember { mutableStateOf<Court?>(CourtsMockData.sampleCourts[1]) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        CourtsComponents.CourtMapListSwitcherScreen(
            courts = CourtsMockData.sampleCourts,
            selectedCourt = selectedCourt,
            userLocation = LatLng(7.8804, 98.3923),
            showDetails = false,
            isLoading = false,
            error = null,
            isMapSelected = isMapSelected,
            onTabSelected = { isMapSelected = it },
            onBackNavigationRequested = {},
            onCourtClick = { },
            onCourtChoose = { },
            onMapClick = {},
            onCourtDetailsClick = { },
            onUserLocationUpdate = { },
            onUserLocationDenied = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMapListTopBar() {
    var isMapSelected by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(VolleyColor.TurquoiseDark)
    ) {
        MapListTopBar(
            isMapSelected = isMapSelected,
            onTabSelected = { isMapSelected = it },
            onBackNavigationRequested = {}
        )
    }
}
