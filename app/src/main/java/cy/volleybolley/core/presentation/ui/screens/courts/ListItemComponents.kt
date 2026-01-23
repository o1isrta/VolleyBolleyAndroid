package cy.volleybolley.core.presentation.ui.screens.courts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.courts.presentation.model.CourtUi

object ListItemComponents {
    @Stable
    @Composable
    fun CourtDetailsContent(
        modifier: Modifier = Modifier,
        court: CourtUi
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                VolleyText.BodyBold(
                    text = "${stringResource(R.string.court_pricing)} ",
                    color = VolleyColor.White
                )
                VolleyText.BodyRegular(
                    text = court.price,
                    color = VolleyColor.White
                )
            }

            VolleyText.BodyRegular(
                text = court.description,
                color = VolleyColor.White,
            )

            if (court.contacts.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    VolleyText.BodyBold(
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
    }

    @Stable
    @Composable
    fun DividerItem() {
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
