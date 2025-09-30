package cy.volleybolley.core.presentation.ui.model

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import cy.volleybolley.core.presentation.ui.component.model.UiLibraryMarker

@UiLibraryMarker
object VolleyText {

    @Stable
    @Composable
    fun TitleXL(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Color.Unspecified,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            style = VolleyTypography.TitleXL,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Stable
    @Composable
    fun TitleXLAlt(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Color.Unspecified,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            style = VolleyTypography.TitleXLAlt,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Stable
    @Composable
    fun TitleLarge(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Color.Unspecified,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            style = VolleyTypography.TitleLarge,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Stable
    @Composable
    fun TitleMedium(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Color.Unspecified,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            style = VolleyTypography.TitleMedium,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Stable
    @Composable
    fun BodyBoldMedium(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Color.Unspecified,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            style = VolleyTypography.BodyBoldMedium,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Stable
    @Composable
    fun BodyBold(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Color.Unspecified,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            style = VolleyTypography.BodyBold,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Stable
    @Composable
    fun BodyRegular(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Color.Unspecified,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            style = VolleyTypography.BodyRegular,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Stable
    @Composable
    fun BodySmall(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Color.Unspecified,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            style = VolleyTypography.BodySmall,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Stable
    @Composable
    fun BodyLight(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Color.Unspecified,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            style = VolleyTypography.BodyLight,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Stable
    @Composable
    fun BodyBoldSmall(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Color.Unspecified,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            style = VolleyTypography.BodyBoldSmall,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Stable
    @Composable
    fun BodyTiny(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Color.Unspecified,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            style = VolleyTypography.BodyTiny,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Stable
    @Composable
    fun BodyTinyBottomNav(
        text: String,
        modifier: Modifier = Modifier,
        style: TextStyle,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            style = style,
            modifier = modifier,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Stable
    @Composable
    fun ButtonText(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Color.Unspecified,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            style = VolleyTypography.ButtonText,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Stable
    @Composable
    fun LogoDisplay(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Color.Unspecified,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            style = VolleyTypography.LogoDisplay,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

    @Stable
    @Composable
    fun HeroBody(
        text: String,
        modifier: Modifier = Modifier,
        color: Color = Color.Unspecified,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            style = VolleyTypography.HeroBody,
            modifier = modifier,
            color = color,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow
        )
    }

}
