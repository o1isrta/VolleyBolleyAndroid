package cy.volleybolley.core.presentation.ui.screens.createnewgame

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleyMessageTextField
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.component.PreviewContainer
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar.TopBar
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.ButtonSText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.ButtonXSText

@Composable
fun BasicGameSetupScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        TitleWithBackArrow(
            title = stringResource(R.string.create_a_game),
            modifier = Modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp)
        )

        VolleyText.TitleMedium(
            text = stringResource(R.string.your_message),
            modifier = Modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp),
            color = VolleyColor.White
        )

        VolleyMessageTextField.MessageField(
            hint = stringResource(R.string.leave_a_note_for_players),
            textInput = "",
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_20.dp, 0.dp)
        ) { }

        HorizontalLine()

        VolleyText.TitleMedium(
            text = stringResource(R.string.place),
            modifier = Modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp),
            color = VolleyColor.White
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(VolleyDimens.DIMEN_44.dp)
                .padding(VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_4.dp, VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(R.drawable.ic_mark_yellow),
                contentDescription = null,
                //modifier = Modifier//.size(width = 16.dp, height = 15.dp)
            )
            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.width(140.dp)
            ) {
                VolleyText.BodyBold(
                    text = stringResource(R.string.place),
                    modifier = Modifier
                        .fillMaxWidth(),
                       // .padding(VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp),
                    color = VolleyColor.White
                )
                VolleyText.BodyLight(
                    text = stringResource(R.string.place),
                    modifier = Modifier
                        .fillMaxWidth(),
                       // .padding(VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp),
                    color = VolleyColor.White
                )
            }
            VolleyButton.ActiveGradientButton(
                modifier = Modifier.height(VolleyDimens.DIMEN_44.dp),
                text = "Create"
            ) { }
            Spacer(modifier = Modifier.size(size = 8.dp))

        }

//        VolleyContainersRootTransparent.TransparentContainer(
//            cornerRadius = VolleyDimens.DIMEN_16,
//            modifier = Modifier
//                .padding(VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp)
//        ) {
//            VolleyText.BodyRegular(
//                text = "Hi! Just old friends meet at the court, beer afterwards, no entry fee, come see us :)",
//                color = VolleyColor.White,
//                modifier = Modifier
//                    .padding(VolleyDimens.DIMEN_16.dp)
//                    .height(VolleyDimens.DIMEN_90.dp)
//                    .fillMaxWidth()
//
//            )
//        }



    }
//    Button(onClick = { navController.popBackStack() }) {
//        Text("Назад")
//    }
}
@Composable
fun HorizontalLine(
) {
    HorizontalDivider(
        modifier = Modifier.padding(VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp),
        color = VolleyColor.Divider,
        thickness = VolleyDimens.DIMEN_1.dp
    )
}

@Preview
@Composable
private fun GameSetupScreenPreview() {
    val navController = rememberNavController() // Создаем моковый NavHostController
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        BasicGameSetupScreen(navController = navController)
    }
}
