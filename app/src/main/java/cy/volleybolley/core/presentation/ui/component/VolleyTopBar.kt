package cy.volleybolley.core.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
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
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.component.VolleyTopBar.TopBar
import cy.volleybolley.core.presentation.ui.component.model.LevelIcon
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText

object VolleyTopBar {
    private val TopBarHeight = 106.dp
    private val CornerRadius = 32.dp
    private val BackgroundColor = VolleyColor.SEAWAVE_HEADER
    private val Diameter = 46.dp
    private val MarginIcon = 8.dp

    @Composable
    @Stable
    fun TopBar(
        firstName: String?,
        avatar: String?,
        levelName: String,
        levelIconResolver: (String) -> LevelIcon = LevelIcon.Companion::fromLevelName
    ) {
        val shape = RoundedCornerShape(
            topStart = CornerSize(0.dp),
            topEnd = CornerSize(0.dp),
            bottomStart = CornerSize(CornerRadius),
            bottomEnd = CornerSize(CornerRadius)
        )
        val levelIcon = levelIconResolver(levelName)

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(TopBarHeight),
            color = BackgroundColor,
            shape = shape,
            shadowElevation = 4.dp
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Имя
                if (!firstName.isNullOrEmpty()) {
                    VolleyText.TitleMedium(
                        text = firstName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 71.dp,
                                end = 71.dp,
                                top = 63.dp
                            ),
                        color = VolleyColor.WHITE,
                        textAlign = TextAlign.Left,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                // Аватарка
                Box(
                    modifier = Modifier
                        .size(Diameter)
                        .align(Alignment.BottomStart)
                        .offset(x = MarginIcon, y = -MarginIcon)
                ) {
                    VolleyAvatar.CircularAvatar(
                        avatar = avatar,
                        size = Diameter,
                    )
                }

                // Иконка уровня игрока
                Image(
                    painter = painterResource(id = levelIcon.resId),
                    contentDescription = stringResource(id = R.string.top_bar_level_content_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(Diameter)
                        .align(Alignment.BottomEnd)
                        .offset(x = -MarginIcon, y = -MarginIcon)
                        .clip(CircleShape)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MyScreen() {
    Box {
        TopBar(
            "Artem",
            "https://avatars.mds.yandex.net/get-yapic/15298/aPbyeCWI9oijiql2AFh3GaX3xyg-1/orig",
            "pro",
        )
    }
}
