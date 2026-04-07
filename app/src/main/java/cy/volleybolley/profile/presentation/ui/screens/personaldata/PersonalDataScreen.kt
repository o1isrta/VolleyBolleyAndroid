package cy.volleybolley.profile.presentation.ui.screens.personaldata

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.domain.VolleyFeature
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.VolleyTextFieldAttribute
import cy.volleybolley.core.presentation.ui.VolleyTextFieldGradient
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.navigation.ChangePhotoRoute
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.CitySelect
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.CountrySelect
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.DateSelect
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.GenderSelect
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.NameChanged
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.OnAvatarEditClick
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.OnBackFromPersonalDataClick
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.OnUpdateButtonClick
import cy.volleybolley.profile.presentation.ui.screens.personaldata.PersonalDataScreenEvent.SurnameChanged
import cy.volleybolley.profile.presentation.ui.screens.personaldata.model.GenderType

@Composable
fun PersonalDataScreen(
    onNavigateToChangePhoto: (String?) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: PersonalDataScreenViewModel,
    paddingFromSystemUi: PaddingValues,
) {
    viewModel.handleBackAvatar()
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value
    val context = LocalContext.current

    LaunchedEffect(effect) {
        when (effect) {
            is PersonalDataScreenEffect.NavigateFromPersonalDataScreen -> {
                when (val route = effect.route) {
                    is ChangePhotoRoute -> {
                        onNavigateToChangePhoto(route.avatarUrl)
                    }

                    else -> onNavigateBack()
                }
            }

            is PersonalDataScreenEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }

            null -> {}
        }
    }

    PersonalDataScreen(
        state = state,
        eventCallback = { event -> viewModel.obtainEvent(event) },
        modifier = Modifier.padding(paddingFromSystemUi)
    )
}

@Composable
private fun PersonalDataScreen(
    modifier: Modifier = Modifier,
    state: PersonalDataScreenState,
    eventCallback: (PersonalDataScreenEvent) -> Unit,
) {
    VolleyContainersRootTransparent.TransparentContainer(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            VolleySimpleComponent.TitleWithBackArrow(
                title = stringResource(R.string.personal_data),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                onBackClick = { eventCallback(OnBackFromPersonalDataClick) }
            )

            ChangeFieldsBlock(
                modifier = Modifier,
                state = state,
                eventCallback = eventCallback
            )
        }

        VolleyButton.ActiveButton(
            enabled = state.buttonEnabled,
            isLoading = state.isLoading,
            text = stringResource(R.string.update),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(20.dp)
                .height(44.dp)
        ) { eventCallback(OnUpdateButtonClick) }
    }
}

@Composable
private fun ChangeFieldsBlock(
    modifier: Modifier = Modifier,
    state: PersonalDataScreenState,
    eventCallback: (PersonalDataScreenEvent) -> Unit,
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            AvatarBlock(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                avatarString = state.avatar,
                onIconClick = { eventCallback(OnAvatarEditClick) }
            )
        }

        item {
            PersonalDataTextMark(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(R.string.name)
            )

            VolleyTextFieldGradient.SimpleGradientTextField(
                hint = stringResource(R.string.name),
                text = state.name,
                actionToTransferContent = { eventCallback(NameChanged(it)) }
            )
        }

        item {
            PersonalDataTextMark(
                modifier = Modifier.padding(top = 14.dp, bottom = 8.dp),
                text = stringResource(R.string.surname)
            )

            VolleyTextFieldGradient.SimpleGradientTextField(
                hint = stringResource(R.string.surname),
                text = state.surname,
                actionToTransferContent = { eventCallback(SurnameChanged(it)) }
            )
        }

        item {
            PersonalDataDivider(modifier = Modifier.padding(bottom = 16.dp, top = 14.dp))
        }

        item {
            PersonalDataTextMark(
                modifier = Modifier.padding(bottom = 8.dp),
                text = stringResource(R.string.gender)
            )

            if (VolleyFeature.IS_GENDER_CHANGE_AVAILABLE) {
                VolleyButton.SingleChoiceButtonGroup(
                    items = listOf(GenderType.MALE, GenderType.FEMALE),
                    selected = GenderType.getById(state.genderId),
                    label = { it.displayText },
                    modifier = Modifier,
                    paddingValues = PaddingValues(10.dp),
                    onSelect = { eventCallback(GenderSelect(it.id)) }
                )
            } else {
                VolleyButton.ActiveGradientButton(
                    modifier = Modifier,
                    text = GenderType.getById(state.genderId).displayText,
                    onClick = {}
                )
            }
        }

        item {
            PersonalDataDivider(modifier = Modifier.padding(vertical = 16.dp))
        }

        item {
            PersonalDataTextMark(
                modifier = Modifier.padding(bottom = 8.dp),
                text = stringResource(R.string.date_of_birth)
            )

            VolleyTextFieldAttribute.DatePickerField(
                inputDate = state.dateOfBirthMillis,
                actionForSaveDate = { eventCallback(DateSelect(it)) }
            )
        }

        item {
            PersonalDataDivider(modifier = Modifier.padding(vertical = 16.dp))
        }

        item {
            VolleyTextFieldGradient.GradientSpinner(
                modifier = Modifier.fillMaxWidth(),
                selectedItem = state.selectedCountry,
                itemList = state.countryList,
                getTextByItem = { it?.name ?: "" },
                hint = stringResource(id = R.string.your_country),
                onItemSelect = { country, _ -> eventCallback(CountrySelect(country!!)) }
            )
        }

        item {
            PersonalDataDivider(modifier = Modifier.padding(vertical = 16.dp))
        }

        item {
            VolleyTextFieldGradient.GradientSpinner(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 64.dp),
                selectedItem = state.selectedCity,
                itemList = state.cityList,
                getTextByItem = { it?.name ?: "" },
                hint = stringResource(id = R.string.your_city),
                onItemSelect = { city, _ -> eventCallback(CitySelect(city!!)) }
            )
        }
    }
}

@Composable
private fun PersonalDataDivider(
    modifier: Modifier = Modifier
) {
    VolleySimpleComponent.DividerLine(
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
private fun PersonalDataTextMark(
    modifier: Modifier = Modifier,
    text: String,
) {
    VolleyText.BodyBold(
        text = text,
        color = VolleyColor.White,
        textAlign = TextAlign.Start,
        maxLines = 1,
        modifier = modifier.fillMaxWidth(),
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
                size = 122.dp
            )

            Image(
                painter = painterResource(R.drawable.ic_edit_avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        0.dp,
                        0.dp,
                        10.dp,
                        6.dp
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
                    .padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_9_PRO)
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
                state = PersonalDataScreenState(genderId = 1),
                eventCallback = { }
            )
        }
    }
}
