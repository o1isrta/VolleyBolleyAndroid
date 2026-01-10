package cy.volleybolley.core.presentation.ui.screens.authorization.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.screens.authorization.launch.LogoWithAppName

@Composable
fun OnboardingScreen(
    onNextScreenRequested: () -> Unit,
    paddingFromSystemUi: PaddingValues
) {
    val density = LocalDensity.current
    var logoWithNameHeight by remember { mutableStateOf(IntSize.Zero) }
    val buttonTopPadding = with(density) { logoWithNameHeight.height.toDp() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_launch),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
        ) {
            VolleyText.TitleXL(
                modifier = Modifier.padding(
                    top = paddingFromSystemUi.calculateTopPadding() + VolleyDimens.DIMEN_40.dp,
                    start = 26.dp
                ),
                text = stringResource(id = R.string.welcome),
                color = VolleyColor.White
            )

            VolleyText.HeroBody(
                modifier = Modifier.padding(top = 16.dp, start = 26.dp, end = 32.dp),
                text = stringResource(id = R.string.app_description),
                color = VolleyColor.White
            )
        }

        LogoWithAppName(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) { size ->
            logoWithNameHeight = size
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    start = VolleyDimens.DIMEN_20.dp,
                    end = VolleyDimens.DIMEN_20.dp,
                    top = buttonTopPadding + VolleyDimens.DIMEN_90.dp,
                )
        ) {
            VolleyButton.ActiveButton(
                text = stringResource(id = R.string.get_started),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(VolleyDimens.DIMEN_44.dp),
                onClick = onNextScreenRequested
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OnboardingScreenPreview() {
    VolleyContainersRootTransparent.Root { paddingValues ->
        OnboardingScreen(
            onNextScreenRequested = {},
            paddingFromSystemUi = paddingValues
        )
    }
}
