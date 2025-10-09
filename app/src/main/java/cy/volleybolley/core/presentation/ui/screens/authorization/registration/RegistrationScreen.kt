package cy.volleybolley.core.presentation.ui.screens.authorization.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleyTextFieldAttribute
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient
import cy.volleybolley.core.presentation.ui.component.ScreenPreviewContainer
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
    onRequestNavigateToAboutLevels: () -> Unit,
    onRegistrationSuccessEvent: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)
    LaunchedEffect(effect) {
        when (effect) {
            is RegistrationEffect.NavigateToHome -> onRegistrationSuccessEvent()
            null -> {}
        }
    }
    RegistrationScreen(
        paddingFromSystemUi = paddingFromSystemUi,
        state = state,
        onRequestNavigateToAboutLevels = onRequestNavigateToAboutLevels,
        eventCallback = { viewModel.obtainEvent(it) }
    )
}

@Composable
fun RegistrationScreen(
    paddingFromSystemUi: PaddingValues,
    state: RegistrationState,
    onRequestNavigateToAboutLevels: () -> Unit,
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
            Column {
                VolleyText.TitleLarge(
                    modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                    text = stringResource(id = R.string.registration),
                    color = VolleyColor.White
                )
                FillRegistrationData(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                    state = state,
                    onRequestNavigateToAboutLevels = onRequestNavigateToAboutLevels,
                    eventCallback = eventCallback
                )
            }

            VolleyButton.ActiveButton(
                text = stringResource(id = R.string.get_started),
                enabled = state.isBtnRegistrationEnabled,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .height(VolleyDimens.DIMEN_56.dp),
                onClick = { eventCallback(RegistrationEvent.GetStartedClicked) }
            )
        }
    }
}

@Stable
@Composable
private fun FillRegistrationData(
    modifier: Modifier = Modifier,
    state: RegistrationState,
    onRequestNavigateToAboutLevels: () -> Unit,
    eventCallback: (RegistrationEvent) -> Unit
) {
    LazyColumn(modifier = modifier) {
        item {
            FillNameAndSurname(
                name = state.name,
                surname = state.surname,
                onTypeName = { eventCallback(RegistrationEvent.NameChanged(it)) },
                onTypeSurname = { eventCallback(RegistrationEvent.SurnameChanged(it)) }
            )
        }

        item {
            GenderChooser(
                modifier = Modifier.padding(top = 16.dp),
                selectedGenderIndex = state.gender,
                onGenderClick = { eventCallback(RegistrationEvent.GenderSelected(it)) }
            )
        }

        item {
            FillDateOfBirth(
                modifier = Modifier.padding(top = 16.dp),
                dateOfBirthMillis = state.dateOfBirthMillis,
                onSelectDateOfBirth = { eventCallback(RegistrationEvent.DateOfBirthChanged(it)) }
            )
        }

        item {
            LevelChooser(
                modifier = Modifier.padding(top = 16.dp),
                selectedLevelIndex = state.level,
                onLevelClick = { eventCallback(RegistrationEvent.LevelSelected(it)) },
                onRequestNavigateToAboutLevels = onRequestNavigateToAboutLevels
            )
        }

        item {
            VolleyTextFieldGradient.GradientSpinner(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                selectedItem = state.selectedCountry,
                itemList = state.countryList,
                getTextByItem = { it?.name ?: "" },
                hint = stringResource(id = R.string.your_country),
                onItemSelect = { item, _ -> eventCallback(RegistrationEvent.CountrySelected(item!!)) }
            )
            HorizontalDivider(
                modifier = Modifier.padding(top = 16.dp),
                thickness = VolleyDimens.REGISTRATION_DIVIDER_THICKNESS.dp,
                color = VolleyColor.TextCalendarLightGrey
            )
        }

        item {
            VolleyTextFieldGradient.GradientSpinner(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 188.dp)
                    .fillMaxWidth(),
                selectedItem = state.selectedCity,
                itemList = state.cityList,
                getTextByItem = { it?.name ?: "" },
                hint = stringResource(id = R.string.your_city),
                onItemSelect = { item, _ -> eventCallback(RegistrationEvent.CitySelected(item!!)) }
            )
        }
    }
}

@Stable
@Composable
private fun FillNameAndSurname(
    modifier: Modifier = Modifier,
    name: String,
    surname: String,
    onTypeName: (String) -> Unit,
    onTypeSurname: (String) -> Unit
) {
    Column(modifier = modifier) {
        VolleyTextFieldGradient.GradientTextFieldWithLabel(
            modifier = Modifier.fillMaxWidth(),
            text = name,
            hint = stringResource(R.string.name),
            actionToTransferContent = onTypeName
        )
        VolleyTextFieldGradient.GradientTextFieldWithLabel(
            modifier = Modifier
                .padding(top = 14.dp)
                .fillMaxWidth(),
            text = surname,
            hint = stringResource(R.string.surname),
            actionToTransferContent = onTypeSurname
        )
        HorizontalDivider(
            modifier = Modifier.padding(top = 14.dp),
            thickness = VolleyDimens.REGISTRATION_DIVIDER_THICKNESS.dp,
            color = VolleyColor.TextCalendarLightGrey
        )
    }
}

@Stable
@Composable
private fun GenderChooser(
    modifier: Modifier = Modifier,
    selectedGenderIndex: Int,
    onGenderClick: (index: Int) -> Unit
) {
    Column(modifier = modifier) {
        VolleyText.BodyBold(
            text = stringResource(id = R.string.gender),
            color = VolleyColor.White
        )
        GroupButtonsForGender2(
            checkId = selectedGenderIndex,
            modifier = Modifier.padding(top = 8.dp),
            onSelected = onGenderClick
        )
        HorizontalDivider(
            modifier = Modifier.padding(top = 16.dp),
            thickness = VolleyDimens.REGISTRATION_DIVIDER_THICKNESS.dp,
            color = VolleyColor.TextCalendarLightGrey
        )
    }
}

@Stable
@Composable
private fun FillDateOfBirth(
    modifier: Modifier = Modifier,
    dateOfBirthMillis: Long?,
    onSelectDateOfBirth: (Long?) -> Unit
) {
    Column(modifier = modifier) {
        VolleyText.BodyBold(
            text = stringResource(id = R.string.date_of_bith),
            color = VolleyColor.White
        )
        VolleyTextFieldAttribute.DatePickerField(
            modifier = Modifier.padding(top = 8.dp),
            inputDate = dateOfBirthMillis,
            actionForSaveDate = onSelectDateOfBirth
        )
        HorizontalDivider(
            modifier = Modifier.padding(top = 16.dp),
            thickness = VolleyDimens.REGISTRATION_DIVIDER_THICKNESS.dp,
            color = VolleyColor.TextCalendarLightGrey
        )
    }
}

@Stable
@Composable
private fun LevelChooser(
    modifier: Modifier = Modifier,
    selectedLevelIndex: Int,
    onLevelClick: (index: Int) -> Unit,
    onRequestNavigateToAboutLevels: () -> Unit
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            VolleyText.BodyBold(
                text = stringResource(id = R.string.level),
                color = VolleyColor.White
            )
            IconButton(
                modifier = Modifier
                    .padding(top = 1.dp, start = 8.dp)
                    .size(18.dp),
                onClick = onRequestNavigateToAboutLevels
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_info_placeholder),
                    contentDescription = stringResource(R.string.registration_about_levels_navigation_hint)
                )
            }
        }
        GroupButtonsForLevel(
            checkId = selectedLevelIndex,
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(),
            onSelected = onLevelClick
        )
        HorizontalDivider(
            modifier = Modifier.padding(top = 16.dp),
            thickness = VolleyDimens.REGISTRATION_DIVIDER_THICKNESS.dp,
            color = VolleyColor.TextCalendarLightGrey
        )
    }
}

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun RegistrationScreenPreview() {
    ScreenPreviewContainer {
        RegistrationScreen(
            paddingFromSystemUi = PaddingValues(0.dp),
            state = RegistrationState(),
            onRequestNavigateToAboutLevels = {},
            eventCallback = {}
        )
    }
}
