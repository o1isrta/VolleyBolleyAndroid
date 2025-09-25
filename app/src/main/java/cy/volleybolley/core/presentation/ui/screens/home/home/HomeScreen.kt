package cy.volleybolley.core.presentation.ui.screens.home.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeScreenViewModel = koinViewModel(),
    finisher: () -> Unit,
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    HomeScreen(
        state = state,
        effect = effect,
        navigateAction = { route -> navController.navigate(route) },
        eventCallback = { event -> viewModel.obtainEvent(event) }
    )

    BackHandler { finisher() }
}

@Composable
private fun HomeScreen(
    state: HomeScreenState,
    effect: HomeScreenEffect?,
    navigateAction: (NavMap) -> Unit,
    eventCallback: (HomeScreenEvent) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            painter = painterResource(R.drawable.home_wallpaper),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .aspectRatio(1f)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = VolleyDimens.DIMEN_20.dp,
                    bottom = VolleyDimens.DIMEN_20.dp,
                    start = VolleyDimens.DIMEN_8.dp,
                    end = VolleyDimens.DIMEN_8.dp
                )
                .align(Alignment.BottomCenter)
        ) {

        }
    }

    LaunchedEffect(effect) {
        when(effect){
            is HomeScreenEffect.NavigateFromHomeScreen -> navigateAction(effect.route)
            null -> Unit
        }
    }
}

@Composable
private fun CreateTourneyButton(
    onClick: () -> Unit,
) {
    val shape = remember { RoundedCornerShape(VolleyDimens.DIMEN_32.dp) }
    val insidePaddings = remember {
        PaddingValues(
            top = VolleyDimens.DIMEN_20.dp,
            start = VolleyDimens.DIMEN_20.dp
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(color = VolleyColor.YellowPro, shape = shape)
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = onClick
            )
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            painter = painterResource(R.drawable.crown),
            contentDescription = null
        )

        VolleyText.TitleLarge(
            modifier = Modifier
                .padding(insidePaddings)
                .rotate(VolleyDimens.ROTATION_8),
            text = stringResource(R.string.create_a_tourney),
            color = VolleyColor.TextDark
        )
    }
}

@Composable
private fun DonateButton(
    onClick: () -> Unit,
) {
    val insidePaddings = remember {
        PaddingValues(
            top = VolleyDimens.DIMEN_28.dp,
            start = VolleyDimens.DIMEN_30.dp
        )
    }

    VolleyContainersRootTransparent.TransparentContainer(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = onClick
            )
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            painter = painterResource(R.drawable.donate_heart),
            contentDescription = null
        )

        VolleyText.TitleLarge(
            modifier = Modifier
                .padding(insidePaddings)
                .rotate(VolleyDimens.ROTATION_8),
            text = stringResource(R.string.donate),
            color = VolleyColor.White
        )
    }
}

@Preview
@Composable
private fun PreviewCreateTourneyButton() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(260.dp)
            .background(VolleyColor.TurquoiseDark)
    ) {
        Box(
            Modifier.size(180.dp)
        ) {
            CreateTourneyButton {  }
        }
    }
}

@Preview
@Composable
private fun PreviewDonateButton() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(260.dp)
            .background(VolleyColor.TurquoiseDark)
    ) {
        Box(
            Modifier.size(180.dp)
        ) {
            DonateButton {  }
        }
    }
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            HomeScreen(
                state = HomeScreenState(),
                effect = null,
                navigateAction = {},
                eventCallback = {}
            )
        }
    }
}
