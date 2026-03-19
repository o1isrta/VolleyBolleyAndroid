package cy.volleybolley.core.presentation.ui.screens.games.mygames.manageplayers

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import cy.volleybolley.ui.theme.VolleybolleyTheme
import org.koin.compose.viewmodel.koinViewModel

private const val LEVEL_HIGH = "H"

@Composable
fun ManagePlayersScreen(
    paddingFromSystemUi: PaddingValues,
    onNavigateBack: () -> Unit,
    viewModel: ManagePlayersViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (effect) {
            is ManagePlayersEffect.NavigateBack -> onNavigateBack()
            null -> {}
        }
    }

    ManagePlayersContent(
        paddingFromSystemUi = paddingFromSystemUi,
        onBack = { viewModel.obtainEvent(ManagePlayersAction.ClickBack) },
        onRemovePlayer = { index -> viewModel.obtainEvent(ManagePlayersAction.RemovePlayer(index)) }
    )
}

@Stable
@Composable
private fun ManagePlayersContent(
    paddingFromSystemUi: PaddingValues,
    onBack: () -> Unit,
    onRemovePlayer: (Int) -> Unit
) {
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingFromSystemUi)
            .verticalScroll(scroll)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { }
    ) {
        GlassCard(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp),
            minHeight = 280.dp
        ) {
            CardHeader(
                title = stringResource(R.string.players_title),
                onBack = onBack
            )
            PlayersList8(onRemove = onRemovePlayer)
        }

        Box(Modifier.padding(bottom = 24.dp))
    }
}

@Composable
private fun PlayersList8(
    onRemove: (index: Int) -> Unit
) {
    val players = listOf(
        PlayerUi(stringResource(R.string.player_demo_1), LEVEL_HIGH),
        PlayerUi(stringResource(R.string.player_demo_2), LEVEL_HIGH),
        PlayerUi(stringResource(R.string.player_demo_3), LEVEL_HIGH),
        PlayerUi(stringResource(R.string.player_demo_4), LEVEL_HIGH),
        PlayerUi(stringResource(R.string.player_demo_5), LEVEL_HIGH),
        PlayerUi(null, null),
        PlayerUi(null, null),
        PlayerUi(null, null)
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        players.forEachIndexed { index, p ->
            val hasPlayer = !p.name.isNullOrBlank()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 23.dp)
            ) {
                VolleyText.BodyRegular(
                    text = "${index + 1}. " + (p.name ?: stringResource(R.string.free_spot)),
                    color = VolleyColor.White,
                    modifier = Modifier.weight(1f)
                )

                if (hasPlayer) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = { onRemove(index) },
                            modifier = Modifier.size(21.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_remove),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(21.dp)
                            )
                        }
                        p.level?.let { LevelBadge(it) }
                    }
                }
            }
        }
    }
}

@Composable
private fun CardHeader(
    title: String,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(24.dp)
                .clickable(onClick = onBack),
            contentAlignment = Alignment.CenterStart
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back_icon_white),
                contentDescription = null,
                tint = VolleyColor.White,
                modifier = Modifier.size(
                    width = 18.dp,
                    height = 24.dp
                )
            )
        }
        VolleyText.TitleLarge(
            text = title,
            color = VolleyColor.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun GlassCard(
    modifier: Modifier = Modifier,
    minHeight: Dp = 280.dp,
    cornerRadiusDp: Int = 32,
    innerPadding: Dp = 20.dp,
    gap: Dp = 16.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    VolleyContainersRootTransparent.TransparentContainer(
        modifier = modifier,
        cornerRadius = cornerRadiusDp,
        mainContainerAlignment = Alignment.TopStart,
        contentContainerAlignment = Alignment.TopStart
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = minHeight)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(gap),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = content
            )
        }
    }
}

@Composable
private fun LevelBadge(level: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(VolleyColor.GreyDark)
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = 2.dp,
                bottom = 2.dp
            )
            .height(23.dp)
    ) {
        VolleyText.BodyRegular(level, color = VolleyColor.White)
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_9_PRO)
@Composable
private fun ManagePlayersScreenPreview() {
    RootContainerForPreview {
        ManagePlayersContent(
            paddingFromSystemUi = it,
            onBack = {},
            onRemovePlayer = {}
        )
    }
}
