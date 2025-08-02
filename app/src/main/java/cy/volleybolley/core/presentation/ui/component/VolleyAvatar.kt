package cy.volleybolley.core.presentation.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.component.VolleyAvatar.CircularAvatar
import cy.volleybolley.core.presentation.ui.model.VolleyColor

object VolleyAvatar {
    @Composable
    @Stable
    fun CircularAvatar(
        avatar: String?,
        size: Dp,
        onClick: () -> Unit = {}
    ) {
        val placeholder = painterResource(id = R.drawable.ic_avatar_placeholder)

        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .border(1.dp, VolleyColor.WHITE, CircleShape)
                .clickable(onClick = onClick)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(avatar)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(id = R.string.avatar_content_description),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                error = placeholder,
                placeholder = placeholder
            )
        }
    }
}
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

