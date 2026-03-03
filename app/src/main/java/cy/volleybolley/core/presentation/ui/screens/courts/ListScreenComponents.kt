package cy.volleybolley.core.presentation.ui.screens.courts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9_PRO_XL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.TransparentContainer
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient.SearchField
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.screens.courts.CourtDetailComponents.CourtItemDetails
import cy.volleybolley.core.presentation.ui.screens.courts.ListItemComponents.DistanceContainer
import cy.volleybolley.core.presentation.ui.screens.courts.ListItemComponents.DividerItem
import cy.volleybolley.core.presentation.ui.screens.courts.ListScreenComponents.ListContent
import cy.volleybolley.courts.presentation.model.CourtUi

object ListScreenComponents {
    @Composable
    fun ListContent(
        modifier: Modifier = Modifier,
        courts: List<CourtUi> = emptyList(),
        selectedCourt: CourtUi? = null,
        onClick: (CourtUi) -> Unit,
        onChooseCourt: (CourtUi) -> Unit,
    ) {
        TransparentContainer(
            modifier = modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            CourtsSearchList(
                courts = courts,
                selectedCourt = selectedCourt,
                onClick = onClick,
                onChooseCourt = onChooseCourt
            )
        }
    }

    @Composable
    private fun CourtsSearchList(
        courts: List<CourtUi>,
        selectedCourt: CourtUi?,
        onClick: (CourtUi) -> Unit,
        onChooseCourt: (CourtUi) -> Unit
    ) {
        var searchText by remember { mutableStateOf("") }
        val filteredCourts by remember(courts, searchText) {
            derivedStateOf {
                courts.filter {
                    it.location.courtName.contains(searchText, ignoreCase = true) ||
                        it.location.locationName.contains(searchText, ignoreCase = true)
                }
            }
        }

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(20.dp)
        ) {
            SearchField(
                modifier = Modifier.fillMaxWidth(),
                text = searchText,
                hint = stringResource(R.string.search_field_hint),
                actionToTransferContent = { searchText = it },
                actionOnInputComplete = {}
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                itemsIndexed(filteredCourts, key = { _, court -> court.courtId }) { index, court ->
                    val isSelected = court == selectedCourt
                    if (isSelected) {
                        CourtItemDetails(
                            court = court,
                            onClick = { onClick(court) },
                            onChooseCourt = { onChooseCourt(court) }
                        )
                    } else {
                        CourtListItem(
                            courtName = court.location.courtName,
                            locationName = court.location.locationName,
                            distance = court.distanceText,
                            onClick = { onClick(court) },
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                    if (index < filteredCourts.lastIndex) {
                        DividerItem()
                    }
                }
            }
        }
    }

    @Composable
    fun CourtListItem(
        modifier: Modifier = Modifier,
        courtName: String,
        locationName: String,
        distance: String,
        onClick: () -> Unit,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                VolleyText.BodyBold(
                    text = courtName,
                    color = VolleyColor.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                VolleyText.BodyLight(
                    text = locationName,
                    color = VolleyColor.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            DistanceContainer(
                modifier = Modifier.align(Alignment.CenterVertically),
                distance = distance,
            )
        }
    }
}

@Preview(device = PIXEL_9_PRO_XL)
@Composable
private fun PreviewListScreen() {
    Box(
        modifier = Modifier
            .background(VolleyColor.TurquoiseDark)
            .fillMaxSize()
    ) {
        ListContent(
            courts = CourtsMockData.sampleCourts,
            selectedCourt = CourtsMockData.sampleCourts[2],
            onClick = {},
            onChooseCourt = {}
        )
    }
}
