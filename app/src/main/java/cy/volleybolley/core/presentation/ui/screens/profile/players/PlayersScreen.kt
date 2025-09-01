package cy.volleybolley.core.presentation.ui.screens.profile.players

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.core.presentation.ui.screens.profile.personaldata.PersonalDataScreenEffect
import cy.volleybolley.core.presentation.ui.screens.profile.personaldata.PersonalDataScreenEvent
import cy.volleybolley.core.presentation.ui.screens.profile.personaldata.PersonalDataScreenState

@Composable
fun PlayersScreen(
    navController: NavHostController,
    viewModel: PlayersScreenViewModel,
) {

}

@Composable
private fun PlayersScreen(
    state: PersonalDataScreenState,
    effect: PersonalDataScreenEffect?,
    navigateAction: (NavMap?) -> Unit,
    eventCallback: (PersonalDataScreenEvent) -> Unit,
){

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
            //////
        }
    }
}
