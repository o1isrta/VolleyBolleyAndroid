package cy.volleybolley.registration.presentation.ui.screens.aboutlevels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyTypography
import cy.volleybolley.core.presentation.ui.util.safeTopPadding

@Stable
@Composable
fun AboutLevelsScreen(onBackNavigationRequested: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
            .safeTopPadding(extraTopPadding = 38.dp)
            .padding(horizontal = 16.dp)
    ) {
        VolleyContainersRootTransparent.TransparentContainer(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.TopCenter),
            cornerRadius = 32,
            mainContainerAlignment = Alignment.TopStart,
            contentContainerAlignment = Alignment.TopStart
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                VolleyTopBar.TopBarWithBackButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(id = R.string.about_levels),
                    onBackNavigationRequested = onBackNavigationRequested
                )

                LevelItem(
                    modifier = Modifier.padding(top = 16.dp),
                    prefixResId = R.string.level_light_prefix,
                    descriptionResId = R.string.level_light_description,
                    gradient = Brush.verticalGradient(
                        0f to VolleyColor.YellowForGradient,
                        1f to VolleyColor.GreenForGradient,
                    )
                )

                LevelItem(
                    modifier = Modifier.padding(top = 16.dp),
                    prefixResId = R.string.level_medium_prefix,
                    descriptionResId = R.string.level_medium_description,
                    gradient = Brush.verticalGradient(
                        0f to VolleyColor.YellowForGradient,
                        1f to VolleyColor.GreenForGradient,
                    )
                )

                LevelItem(
                    modifier = Modifier.padding(top = 16.dp),
                    prefixResId = R.string.level_hard_prefix,
                    descriptionResId = R.string.level_hard_description,
                    gradient = Brush.verticalGradient(
                        Pair(first = 0f, second = VolleyColor.YellowForGradient),
                        Pair(first = 0.49f, second = VolleyColor.GreenForGradient)
                    )
                )

                LevelItem(
                    modifier = Modifier.padding(top = 16.dp),
                    prefixResId = R.string.level_pro_prefix,
                    descriptionResId = R.string.level_pro_description,
                    gradient = Brush.verticalGradient(
                        Pair(first = 0f, second = VolleyColor.YellowForGradient),
                        Pair(first = 0.49f, second = VolleyColor.GreenForGradient)
                    )
                )
            }
        }
    }
}

@Stable
@Composable
private fun LevelItem(modifier: Modifier = Modifier, prefixResId: Int, descriptionResId: Int, gradient: Brush) {
    val annotated = buildAnnotatedString {
        withStyle(style = SpanStyle(brush = gradient, fontWeight = FontWeight.Bold)) {
            append(stringResource(id = prefixResId))
        }
        append(' ')
        append(stringResource(id = descriptionResId))
    }

    Text(
        modifier = modifier,
        text = annotated,
        style = VolleyTypography.BodyRegular,
        color = VolleyColor.White
    )
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_9_PRO)
@Composable
private fun AboutLevelsScreenPreview() {
    AboutLevelsScreen { }
}
