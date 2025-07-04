package cy.volleybolley

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

object VolleyTopBar {

    private val TopBarHeight = 106.dp   // Высота TopBar
    private val CornerRadius = 31.dp    // Радиусы скругления нижних углов TopBar
    // зелёный фон задать через ресурс, когда будут созданы цвета *******************************************************************
    private val BackgroundColor = Color(0xFF53A8A1) // Цвет фона TopBar

    private val AvatarDiameter = 46.dp      // Диаметр аватарки
    private val AvatarMarginStart = 8.dp    // Правый отступ аватарки
    private val AvatarMarginBottom = 8.dp   // Нижний отступ аватарки

    private val LevelDiameter = 40.dp       // Диаметр иконки уровня игрока
    private val LevelMarginEnd = 8.dp       // Левый отступ иконки уровня игрока
    private val LevelMarginBottom = 11.dp   // Нижний отступ иконки уровня игрока

    @Composable
    fun TopBar(
        avatar: String?,
        levelId: Int
    ) {
        val shape = RoundedCornerShape(
            topStart = CornerSize(0.dp),
            topEnd = CornerSize(0.dp),
            bottomStart = CornerSize(CornerRadius),
            bottomEnd = CornerSize(CornerRadius)
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(TopBarHeight),
            color = BackgroundColor,
            shape = shape,
            shadowElevation = 4.dp
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Аватарка
                Box(
                    modifier = Modifier
                        .size(AvatarDiameter)
                        .align(Alignment.BottomStart)
                        .offset(x = AvatarMarginStart, y = (-AvatarMarginBottom))
                ) {
                    VolleyAvatar.CircularAvatar(
                        avatar = avatar,
                        size = AvatarDiameter,
                    )
                }

                // Иконка уровня игрока
                // значения levelId предположительныу, уточнить когда будут реальные значения ***********************************************
                val levelIconRes = when (levelId) {
                    0 -> R.drawable.ic_level_light
                    1 -> R.drawable.ic_level_medium
                    2 -> R.drawable.ic_level_hard
                    3 -> R.drawable.ic_level_pro
                    else -> R.drawable.ic_level_light
                }

                Image(
                    painter = painterResource(id = levelIconRes),
                    contentDescription = stringResource(id = R.string.top_bar_level_content_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(LevelDiameter)
                        .align(Alignment.BottomEnd)
                        .offset(x = (-LevelMarginEnd), y = (-LevelMarginBottom))
                        .clip(CircleShape)
                )
            }
        }
    }
}