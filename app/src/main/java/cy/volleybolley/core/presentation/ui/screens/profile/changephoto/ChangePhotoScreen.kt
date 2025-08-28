package cy.volleybolley.core.presentation.ui.screens.profile.changephoto

import android.content.Context
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEffect.NavigateFromChangePhotoScreen
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEvent.GetAvatarFromPersonalData
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEvent.OnBackFromChangePhotoClick
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEvent.OnCameraPhotoCreate
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEvent.OnDeletePhotoClick
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEvent.OnGalleryPhotoSelect
import cy.volleybolley.core.presentation.ui.screens.profile.changephoto.ChangePhotoScreenEvent.OnSaveButtonClick
import cy.volleybolley.core.presentation.ui.screens.profile.personaldata.model.BackAvatarHolder
import org.koin.androidx.compose.koinViewModel
import java.io.File

@Composable
fun ChangePhotoScreen(
    avatarFromPersonalData: String? = null,
    navController: NavHostController,
    viewModel: ChangePhotoScreenViewModel = koinViewModel(),
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    ChangePhotoScreen(
        inputAvatar = avatarFromPersonalData,
        state = state,
        effect = effect,
        navigateAction = { newAvatar ->
            newAvatar?.let {
                navController.previousBackStackEntry?.savedStateHandle?.set(BackAvatarHolder.AVATAR_KEY, it)
                navController.popBackStack()
            } ?: navController.popBackStack()
        },
        eventCallback = { event -> viewModel.obtainEvent(event) }
    )
}

@Composable
private fun ChangePhotoScreen(
    inputAvatar: String? = null,
    state: ChangePhotoScreenState,
    effect: ChangePhotoScreenEffect?,
    navigateAction: (String?) -> Unit,
    eventCallback: (ChangePhotoScreenEvent) -> Unit,
) {
    LaunchedEffect(inputAvatar) {
        eventCallback(GetAvatarFromPersonalData(inputAvatar))
    }

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val gradientBrush = remember {
        Brush.verticalGradient(
            colors = listOf(
                VolleyColor.YellowForGradient,
                VolleyColor.GreenForGradient
            )
        )
    }
    val menuShape = remember { RoundedCornerShape(VolleyDimens.DIMEN_32.dp) }

    // Create PhotoPicker request
    val galleryPhotoPicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            eventCallback(OnGalleryPhotoSelect(it.toString()))
        }
    }

    // Create template file and define it`s uri
    val cameraPhotoFile = createPhotoFile(context)
    val cameraPhotoUri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        cameraPhotoFile
    )

    // Create Camera Request
    val cameraPhotoPicker = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) eventCallback(OnCameraPhotoCreate(cameraPhotoFile.absolutePath))
    }

    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = VolleyDimens.DIMEN_32,
        modifier = Modifier
            .fillMaxWidth()
            .padding(VolleyDimens.DIMEN_8.dp, 0.dp)
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_20.dp)
        ) {
            VolleySimpleComponent.TitleWithBackArrow(
                title = stringResource(R.string.change_photo),
                modifier = Modifier.fillMaxWidth(),
                onBackClick = { eventCallback(OnBackFromChangePhotoClick) }
            )

            Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))
            Avatar(
                modifier = Modifier.fillMaxWidth(),
                avatarString = state.avatarUrl,
            )
            Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))

            Column(
                modifier = Modifier
                    .background(VolleyColor.White, menuShape)
                    .border(
                        width = 1.dp,
                        brush = gradientBrush,
                        shape = menuShape
                    )
                    .padding(VolleyDimens.DIMEN_20.dp)
            ) {
                MenuComponent(
                    painter = painterResource(R.drawable.ic_photo_gallery),
                    title = stringResource(R.string.choose_from_gallery)
                ) {
                    galleryPhotoPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }

                ChangePhotoScreenDivider()

                MenuComponent(
                    painter = painterResource(R.drawable.ic_photo_camera),
                    title = stringResource(R.string.take_photo)
                ) {
                    cameraPhotoPicker.launch(cameraPhotoUri)
                }

                ChangePhotoScreenDivider()

                MenuComponent(
                    painter = painterResource(R.drawable.ic_photo_delete),
                    title = stringResource(R.string.delete_photo)
                ) { eventCallback(OnDeletePhotoClick) }
            }

            Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))

            VolleyButton.ActiveButton(
                enabled = state.buttonEnabled,
                text = stringResource(R.string.save),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(VolleyDimens.DIMEN_44.dp)
            ) { eventCallback(OnSaveButtonClick) }
        }
    }

    LaunchedEffect(effect) {
        when (effect) {
            is NavigateFromChangePhotoScreen -> navigateAction(effect.avatarArgument)
            null -> {}
        }
    }
}

private fun createPhotoFile(context: Context): File {
    // Create an image file name
    val fileName = "volleyPhoto_${System.currentTimeMillis()}"
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        fileName,
        ".jpg",
        storageDir
    )
}

@Composable
private fun Avatar(
    modifier: Modifier = Modifier,
    avatarString: String? = null,
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
                        0.dp,
                        0.dp,
                        VolleyDimens.DIMEN_10.dp,
                        VolleyDimens.DIMEN_6.dp
                    )
            )
        }
    }
}

@Stable
@Composable
private fun MenuComponent(
    painter: Painter,
    title: String,
    onComponentClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = onComponentClick
            )
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = VolleyColor.TextDark
        )

        VolleyText.BodyRegular(
            text = title,
            color = VolleyColor.TextDark,
            maxLines = 1,
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = VolleyDimens.DIMEN_8.dp,
                    vertical = 0.dp
                )
        )
    }
}

@Composable
private fun ChangePhotoScreenDivider() {
    VolleySimpleComponent.DividerLine(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, VolleyDimens.DIMEN_16.dp)
    )
}

@Preview
@Composable
private fun PreviewChangePhotoScreen() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            ChangePhotoScreen(
                state = ChangePhotoScreenState(),
                effect = null,
                navigateAction = {}
            ) { }
        }
    }
}
