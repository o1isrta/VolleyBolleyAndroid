package cy.volleybolley.core.presentation.ui.model

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.core.presentation.ui.component.PreviewContainer
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
}

@Preview(showBackground = true)
@Composable
fun PreviewTitleXL() {
    PreviewContainer(
        modifier = Modifier
            .width(550.dp)
    ) {
        TitleXL(
            "Пример текста",
            modifier = Modifier
                .padding(10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTitleXLAlt() {
    PreviewContainer(
        modifier = Modifier
            .width(550.dp)
    ) {
        TitleXLAlt(
            "Пример текста",
            modifier = Modifier
                .padding(10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTitleLarge() {
    PreviewContainer(
        modifier = Modifier
            .width(550.dp)
    ) {
        TitleLarge(
            "Пример текста",
            modifier = Modifier
                .padding(10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTitleMedium() {
    PreviewContainer(
        modifier = Modifier
            .width(550.dp)
    ) {
        TitleMedium(
            "Пример текста",
            modifier = Modifier
                .padding(10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBodyBoldMedium() {
    PreviewContainer(
        modifier = Modifier
            .width(550.dp)
    ) {
        BodyBoldMedium(
            "Пример текста",
            modifier = Modifier
                .padding(10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBodySmall() {
    PreviewContainer(
        modifier = Modifier
            .width(550.dp)
    ) {
        BodySmall(
            "Пример текста",
            modifier = Modifier
                .padding(10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBodyLight() {
    PreviewContainer(
        modifier = Modifier
            .width(550.dp)
    ) {
        BodyLight(
            "Пример текста",
            modifier = Modifier
                .padding(10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBodyBoldSmall() {
    PreviewContainer(
        modifier = Modifier
            .width(550.dp)
    ) {
        BodyBoldSmall(
            "Пример текста",
            modifier = Modifier
                .padding(10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBodyTiny() {
    PreviewContainer(
        modifier = Modifier
            .width(550.dp)
    ) {
        BodyTiny(
            "Пример текста",
            modifier = Modifier
                .padding(10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewButtonText() {
    PreviewContainer(
        modifier = Modifier
            .width(550.dp)
    ) {
        ButtonText(
            "Пример текста",
            modifier = Modifier
                .padding(10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogoDisplay() {
    PreviewContainer(
        modifier = Modifier
            .width(550.dp)
    ) {
        LogoDisplay(
            "Пример текста",
            modifier = Modifier
                .padding(10.dp),
            color = VolleyColor.White,
            textAlign = TextAlign.Left
        )
    }
}
