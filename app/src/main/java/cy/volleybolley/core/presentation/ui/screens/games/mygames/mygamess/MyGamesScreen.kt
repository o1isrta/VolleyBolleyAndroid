package cy.volleybolley.core.presentation.ui.screens.games.mygames.mygamess

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.VolleyMessageTextField.MessageBubble
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar.CircularAvatar
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ActiveButton
import cy.volleybolley.core.presentation.ui.component.VolleyButton.OutlinedActiveButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography
import cy.volleybolley.ui.theme.VolleybolleyTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyGamesScreen(
    navController: NavHostController,
    viewModel: MyGamesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                MyGamesEffect.NavigateBack -> navController.popBackStack()
                is MyGamesEffect.Navigate -> navController.navigate(effect.route)
                is MyGamesEffect.OpenMap -> openMap(context, effect.location)
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        if (!state.hasGames) {
            // Плейсхолдер
            CardShell(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        start = VolleyDimens.DIMEN_8.dp,
                        end = VolleyDimens.DIMEN_8.dp,
                        top = VolleyDimens.DIMEN_116.dp
                    ),
                cardMinHeight = VolleyDimens.DIMEN_380.dp
            ) {
                CardHeader(
                    title = stringResource(R.string.my_games),
                    onBack = { viewModel.dispatch(MyGamesAction.ClickBack) }
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp)
                        .size(158.dp)
                )

                VolleyText.BodyRegular(
                    text = stringResource(R.string.no_games_found),
                    color = VolleyColor.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(10.dp))

                ActiveButton(
                    text = stringResource(R.string.create_a_game),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(VolleyDimens.DIMEN_44.dp),
                    onClick = { viewModel.dispatch(MyGamesAction.ClickCreateGame) }
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = VolleyDimens.DIMEN_8.dp,
                    end = VolleyDimens.DIMEN_8.dp,
                    top = VolleyDimens.DIMEN_116.dp,
                    bottom = 24.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(state.games) { index, details ->
                    GameCard(
                        details = details,
                        showHeader = index == 0,
                        cardMinHeight = if (index == 0) 380.dp else 344.dp,
                        onBack = { viewModel.dispatch(MyGamesAction.ClickBack) },
                        onMapClick = { location -> viewModel.dispatch(MyGamesAction.ClickMap(location)) },
                        onDetailsClick = { d -> viewModel.dispatch(MyGamesAction.ClickDetails(d)) }
                    )
                }
            }
        }
    }
}

// Карточка игры/турнира
@Composable
fun GameCard(
    details: GameDetails,
    showHeader: Boolean,
    cardMinHeight: Dp,
    onBack: () -> Unit,
    onMapClick: (Location) -> Unit,
    onDetailsClick: (GameDetails) -> Unit,
) {
    CardShell(
        modifier = Modifier.fillMaxWidth(),
        cardMinHeight = cardMinHeight
    ) {
        if (showHeader) {
            CardHeader(
                title = stringResource(R.string.my_games),
                onBack = onBack
            )
        }

        VolleyText.BodyBold(
            text = if (details.gameType.equals("TOURNAMENT", true))
                stringResource(R.string.tourney_host)
            else
                stringResource(R.string.game_host),
            color = VolleyColor.White,
            modifier = Modifier.align(Alignment.Start)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            CircularAvatar(avatar = details.host.avatar, size = 32.dp)
            VolleyText.BodyBold(text = details.host.name, color = VolleyColor.White)
            Spacer(Modifier.weight(1f))
            LevelPill(details.host.level)
        }

        HorizontalDivider(thickness = 1.dp, color = VolleyColor.White)

        val (dateText, timeText) = remember(details.startTime, details.endTime) {
            formatDateTimeRange(details.startTime, details.endTime)
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            VolleyText.BodyBold(text = stringResource(R.string.when_label), color = VolleyColor.White)
            Spacer(Modifier.width(VolleyDimens.DIMEN_8.dp))
            VolleyText.BodyRegular(text = "$dateText, $timeText", color = VolleyColor.White)
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            val label = stringResource(R.string.where)
            val text = buildAnnotatedString {
                append("$label ")
                addStyle(SpanStyle(fontWeight = FontWeight.Bold), 0, label.length)
                append(details.courtLocation.courtName)
            }
            Text(
                text = text,
                color = VolleyColor.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = VolleyTypography.BodyRegular,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = VolleyDimens.DIMEN_12.dp)
            )
            MapChipSm { onMapClick(details.courtLocation) }
        }

        MessageBubble(text = details.message, modifier = Modifier.fillMaxWidth())

        OutlinedActiveButton(
            text = stringResource(R.string.details),
            onClick = { onDetailsClick(details) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun MapChipSm(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(VolleyDimens.DIMEN_10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = VolleyColor.OrangeHard, contentColor = VolleyColor.White),
        contentPadding = PaddingValues(
            horizontal = VolleyDimens.DIMEN_8.dp,
            vertical = VolleyDimens.DIMEN_4.dp
        ),
        modifier = Modifier.height(VolleyDimens.DIMEN_35.dp)
    ) {
        VolleyText.BodySmall(stringResource(R.string.map), color = VolleyColor.Black)
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
                modifier = Modifier.size(width = (VolleyDimens.DIMEN_18.dp), height = (VolleyDimens.DIMEN_24.dp))
            )
        }
        VolleyText.TitleLarge(text = title, color = VolleyColor.White, modifier = Modifier.align(Alignment.Center))
    }
}

// Стекло
@Composable
private fun CardShell(
    modifier: Modifier = Modifier,
    cornerRadiusDp: Int = VolleyDimens.DIMEN_32,
    innerPadding: Dp = VolleyDimens.DIMEN_20.dp,
    itemsGap: Dp = VolleyDimens.DIMEN_16.dp,
    cardMinHeight: Dp,
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
                .defaultMinSize(minHeight = cardMinHeight)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(itemsGap),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = content
            )
        }
    }
}

@Composable
private fun LevelPill(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(VolleyDimens.DIMEN_23.dp)
            .width(VolleyDimens.DIMEN_30.dp)
            .clip(RoundedCornerShape(VolleyDimens.DIMEN_10.dp))
            .background(VolleyColor.GreyDark)
    ) {
        VolleyText.BodyRegular(text, color = VolleyColor.White)
    }
}

// Карты
private fun openMap(context: Context, location: Location) {
    val uri = "geo:${location.latitude},${location.longitude}?q=${location.latitude},${location.longitude}(${
        Uri.encode(location.courtName)
    })".toUri()
    val intent = Intent(Intent.ACTION_VIEW, uri)
    val chooser = Intent.createChooser(intent, context.getString(R.string.open_with))
    context.startActivity(chooser)
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=411dp,height=1000dp,dpi=420")
@Composable
private fun MyGames_Empty_Preview() {
    VolleybolleyTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            MyGamesScreen(navController = rememberNavController())
        }
    }
}

