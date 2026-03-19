package cy.volleybolley.core.presentation.ui.screens.games.mygames.gamehome

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cy.volleybolley.R
import cy.volleybolley.core.presentation.RootContainerForPreview
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameHomeScreen(
    paddingFromSystemUi: PaddingValues,
    onNavigateToMyGames: () -> Unit,
    onNavigateToUpcomingGames: () -> Unit,
    onNavigateToInvites: () -> Unit,
    onNavigateToArchive: () -> Unit,
    viewModel: GameHomeViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (val currentEffect = effect) {
            is GameHomeEffect.NavigateToMyGames -> onNavigateToMyGames()
            is GameHomeEffect.NavigateToUpcomingGames -> onNavigateToUpcomingGames()
            is GameHomeEffect.NavigateToInvites -> onNavigateToInvites()
            is GameHomeEffect.NavigateToArchive -> onNavigateToArchive()
            null -> {}
        }
    }

    GameHomeContent(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        onMyGamesClick = { viewModel.obtainEvent(GameHomeAction.ClickMyGames) },
        onUpcomingGamesClick = { viewModel.obtainEvent(GameHomeAction.ClickUpcomingGames) },
        onInvitesClick = { viewModel.obtainEvent(GameHomeAction.ClickInvites) },
        onArchiveClick = { viewModel.obtainEvent(GameHomeAction.ClickArchive) }
    )
}

@Stable
@Composable
private fun GameHomeContent(
    state: GameHomeState,
    paddingFromSystemUi: PaddingValues,
    onMyGamesClick: () -> Unit,
    onUpcomingGamesClick: () -> Unit,
    onInvitesClick: () -> Unit,
    onArchiveClick: () -> Unit
) {
    Box(Modifier
        .padding(top = paddingFromSystemUi.calculateTopPadding())
        .fillMaxSize()) {
        GlassCard {
            MenuItem(
                text = stringResource(R.string.my_games),
                onClick = onMyGamesClick
            )
            HorizontalDivider(thickness = 1.dp, color = VolleyColor.White)

            // Upcoming games — подзаголовок только если есть дата
            val upcomingSubtitle =
                if (state.upcomingGame.isNotBlank()) {
                    stringResource(R.string.next_game, state.upcomingGame)
                } else {
                    ""
                }

            MenuItemWithSubtitle(
                title = stringResource(R.string.upcoming_games),
                subtitle = upcomingSubtitle,
                onClick = onUpcomingGamesClick
            )
            HorizontalDivider(thickness = 1.dp, color = VolleyColor.White)

            // Game invites — бейдж только если invites > 0
            MenuItem(
                text = stringResource(R.string.game_invites),
                trailing = {
                    if (state.invites > 0) {
                        CountBadge(text = state.invites.toString())
                    }
                },
                onClick = onInvitesClick
            )
            HorizontalDivider(thickness = 1.dp, color = VolleyColor.White)

            MenuItem(
                text = stringResource(R.string.archive),
                onClick = onArchiveClick
            )
        }
    }
}

@Composable
private fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadiusDp: Int = 32,
    innerPadding: Dp = 20.dp,
    itemsGap: Dp = 16.dp,
    height: Dp = 240.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    VolleyContainersRootTransparent.TransparentContainer(
        modifier = modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 8.dp
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
            .heightIn(min = 16.dp)
            .clickable(role = Role.Button, onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        VolleyText.BodyRegular(text = text, color = VolleyColor.White)
        if (trailing != null) {
            Box(modifier = Modifier.padding(start = 4.dp)) {
                trailing()
            }
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
            .heightIn(min = 16.dp)
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
    val shape = RoundedCornerShape(10.dp)
    Box(
        modifier = modifier
            .defaultMinSize(
                minWidth = 26.dp,
                minHeight = 27.dp
            )
            .clip(shape)
            .background(VolleyColor.OrangeHard)
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        VolleyText.BodyBold(text = text, color = VolleyColor.TextDark)
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_9_PRO)
@Composable
private fun GameHomeScreenPreview() {
    RootContainerForPreview {
        GameHomeContent(
            state = GameHomeState(),
            paddingFromSystemUi = it,
            onMyGamesClick = {},
            onUpcomingGamesClick = {},
            onInvitesClick = {},
            onArchiveClick = {}
        )
    }
}
