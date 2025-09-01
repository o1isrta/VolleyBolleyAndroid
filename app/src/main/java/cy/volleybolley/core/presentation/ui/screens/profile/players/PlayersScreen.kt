package cy.volleybolley.core.presentation.ui.screens.profile.players

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.firebase.annotations.concurrent.Background
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEffect.NavigateFromPlayersScreen
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEvent.ClickOnBackFromPlayers
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEvent.ClickOnSearchButton
import cy.volleybolley.core.presentation.ui.screens.profile.players.PlayersScreenEvent.SearchTextChanged

@Composable
fun PlayersScreen(
    navController: NavHostController,
    viewModel: PlayersScreenViewModel,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    PlayersScreen(
        state = state,
        effect = effect,
        eventCallback = { event -> viewModel.obtainEvent(event) },
        navigateAction = { route ->
            route?.let {
                navController.navigate(it)
            } ?: navController.popBackStack()
        }
    )
}

@Composable
private fun PlayersScreen(
    state: PlayersScreenState,
    effect: PlayersScreenEffect?,
    navigateAction: (NavMap?) -> Unit,
    eventCallback: (PlayersScreenEvent) -> Unit,
){
    val scrollState = rememberScrollState()

    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = VolleyDimens.DIMEN_32,
        modifier = Modifier
            .fillMaxWidth()
            .padding(VolleyDimens.DIMEN_8.dp, 0.dp)
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_20.dp)
        ) {
            VolleySimpleComponent.TitleWithBackArrow(
                title = stringResource(R.string.players),
                modifier = Modifier.fillMaxWidth(),
                onBackClick = { eventCallback(ClickOnBackFromPlayers) }
            )

            Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))

            VolleyTextFieldGradient.SearchField(
                text = state.searchText,
                actionToTransferContent = { fieldText -> eventCallback(SearchTextChanged(fieldText)) },
                actionOnInputComplete = { playerName -> eventCallback(ClickOnSearchButton(playerName)) }
            )

            Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))

            PlayersListModeSwitch(
                onAllClick = {},
                onFavoriteClick = {},
            )
        }
    }

    LaunchedEffect(effect) {
        when(effect) {
            is NavigateFromPlayersScreen -> navigateAction(effect.route)
            null -> {}
        }
    }
}

@Composable
private fun PlayersListModeSwitch(
    paddingValues: PaddingValues = PaddingValues(),
    height: Int = VolleyDimens.DIMEN_32,
    cornerRadius: Int = VolleyDimens.DIMEN_16,
    showAllPlayers: Boolean = true,
    onAllClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    val gradientBrush = remember {
        Brush.verticalGradient(
            colors = listOf(
                VolleyColor.YellowForGradient,
                VolleyColor.GreenForGradient
            )
        )
    }

    val shape = RoundedCornerShape(cornerRadius.dp)

    Box(
        modifier = Modifier.padding(paddingValues)
            .height(height.dp)
            .fillMaxWidth()
            .background(gradientBrush, shape)
    ) {
        Row(Modifier.padding(VolleyDimens.DIMEN_2.dp)) {
            ChangedBackgroundBox(
                text = stringResource(R.string.all_players),
                shape = shape,
                showBackground = showAllPlayers,
                onTextClick = onAllClick,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(VolleyDimens.DIMEN_8.dp))

            ChangedBackgroundBox(
                text = stringResource(R.string.favorites),
                shape = shape,
                showBackground = !showAllPlayers,
                onTextClick = onFavoriteClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ChangedBackgroundBox(
    modifier: Modifier = Modifier,
    text: String,
    shape: Shape,
    showBackground: Boolean,
    onTextClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxHeight()
            .background(
                color = if (showBackground) VolleyColor.White else Color.Transparent,
                shape = shape
            )
    ) {
        VolleyText.BodyRegular(
            text = text,
            color = VolleyColor.TextDark,
            maxLines = 1,
            modifier = Modifier.clickable(
                interactionSource = null,
                indication = null,
                onClick = onTextClick
            )
        )
    }
}

@Preview
@Composable
private fun PreviewPlayersScreen() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            PlayersScreen(
                state = PlayersScreenState(),
                effect = null,
                navigateAction = {},
                eventCallback = {}
            )
        }
    }
}
