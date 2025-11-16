package cy.volleybolley.core.presentation.ui.screens.createnewgame.PrivacyOptionsScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.navigation.PaymentsRoute
import cy.volleybolley.core.presentation.ui.navigation.SuccessRoute
import cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen.GameEnteringConditionsScreenEffect
import cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen.GameEnteringConditionsScreenEvent
import cy.volleybolley.core.presentation.ui.screens.home.success.SucceedGame
import cy.volleybolley.core.presentation.ui.screens.home.success.SucceedGameType
import cy.volleybolley.profile.domain.model.PaymentType
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.json.Json

@Composable
fun PrivacyOptionsScreen(navController: NavHostController,
                         viewModel: PrivacyOptionsScreenViewModel = viewModel()
) {
    val scrollState = rememberScrollState() //Состояние скролла
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel.uiEffect) { // подписываемся на Effect
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
//                is GameEnteringConditionsScreenEffect.ShowError -> {
//                    Toast.makeText(context, "Error: ${effect.message}", Toast.LENGTH_SHORT).show()
//                }
//                GameEnteringConditionsScreenEffect.NavigateToPayments -> {
//                    navController.navigate(PaymentsRoute)
//                }
//                GameEnteringConditionsScreenEffect.NavigateBack -> {
//                    navController.popBackStack()
//                }
                //Обработка всех возможных случаев
                else -> {
                    // Handle unexpected effect or do nothing.  Log it!
                    Log.w("GameEnteringConditionsScreen", "Unhandled effect: $effect")
                }
            }
        }
    }

    // Overlay для отображения индикатора загрузки
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator() // Или другой индикатор загрузки
        }
    } else {  // Отображаем основной контент, только если не загружается
        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = VolleyDimens.DIMEN_32,
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = VolleyDimens.DIMEN_20.dp)
            ) {
                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

//                TitleWithBackArrow(
//                    title = stringResource(R.string.create_a_game),
//                    modifier = Modifier.fillMaxWidth(),
//                    onBackClick = { viewModel.obtainEvent(GameEnteringConditionsScreenEvent.OnBackClicked) }
//                )
            }
        }
    }
}
