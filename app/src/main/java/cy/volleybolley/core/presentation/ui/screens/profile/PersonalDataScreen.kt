package cy.volleybolley.core.presentation.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import cy.volleybolley.core.presentation.ui.screens.profile.effect.PersonalDataScreenEffect
import cy.volleybolley.core.presentation.ui.screens.profile.event.PersonalDataScreenEvent
import cy.volleybolley.core.presentation.ui.screens.profile.state.PersonalDataScreenState
import cy.volleybolley.core.presentation.ui.screens.profile.viewmodel.PersonalDataScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PersonalDataScreen(
    navController: NavHostController,
    viewModel: PersonalDataScreenViewModel = koinViewModel(),
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    PersonalDataScreen(
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
private fun PersonalDataScreen(
    modifier: Modifier = Modifier,
    state: PersonalDataScreenState,
    effect: PersonalDataScreenEffect?,
    navigateAction: (NavMap?) -> Unit,
    eventCallback: (PersonalDataScreenEvent) -> Unit,
) {

}

@Composable
private fun PersonalDataDivider() {
    VolleySimpleComponent.DividerLine(
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
private fun AvatarBlock(
    modifier: Modifier = Modifier,
    avatarString: String? = null,
    onIconClick: () -> Unit = {}

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
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = onIconClick
                    )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewAvatarBlock() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            AvatarBlock(
                modifier = Modifier.fillMaxWidth().padding(VolleyDimens.DIMEN_8.dp)
            )
        }
    }
}
