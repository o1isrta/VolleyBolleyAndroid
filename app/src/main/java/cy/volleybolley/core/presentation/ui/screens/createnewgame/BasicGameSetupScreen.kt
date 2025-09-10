package cy.volleybolley.core.presentation.ui.screens.createnewgame

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyMessageTextField
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent.TitleWithBackArrow
import cy.volleybolley.core.presentation.ui.VolleyTextFieldAttribute
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp

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
                .padding(VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp)
        ) { }

        HorizontalLine()

        VolleyText.TitleLarge(
            text = stringResource(R.string.place),
            modifier = Modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_12.dp),
            color = VolleyColor.White
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .height(VolleyDimens.DIMEN_60.dp)
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f), // Важно!  Занимает только часть доступного пространства,
                    horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_mark_yellow),
                    contentDescription = null,
                 )
                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    VolleyText.BodyBold(
                        text = "Karon Beach Club",
                        modifier = Modifier,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = VolleyColor.White
                    )
                    VolleyText.BodyLight(
                        text = "Patak Rd, Mueang Phuket",
                        modifier = Modifier,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = VolleyColor.White
                    )
                }
            }

            VolleyButton.ActiveGradientButton(
                modifier = Modifier.height(VolleyDimens.DIMEN_44.dp),
                text = "Create"
            ) { }
        }

        HorizontalLine()

        VolleyText.TitleLarge(
            text = stringResource(R.string.date),
            modifier = Modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_12.dp),
            color = VolleyColor.White
        )

        VolleyButton.GroupButtonsForDate2(
            checkId = 1,
            modifier = Modifier.padding(VolleyDimens.DIMEN_20.dp, 0.dp, VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_12.dp),
            onSelected = {}
        )

        // здесь будет календарь
            /* Box(
            modifier = Modifier
                .padding(20.dp, 0.dp, 20.dp, 6.dp)
                .fillMaxWidth()
                .height(266.dp)
                .clip(RoundedCornerShape(32.dp)) // Задаем скругление углов
                .background(VolleyColor.White) // Цвет прямоугольника
         )*/

        VolleyText.BodyBold(
            text = stringResource(R.string.game_duration),
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_2.dp, VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp),
               color = VolleyColor.White
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_20.dp, 0.dp)
        ) {
            VolleyText.BodyRegular(
                text = stringResource(R.string.from),
                modifier = Modifier,
                color = VolleyColor.White
            )
            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
            VolleyTextFieldAttribute.DurationFieldWithArrows(
                inputTime = VolleyTimeStamp(
                    14,
                    0,
                    true
                )
            ) { }
            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
            VolleyText.BodyRegular(
                text = stringResource(R.string.to),
                modifier = Modifier,
                color = VolleyColor.White
            )
            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))
            VolleyTextFieldAttribute.DurationFieldWithArrows(
                inputTime = VolleyTimeStamp(
                    15,
                    0,
                    true
                )
            ) { }
        }

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

        VolleyText.TitleLarge(
            text = stringResource(R.string.tourney_type),
            modifier = Modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_8.dp, VolleyDimens.DIMEN_20.dp, VolleyDimens.DIMEN_12.dp),
            color = VolleyColor.White
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

        VolleyButton.GroupButtonsForTourneyType(
            modifier = Modifier
                .padding(horizontal = 20.dp),
                onClick = {}
        )
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
