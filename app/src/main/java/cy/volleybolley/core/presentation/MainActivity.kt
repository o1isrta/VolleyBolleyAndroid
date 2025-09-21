package cy.volleybolley.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ISCHECKED_TRUE_TEXT
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.BodyTinyBottomNavGradient
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.BodyTinyBottomNavWhite
import cy.volleybolley.core.presentation.ui.navigation.HomeTopLevelRoute
import cy.volleybolley.core.presentation.ui.navigation.MyGamesTopLevelRoute
import cy.volleybolley.core.presentation.ui.navigation.NavHostContainer
import cy.volleybolley.core.presentation.ui.navigation.ProfileTopLevelRoute
import cy.volleybolley.core.presentation.ui.navigation.model.NoBarsRoutes
import cy.volleybolley.core.presentation.ui.navigation.model.TopLevelRoute
import cy.volleybolley.core.presentation.ui.screens.createnewgame.BasicGameSetupScreen
import cy.volleybolley.ui.theme.VolleybolleyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VolleybolleyTheme {
                RootContainer { innerPadding, navController ->
                    NavHostContainer(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        activityFinisher = { finish() }
                    )
                }
               // ButtonDemo()
            }
        }

        /*
          //   enableEdgeToEdge()
        setContent {
            VolleybolleyTheme {
              // BasicGameSetupScreen(rememberNavController())
                RootContainer { innerPadding, navController ->
                    NavHostContainer(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
               // ButtonDemo()
            }
        }
        * */
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
    content: @Composable (PaddingValues, NavHostController) -> Unit
) {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val currentDestinationRoute = currentDestination?.route ?: ""
    val showBottomNav = NoBarsRoutes.showBottomBar(currentDestinationRoute)
    val showTopBar = NoBarsRoutes.showTopBar(currentDestinationRoute)

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
                    VolleyTopBar.TopBar(
                        firstName = "nemislimus",
                        avatar = "https://cdn.fishki.net/upload/post/2021/03/29/3682461/gallery/tn/" +
                            "wil-hughes-troll-face.jpg",
                        levelName = "PRO"
                    )
                }
            },
            bottomBar = {
                if (showBottomNav) {
                    BottomNavComponent(navController, currentDestination)
                }
            },
            content = { innerPadding ->
                content(innerPadding, navController)
            },
        )
    }

        /*
        content: @Composable (PaddingValues, NavHostController) -> Unit
) {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.TurquoiseDark),
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = VolleyColor.TurquoiseDark,
            //content = content
            content = { paddingValues -> //  Захватываем navController
                content(paddingValues, navController)
            }
        )
    }
        * */
}

@Composable
private fun BottomNavComponent(
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
            painterResource(R.drawable.ic_players),
            painterResource(R.drawable.ic_players_gradient)
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
            //content = content
            content = { paddingValues -> //  Захватываем navController
                content(paddingValues, navController)
            }
        )
    }
    BottomAppBar(
        containerColor = VolleyColor.TurquoiseBottom,
        modifier = Modifier
            .background(
                color = VolleyColor.TurquoiseBottom,
                shape = shape
            )
            .height(VolleyDimens.DIMEN_81.dp)
            .padding(top = VolleyDimens.DIMEN_10.dp)
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
        RootContainer { padding, navController ->
            NavHostContainer(modifier = Modifier.padding(padding), navController)
        RootContainer { padding, controller ->
            NavHostContainer(
                navController = controller,
                activityFinisher = {},
                modifier = Modifier.padding(padding)
            )
        }
    }

            /*   VolleybolleyTheme {
            RootContainer { padding, navController ->
                NavHostContainer(modifier = Modifier.padding(padding), navController)
            }
        }*/
}
