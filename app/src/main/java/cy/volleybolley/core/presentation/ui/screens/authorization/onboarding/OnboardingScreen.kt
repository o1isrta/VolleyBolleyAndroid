package cy.volleybolley.core.presentation.ui.screens.authorization.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.component.ScreenPreviewContainer
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            VolleyText.TitleXL(
                modifier = Modifier.padding(
                    top = paddingFromSystemUi.calculateTopPadding() + 40.dp,
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
            LogoWithAppName(modifier = Modifier
                .padding(top = 80.dp)
                .fillMaxWidth())
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = paddingFromSystemUi.calculateBottomPadding() + 50.dp),
                contentAlignment = Alignment.Center
            ) {
                VolleyButton.ActiveButton(
                    text = stringResource(id = R.string.get_started),
                    modifier = Modifier
                        .width(VolleyDimens.DIMEN_335.dp)
                        .height(VolleyDimens.DIMEN_44.dp),
                    onClick = onNextScreenRequested
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun OnboardingScreenPreview() {
    ScreenPreviewContainer {
        OnboardingScreen(
            onNextScreenRequested = {},
            paddingFromSystemUi = PaddingValues(0.dp)
        )
    }
}
