package cy.volleybolley.core.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ISCHECKED_TRUE_TEXT
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.state.MainActivityEvent
import cy.volleybolley.core.presentation.ui.model.state.MainActivityState
import cy.volleybolley.core.presentation.ui.navigation.LaunchRoute
import cy.volleybolley.core.presentation.ui.navigation.NavHostContainer
import cy.volleybolley.notification.presentation.GlobalAlertDialog
import cy.volleybolley.notification.presentation.resolveNotificationRoute
import cy.volleybolley.ui.theme.Background
import cy.volleybolley.ui.theme.VolleybolleyTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModel()
    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        viewModel.onEvent(MainActivityEvent.NotificationPermissionChanged(isGranted))
        viewModel.updateTokenBasedOnPermission()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onEvent(
            MainActivityEvent.IntentReceived(
                screen = intent?.getStringExtra("screen"),
                gameId = intent?.getStringExtra("gameId")
            )
        )
        enableEdgeToEdge()
        val granted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
        setContent {
            setContent {
                val requestPermissionLauncherState = rememberUpdatedState(requestNotificationPermissionLauncher)
                val state by viewModel.uiState.collectAsState()
                LaunchedEffect(Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                        !viewModel.isNotificationPermissionGranted()
                    ) {
                        viewModel.showNotificationPermissionDialog(
                            title = getString(R.string.notifications),
                            message = getString(R.string.notifications_alert_dialog),
                            onConfirm = {
                                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            })
                    } else {
                        viewModel.updateTokenBasedOnPermission()
                    }
                }
                VolleybolleyTheme {
                    RootContainer(
                        state = state,
                        onRequestPermission = {
                            requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        },
                        onDismissDialog = { viewModel.dismissGlobalDialog() }
                    ) { innerPadding ->
                        val routeNotification = resolveNotificationRoute(state.screen, state.gameId)
                        NavHostContainer(
                            modifier = Modifier.padding(innerPadding),
                            startDestination = routeNotification ?: LaunchRoute
                        )
                    }
                }
                ButtonDemo()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        viewModel.onEvent(
            MainActivityEvent.IntentReceived(
                screen = intent.getStringExtra("screen"),
                gameId = intent.getStringExtra("gameId")
            )
        )
    }
}

@Composable
@Stable
private fun ButtonDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = VolleyColor.TurquoiseDark),
        verticalArrangement = Arrangement.Center
    ) {
        VolleyButton.CheckedGradientButtonRightImage(
            modifier = Modifier
                .padding(24.dp)
                .height(44.dp)
                .align(Alignment.CenterHorizontally),
            isChecked = true,
            text = ISCHECKED_TRUE_TEXT,
            onClick = {}
        )
        VolleyButton.SliderButtonsMap(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(24.dp),
            onClick = {}
        )
        VolleyButton.GroupButtonsForChangeLevel(
            checkId = 1,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            onSelected = {}
        )
        VolleyButton.GroupButtonsForDate2(
            checkId = 2,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            onSelected = {}
        )
        VolleyButton.GroupButtonsForDate3(
            checkId = 3,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            onSelected = {}
        )
        VolleyButton.GroupButtonsForPrivacy(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            onSelected = {}
        )
        VolleyButton.GroupButtonsForGender3(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            onSelected = {}
        )
        VolleyButton.GroupButtonsForGender2(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            onSelected = {}
        )
        VolleyButton.GroupButtonsForLevel(
            checkId = 2,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            onSelected = {}
        )
    }
}

@Composable
fun RootContainer(
    state: MainActivityState,
    onRequestPermission: () -> Unit,//Оставил для времён, когда будут готовы экраны авторизации, чтобы оттуда запрашивать
    onDismissDialog: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark),
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = VolleyColor.TurquoiseDark,
            content = content
        )
    }
    state.globalDialog?.let { dialog ->
        GlobalAlertDialog(
            dialog = dialog,
            onConfirm = {
                dialog.onConfirm()
                onDismissDialog()
            },
            onDismiss = {
                dialog.onDismiss()
                onDismissDialog()
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRootContainer() {
    val fakeState = MainActivityState()
    VolleybolleyTheme {
        RootContainer(
            state = fakeState,
            onRequestPermission = {},
            onDismissDialog = {}
        ) { padding ->
            NavHostContainer(modifier = Modifier.padding(padding))
        }
    }
}
