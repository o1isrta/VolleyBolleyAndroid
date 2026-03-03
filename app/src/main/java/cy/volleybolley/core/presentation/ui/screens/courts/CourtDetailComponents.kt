package cy.volleybolley.core.presentation.ui.screens.courts

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.TransparentContainer
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ActiveButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.screens.courts.CourtDetailComponents.CourtDetailsContentWithButton
import cy.volleybolley.core.presentation.ui.screens.courts.CourtDetailComponents.CourtItemDetails
import cy.volleybolley.core.presentation.ui.screens.courts.CourtDetailComponents.CourtMapItemDetail
import cy.volleybolley.core.presentation.ui.screens.courts.CourtMapItems.CourtActionButtons
import cy.volleybolley.core.presentation.ui.screens.courts.CourtMapItems.CourtMapItem
import cy.volleybolley.core.presentation.ui.screens.courts.ListItemComponents.CourtDetailsContent
import cy.volleybolley.core.presentation.ui.screens.courts.ListItemComponents.CourtImageWithTags
import cy.volleybolley.core.presentation.ui.screens.courts.ListItemComponents.DistanceContainer
import cy.volleybolley.core.presentation.ui.screens.courts.ListScreenComponents.CourtListItem
import cy.volleybolley.courts.presentation.model.CourtUi

object CourtDetailComponents {
    @Composable
    fun CourtItemDetails(
        court: CourtUi,
        onClick: () -> Unit,
        onChooseCourt: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .animateContentSize()
        ) {
            CourtListItem(
                courtName = court.location.courtName,
                locationName = court.location.locationName,
                onClick = onClick,
                distance = court.distanceText
            )
            CourtImageWithTags(
                photoUrl = court.photo,
                tags = court.tags
            )
            CourtDetailsContentWithButton(
                court = court,
                onChooseCourt = onChooseCourt
            )
        }
    }

    @Composable
    fun CourtDetailsContentWithButton(
        modifier: Modifier = Modifier,
        court: CourtUi,
        onChooseCourt: () -> Unit
    ) {
        Column(modifier = modifier) {
            CourtDetailsContent(
                court = court,
                modifier = Modifier.fillMaxWidth()
            )

            ActiveButton(
                onClick = onChooseCourt,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(44.dp),
                text = stringResource(R.string.choose_this_court),
                enabled = true,
            )
        }
    }

    @Stable
    @Composable
    fun CourtMapItemDetail(
        court: CourtUi,
        onClickDetails: () -> Unit,
        onChooseCourt: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        TransparentContainer(
            modifier = modifier
                .clip(RoundedCornerShape(32.dp))
                .background(VolleyColor.TurquoiseDark)
                .wrapContentHeight(),
            mainContainerAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                CourtMapItem(
                    court = court,
                )
                CourtImageWithTags(
                    photoUrl = court.photo,
                    tags = court.tags
                )
                CourtDetailsContent(
                    court = court,
                )
                CourtActionButtons(
                    modifier = Modifier.padding(top = 16.dp),
                    onClickDetails = onClickDetails,
                    onChooseCourt = onChooseCourt
                )
            }
        }
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
            modifier = Modifier.padding(vertical = 16.dp),
            courtName = CourtsMockData.sampleCourts[0].location.courtName,
            locationName = CourtsMockData.sampleCourts[0].location.locationName,
            onClick = {},
            distance = CourtsMockData.sampleCourts[0].distanceText
        )
        CourtDetailsContentWithButton(
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

@Preview(showBackground = true)
@Composable
private fun PreviewCourtMapItemDetail() {
    CourtMapItemDetail(
        court = CourtsMockData.sampleCourts[1],
        onClickDetails = {},
        onChooseCourt = {}
    )
}
