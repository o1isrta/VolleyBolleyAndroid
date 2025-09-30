package cy.volleybolley.profile.presentation.ui.screens.changephoto

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import cy.volleybolley.BuildConfig
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleySimpleComponent
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.profile.presentation.ui.screens.changephoto.ChangePhotoScreenEffect.NavigateFromChangePhotoScreen
import cy.volleybolley.profile.presentation.ui.screens.changephoto.ChangePhotoScreenEvent.GetAvatarFromPersonalData
import cy.volleybolley.profile.presentation.ui.screens.changephoto.ChangePhotoScreenEvent.OnBackFromChangePhotoClick
import cy.volleybolley.profile.presentation.ui.screens.changephoto.ChangePhotoScreenEvent.OnCameraPhotoCreate
import cy.volleybolley.profile.presentation.ui.screens.changephoto.ChangePhotoScreenEvent.OnDeletePhotoClick
import cy.volleybolley.profile.presentation.ui.screens.changephoto.ChangePhotoScreenEvent.OnGalleryPhotoSelect
import cy.volleybolley.profile.presentation.ui.screens.changephoto.ChangePhotoScreenEvent.OnSaveButtonClick
import cy.volleybolley.profile.presentation.ui.screens.personaldata.model.BackAvatarHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import java.io.File

@Composable
fun ChangePhotoScreen(
    avatarFromPersonalData: String? = null,
    navController: NavHostController,
    viewModel: ChangePhotoScreenViewModel = koinViewModel(),
    paddingFromSystemUi: PaddingValues,
) {
    val context = LocalContext.current
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    val effect = viewModel.uiEffect.collectAsStateWithLifecycle(null).value

    var galleryPictureUri: Uri? by remember { mutableStateOf(null) }
    var cameraPictureUri: Uri? by remember { mutableStateOf(null) }

    // Create PhotoPicker request
    val galleryPhotoPicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let { galleryPictureUri = it }
    }

    // Create template file and define it`s uri
    val cameraPhotoFile = createTemplatePhotoFile(context)
    val cameraPhotoUri = cameraPhotoFile?.let {
        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            it
        )
    }

    // Create Camera Request
    val cameraPhotoPicker = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            cameraPictureUri = cameraPhotoUri
        }
    }

    LaunchedEffect(galleryPictureUri) {
        galleryPictureUri?.let {
            viewModel.obtainEvent(
                OnGalleryPhotoSelect(
                    pictureUri = it,
                    pictureBytes = convertUriToByteArray(context, it)

                )
            )
        }
    }

    LaunchedEffect(cameraPictureUri) {
        cameraPictureUri?.let {
            viewModel.obtainEvent(
                OnCameraPhotoCreate(
                    photoUri = it,
                    photoBytes = convertUriToByteArray(context, it)
                )
            )
        }
    }

    ChangePhotoScreen(
        inputAvatar = avatarFromPersonalData,
        galleryPhotoPicker = galleryPhotoPicker,
        cameraPhotoPicker = cameraPhotoPicker,
        cameraPhotoUri = cameraPhotoUri,
        state = state,
        effect = effect,
        navigateAction = { newAvatar ->
            newAvatar?.let {
                navController.previousBackStackEntry?.savedStateHandle?.set(BackAvatarHolder.AVATAR_KEY, it)
            }
            navController.popBackStack()
        },
        eventCallback = { event -> viewModel.obtainEvent(event) },
        modifier = Modifier.padding(paddingFromSystemUi)
    )
}

@Composable
private fun ChangePhotoScreen(
    modifier: Modifier = Modifier,
    inputAvatar: String? = null,
    galleryPhotoPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>? = null,
    cameraPhotoPicker: ManagedActivityResultLauncher<Uri, Boolean>? = null,
    cameraPhotoUri: Uri? = null,
    state: ChangePhotoScreenState,
    effect: ChangePhotoScreenEffect?,
    navigateAction: (String?) -> Unit,
    eventCallback: (ChangePhotoScreenEvent) -> Unit,
) {
    LaunchedEffect(inputAvatar) {
        eventCallback(GetAvatarFromPersonalData(inputAvatar))
    }

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
                title = stringResource(R.string.change_photo),
                modifier = Modifier.fillMaxWidth(),
                onBackClick = { eventCallback(OnBackFromChangePhotoClick) }
            )
            Spacer(Modifier.height(VolleyDimens.DIMEN_8.dp))

            Column(Modifier.verticalScroll(rememberScrollState())) {
                Spacer(Modifier.height(VolleyDimens.DIMEN_8.dp))
                Avatar(
                    modifier = Modifier.fillMaxWidth(),
                    avatarUrl = state.avatarUrl,
                )
                Spacer(Modifier.height(VolleyDimens.DIMEN_16.dp))

                Menu(
                    galleryPhotoPicker = galleryPhotoPicker,
                    cameraPhotoPicker = cameraPhotoPicker,
                    cameraPhotoUri = cameraPhotoUri,
                    eventCallback = eventCallback
                )

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
    }

    LaunchedEffect(effect) {
        when (effect) {
            is NavigateFromChangePhotoScreen -> navigateAction(effect.avatarArgument)
            null -> {}
        }
    }
}

@Composable
private fun Avatar(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Box {
            VolleyAvatar.CircularAvatar(
                avatar = avatarUrl,
                size = VolleyDimens.DIMEN_122.dp
            )
            Image(
                painter = painterResource(R.drawable.ic_edit_avatar),
                contentDescription = stringResource(R.string.avatar_content_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        VolleyDimens.DIMEN_0.dp,
                        VolleyDimens.DIMEN_0.dp,
                        VolleyDimens.DIMEN_10.dp,
                        VolleyDimens.DIMEN_6.dp
                    )
            )
        }
    }
}

@Stable
@Composable
private fun Menu(
    galleryPhotoPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>? = null,
    cameraPhotoPicker: ManagedActivityResultLauncher<Uri, Boolean>? = null,
    cameraPhotoUri: Uri? = null,
    eventCallback: (ChangePhotoScreenEvent) -> Unit,
) {
    val gradientBrush = remember {
        Brush.verticalGradient(
            colors = listOf(
                VolleyColor.YellowForGradient,
                VolleyColor.GreenForGradient
            )
        )
    }
    val menuShape = remember { RoundedCornerShape(VolleyDimens.DIMEN_32.dp) }

    Column(
        modifier = Modifier
            .background(VolleyColor.White, menuShape)
            .border(
                width = VolleyDimens.DIMEN_1.dp,
                brush = gradientBrush,
                shape = menuShape
            )
            .padding(VolleyDimens.DIMEN_20.dp)
    ) {
        MenuComponent(
            painter = painterResource(R.drawable.ic_photo_gallery),
            title = stringResource(R.string.choose_from_gallery)
        ) {
            galleryPhotoPicker?.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }

        ChangePhotoScreenDivider()

        MenuComponent(
            painter = painterResource(R.drawable.ic_photo_camera),
            title = stringResource(R.string.take_photo)
        ) {
            cameraPhotoUri?.let { cameraPhotoPicker?.launch(it) }
        }

        ChangePhotoScreenDivider()

        MenuComponent(
            painter = painterResource(R.drawable.ic_photo_delete),
            title = stringResource(R.string.delete_photo)
        ) { eventCallback(OnDeletePhotoClick) }
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
                    vertical = VolleyDimens.DIMEN_0.dp
                )
        )
    }
}

@Composable
private fun ChangePhotoScreenDivider() {
    VolleySimpleComponent.DividerLine(
        modifier = Modifier
            .fillMaxWidth()
            .padding(VolleyDimens.DIMEN_0.dp, VolleyDimens.DIMEN_16.dp)
    )
}

private fun createTemplatePhotoFile(context: Context): File? {
    // Create an image file name
    return runCatching {
        val fileName = "volleyPhoto_${System.currentTimeMillis()}"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        File.createTempFile(
            fileName,
            ".jpg",
            storageDir
        )
    }.onFailure { error ->
        if (BuildConfig.DEBUG) {
            Log.e(VolleyUiUtil.PHOTO_FILE_TAG, "ChangePhotoScreen >> createPhotoFile >> ${error.message}")
        }
    }.getOrNull()
}

private suspend fun convertUriToByteArray(context: Context, uri: Uri): ByteArray? {
    return withContext(Dispatchers.IO) {
        context.contentResolver.openInputStream(uri)?.use { stream -> stream.readBytes() }
    }
}

@Preview(showBackground = true, showSystemUi = true)
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
                navigateAction = {},
                eventCallback = {}
            )
        }
    }
}
