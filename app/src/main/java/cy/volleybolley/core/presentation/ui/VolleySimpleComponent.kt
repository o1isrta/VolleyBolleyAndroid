package cy.volleybolley.core.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText

object VolleySimpleComponent {
    @Stable
    @Composable
    fun TitleWithBackArrow(
        modifier: Modifier = Modifier,
        title: String,
        onBackClick: () -> Unit = {}
    ) {
        Row(
            modifier = modifier
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(VolleyDimens.DIMEN_24.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.back_arrow_button),
                    tint = VolleyColor.White,
                )
            }

            VolleyText.TitleLarge(
                text = title,
                color = VolleyColor.White,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
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
                VolleySimpleComponent.TitleWithBackArrow(
                    title = "Some long long long title",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(VolleyDimens.DIMEN_20.dp)
                )
                VolleySimpleComponent.DividerLine(
                    modifier = Modifier
                        .padding(VolleyDimens.DIMEN_20.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}
