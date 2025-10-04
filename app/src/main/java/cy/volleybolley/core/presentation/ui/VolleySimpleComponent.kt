package cy.volleybolley.core.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText

object VolleySimpleComponent {
    @Composable
    fun TitleWithBackArrow(
        modifier: Modifier = Modifier,
        title: String,
        onBackClick: () -> Unit = {}
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
        ) {
            VolleyText.TitleLarge(
                text = title,
                color = VolleyColor.White,
                textAlign = TextAlign.Center,
                maxLines = 1,
            )

            Box(
                modifier = Modifier
                    .size(VolleyDimens.DIMEN_24.dp)
                    .align(Alignment.CenterStart)
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = onBackClick
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = null,
                    tint = VolleyColor.White,
                )
            }
        }
    }

    @Composable
    fun DividerLine(
        modifier: Modifier = Modifier,
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = VolleyColor.Divider,
            modifier = modifier
        )
    }

    // из 62 ветки, из файла ChangeTeamScreen.kt
    @Composable
    fun LevelBadge(level: String, modifier: Modifier = Modifier) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .clip(RoundedCornerShape(VolleyDimens.DIMEN_10.dp))
                .background(VolleyColor.GreyDark)
                //.padding(
                //    start = VolleyDimens.DIMEN_10.dp,
                //    end = VolleyDimens.DIMEN_10.dp,
                //    top = VolleyDimens.DIMEN_2.dp,
                //    bottom = VolleyDimens.DIMEN_2.dp
                //)
                .height(VolleyDimens.DIMEN_23.dp)
                .width(VolleyDimens.DIMEN_30.dp)
        ) {
            VolleyText.BodyRegular(level, color = VolleyColor.White)
        }
    }

    // элемент для выбора игроков. Отличается PlayerRow из СрфтпуеeamScreen порядком элементов
   /* @Composable
    @Stable
    fun PlayerCheckRow (
        member: MemberUi,
        showActions: Boolean,
        onRemove: () -> Unit
        Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
    .fillMaxWidth()
    .heightIn(min = VolleyDimens.DIMEN_23.dp)
    ) {
        VolleyText.BodyRegular(
            text = member.name ?: stringResource(R.string.free_spot),
            color = VolleyColor.White,
            modifier = Modifier.weight(1f)
        )

        if (showActions) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp)
            ) {
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(VolleyDimens.DIMEN_21.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_remove),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(VolleyDimens.DIMEN_21.dp)
                    )
                }
                member.level?.let { LevelBadge(it) }
            }
        }
    }*/
}


@Preview
@Composable
private fun PreviewTitleWithBackArrow() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            Column {
                VolleySimpleComponent.TitleWithBackArrow(
                    title = "Title",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(VolleyDimens.DIMEN_20.dp)
                )
                VolleySimpleComponent.TitleWithBackArrow(
                    title = "Some long title",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(VolleyDimens.DIMEN_20.dp)
                )
                VolleySimpleComponent.DividerLine(
                    modifier = Modifier
                        .padding(VolleyDimens.DIMEN_20.dp)
                        .fillMaxWidth()
                )
                VolleySimpleComponent.LevelBadge(
                    "L",
                    modifier = Modifier
                        .padding(VolleyDimens.DIMEN_20.dp)
                 )
                VolleySimpleComponent.LevelBadge(
                    "M",
                    modifier = Modifier
                        .padding(VolleyDimens.DIMEN_20.dp)
                )
                VolleySimpleComponent.LevelBadge(
                    "H",
                    modifier = Modifier
                        .padding(VolleyDimens.DIMEN_20.dp)
                )
            }
        }
    }
}
