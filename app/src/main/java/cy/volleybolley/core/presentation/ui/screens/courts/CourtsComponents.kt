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
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.ViewTab
import cy.volleybolley.core.presentation.ui.screens.courts.ListScreenComponents.ListContent
import cy.volleybolley.core.presentation.ui.screens.courts.MapScreenComponents.MapScreen
import cy.volleybolley.courts.presentation.model.CourtUi
import cy.volleybolley.courts.util.UiStateRenderer

object CourtsComponents {
    @Composable
    fun CourtMapListSwitcherScreen(
        modifier: Modifier = Modifier,
        courts: List<CourtUi> = emptyList(),
        selectedCourt: CourtUi? = null,
        userLocation: LatLng? = null,
        showDetails: Boolean = false,
        isLoading: Boolean = false,
        error: String? = null,
        isMapSelected: Boolean,
        onTabSelected: (Boolean) -> Unit,
        onBackNavigationRequested: () -> Unit = {},
        onMapCourtClick: (CourtUi) -> Unit,
        onMapCourtDetailsClick: (CourtUi) -> Unit,
        onMapClick: () -> Unit,
        onListCourtClick: (CourtUi) -> Unit,
        onCourtChoose: (CourtUi) -> Unit,
        onUserLocationUpdate: (LatLng) -> Unit,
        onUserLocationDenied: () -> Unit,
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            UiStateRenderer(isLoading = isLoading, error = error) {
                if (isMapSelected) {
                    MapScreen(
                        courts = courts,
                        selectedCourt = selectedCourt,
                        userLocation = userLocation,
                        showDetails = showDetails,
                        onMapClick = onMapClick,
                        onCourtClick = onMapCourtClick,
                        onCourtDetailsClick = onMapCourtDetailsClick,
                        onCourtChoose = onCourtChoose,
                        onUserLocationUpdate = onUserLocationUpdate,
                        onUserLocationDenied = onUserLocationDenied
                    )
                } else {
                    ListContent(
                        courts = courts,
                        selectedCourt = selectedCourt,
                        onClick = onListCourtClick,
                        onChooseCourt = onCourtChoose,
                        modifier = Modifier.padding(top = 40.dp)
                    )
                }
            }

            MapListTopBar(
                isMapSelected = isMapSelected,
                onTabSelected = onTabSelected,
                onBackNavigationRequested = onBackNavigationRequested,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
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
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = stringResource(id = R.string.back),
                tint = if (isMapSelected) VolleyColor.Black else VolleyColor.White,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        VolleyButton.SliderButtonGroup(
            items = ViewTab.entries,
            selected = if (isMapSelected) ViewTab.Map else ViewTab.List,
            label = { it.displayText },
            fillWidth = false,
            modifier = Modifier,
            onSelect = { tab -> onTabSelected(tab == ViewTab.Map) }
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCourtMapListSwitcherScreen() {
    var isMapSelected by remember { mutableStateOf(false) }
    var selectedCourt by remember { mutableStateOf<CourtUi?>(CourtsMockData.sampleCourts[1]) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        CourtsComponents.CourtMapListSwitcherScreen(
            courts = CourtsMockData.sampleCourts,
            selectedCourt = selectedCourt,
            userLocation = CourtsMockData.LAT_LNG_PHUKET,
            showDetails = false,
            isLoading = false,
            error = null,
            isMapSelected = isMapSelected,
            onTabSelected = { isMapSelected = it },
            onBackNavigationRequested = {},
            onMapCourtClick = { },
            onCourtChoose = { },
            onMapClick = {},
            onListCourtClick = { },
            onUserLocationUpdate = { },
            onMapCourtDetailsClick = { },
            onUserLocationDenied = { },
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
