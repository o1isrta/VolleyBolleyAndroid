package cy.volleybolley.core.presentation.ui.screens.authorization.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.RootContainer
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleyTextFieldAttribute
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyButton.GroupButtonsForGender2
import cy.volleybolley.core.presentation.ui.component.VolleyButton.GroupButtonsForLevel
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    paddingFromSystemUi: PaddingValues,
    viewModel: RegistrationViewModel = koinViewModel(),
    onShowAboutLevelsFaqRequested: () -> Unit,
    onRegistrationSuccessEvent: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    LaunchedEffect(Unit) {
        when (effect) {
            is RegistrationEffect.NavigateToAboutLevels -> onShowAboutLevelsFaqRequested()
            is RegistrationEffect.NavigateToHome -> onRegistrationSuccessEvent()
            else -> {}
        }
    }
    RegistrationScreen(
        paddingFromSystemUi = paddingFromSystemUi,
        state = state,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Composable
fun RegistrationScreen(
    paddingFromSystemUi: PaddingValues,
    state: RegistrationState,
    eventCallback: (RegistrationEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark)
    ) {
        VolleyContainersRootTransparent.TransparentContainer(
            modifier = Modifier
                .padding(
                    top = 8.dp + paddingFromSystemUi.calculateTopPadding(),
                    bottom = 4.dp + paddingFromSystemUi.calculateBottomPadding(),
                    start = 8.dp,
                    end = 8.dp
                )
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(VolleyDimens.DIMEN_20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    VolleyText.TitleLarge(
                        text = stringResource(id = R.string.registration),
                        color = VolleyColor.White
                    )
                }

                item {
                    VolleyText.BodyBold(
                        modifier = Modifier.padding(top = VolleyDimens.DIMEN_16.dp),
                        text = stringResource(id = R.string.name),
                        color = VolleyColor.White
                    )
                    VolleyTextFieldGradient.SimpleGradientTextField(
                        modifier = Modifier
                            .padding(top = VolleyDimens.DIMEN_8.dp)
                            .fillMaxWidth(),
                        text = state.name,
                        hint = stringResource(id = R.string.name),
                        actionToTransferContent = { value -> eventCallback(RegistrationEvent.NameChanged(value)) }
                    )
                }

                item {
                    VolleyText.BodyBold(
                        modifier = Modifier.padding(top = VolleyDimens.DIMEN_14.dp),
                        text = stringResource(id = R.string.surname),
                        color = VolleyColor.White
                    )
                    VolleyTextFieldGradient.SimpleGradientTextField(
                        modifier = Modifier
                            .padding(top = VolleyDimens.DIMEN_8.dp)
                            .fillMaxWidth(),
                        text = state.surname,
                        hint = stringResource(id = R.string.surname),
                        actionToTransferContent = { value -> eventCallback(RegistrationEvent.SurnameChanged(value)) }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 14.dp),
                        thickness = VolleyDimens.DIMEN_1.dp,
                        color = VolleyColor.TextCalendarLightGrey
                    )
                }

                item {
                    VolleyText.BodyBold(
                        modifier = Modifier.padding(top = 16.dp),
                        text = stringResource(id = R.string.gender),
                        color = VolleyColor.White
                    )
                    GroupButtonsForGender2(
                        checkId = state.gender,
                        modifier = Modifier.padding(top = 8.dp),
                        onSelected = { id -> eventCallback(RegistrationEvent.GenderSelected(id)) }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 16.dp),
                        thickness = VolleyDimens.DIMEN_1.dp,
                        color = VolleyColor.TextCalendarLightGrey
                    )
                }

                item {
                    VolleyText.BodyBold(
                        modifier = Modifier.padding(top = 16.dp),
                        text = stringResource(id = R.string.date_of_bith),
                        color = VolleyColor.White
                    )
                    VolleyTextFieldAttribute.DatePickerField(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        inputDate = state.dateOfBirthMillis,
                        actionForSaveDate = { millis -> eventCallback(RegistrationEvent.DateOfBirthChanged(millis)) }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 16.dp),
                        thickness = VolleyDimens.DIMEN_1.dp,
                        color = VolleyColor.TextCalendarLightGrey
                    )
                }

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                    ) {
                        VolleyText.BodyBold(
                            text = stringResource(id = R.string.level),
                            color = VolleyColor.White
                        )
                        Box(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .size(VolleyDimens.DIMEN_24.dp)
                                .clickable { eventCallback(RegistrationEvent.AboutLevelsClicked) },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_info_placeholder),
                                contentDescription = null,
                                modifier = Modifier.size(VolleyDimens.DIMEN_20.dp)
                            )
                        }
                    }
                    GroupButtonsForLevel(
                        checkId = state.level,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        onSelected = { id -> eventCallback(RegistrationEvent.LevelSelected(id)) }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 16.dp),
                        thickness = VolleyDimens.DIMEN_1.dp,
                        color = VolleyColor.TextCalendarLightGrey
                    )
                }

                item {
                    VolleyText.BodyBold(
                        modifier = Modifier.padding(top = 16.dp),
                        text = stringResource(id = R.string.your_county),
                        color = VolleyColor.White
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                            .height(VolleyDimens.DIMEN_52.dp)
                            .background(
                                color = VolleyColor.White,
                                shape = RoundedCornerShape(VolleyDimens.DIMEN_16.dp)
                            )
                            .padding(
                                start = VolleyDimens.DIMEN_16.dp,
                                end = VolleyDimens.DIMEN_16.dp
                            ),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        VolleyText.BodyRegular(
                            text = state.country.ifEmpty {
                                stringResource(id = R.string.your_county)
                            },
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
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 16.dp),
                        thickness = VolleyDimens.DIMEN_1.dp,
                        color = VolleyColor.TextCalendarLightGrey
                    )
                }

                item {
                    VolleyText.BodyBold(
                        modifier = Modifier.padding(top = 16.dp),
                        text = stringResource(id = R.string.your_city),
                        color = VolleyColor.White
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth()
                            .height(VolleyDimens.DIMEN_52.dp)
                            .background(VolleyColor.White, shape = RoundedCornerShape(VolleyDimens.DIMEN_16.dp))
                            .padding(start = VolleyDimens.DIMEN_16.dp, end = VolleyDimens.DIMEN_16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        VolleyText.BodyRegular(
                            text = state.city.ifEmpty { stringResource(id = R.string.your_city) },
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
                }

                item {
                    VolleyButton.ActiveButton(
                        text = stringResource(id = R.string.get_started),
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                            .height(VolleyDimens.DIMEN_56.dp),
                        onClick = { eventCallback(RegistrationEvent.GetStartedClicked) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegistrationScreenPreview() {
    RootContainer { paddingFromSystemUi, navController ->
        RegistrationScreen(
            paddingFromSystemUi = paddingFromSystemUi,
            state = RegistrationState(),
            eventCallback = {}
        )
    }
}
