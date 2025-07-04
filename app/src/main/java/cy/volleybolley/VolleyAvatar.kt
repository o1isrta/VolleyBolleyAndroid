package cy.volleybolley

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cy.volleybolley.VolleyAvatar.CircularAvatar

object VolleyAvatar {

    private val AVATAR_BORDER_WIDTH = 1.dp  // Толщина обводки

    @Composable
    fun CircularAvatar(
        avatar: String?,
        size: Dp,
        onClick: () -> Unit = {}
    ) {
        val shape = CircleShape
        // Плейс холдер временный - заменить, когда дизайнеры дадут векторный рисунок**************************************************
        val placehold = painterResource(id = R.drawable.ic_avatar_placeholder)

        Box(
            modifier = Modifier
                .size(size)
                // зелёный фон задать через ресурс, когда будут созданы цвета**************************************************************
                .background(color = Color(0xFF438B97), shape = shape)
                .clip(shape)
                .border(AVATAR_BORDER_WIDTH, Color.White, shape)
                .clickable { onClick() }
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(avatar)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(id = R.string.avatar_content_description),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                error = placehold,
                placeholder = placehold
            )
        }
    }
}

// К сожалению Preview не хочет подкачивать изображения, поэтому показывает заглушку
// Но на устройстве все работает, проверил, тестил с правильным url, с кривым url и с url= null. Все ОК
@Preview
@Composable
private fun MyScreen() {
    Box {
        CircularAvatar(
            "https://avatars.mds.yandex.net/get-yapic/15298/aPbyeCWI9oijiql2AFh3GaX3xyg-1/orig",
            size = 100.dp,
        )
    }
}
