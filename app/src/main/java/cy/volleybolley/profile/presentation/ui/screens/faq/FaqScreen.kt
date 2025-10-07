package cy.volleybolley.profile.presentation.ui.screens.faq

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import cy.volleybolley.core.presentation.ui.model.VolleyMocks
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.profile.presentation.ui.screens.faq.FaqScreenEffect.NavigateFromFaqScreen
import cy.volleybolley.profile.presentation.ui.screens.faq.FaqScreenEvent.OnBackFromFaqClick
import cy.volleybolley.profile.presentation.ui.screens.faq.model.FaqString
import cy.volleybolley.profile.presentation.ui.screens.faq.model.FaqStringType
import org.koin.androidx.compose.koinViewModel

@Composable
fun FaqScreen(
    navController: NavHostController,
    viewModel: FaqScreenViewModel = koinViewModel(),
    paddingFromSystemUi: PaddingValues,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    FaqScreen(
        state = state,
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
private fun FaqScreen(
    modifier: Modifier = Modifier,
    state: FaqScreenState,
    effect: FaqScreenEffect?,
    navigateAction: (NavMap?) -> Unit,
    eventCallback: (FaqScreenEvent) -> Unit,
) {
    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = VolleyDimens.DIMEN_32,
        modifier = modifier
            .fillMaxWidth()
            .padding(VolleyDimens.DIMEN_8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_20.dp)
        ) {
            VolleySimpleComponent.TitleWithBackArrow(
                title = stringResource(R.string.faq),
                modifier = Modifier.fillMaxWidth(),
                onBackClick = { eventCallback(OnBackFromFaqClick) }
            )
            Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))

            LazyColumn(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(state.faqText) { index, faqString ->
                    FaqBlock(faqString, index)
                }
            }
        }
    }

    LaunchedEffect(effect) {
        when (effect) {
            is NavigateFromFaqScreen -> navigateAction(effect.route)
            null -> {}
        }
    }
}

@Composable
private fun FaqBlock(faqString: FaqString, index: Int) {
    when (faqString.type) {
        FaqStringType.HEADER -> {
            if (index != 0) {
                FaqScreenDivider(
                    paddingValues = PaddingValues(
                        horizontal = VolleyDimens.DIMEN_0.dp,
                        vertical = VolleyDimens.DIMEN_16.dp,
                    )
                )
            }

            VolleyText.TitleMedium(
                text = faqString.value,
                color = VolleyColor.White,
                textAlign = TextAlign.Start,
                maxLines = 1,
            )
            Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))
        }

        FaqStringType.BULLET -> {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                VolleyText.BodyRegular(
                    text = VolleyUiUtil.FAQ_BULLET_OUT_PREFIX,
                    color = VolleyColor.White,
                    textAlign = TextAlign.Start
                )
                VolleyText.BodyRegular(
                    text = faqString.value,
                    color = VolleyColor.White,
                    textAlign = TextAlign.Start
                )
            }
        }

        FaqStringType.REGULAR -> {
            VolleyText.BodyRegular(
                text = faqString.value,
                color = VolleyColor.White,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
private fun FaqScreenDivider(
    paddingValues: PaddingValues,
) {
    VolleySimpleComponent.DividerLine(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
    )
}

@Preview(showBackground = true, showSystemUi = false, heightDp = 1200)
@Composable
private fun PreviewAboutScreen() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            val state = FaqScreenState(faqText = VolleyUiUtil.parseMarkdown(VolleyMocks.MOCK_FAQ))
            FaqScreen(
                state = state,
                effect = null,
                navigateAction = {},
                eventCallback = {},
            )
        }
    }
}
