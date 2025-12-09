package cy.volleybolley.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.auth.domain.api.usecase.CheckRefreshTokenExpirationUseCase
import cy.volleybolley.auth.domain.api.usecase.ClearAllLoginDataUseCase
import cy.volleybolley.auth.domain.api.usecase.GetAuthenticatedStatusUseCase
import cy.volleybolley.auth.domain.api.usecase.GetPersonalDataUseCase
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.BodyTinyBottomNavGradient
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.BodyTinyBottomNavWhite
import cy.volleybolley.core.presentation.ui.navigation.AuthorizationRoute
import cy.volleybolley.core.presentation.ui.navigation.HomeTopLevelRoute
import cy.volleybolley.core.presentation.ui.navigation.LaunchRoute
import cy.volleybolley.core.presentation.ui.navigation.MyGamesTopLevelRoute
import cy.volleybolley.core.presentation.ui.navigation.NavHostContainer
import cy.volleybolley.core.presentation.ui.navigation.OnboardingRoute
import cy.volleybolley.core.presentation.ui.navigation.ProfileTopLevelRoute
import cy.volleybolley.core.presentation.ui.navigation.RegistrationRoute
import cy.volleybolley.core.presentation.ui.navigation.model.NoBarsRoutes
import cy.volleybolley.core.presentation.ui.navigation.model.TopLevelRoute
import cy.volleybolley.profile.domain.model.PersonalData
import cy.volleybolley.ui.theme.VolleybolleyTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    private val checkRefreshTokenExpirationUseCase: CheckRefreshTokenExpirationUseCase by inject()
    private val clearAllLoginDataUseCase: ClearAllLoginDataUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VolleybolleyTheme {
                RootContainer { innerPadding, navController ->
                    NavHostContainer(
                        navController = navController,
                        paddingFromSystemUi = innerPadding,
                        activityFinisher = { finish() }
                    )
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.launch {
            val shouldLogout = checkRefreshTokenExpirationUseCase.execute()
            if (shouldLogout) {
                clearAllLoginDataUseCase.execute()
                // Navigation to the authorization screen will happen automatically
                // via Flow IsAuthenticated in the RootContainer
            }
        }
    }
}

private fun isAuthRoute(route: String): Boolean {
    return route.contains(LaunchRoute::class.qualifiedName.toString()) ||
        route.contains(OnboardingRoute::class.qualifiedName.toString()) ||
        route.contains(AuthorizationRoute::class.qualifiedName.toString()) ||
        route.contains(RegistrationRoute::class.qualifiedName.toString())
}

@Composable
fun RootContainer(
    getAuthenticatedStatusUseCase: GetAuthenticatedStatusUseCase = koinInject(),
    getPersonalDataUseCase: GetPersonalDataUseCase = koinInject(),
    content: @Composable (PaddingValues, NavHostController) -> Unit
) {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val currentDestinationRoute = currentDestination?.route ?: ""
    val showBottomNav = NoBarsRoutes.showBottomBar(currentDestinationRoute)
    val showTopBar = NoBarsRoutes.showTopBar(currentDestinationRoute)

    // Automatic navigation to the authorization screen during logout
    val isAuthenticated by getAuthenticatedStatusUseCase.execute().collectAsStateWithLifecycle()

    // Observe user personal data
    val personalData by getPersonalDataUseCase.execute().collectAsStateWithLifecycle()

    LaunchedEffect(isAuthenticated) {
        // Do not navigate if currentRoute is not already installed.
        // This is a fix for the first launch.
        if (currentDestinationRoute.isNotEmpty() && !isAuthenticated && !isAuthRoute(currentDestinationRoute)) {
            navController.navigate(AuthorizationRoute) {
                popUpTo(navController.graph.id) { inclusive = true }
            }
        }
    }

    RootContainer(
        navController = navController,
        currentDestination = currentDestination,
        showBottomNav = showBottomNav,
        showTopBar = showTopBar,
        userData = personalData,
        content = content
    )
}

@Composable
private fun RootContainer(
    navController: NavHostController,
    currentDestination: NavDestination?,
    showBottomNav: Boolean,
    showTopBar: Boolean,
    userData: PersonalData?,
    content: @Composable (PaddingValues, NavHostController) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark),
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = VolleyColor.TurquoiseDark,
            topBar = {
                if (showTopBar) {
                    var name: String = stringResource(R.string.default_name)
                    var avatar: String? = null
                    var level: String = stringResource(R.string.default_level)

                    userData?.let {
                        name = it.firstName
                        avatar = it.avatar
                        level = it.level
                    }

                    VolleyTopBar.TopBar(
                        firstName = name,
                        avatar = avatar,
                        levelName = level
                    )
                }
            },
            bottomBar = {
                val paddingFromSystemUi = ScaffoldDefaults.contentWindowInsets.asPaddingValues()
                val bottomBarHeight = remember {
                    VolleyDimens.DIMEN_60.dp + paddingFromSystemUi.calculateBottomPadding()
                }
                if (showBottomNav) {
                    BottomNavComponent(bottomBarHeight, navController, currentDestination)
                }
            },
            content = { innerPadding ->
                content(innerPadding, navController)
            },
        )
    }
}

@Composable
private fun BottomNavComponent(
    bottomNavBarHeight: Dp,
    navController: NavHostController,
    currentDestination: NavDestination?
) {
    val topLevelRoutes = listOf(
        TopLevelRoute(
            stringResource(R.string.home),
            HomeTopLevelRoute,
            painterResource(R.drawable.ic_home),
            painterResource(R.drawable.ic_home_gradient)
        ),
        TopLevelRoute(
            stringResource(R.string.my_games),
            MyGamesTopLevelRoute,
            painterResource(R.drawable.ic_ball),
            painterResource(R.drawable.ic_ball_gradient)
        ),
        TopLevelRoute(
            stringResource(R.string.profile),
            ProfileTopLevelRoute,
            painterResource(R.drawable.ic_personal_data),
            painterResource(R.drawable.ic_personal_data_gradient)
        ),
    )

    val shape = remember {
        RoundedCornerShape(
            topStart = VolleyDimens.DIMEN_36.dp,
            topEnd = VolleyDimens.DIMEN_36.dp
        )
    }

    BottomAppBar(
        contentPadding = PaddingValues(0.dp),
        containerColor = VolleyColor.TurquoiseBottom,
        modifier = Modifier
            .background(
                color = VolleyColor.TurquoiseBottom,
                shape = shape
            )
            .padding(top = VolleyDimens.DIMEN_10.dp)
            .height(bottomNavBarHeight)
            .clip(shape)
    ) {
        topLevelRoutes.forEach { topRoute ->
            val itemIsSelected = currentDestination?.hierarchy?.any { it.hasRoute(topRoute.route::class) } == true
            val labelStyle = if (itemIsSelected) BodyTinyBottomNavGradient else BodyTinyBottomNavWhite
            NavigationBarItem(
                selected = itemIsSelected,
                onClick = {
                    navController.navigate(topRoute.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Image(
                        painter = if (itemIsSelected) topRoute.iconSelected else topRoute.icon,
                        contentDescription = topRoute.name,
                    )
                },
                label = {
                    VolleyText.BodyTinyBottomNav(
                        text = topRoute.name,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        style = labelStyle
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    VolleybolleyTheme {
        RootContainer { padding, controller ->
            NavHostContainer(
                navController = controller,
                activityFinisher = {},
                paddingFromSystemUi = padding
            )
        }
    }
}
