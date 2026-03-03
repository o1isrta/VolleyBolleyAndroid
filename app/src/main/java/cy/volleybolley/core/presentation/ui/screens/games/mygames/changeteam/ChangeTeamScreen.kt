package cy.volleybolley.core.presentation.ui.screens.games.mygames.changeteam

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ActiveButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.ui.theme.VolleybolleyTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChangeTeamScreen(
    navController: NavHostController,
    viewModel: ChangeTeamViewModel = koinViewModel(),
    paddingFromSystemUi: PaddingValues = PaddingValues(0.dp)
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effect by viewModel.uiEffect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effect) {
        when (effect) {
            is ChangeTeamEffect.NavigateBack -> navController.popBackStack()
            is ChangeTeamEffect.TeamSelected -> { /* handled internally */ }
            null -> {}
        }
    }

    ChangeTeamContent(
        state = state,
        paddingFromSystemUi = paddingFromSystemUi,
        onBack = { navController.popBackStack() },
        onSelectTeam = { index -> viewModel.obtainEvent(ChangeTeamAction.SelectTeam(index)) },
        onRemoveMember = { teamIndex, memberIndex ->
            viewModel.obtainEvent(ChangeTeamAction.RemoveMember(teamIndex, memberIndex))
        },
        onConfirm = {
            viewModel.obtainEvent(ChangeTeamAction.ConfirmSelection)
            navController.popBackStack()
        }
    )
}

@Stable
@Composable
private fun ChangeTeamContent(
    state: ChangeTeamState,
    paddingFromSystemUi: PaddingValues,
    onBack: () -> Unit,
    onSelectTeam: (Int) -> Unit,
    onRemoveMember: (Int, Int) -> Unit,
    onConfirm: () -> Unit
) {
    val scroll = rememberScrollState()
    val interaction = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingFromSystemUi)
            .verticalScroll(scroll)
            .clickable(indication = null, interactionSource = interaction) { }
    ) {
        GlassCard(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp),
            minHeight = 380.dp
        ) {
            CardHeader(
                title = stringResource(R.string.teams_title),
                onBack = onBack
            )

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                state.teams.forEachIndexed { index, team ->
                    TeamBlock(
                        team = team,
                        isSelected = state.selectedTeam == index,
                        onSelect = { onSelectTeam(index) },
                        onRemoveMember = { memberIndex -> onRemoveMember(index, memberIndex) }
                    )
                }
            }
        }

        ActiveButton(
            text = stringResource(R.string.choose_team_cta),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 16.dp)
                .height(44.dp),
            onClick = onConfirm
        )

        Box(Modifier.padding(bottom = 16.dp))
    }
}

@Composable
private fun TeamBlock(
    team: TeamUi,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onRemoveMember: (memberIndex: Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            VolleyText.TitleMedium(stringResource(team.nameRes), color = VolleyColor.White)
            Box(Modifier.weight(1f))
            SelectDot(
                selected = isSelected,
                onClick = onSelect
            )
        }

        repeat(2) { i ->
            val member = team.members.getOrNull(i) ?: MemberUi(null, null)
            PlayerRow(
                member = member,
                showActions = member.name != null,
                onRemove = { onRemoveMember(i) }
            )
        }
    }
}

@Composable
private fun PlayerRow(
    member: MemberUi,
    showActions: Boolean,
    onRemove: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 23.dp)
    ) {
        VolleyText.BodyRegular(
            text = member.name ?: stringResource(R.string.free_spot),
            color = VolleyColor.White,
            modifier = Modifier.weight(1f)
        )

        if (showActions) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(21.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_remove),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(21.dp)
                    )
                }
                member.level?.let { LevelBadge(it) }
            }
        }
    }
}

@Composable
private fun SelectDot(
    selected: Boolean,
    onClick: () -> Unit
) {
    val size = 21.dp
    Box(
        modifier = Modifier
            .size(size)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(
                if (selected) R.drawable.ic_selected_dot else R.drawable.ic_unselected_dot
            ),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.fillMaxSize()
        )
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
    minHeight: Dp = 380.dp,
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

@Preview(showBackground = true, showSystemUi = true, name = "ChangeTeamScreen Preview")
@Composable
private fun ChangeTeamScreenPreview() {
    VolleybolleyTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            ChangeTeamScreen(navController = rememberNavController())
        }
    }
}
