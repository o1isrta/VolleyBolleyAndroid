package cy.volleybolley.profile.presentation.ui.screens.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.profile.presentation.ui.screens.about.AboutScreenEffect.NavigateFromAboutScreen
import cy.volleybolley.profile.presentation.ui.screens.about.AboutScreenEvent.OnBackFromAboutClick
import cy.volleybolley.profile.presentation.ui.screens.about.AboutScreenEvent.OnStateInitialiseByResources
import org.koin.androidx.compose.koinViewModel

@Composable
fun AboutScreen(
    navController: NavHostController,
    viewModel: AboutScreenViewModel = koinViewModel(),
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    AboutScreen(
        state = state,
        effect = effect,
        navigateAction = { route ->
            route?.let {
                navController.navigate(it)
            } ?: navController.popBackStack()
        },
        eventCallback = { event -> viewModel.obtainEvent(event) }
    )
}

@Composable
private fun AboutScreen(
    state: AboutScreenState,
    effect: AboutScreenEffect?,
    navigateAction: (NavMap?) -> Unit,
    eventCallback: (AboutScreenEvent) -> Unit,
) {
    val scrollState = rememberScrollState()
    if (!state.isInitializedState) {
        eventCallback(
            OnStateInitialiseByResources(
                founderName = stringResource(R.string.about_founder_value),
                designersNames = stringResource(R.string.about_designers_value),
                developersNames = stringResource(R.string.about_developers_value),
            )
        )
    }

    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = VolleyDimens.DIMEN_32,
        modifier = Modifier
            .fillMaxWidth()
            .padding(VolleyDimens.DIMEN_8.dp)
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_20.dp)
        ) {
            VolleySimpleComponent.TitleWithBackArrow(
                title = stringResource(R.string.about),
                modifier = Modifier.fillMaxWidth(),
                onBackClick = { eventCallback(OnBackFromAboutClick) }
            )

            Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))

            AboutTextLine(
                title = stringResource(R.string.founder),
                value = state.founder,
                modifier = Modifier.fillMaxWidth()
            )

            AboutScreenDivider()

            AboutTextLine(
                title = stringResource(R.string.designed_by),
                value = state.designedBy,
                modifier = Modifier.fillMaxWidth()
            )

            AboutScreenDivider()

            AboutTextLine(
                title = stringResource(R.string.developed_by),
                value = state.developedBy,
                modifier = Modifier.fillMaxWidth()
            )
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

        Spacer(Modifier.width(VolleyDimens.DIMEN_8.dp))

        VolleyText.BodyRegular(
            text = value,
            textAlign = TextAlign.Start,
            color = VolleyColor.White,
            modifier = Modifier.weight(VolleyUiUtil.ABOUT_SCREEN_CONTENT_WEIGHT)
        )
    }

}

@Composable
private fun AboutScreenDivider() {
    VolleySimpleComponent.DividerLine(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = VolleyDimens.DIMEN_0.dp,
                vertical = VolleyDimens.DIMEN_16.dp
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
                state = AboutScreenState(
                    founder = "Name Surname",
                    designedBy = "Name Surname\nLongName LongSurname\nSuperLongName SuperLongSurname\nName Surname",
                    developedBy = "Name Surname\nName Surname\nName Surname"
                ),
                effect = null,
                navigateAction = {},
                eventCallback = {},
            )
        }
    }
}
