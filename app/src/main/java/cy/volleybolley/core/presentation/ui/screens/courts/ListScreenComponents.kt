package cy.volleybolley.core.presentation.ui.screens.courts

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9_PRO_XL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.TransparentContainer
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient.SearchField
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ActiveButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.screens.courts.ListScreenComponents.CourtDetailsContent
import cy.volleybolley.core.presentation.ui.screens.courts.ListScreenComponents.CourtItemDetails
import cy.volleybolley.core.presentation.ui.screens.courts.ListScreenComponents.CourtListItem
import cy.volleybolley.core.presentation.ui.screens.courts.ListScreenComponents.DistanceContainer
import cy.volleybolley.core.presentation.ui.screens.courts.ListScreenComponents.ListContent
import cy.volleybolley.courts.domain.model.Court

object ListScreenComponents {
    @Composable
    fun ListContent(
        modifier: Modifier = Modifier,
        courts: List<Court> = emptyList(),
        selectedCourt: Court? = null,
        onClick: (Court) -> Unit,
        onChooseCourt: (Court) -> Unit,
    ) {
        TransparentContainer(
            modifier = modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_8.dp)
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
        courts: List<Court>,
        selectedCourt: Court?,
        onClick: (Court) -> Unit,
        onChooseCourt: (Court) -> Unit
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
                .padding(VolleyDimens.DIMEN_20.dp)
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
                            onClick = { onClick(court) },
                            modifier = Modifier.padding(vertical = VolleyDimens.DIMEN_16.dp)
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
                distance = "${(1..20).random()} km",
            )
        }
    }

    @Composable
    fun CourtItemDetails(
        court: Court,
        onClick: () -> Unit,
        onChooseCourt: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = VolleyDimens.DIMEN_16.dp)
                .clip(RoundedCornerShape(VolleyDimens.DIMEN_16.dp))
                .animateContentSize()
        ) {
            CourtListItem(
                courtName = court.location.courtName,
                locationName = court.location.locationName,
                onClick = onClick,
            )
            CourtImageWithTags(
                photoUrl = court.photo,
                tags = court.tags
            )
            CourtDetailsContent(
                court = court,
                onChooseCourt = onChooseCourt
            )
        }
    }

    @Composable
    fun CourtDetailsContent(
        modifier: Modifier = Modifier,
        court: Court,
        onChooseCourt: () -> Unit
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp)
        ) {
            VolleyText.BodyBold(
                text = court.price,
                color = VolleyColor.White,
            )

            VolleyText.BodyRegular(
                text = court.description,
                color = VolleyColor.White,
            )

            if (court.contacts.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    VolleyText.BodyRegular(
                        text = "${stringResource(R.string.contacts)} ",
                        color = VolleyColor.White
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        court.contacts.forEach { contact ->
                            VolleyText.BodyRegular(
                                text = contact.contact,
                                color = VolleyColor.White
                            )
                        }
                    }
                }
            }
        }
        ActiveButton(
            onClick = onChooseCourt,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = VolleyDimens.DIMEN_16.dp)
                .height(VolleyDimens.DIMEN_44.dp),
            text = stringResource(R.string.choose_this_court),
            enabled = true,
        )
    }

    @Stable
    @Composable
    private fun DividerItem() {
        HorizontalDivider(
            thickness = VolleyDimens.REGISTRATION_DIVIDER_THICKNESS.dp,
            color = VolleyColor.Divider
        )
    }

    @Stable
    @Composable
    fun DistanceContainer(
        modifier: Modifier = Modifier,
        paddingValues: PaddingValues = PaddingValues(),
        distance: String,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .padding(paddingValues)
                .wrapContentSize()
                .background(
                    color = VolleyColor.GreyDark,
                    shape = RoundedCornerShape(VolleyDimens.DIMEN_10.dp)
                )
        ) {
            VolleyText.BodyRegular(
                modifier = Modifier.padding(
                    vertical = VolleyDimens.DIMEN_4.dp,
                    horizontal = VolleyDimens.DIMEN_8.dp
                ),
                text = distance,
                color = VolleyColor.White,
                maxLines = 1,
            )
        }
    }

    @Stable
    @Composable
    private fun TagItem(tag: String) {
        Box(
            modifier = Modifier
                .background(
                    color = VolleyColor.TagColor,
                    shape = RoundedCornerShape(VolleyDimens.DIMEN_6.dp)
                )
                .padding(horizontal = VolleyDimens.DIMEN_4.dp, vertical = VolleyDimens.DIMEN_2.dp)
        ) {
            VolleyText.BodyRegular(
                text = tag,
                color = VolleyColor.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    @Stable
    @Composable
    fun CourtImageWithTags(
        modifier: Modifier = Modifier,
        photoUrl: String,
        tags: List<String>,
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(VolleyDimens.DIMEN_194.dp)
                .padding(vertical = VolleyDimens.DIMEN_16.dp)
                .clip(RoundedCornerShape(VolleyDimens.DIMEN_16.dp))
        ) {
            val imageModel = photoUrl.ifBlank { R.drawable.preview_court_png }
            AsyncImage(
                model = imageModel,
                contentDescription = "Court Photo",
                modifier = Modifier.matchParentSize(),
                placeholder = painterResource(R.drawable.preview_court_png),
                error = painterResource(R.drawable.preview_court_png),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(VolleyDimens.DIMEN_16.dp),
                horizontalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_4.dp)
            ) {
                tags.forEach { tag ->
                    TagItem(tag = tag)
                }
            }
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

@Preview(showBackground = true)
@Composable
private fun PreviewComponentContainer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        DistanceContainer(
            distance = "3.4 km"
        )
        CourtListItem(
            modifier = Modifier.padding(vertical = VolleyDimens.DIMEN_16.dp),
            courtName = CourtsMockData.sampleCourts[0].location.courtName,
            locationName = CourtsMockData.sampleCourts[0].location.locationName,
            onClick = {}
        )
        CourtDetailsContent(
            court = CourtsMockData.sampleCourts[0],
            onChooseCourt = {},
        )
        CourtItemDetails(
            court = CourtsMockData.sampleCourts[0],
            onChooseCourt = {},
            onClick = {},
        )
    }
}
