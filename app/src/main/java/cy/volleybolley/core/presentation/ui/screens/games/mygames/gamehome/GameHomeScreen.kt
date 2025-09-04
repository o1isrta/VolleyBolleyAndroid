package cy.volleybolley.core.presentation.ui.screens.games.mygames.gamehome

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.ui.theme.VolleybolleyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GameHomeScreen(
    navController: NavHostController,
    viewModel: GameHomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is GameHomeEffect.Navigate -> {
                    navController.navigate(effect.route)
                }
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        GlassCard {
            MenuItem(
                text = stringResource(R.string.my_games),
                onClick = { viewModel.dispatch(GameHomeAction.ClickMyGames) }
            )
            HorizontalDivider(thickness = VolleyDimens.DIMEN_1.dp, color = VolleyColor.White)

            // Upcoming games — подзаголовок только если есть дата
            val upcomingSubtitle =
                if (state.upcomingGame.isNotBlank())
                    stringResource(R.string.next_game, state.upcomingGame)
                else
                    ""

            MenuItemWithSubtitle(
                title = stringResource(R.string.upcoming_games),
                subtitle = upcomingSubtitle,
                onClick = { viewModel.dispatch(GameHomeAction.ClickUpcomingGames) }
            )
            HorizontalDivider(thickness = VolleyDimens.DIMEN_1.dp, color = VolleyColor.White)

            // Game invites — бейдж только если invites > 0
            MenuItem(
                text = stringResource(R.string.game_invites),
                trailing = {
                    if (state.invites > 0) {
                        CountBadge(text = state.invites.toString())
                    }
                },
                onClick = { viewModel.dispatch(GameHomeAction.ClickInvites) }
            )
            HorizontalDivider(thickness = VolleyDimens.DIMEN_1.dp, color = VolleyColor.White)

            MenuItem(
                text = stringResource(R.string.archive),
                onClick = { viewModel.dispatch(GameHomeAction.ClickArchive) }
            )
        }
    }
}

@Composable
private fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadiusDp: Int = VolleyDimens.DIMEN_32,
    innerPadding: Dp = VolleyDimens.DIMEN_20.dp,
    itemsGap: Dp = VolleyDimens.DIMEN_16.dp,
    height: Dp = VolleyDimens.DIMEN_240.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    VolleyContainersRootTransparent.TransparentContainer(
        modifier = modifier
            .padding(
                start = VolleyDimens.DIMEN_8.dp,
                end = VolleyDimens.DIMEN_8.dp,
                top = VolleyDimens.DIMEN_116.dp
            ),
        cornerRadius = cornerRadiusDp,
        mainContainerAlignment = Alignment.TopStart,
        contentContainerAlignment = Alignment.TopStart
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(height)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(itemsGap),
                horizontalAlignment = Alignment.Start,
                content = content
            )
        }
    }
}

@Composable
private fun MenuItem(
    text: String,
    trailing: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = VolleyDimens.DIMEN_16.dp)
            .clickable(role = Role.Button, onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        VolleyText.BodyRegular(text = text, color = VolleyColor.White)
        if (trailing != null) {
            Spacer(Modifier.width(VolleyDimens.DIMEN_4.dp))
            trailing()
        }
    }
}

@Composable
private fun MenuItemWithSubtitle(
    title: String,
    subtitle: String?,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = VolleyDimens.DIMEN_16.dp)
            .clickable(role = Role.Button, onClick = onClick)
    ) {
        VolleyText.BodyRegular(text = title, color = VolleyColor.White)
        if (!subtitle.isNullOrBlank()) {
            VolleyText.BodySmall(text = subtitle, color = VolleyColor.White.copy(alpha = 0.7f))
        }
    }
}

@Composable
private fun CountBadge(
    text: String,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(VolleyDimens.DIMEN_10.dp)
    Box(
        modifier = modifier
            .defaultMinSize(
                minWidth = VolleyDimens.DIMEN_26.dp,
                minHeight = VolleyDimens.DIMEN_27.dp
            )
            .clip(shape)
            .background(VolleyColor.OrangeHard)
            .padding(
                horizontal = VolleyDimens.DIMEN_8.dp,
                vertical = VolleyDimens.DIMEN_4.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        VolleyText.BodyBold(text = text, color = VolleyColor.TextDark)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun GameHomeScreenPreview() {
    VolleybolleyTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            val fakeState = GameHomeState(upcomingGame = "March, 10", invites = 2)
            val navController = rememberNavController()

            Box(Modifier.fillMaxSize()) {
                GlassCard {
                    MenuItem(
                        text = stringResource(R.string.my_games),
                        onClick = { /* no-op in preview */ }
                    )
                    HorizontalDivider(thickness = VolleyDimens.DIMEN_1.dp, color = VolleyColor.White)

                    val upcomingSubtitle =
                        if (fakeState.upcomingGame.isNotBlank())
                            stringResource(R.string.next_game, fakeState.upcomingGame)
                        else
                            ""

                    MenuItemWithSubtitle(
                        title = stringResource(R.string.upcoming_games),
                        subtitle = upcomingSubtitle,
                        onClick = { /* no-op in preview */ }
                    )
                    HorizontalDivider(thickness = VolleyDimens.DIMEN_1.dp, color = VolleyColor.White)

                    MenuItem(
                        text = stringResource(R.string.game_invites),
                        trailing = {
                            if (fakeState.invites > 0) CountBadge(text = fakeState.invites.toString())
                        },
                        onClick = { /* no-op in preview */ }
                    )
                    HorizontalDivider(thickness = VolleyDimens.DIMEN_1.dp, color = VolleyColor.White)

                    MenuItem(
                        text = stringResource(R.string.archive),
                        onClick = { /* no-op in preview */ }
                    )
                }
            }
        }
    }
}
