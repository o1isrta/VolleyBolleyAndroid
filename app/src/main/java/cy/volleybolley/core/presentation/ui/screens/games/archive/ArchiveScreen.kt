package cy.volleybolley.core.presentation.ui.screens.games.archive

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar
import cy.volleybolley.core.presentation.ui.component.VolleyButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.Game
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.Host
import cy.volleybolley.core.presentation.ui.screens.games.archive.datamodel.PlayerShort
import cy.volleybolley.courts.domain.model.Location

@Composable
fun ArchiveScreen(navController: NavHostController) {
    ArchiveScreen(
        emptyArchive = false,
        onBackClick = { navController.navigateUp() },
        onButtonClick = { }, // если true, то navController.navigate(SearchCourtRoute)
        games = listOf(
//            Game(
//                gameHost = PlayerShort(
//                    0,
//                    name = "Artem Ivanov",
//                    level = "L"
//                ),
//                hostMessage = "Hey! Can’t wait to see you. Make sure to bring some water and towels!",
//                location = "Karon Beach Club",
//                timeAndDate = "1 October, 6:00-8:00 pm",
//                level = "Light",
//                gender = "Mix",
//                paymentMethodType = "Thai bank",
//                paymentMethod = "988 016 7890",
//                feePerPerson = "2$",
//                players = listOf(
//                    PlayerShort(10,"Anton Ivanov", "H"),
//                    PlayerShort(0,"Aleksandr Abramov", "H")
//                )
//            ),
//            Game(
//                gameHost = PlayerShort(
//                    0,
//                    name = "Polina Vasilieva",
//                    level = "M"
//                ),
//                hostMessage = "Just be happy! 1111112312 3312111123dw f3q42343tkzjbfk segffhbefvsef" +
//                    " hjfghjsF SEUFhsehFkSF iSUGHEFiuesGF kushFKSEKGK",
//                location = "Default Court 11111123123312111123",
//                timeAndDate = "1 September, 6:00-8:00 pm 11111123123312111",
//                level = "Light",
//                gender = "Mix",
//                paymentMethodType = "Thai bank",
//                paymentMethod = "988 016 7890",
//                feePerPerson = "3$",
//                players = listOf(
//                    PlayerShort("Anya Levan", "H"),
//                    PlayerShort("Alina Lyubimova", "H")
//                )
//            ),
        )
    )

//    ArchiveScreen(
//        emptyArchive = false,
//        onBackClick = { navController.navigateUp() },
//    )
}

@Composable
private fun ArchiveScreen(
    emptyArchive: Boolean,
    onBackClick: () -> Unit,
    onButtonClick: () -> Unit,
    games: List<Game> // Я пока не знаю в каком виде будут приходить игры и турниры вперемешку
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = VolleyDimens.DIMEN_116.dp,
                start = VolleyDimens.DIMEN_8.dp,
                end = VolleyDimens.DIMEN_8.dp
            )
    ) {

        if (emptyArchive) {
            ArchiveNotFoundPlaceHolder(
                onBackClick = onBackClick,
                onButtonClick = onButtonClick
            )
        } else {
            ArchiveLazyColumn(
                games = games,
                onBackClick = onBackClick,
                onButtonClick = onButtonClick
            )
        }
    }
}

@Composable
private fun ArchiveLazyColumn(
    games: List<Game>,
    onBackClick: () -> Unit,
    onButtonClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp)
    ) {
        itemsIndexed(
            items = games
        ) { index, game ->
            ArchiveCard(
                game = game,
                onBackClick = onBackClick,
                onButtonClick = onButtonClick,
                showHeader = index == 0
            )
        }
    }
}

@Composable
private fun ArchiveCard(
    game: Game,
    onBackClick: () -> Unit,
    onButtonClick: () -> Unit,
    showHeader: Boolean
) {
    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = VolleyDimens.DIMEN_32
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_20.dp)
        ) {

            if (showHeader) {
                ArchiveHeader(onBackClick)
            }

            HostInfoBlock(
                host = game.host
            )

            HorizontalDivider(
                thickness = VolleyDimens.DIMEN_1.dp,
                color = VolleyColor.White
            )

            GameInfoBlock(
                game = game
            )

            DetailsButton(onButtonClick) // передавать кал
        }
    }
}

@Composable
private fun HostInfoBlock(host: Host) {
    Column {
        VolleyText.BodyBold(
            text = stringResource(R.string.game_host),
            color = VolleyColor.White,
            modifier = Modifier.align(Alignment.Start)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = VolleyDimens.DIMEN_8.dp
                ),
            verticalAlignment = Alignment.CenterVertically

        ) {
            VolleyAvatar.CircularAvatar(
                null,
                VolleyDimens.DIMEN_40.dp // добавить в модель фотку хоста и сделать лямбду
            )

            VolleyText.BodyRegular(
                text = host.name,
                color = VolleyColor.White,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = VolleyDimens.DIMEN_8.dp)
            )

            Box(
                modifier = Modifier
                    .size(VolleyDimens.DIMEN_32.dp, VolleyDimens.DIMEN_20.dp)
                    .clip(RoundedCornerShape(VolleyDimens.DIMEN_8.dp))
                    .background(VolleyColor.GreyDark)

            ) {
                VolleyText.BodyRegular(
                    text = host.level,
                    color = VolleyColor.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun GameInfoBlock(game: Game) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = VolleyDimens.DIMEN_8.dp)
        ) {
            VolleyText.BodyBold(
                text = stringResource(R.string.completed),
                color = VolleyColor.White,
                modifier = Modifier.padding(end = VolleyDimens.DIMEN_4.dp)
            )

            VolleyText.BodyRegular(
                text = game.startTime,
                color = VolleyColor.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = VolleyDimens.DIMEN_8.dp)
        ) {
            VolleyText.BodyBold(
                text = stringResource(R.string.place_archive),
                color = VolleyColor.White,
                modifier = Modifier.padding(end = VolleyDimens.DIMEN_4.dp)
            )

            VolleyText.BodyRegular(
                text = game.courtLocation.courtName,
                color = VolleyColor.White,
                maxLines = 2,
                overflow = TextOverflow.Clip,
                modifier = Modifier.weight(1f)
            )

            VolleyButton.ActiveButtonMap(
                text = stringResource(R.string.map),
                onClick = { }, // Тут будет внешний интент в карты
                paddingValues = PaddingValues(VolleyDimens.DIMEN_12.dp, VolleyDimens.DIMEN_8.dp),
                cornerRadius = VolleyDimens.DIMEN_12
            )
        }

        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = VolleyDimens.DIMEN_16,
            modifier = Modifier.fillMaxWidth()
        ) {
            VolleyText.BodyRegular(
                text = game.message,
                color = VolleyColor.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(VolleyDimens.DIMEN_16.dp)
            )
        }
    }
}

@Composable
private fun DetailsButton(onButtonClick: () -> Unit) {
    VolleyButton.OutlinedActiveButton(
        text = stringResource(R.string.details),
        onClick = { onButtonClick },
        modifier = Modifier
            .fillMaxWidth() // возможно придется переделать что ты редачил в стилях
    )
}

@Composable
private fun ArchiveNotFoundPlaceHolder(
    onBackClick: () -> Unit,
    onButtonClick: () -> Unit
) {
    VolleyContainersRootTransparent.TransparentContainer(
        cornerRadius = VolleyDimens.DIMEN_32
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_20.dp)
        ) {
            ArchiveHeader(onBackClick)
            PlaceholderMessage()
            CreateGameButton(onButtonClick)
        }
    }
}

@Composable
private fun ArchiveHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(R.drawable.arrow_left_white),
            contentDescription = null,
            tint = VolleyColor.White,
            modifier = Modifier.clickable {
                onBackClick()
            }
        )


        VolleyText.TitleLarge(
            text = stringResource(R.string.archive),
            color = VolleyColor.White,
            modifier = Modifier.align(Alignment.Center)
        )

    }
}

@Composable
private fun PlaceholderMessage() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.smiley_placeholder),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(
                    top = VolleyDimens.DIMEN_8.dp,
                    bottom = VolleyDimens.DIMEN_16.dp
                )
                .size(VolleyDimens.DIMEN_160.dp)
        )

        VolleyText.BodyRegular(
            text = stringResource(R.string.No_archived_games_found),
            color = VolleyColor.White,
            textAlign = TextAlign.Center
        )
    }
}

private fun openMap(context: Context, location: Location) {
    val uri = "geo:${location.latitude},${location.longitude}?q=${location.latitude},${location.longitude}(${
        Uri.encode(location.courtName)
    })".toUri()
    val intent = Intent(Intent.ACTION_VIEW, uri)
    val chooser = Intent.createChooser(intent, context.getString(R.string.open_with))
    context.startActivity(chooser)
}

@Composable
private fun CreateGameButton(onClick: () -> Unit) { //
    VolleyButton.ActiveButton(
        text = stringResource(R.string.create_a_game),
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ArchiveScreenPlaceholderPreview() {
    VolleyContainersRootTransparent.Root {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            ArchiveScreen(rememberNavController())
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun ArchiveScreenPreview(){
//    VolleyContainersRootTransparent.Root {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(VolleyColor.TurquoiseDark)
//        ) {
//            ArchiveScreen(rememberNavController())
//        }
//    }
//}
