package cy.volleybolley.profile.presentation.ui.screens.faq

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyMocks
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.profile.presentation.ui.screens.faq.FaqScreenEffect.NavigateFromFaqScreen
import cy.volleybolley.profile.presentation.ui.screens.faq.FaqScreenEvent.OnBackFromFaqClick
import cy.volleybolley.profile.presentation.ui.screens.faq.model.FaqString
import cy.volleybolley.profile.presentation.ui.screens.faq.model.FaqStringType
import org.koin.androidx.compose.koinViewModel

@Composable
fun FaqScreen(
    paddingFromSystemUi: PaddingValues,
    onNavigateBack: () -> Unit,
    viewModel: FaqScreenViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value
    val context = LocalContext.current

    LaunchedEffect(effect) {
        when (effect) {
            is NavigateFromFaqScreen -> onNavigateBack()

            is FaqScreenEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }

            null -> {}
        }
    }

    FaqScreen(
        state = state,
        eventCallback = { event -> viewModel.obtainEvent(event) },
        modifier = Modifier.padding(paddingFromSystemUi)
    )
}

@Composable
private fun FaqScreen(
    modifier: Modifier = Modifier,
    state: FaqScreenState,
    eventCallback: (FaqScreenEvent) -> Unit,
) {
    VolleyContainersRootTransparent.TransparentContainer(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            VolleySimpleComponent.TitleWithBackArrow(
                title = stringResource(R.string.faq),
                modifier = Modifier.fillMaxWidth(),
                onBackClick = { eventCallback(OnBackFromFaqClick) }
            )

            LazyColumn(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                itemsIndexed(state.faqText) { index, faqString ->
                    FaqBlock(faqString, index)
                }
            }
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
                        horizontal = 0.dp,
                        vertical = 16.dp,
                    )
                )
            }

            VolleyText.TitleMedium(
                text = faqString.value,
                color = VolleyColor.White,
                textAlign = TextAlign.Start,
                maxLines = 1,
                modifier = Modifier.padding(bottom = 16.dp)
            )
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
                eventCallback = {},
            )
        }
    }
}
