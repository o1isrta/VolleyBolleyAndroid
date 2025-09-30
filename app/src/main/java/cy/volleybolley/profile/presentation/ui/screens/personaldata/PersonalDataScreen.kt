package cy.volleybolley.profile.presentation.ui.screens.personaldata

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleyTextFieldAttribute
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.NavMap
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEffect.NavigateFromPersonalDataScreen
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.CitySelect
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.CountrySelect
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.DateSelect
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.GenderSelect
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.NameChanged
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.OnAvatarEditClick
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.OnBackFromPersonalDataClick
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.OnUpdateButtonClick
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.SurnameChanged

@Composable
fun PersonalDataScreen(
    navController: NavHostController,
    viewModel: PersonalDataScreenViewModel,
    paddingFromSystemUi: PaddingValues,
) {
    viewModel.handleBackAvatar()
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
        eventCallback = { event -> viewModel.obtainEvent(event) },
        modifier = Modifier.padding(paddingFromSystemUi)
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
    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = VolleyDimens.DIMEN_32,
        modifier = modifier
            .fillMaxWidth()
            .padding(VolleyDimens.DIMEN_8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_20.dp)
        ) {
            VolleySimpleComponent.TitleWithBackArrow(
                title = stringResource(R.string.personal_data),
                modifier = Modifier.fillMaxWidth(),
                onBackClick = { eventCallback(OnBackFromPersonalDataClick) }
            )
            Spacer(Modifier.height(VolleyDimens.DIMEN_8.dp))

            Column(Modifier.verticalScroll(rememberScrollState())) {
                Spacer(Modifier.height(VolleyDimens.DIMEN_8.dp))
                AvatarBlock(
                    modifier = Modifier.fillMaxWidth(),
                    avatarString = state.avatar,
                    onIconClick = { eventCallback(OnAvatarEditClick) }
                )

                Spacer(Modifier.height(VolleyDimens.DIMEN_8.dp))
                PersonalDataTextMark(stringResource(R.string.name))
                Spacer(Modifier.height(VolleyDimens.DIMEN_8.dp))

                VolleyTextFieldGradient.SimpleGradientTextField(
                    hint = stringResource(R.string.name),
                    text = state.name,
                    actionToTransferContent = { eventCallback(NameChanged(it)) }
                )

                Spacer(Modifier.height(VolleyDimens.DIMEN_14.dp))
                PersonalDataTextMark(stringResource(R.string.surname))
                Spacer(Modifier.height(VolleyDimens.DIMEN_8.dp))

                VolleyTextFieldGradient.SimpleGradientTextField(
                    hint = stringResource(R.string.surname),
                    text = state.surname,
                    actionToTransferContent = { eventCallback(SurnameChanged(it)) }
                )

                Spacer(Modifier.height(VolleyDimens.DIMEN_14.dp))
                PersonalDataDivider()
                Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))
                PersonalDataTextMark(stringResource(R.string.gender))
                Spacer(Modifier.height(VolleyDimens.DIMEN_8.dp))

                VolleyButton.GroupButtonsForGender2(
                    modifier = Modifier,
                    checkId = state.genderId,
                    onSelected = { eventCallback(GenderSelect(it)) },
                )

                Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))
                PersonalDataDivider()
                Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))
                PersonalDataTextMark(stringResource(R.string.date_of_bith))
                Spacer(Modifier.height(VolleyDimens.DIMEN_8.dp))

                VolleyTextFieldAttribute.DatePickerField(
                    inputDate = state.dateOfBirthMillis,
                    actionForSaveDate = { eventCallback(DateSelect(it)) }
                )

                Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))
                PersonalDataDivider()
                Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))

                VolleyTextFieldGradient.GradientSpinner(
                    modifier = Modifier.fillMaxWidth(),
                    selectedItem = state.selectedCountry,
                    itemList = state.countryList,
                    getTextByItem = { it?.name ?: "" },
                    hint = stringResource(id = R.string.your_country),
                    onItemSelect = { country, _ -> eventCallback(CountrySelect(country!!)) }
                )

                Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))
                PersonalDataDivider()
                Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))

                VolleyTextFieldGradient.GradientSpinner(
                    modifier = Modifier.fillMaxWidth(),
                    selectedItem = state.selectedCity,
                    itemList = state.cityList,
                    getTextByItem = { it?.name ?: "" },
                    hint = stringResource(id = R.string.your_city),
                    onItemSelect = { city, _ -> eventCallback(CitySelect(city!!)) }
                )

                Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))

                VolleyButton.ActiveButton(
                    enabled = state.buttonEnabled,
                    text = stringResource(R.string.update),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(VolleyDimens.DIMEN_44.dp)
                ) { eventCallback(OnUpdateButtonClick) }
            }
        }
    }

    LaunchedEffect(effect) {
        when (effect) {
            is NavigateFromPersonalDataScreen -> navigateAction(effect.route)
            null -> {}
        }
    }
}

@Composable
private fun PersonalDataDivider() {
    VolleySimpleComponent.DividerLine(
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
private fun PersonalDataTextMark(
    text: String,
) {
    VolleyText.BodyBold(
        text = text,
        color = VolleyColor.White,
        textAlign = TextAlign.Start,
        maxLines = 1,
        modifier = Modifier.fillMaxWidth(),
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
                        VolleyDimens.DIMEN_0.dp,
                        VolleyDimens.DIMEN_0.dp,
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(VolleyDimens.DIMEN_8.dp)
            )
        }
    }
}

@Preview(showSystemUi = false, heightDp = 1000)
@Composable
private fun PreviewPersonalDataScreen() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            PersonalDataScreen(
                state = PersonalDataScreenState(),
                effect = null,
                navigateAction = {}
            ) { }
        }
    }
}
