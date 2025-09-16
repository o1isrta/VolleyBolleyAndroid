package cy.volleybolley.core.presentation.ui.screens.games.mygames.manageplayers

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
fun ManagePlayersScreen(
    navController: NavHostController,
    viewModel: ManagePlayersViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle() // пока не используется

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                ManagePlayersEffect.NavigateBack -> navController.popBackStack()
                null -> Unit
            }
        }
    }

    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { }
    ) {
        Spacer(Modifier.height(VolleyDimens.DIMEN_116.dp))

        GlassCard(
            modifier = Modifier.padding(horizontal = VolleyDimens.DIMEN_8.dp),
            minHeight = VolleyDimens.DIMEN_280.dp
        ) {
            CardHeader(
                title = stringResource(R.string.players_title),
                onBack = { viewModel.obtainEvent(ManagePlayersAction.ClickBack) }
            )
            PlayersList8(onRemove = { index ->
                viewModel.obtainEvent(ManagePlayersAction.RemovePlayer(index))
            })
        }

        Spacer(Modifier.height(VolleyDimens.DIMEN_24.dp))
    }
}

@Composable
private fun PlayersList8(
    onRemove: (index: Int) -> Unit
) {
    val players = listOf(
        PlayerUi(stringResource(R.string.player_demo_1), "H"),
        PlayerUi(stringResource(R.string.player_demo_2), "H"),
        PlayerUi(stringResource(R.string.player_demo_3), "H"),
        PlayerUi(stringResource(R.string.player_demo_4), "H"),
        PlayerUi(stringResource(R.string.player_demo_5), "H"),
        PlayerUi(null, null),
        PlayerUi(null, null),
        PlayerUi(null, null)
    )

    Column(verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp)) {
        players.forEachIndexed { index, p ->
            val hasPlayer = !p.name.isNullOrBlank()

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = VolleyDimens.DIMEN_23.dp)
            ) {
                VolleyText.BodyRegular(
                    text = "${index + 1}. " + (p.name ?: stringResource(R.string.free_spot)),
                    color = VolleyColor.White,
                    modifier = Modifier.weight(1f)
                )

                if (hasPlayer) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp)
                    ) {
                        IconButton(
                            onClick = { onRemove(index) },
                            modifier = Modifier.size(VolleyDimens.DIMEN_21.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_remove),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(VolleyDimens.DIMEN_21.dp)
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
            .height(VolleyDimens.DIMEN_24.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(VolleyDimens.DIMEN_24.dp)
                .clickable(onClick = onBack),
            contentAlignment = Alignment.CenterStart
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back_icon_white),
                contentDescription = null,
                tint = VolleyColor.White,
                modifier = Modifier.size(
                    width = VolleyDimens.DIMEN_18.dp,
                    height = VolleyDimens.DIMEN_24.dp
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
    minHeight: Dp = VolleyDimens.DIMEN_280.dp,
    cornerRadiusDp: Int = VolleyDimens.DIMEN_32,
    innerPadding: Dp = VolleyDimens.DIMEN_20.dp,
    gap: Dp = VolleyDimens.DIMEN_16.dp,
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
            .clip(RoundedCornerShape(VolleyDimens.DIMEN_10.dp))
            .background(VolleyColor.GreyDark)
            .padding(
                start = VolleyDimens.DIMEN_10.dp,
                end = VolleyDimens.DIMEN_10.dp,
                top = VolleyDimens.DIMEN_2.dp,
                bottom = VolleyDimens.DIMEN_2.dp
            )
            .height(VolleyDimens.DIMEN_23.dp)
    ) {
        VolleyText.BodyRegular(level, color = VolleyColor.White)
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ManagePlayersScreenPreview() {
    VolleybolleyTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            ManagePlayersScreen(rememberNavController())
        }
    }
}
