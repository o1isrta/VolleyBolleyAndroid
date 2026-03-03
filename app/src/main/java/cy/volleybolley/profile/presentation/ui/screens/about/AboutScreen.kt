package cy.volleybolley.profile.presentation.ui.screens.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.profile.presentation.ui.screens.about.AboutScreenEffect.NavigateFromAboutScreen
import cy.volleybolley.profile.presentation.ui.screens.about.AboutScreenEvent.OnBackFromAboutClick
import org.koin.androidx.compose.koinViewModel

@Composable
fun AboutScreen(
    navController: NavHostController,
    viewModel: AboutScreenViewModel = koinViewModel(),
    paddingFromSystemUi: PaddingValues,
) {
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    AboutScreen(
        effect = effect,
        navigateAction = { route ->
            route?.let {
                navController.navigate(it)
            } ?: navController.popBackStack()
        },
        eventCallback = { event -> viewModel.obtainEvent(event) },
        modifier = Modifier.padding(paddingFromSystemUi)
    )
}

@Composable
private fun AboutScreen(
    modifier: Modifier = Modifier,
    effect: AboutScreenEffect?,
    navigateAction: (NavMap?) -> Unit,
    eventCallback: (AboutScreenEvent) -> Unit,
) {
    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = 32,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            VolleySimpleComponent.TitleWithBackArrow(
                title = stringResource(R.string.about),
                modifier = Modifier.fillMaxWidth(),
                onBackClick = { eventCallback(OnBackFromAboutClick) }
            )
            Spacer(Modifier.height(8.dp))

            Column(Modifier.verticalScroll(rememberScrollState())) {
                Spacer(Modifier.height(8.dp))

                AboutTextLine(
                    title = stringResource(R.string.founder),
                    value = stringResource(R.string.about_founder_value),
                    modifier = Modifier.fillMaxWidth()
                )

                AboutScreenDivider(
                    bottomPadding = 22
                )

                AboutTextLine(
                    title = stringResource(R.string.designed_by),
                    value = stringResource(R.string.about_designers_value),
                    modifier = Modifier.fillMaxWidth()
                )

                AboutScreenDivider(
                    bottomPadding = 12
                )

                AboutTextLine(
                    title = stringResource(R.string.developed_by),
                    value = stringResource(R.string.about_developers_value),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    LaunchedEffect(effect) {
        when (effect) {
            is NavigateFromAboutScreen -> navigateAction(effect.route)
            null -> {}
        }
    }
}

@Composable
private fun AboutTextLine(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier
    ) {
        VolleyText.BodyBoldGradient(
            text = title,
            textAlign = TextAlign.Start,
            maxLines = 1,
            modifier = Modifier.weight(VolleyUiUtil.ABOUT_SCREEN_TITLES_WEIGHT)
        )

        Spacer(Modifier.width(8.dp))

        VolleyText.BodyRegular(
            text = value,
            textAlign = TextAlign.Start,
            color = VolleyColor.White,
            modifier = Modifier.weight(VolleyUiUtil.ABOUT_SCREEN_CONTENT_WEIGHT)
        )
    }

}

@Composable
private fun AboutScreenDivider(
    topPadding: Int = 16,
    bottomPadding: Int = 16,
) {
    VolleySimpleComponent.DividerLine(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 0.dp,
                end = 0.dp,
                top = topPadding.dp,
                bottom = bottomPadding.dp,
            )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewAboutScreen() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            AboutScreen(
                effect = null,
                navigateAction = {},
                eventCallback = {},
            )
        }
    }
}
