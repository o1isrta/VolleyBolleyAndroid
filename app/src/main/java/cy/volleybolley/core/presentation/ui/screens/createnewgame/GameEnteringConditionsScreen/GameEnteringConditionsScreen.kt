package cy.volleybolley.core.presentation.ui.screens.createnewgame.GameEnteringConditionsScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyCashField
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.VolleyTextFieldAttribute
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GameEnteringConditionsScreen(navController: NavHostController,
                                 viewModel: GameEnteringConditionsScreenViewModel = GameEnteringConditionsScreenViewModel()) {

    val scrollState = rememberScrollState() //Состояние скролла
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel.uiEffect) { // подписываемся на Effect
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is GameEnteringConditionsScreenEffect.ShowError -> {
                    Toast.makeText(context, "Error: ${effect.message}", Toast.LENGTH_SHORT).show()
                }
                is GameEnteringConditionsScreenEffect.AccountExists -> {
                    Toast.makeText(context, "Account exists with number: ${effect.accountNumber}", Toast.LENGTH_SHORT).show()
                }
                GameEnteringConditionsScreenEffect.AccountNotExists -> {
                    Toast.makeText(context, "Account does not exist. Please create one.", Toast.LENGTH_SHORT).show()
                }
                GameEnteringConditionsScreenEffect.NavigateToPayments -> {
                    navController.navigate("PaymentsScreen")
                }
                GameEnteringConditionsScreenEffect.NavigateBack  -> {
                    navController.popBackStack()
                }
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
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

            TitleWithBackArrow(
                title = stringResource(R.string.create_a_game),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

            VolleyText.TitleMedium(
                text = stringResource(R.string.maximum_players),
                modifier = Modifier.fillMaxWidth(),
                color = VolleyColor.White
            )

            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

            VolleyTextFieldAttribute.CountField(
                inputCount = 8,
                actionToTransferCount = {}
            )

            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

            VolleyText.TitleMedium(
                text = stringResource(R.string.privacy),
                modifier = Modifier.fillMaxWidth(),
                color = VolleyColor.White
            )

            VolleyText.BodyLight(
                text = stringResource(R.string.set_privacy_if_you_want_to_play_with_particular_players),
                modifier = Modifier.fillMaxWidth(),
                color = VolleyColor.White,
                textAlign = TextAlign.Left
            )

            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

            VolleyButton.GroupButtonsForPrivacy(
                modifier = Modifier.padding(vertical = 12.dp),
                onSelected = {}
            )

            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

            VolleySimpleComponent.DividerLine()

            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

            VolleyText.TitleMedium(
                text = stringResource(R.string.payment),
                modifier = Modifier.fillMaxWidth(),
                color = VolleyColor.White
            )

            VolleyText.BodyLight(
                text = stringResource(R.string.enter_the_participation_fee_per_person),
                modifier = Modifier.fillMaxWidth(),
                color = VolleyColor.White,
                textAlign = TextAlign.Left
            )

            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier//.fillMaxWidth()
            ) {
                VolleyText.BodyBold(
                    text = stringResource(R.string.per_person),
                    color = VolleyColor.White
                )

                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_9.dp))

                VolleyCashField.CashField(
                    value = "0",
                    currency = "$"
                ) { }
            }

            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                VolleyText.BodyRegular(
                    text = stringResource(R.string.current_account),
                    modifier = Modifier,//.fillMaxWidth(),
                    color = VolleyColor.White
                )

                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_9.dp))

                VolleyCashField.CashField(
                    value = "0",
                    currency = "$"
                ) { }
            }

            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

            VolleyButton.ActiveButton(
                modifier = Modifier
                    .padding(0.dp, VolleyDimens.DIMEN_8.dp, 0.dp, VolleyDimens.DIMEN_16.dp)
                    .height(44.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                text = stringResource(R.string.next_game),
                onClick = {}
            )
        }
    }
    } else {
        CircularProgressIndicator()
    }

//        Button(onClick = { navController.popBackStack() }) {
//            Text("Назад")
//        }
}

fun navigateToPrivacyOptionsScreen(accountNumber: Any) {

}

@Preview
@Composable
private fun GameEnteringConditionsScreenPreview() {
    val navController = rememberNavController() // Создаем моковый NavHostController
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        GameEnteringConditionsScreen(navController = navController)
    }
}
