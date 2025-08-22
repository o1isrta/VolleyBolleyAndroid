package cy.volleybolley.core.presentation.ui.screens.profile.changephoto

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEffect.NavigateFromChangePhotoScreen
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEvent.GetAvatarFromPersonalData
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEvent.OnBackFromChangePhotoClick
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChangePhotoScreen(
    avatarFromPersonalData: String? = null,
    navController: NavHostController,
    viewModel: ChangePhotoScreenViewModel = koinViewModel(),
    ) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    ChangePhotoScreen(
        inputAvatar = avatarFromPersonalData,
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
private fun ChangePhotoScreen(
    inputAvatar: String? = null,
    state: ChangePhotoScreenState,
    effect: ChangePhotoScreenEffect?,
    navigateAction: (NavMap?) -> Unit,
    eventCallback: (ChangePhotoScreenEvent) -> Unit,
) {
    LaunchedEffect(inputAvatar) {
        eventCallback(GetAvatarFromPersonalData(inputAvatar))
    }

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
                title = stringResource(R.string.change_photo),
                modifier = Modifier.fillMaxWidth(),
                onBackClick = { eventCallback(OnBackFromChangePhotoClick) }
            )

            Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))
            Avatar(
                modifier = Modifier.fillMaxWidth(),
                avatarString = state.avatarUrl,
            )
            Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))



        }
    }

    LaunchedEffect(effect) {
        when(effect) {
            is NavigateFromChangePhotoScreen -> navigateAction(effect.route)
            null -> {}
        }
    }

}

@Composable
private fun Avatar(
    modifier: Modifier = Modifier,
    avatarString: String? = null,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Box {
            VolleyAvatar.CircularAvatar(
                avatar = avatarString,
                size = VolleyDimens.DIMEN_122.dp
            )
            Image(
                painter = painterResource(R.drawable.ic_edit_avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        0.dp,
                        0.dp,
                        VolleyDimens.DIMEN_10.dp,
                        VolleyDimens.DIMEN_6.dp
                    )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewChangePhotoScreen() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            ChangePhotoScreen(
                state = ChangePhotoScreenState(),
                effect = null,
                navigateAction = {}
            ) { }
        }
    }
}
