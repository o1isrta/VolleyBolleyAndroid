package cy.volleybolley.core.presentation.ui.model

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.component.PreviewContainer
import cy.volleybolley.core.presentation.ui.component.model.UiLibraryMarker
import cy.volleybolley.core.presentation.ui.model.VolleyText.BodyBoldMedium
import cy.volleybolley.core.presentation.ui.model.VolleyText.BodyBoldSmall
import cy.volleybolley.core.presentation.ui.model.VolleyText.BodyLight
import cy.volleybolley.core.presentation.ui.model.VolleyText.BodySmall
import cy.volleybolley.core.presentation.ui.model.VolleyText.BodyTiny
import cy.volleybolley.core.presentation.ui.model.VolleyText.ButtonText
import cy.volleybolley.core.presentation.ui.model.VolleyText.LogoDisplay
import cy.volleybolley.core.presentation.ui.model.VolleyText.TitleLarge
import cy.volleybolley.core.presentation.ui.model.VolleyText.TitleMedium
import cy.volleybolley.core.presentation.ui.model.VolleyText.TitleXL
import cy.volleybolley.core.presentation.ui.model.VolleyText.TitleXLAlt

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
    fun BodyBoldGradient(
        text: String,
        modifier: Modifier = Modifier,
        textAlign: TextAlign? = null,
        maxLines: Int = Int.MAX_VALUE,
        overflow: TextOverflow = TextOverflow.Clip
    ) {
        Text(
            text = text,
            style = VolleyTypography.BodyBoldGradient,
            modifier = modifier,
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

@Preview(showBackground = true)
@Composable
private fun PreviewTitleXL() {
    PreviewContainer(
        modifier = Modifier
            .width(VolleyDimens.DIMEN_550.dp)
    ) {
        TitleXL(
            stringResource(R.string.text_example),
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTitleXLAlt() {
    PreviewContainer(
        modifier = Modifier
            .width(VolleyDimens.DIMEN_550.dp)
    ) {
        TitleXLAlt(
            stringResource(R.string.text_example),
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTitleLarge() {
    PreviewContainer(
        modifier = Modifier
            .width(VolleyDimens.DIMEN_550.dp)
    ) {
        TitleLarge(
            stringResource(R.string.text_example),
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTitleMedium() {
    PreviewContainer(
        modifier = Modifier
            .width(VolleyDimens.DIMEN_550.dp)
    ) {
        TitleMedium(
            stringResource(R.string.text_example),
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBodyBoldMedium() {
    PreviewContainer(
        modifier = Modifier
            .width(VolleyDimens.DIMEN_550.dp)
    ) {
        BodyBoldMedium(
            stringResource(R.string.text_example),
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBodySmall() {
    PreviewContainer(
        modifier = Modifier
            .width(VolleyDimens.DIMEN_550.dp)
    ) {
        BodySmall(
            stringResource(R.string.text_example),
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBodyLight() {
    PreviewContainer(
        modifier = Modifier
            .width(VolleyDimens.DIMEN_550.dp)
    ) {
        BodyLight(
            stringResource(R.string.text_example),
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBodyBoldSmall() {
    PreviewContainer(
        modifier = Modifier
            .width(VolleyDimens.DIMEN_550.dp)
    ) {
        BodyBoldSmall(
            stringResource(R.string.text_example),
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBodyTiny() {
    PreviewContainer(
        modifier = Modifier
            .width(VolleyDimens.DIMEN_550.dp)
    ) {
        BodyTiny(
            stringResource(R.string.text_example),
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewButtonText() {
    PreviewContainer(
        modifier = Modifier
            .width(VolleyDimens.DIMEN_550.dp)
    ) {
        ButtonText(
            stringResource(R.string.text_example),
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLogoDisplay() {
    PreviewContainer(
        modifier = Modifier
            .width(VolleyDimens.DIMEN_550.dp)
    ) {
        LogoDisplay(
            stringResource(R.string.text_example),
            modifier = Modifier
                .padding(VolleyDimens.DIMEN_10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}
