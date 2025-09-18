package cy.volleybolley.core.presentation.ui.screens.home.success

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.TransparentContainer
import cy.volleybolley.core.presentation.ui.component.VolleyButton.ActiveButton
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.ui.theme.VolleybolleyTheme

@Composable
fun SuccessScreen(navController: NavHostController) {
    SuccessScreen()
}

@Composable
private fun SuccessScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TransparentContainer(
                modifier = Modifier.padding(
                    start = VolleyDimens.DIMEN_8.dp,
                    end = VolleyDimens.DIMEN_8.dp,
                    top = VolleyDimens.DIMEN_116.dp
                ),
                cornerRadius = VolleyDimens.DIMEN_32,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(VolleyDimens.DIMEN_20.dp)
                ) {
                    Header()
                    RowIconText()
                    RowIconText()
                    RowIconText()
                    RowIconText()
                    ActiveButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = VolleyDimens.DIMEN_16.dp,
                                bottom = VolleyDimens.DIMEN_8.dp
                            ),
                        text = stringResource(R.string.done),
                        onClick = {}
                    )
                }
            }

            Row(
                Modifier.padding(
                    start = VolleyDimens.DIMEN_8.dp,
                    end = VolleyDimens.DIMEN_8.dp,
                    top = VolleyDimens.DIMEN_8.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(VolleyDimens.DIMEN_8.dp)
            ) {
                InvitePlayersButton(Modifier.weight(1f))
                ShareButton(Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun Header() {
    Column {
        VolleyText.TitleLarge(
            text = stringResource(R.string.game_created),
            color = VolleyColor.White
        )
    }
}

@Composable
fun RowIconText() {
    Row(
        modifier = Modifier.padding(top = VolleyDimens.DIMEN_16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_geo),
            modifier = Modifier.size(VolleyDimens.DIMEN_16.dp, VolleyDimens.DIMEN_15.dp),
            contentDescription = null
        )
        Column(
            modifier = Modifier.padding(VolleyDimens.DIMEN_8.dp)
        ) {
            VolleyText.BodyBold(text = "Karon Beach Club", color = VolleyColor.White)
            VolleyText.BodyLight(text = "Patak Rd, Mueng Phuket", color = VolleyColor.White)

        }
    }
}

@Composable
fun InvitePlayersButton(modifier: Modifier) {
    Button(
        modifier = modifier.height(VolleyDimens.DIMEN_180.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = VolleyColor.YellowPro,
        ),
        contentPadding = PaddingValues(top = VolleyDimens.DIMEN_20.dp, start = VolleyDimens.DIMEN_20.dp),
        shape = RoundedCornerShape(VolleyDimens.DIMEN_32.dp),
        onClick = {}) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                painter = painterResource(R.drawable.image_invite_players),
                contentDescription = null
            )
            VolleyText.TitleLarge(
                modifier = Modifier.rotate(-8f),
                text = stringResource(R.string.invite_players),
                color = VolleyColor.TextDark
            )
        }
    }
}

@Composable
fun ShareButton(modifier: Modifier) {
    TransparentContainer(
        modifier = modifier.height(VolleyDimens.DIMEN_180.dp),
    ) {
        Button(
            modifier = modifier.height(VolleyDimens.DIMEN_180.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
            ),
            contentPadding = PaddingValues(top = VolleyDimens.DIMEN_20.dp, start = VolleyDimens.DIMEN_20.dp),
            shape = RoundedCornerShape(VolleyDimens.DIMEN_32.dp),
            onClick = {}) {

            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Image(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(R.drawable.image_share_link),
                    contentDescription = null
                )
                VolleyText.TitleLarge(
                    modifier = Modifier.rotate(-8f),
                    text = stringResource(R.string.share_link),
                    color = VolleyColor.White
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=375dp,height=812dp"
)
@Composable
private fun RatePlayersPreview() {
    VolleybolleyTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            SuccessScreen()
        }
    }
}
