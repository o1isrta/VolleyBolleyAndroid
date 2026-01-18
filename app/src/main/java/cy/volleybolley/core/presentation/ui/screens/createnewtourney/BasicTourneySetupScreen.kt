package cy.volleybolley.core.presentation.ui.screens.createnewtourney

import androidx.compose.runtime.Composable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.tooling.preview.Preview

/*const val DEFAULT_START_HOUR = 14
const val DEFAULT_START_MINUTES = 0
const val DEFAULT_FINISH_HOUR = 15
const val DEFAULT_FINISH_MINUTES = 0*/

@Composable
fun BasicTourneySetupScreen(/*navController: NavHostController*/) {
    val scrollState = rememberScrollState() // Состояние скролла
    /* Column(
         modifier = Modifier
             .verticalScroll(scrollState)
     ) {
         VolleyContainersRootTransparent.TransparentContainer(
             cornerRadius = VolleyDimens.DIMEN_32,
             modifier = Modifier
                 .padding(VolleyDimens.DIMEN_8.dp)
         ) {
             BasicTourneySetupScreenContent()
         }
         VolleyButton.ActiveButton(
             modifier = Modifier
                 .padding(
                     VolleyDimens.DIMEN_8.dp,
                     VolleyDimens.DIMEN_8.dp,
                     VolleyDimens.DIMEN_8.dp,
                     VolleyDimens.DIMEN_16.dp
                 )
                 .height(VolleyDimens.DIMEN_44.dp)
                 .align(Alignment.CenterHorizontally)
                 .fillMaxWidth(),
             text = stringResource(R.string.next_game),
             onClick = {}
         )
     }*/
}

/*@Composable
fun HorizontalLine() {
    HorizontalDivider(
        modifier = Modifier,
        color = VolleyColor.Divider,
        thickness = VolleyDimens.DIMEN_1.dp
    )
}*/

@Composable
fun BasicTourneySetupScreenContent() { /*
    Column(
        modifier = Modifier
            .padding(horizontal = VolleyDimens.DIMEN_20.dp)
    ) {
        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))

        TitleWithBackArrow(
            title = stringResource(R.string.create_a_tourney),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

        VolleyText.TitleMedium(
            text = stringResource(R.string.your_message),
            modifier = Modifier.fillMaxWidth(),
            color = VolleyColor.White
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

        VolleyMessageTextField.MessageField(
            hint = stringResource(R.string.leave_a_note_for_players),
            textInput = "",
            modifier = Modifier
        ) { }

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

        HorizontalLine()

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

        VolleyText.TitleMedium(
            text = stringResource(R.string.place),
            modifier = Modifier.fillMaxWidth(),
            color = VolleyColor.White
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .height(VolleyDimens.DIMEN_44.dp)
                .fillMaxWidth()
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
                modifier = Modifier,
                text = "Create"
            ) { }
        }

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

        HorizontalLine()

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

        VolleyText.TitleMedium(
            text = stringResource(R.string.date),
            modifier = Modifier.fillMaxWidth(),
            color = VolleyColor.White
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

        VolleyButton.GroupButtonsForDate2(
            checkId = 1,
            modifier = Modifier,
            onSelected = {}
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_10.dp))

        // здесь будет календарь

        VolleyText.BodyBold(
            text = stringResource(R.string.game_duration),
            modifier = Modifier,
            color = VolleyColor.White
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            VolleyText.BodyRegular(
                text = stringResource(R.string.from),
                modifier = Modifier,
                color = VolleyColor.White
            )

            Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_8.dp))

            VolleyTextFieldAttribute.DurationFieldWithArrows(
                inputTime = VolleyTimeStamp(
                    DEFAULT_START_HOUR,
                    DEFAULT_START_MINUTES,
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
                    DEFAULT_FINISH_HOUR,
                    DEFAULT_FINISH_MINUTES,
                    true
                )
            ) { }
        }

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
        HorizontalLine()
        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

        VolleyText.TitleMedium(
            text = stringResource(R.string.tourney_type),
            modifier = Modifier.fillMaxWidth(),
            color = VolleyColor.White
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

        VolleyButton.GroupButtonsForTourneyType(
            modifier = Modifier,
            onSelected = {}
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
        HorizontalLine()
        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

        VolleyText.TitleMedium(
            text = stringResource(R.string.gender),
            modifier = Modifier.fillMaxWidth(),
            color = VolleyColor.White
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

        VolleyButton.GroupButtonsForGender3(
            modifier = Modifier,
            onSelected = {}
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))
        HorizontalLine()
        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_16.dp))

        VolleyText.TitleMedium(
            text = stringResource(R.string.player_level),
            modifier = Modifier.fillMaxWidth(),
            color = VolleyColor.White
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_12.dp))

        VolleyButton.GroupButtonsForLevelMulti(
            checkedLevels = setOf(Level.Hard),
            modifier = Modifier,
            onSelected = {}
        )

        Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_20.dp))
    }*/
}

@Preview
@Composable
private fun BasicTourneySetupScreenPreview() {
    /*    val navController = rememberNavController() // Создаем моковый NavHostController
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            BasicTourneySetupScreen(*//*navController = navController*//*)
    }*/
}
