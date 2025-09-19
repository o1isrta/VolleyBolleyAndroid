package cy.volleybolley.core.presentation.ui.screens.authorization.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleyTextFieldAttribute
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyButton.GroupButtonsForGender2
import cy.volleybolley.core.presentation.ui.component.VolleyButton.GroupButtonsForLevel
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.AboutLevelsRoute
import cy.volleybolley.core.presentation.ui.navigation.HomeRoute

@Suppress("detekt.CognitiveComplexMethod")
@Composable
fun RegistrationScreen(
    navController: NavHostController,
    vm: RegistrationViewModel = viewModel()
) {
    val state by vm.uiState.collectAsState()
    LaunchedEffect(Unit) {
        vm.uiEffect.collect { effect ->
            when (effect) {
                is RegistrationEffect.NavigateToAboutLevels -> navController.navigate(AboutLevelsRoute)
                is RegistrationEffect.NavigateToHome -> navController.navigate(HomeRoute)
                else -> {}
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        VolleyContainersRootTransparent.GlassContainer(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = VolleyDimens.DIMEN_8.dp,
                    end = VolleyDimens.DIMEN_8.dp,
                    top = VolleyDimens.DIMEN_8.dp,
                    bottom = VolleyDimens.DIMEN_4.dp
                ),
            cornerRadius = VolleyDimens.DIMEN_24,
            contentAlignmentOnContainer = Alignment.TopStart
        ) {
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(scrollState)
                    .padding(VolleyDimens.DIMEN_20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                VolleyText.TitleLarge(
                    text = stringResource(id = R.string.registration),
                    color = VolleyColor.White
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_8.dp))

                VolleyText.BodyBold(
                    text = stringResource(id = R.string.name),
                    color = VolleyColor.White
                )
                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_8.dp))
                VolleyTextFieldGradient.SimpleGradientTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = state.name,
                    hint = stringResource(id = R.string.name),
                    actionToTransferContent = { value -> vm.obtainEvent(RegistrationEvent.NameChanged(value)) }
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(VolleyDimens.DIMEN_1.dp)
                        .background(VolleyColor.TextCalendarLightGrey)
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

                VolleyText.BodyBold(
                    text = stringResource(id = R.string.surname),
                    color = VolleyColor.White
                )
                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_8.dp))
                VolleyTextFieldGradient.SimpleGradientTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = state.surname,
                    hint = stringResource(id = R.string.surname),
                    actionToTransferContent = { value -> vm.obtainEvent(RegistrationEvent.SurnameChanged(value)) }
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(VolleyDimens.DIMEN_1.dp)
                        .background(VolleyColor.TextCalendarLightGrey)
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

                VolleyText.BodyBold(
                    text = stringResource(id = R.string.gender),
                    color = VolleyColor.White
                )
                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_8.dp))
                GroupButtonsForGender2(
                    checkId = state.gender,
                    modifier = Modifier.fillMaxWidth(),
                    onSelected = { id -> vm.obtainEvent(RegistrationEvent.GenderSelected(id)) }
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(VolleyDimens.DIMEN_1.dp)
                        .background(VolleyColor.TextCalendarLightGrey)
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

                VolleyText.BodyBold(
                    text = stringResource(id = R.string.date_of_bith),
                    color = VolleyColor.White
                )
                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_8.dp))
                VolleyTextFieldAttribute.DatePickerField(
                    modifier = Modifier.fillMaxWidth(),
                    inputDate = state.dateOfBirthMillis,
                    actionForSaveDate = { millis -> vm.obtainEvent(RegistrationEvent.DateOfBirthChanged(millis)) }
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(VolleyDimens.DIMEN_1.dp)
                        .background(VolleyColor.TextCalendarLightGrey)
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    VolleyText.BodyBold(
                        text = stringResource(id = R.string.level),
                        color = VolleyColor.White
                    )
                    Spacer(modifier = Modifier.width(VolleyDimens.DIMEN_8.dp))
                    Box(
                        modifier = Modifier
                            .size(VolleyDimens.DIMEN_24.dp)
                            .clickable { vm.obtainEvent(RegistrationEvent.AboutLevelsClicked) },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_info_placeholder),
                            contentDescription = null,
                            modifier = Modifier.size(VolleyDimens.DIMEN_20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_8.dp))
                GroupButtonsForLevel(
                    checkId = state.level,
                    modifier = Modifier.fillMaxWidth(),
                    onSelected = { id -> vm.obtainEvent(RegistrationEvent.LevelSelected(id)) }
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(VolleyDimens.DIMEN_1.dp)
                        .background(VolleyColor.TextCalendarLightGrey)
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

                VolleyText.BodyBold(
                    text = stringResource(id = R.string.your_county),
                    color = VolleyColor.White
                )
                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(VolleyDimens.DIMEN_52.dp)
                        .background(VolleyColor.White, shape = RoundedCornerShape(VolleyDimens.DIMEN_16.dp))
                        .padding(start = VolleyDimens.DIMEN_16.dp, end = VolleyDimens.DIMEN_16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    VolleyText.BodyRegular(
                        text = if (state.country.isEmpty()) stringResource(id = R.string.your_county) else state.country,
                        color = VolleyColor.TextField
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = null,
                        tint = VolleyColor.TextDark,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = VolleyDimens.DIMEN_16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(VolleyDimens.DIMEN_1.dp)
                        .background(VolleyColor.TextCalendarLightGrey)
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

                VolleyText.BodyBold(
                    text = stringResource(id = R.string.your_city),
                    color = VolleyColor.White
                )
                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(VolleyDimens.DIMEN_52.dp)
                        .background(VolleyColor.White, shape = RoundedCornerShape(VolleyDimens.DIMEN_16.dp))
                        .padding(start = VolleyDimens.DIMEN_16.dp, end = VolleyDimens.DIMEN_16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    VolleyText.BodyRegular(
                        text = if (state.city.isEmpty()) stringResource(id = R.string.your_city) else state.city,
                        color = VolleyColor.TextField
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = null,
                        tint = VolleyColor.TextDark,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = VolleyDimens.DIMEN_16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_24.dp))

                VolleyButton.ActiveButton(
                    text = stringResource(id = R.string.get_started),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(VolleyDimens.DIMEN_56.dp),
                    onClick = { vm.obtainEvent(RegistrationEvent.GetStartedClicked) }
                )

                Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_20.dp))
            }
        }
    }
}

@Composable
private fun GroupButtonsForGender2(
    checkId: Int = 1,
    modifier: Modifier,
    onSelected: (Int) -> Unit
) {
    cy.volleybolley.core.presentation.ui.component.VolleyButton.GroupButtonsForGender2(
        checkId = checkId,
        modifier = modifier,
        onSelected = onSelected
    )
}

@Composable
private fun GroupButtonsForLevel(
    checkId: Int = 1,
    modifier: Modifier,
    onSelected: (Int) -> Unit
) {
    cy.volleybolley.core.presentation.ui.component.VolleyButton.GroupButtonsForLevel(
        checkId = checkId,
        modifier = modifier,
        onSelected = onSelected
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegistrationScreenPreview() {
    RegistrationScreen(navController = rememberNavController())
}
