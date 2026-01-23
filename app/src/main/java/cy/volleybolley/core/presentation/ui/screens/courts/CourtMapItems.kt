package cy.volleybolley.core.presentation.ui.screens.courts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.TransparentContainer
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ActiveButton
import cy.volleybolley.core.presentation.ui.component.VolleyButton.OutlinedActiveButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.screens.courts.CourtMapItems.CourtMapItemWithButton
import cy.volleybolley.core.presentation.ui.screens.courts.ListItemComponents.DistanceContainer
import cy.volleybolley.courts.presentation.model.CourtUi

object CourtMapItems {
    @Composable
    fun CourtMapItem(
        modifier: Modifier = Modifier,
        court: CourtUi,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
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
                    distance = court.distanceText
                )
            }
        }
    }

    @Stable
    @Composable
    fun CourtMapItemWithButton(
        modifier: Modifier,
        court: CourtUi,
        onClick: () -> Unit,
        onChooseCourt: () -> Unit,
    ) {
        TransparentContainer(
            modifier = modifier
                .clip(RoundedCornerShape(VolleyDimens.DIMEN_32.dp))
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
                )
                CourtActionButtons(onClickDetails = onClick, onChooseCourt = onChooseCourt)
            }
        }
    }

    @Stable
    @Composable
    fun CourtActionButtons(
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
