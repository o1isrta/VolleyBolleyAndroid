package cy.volleybolley.core.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography

object VolleyCashField {
    @Composable
    fun CashField(
        value: String,
        currency: String,
        inputSymbolLimit: Int = 6,
        onValueChanged: (String) -> Unit,
    ) {
        val textStyle = remember {
            VolleyTypography.BodyRegular.copy(color = VolleyColor.White, textAlign = TextAlign.Start)
        }
        val correctValue = getCorrectValue(value, inputSymbolLimit)
        val widthOfField = getFieldWidth(correctValue)

        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = 16,
            modifier = Modifier
                .sizeIn(minHeight = 30.dp, minWidth = 75.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(28.dp, 6.dp)
            ) {
                BasicTextField(
                    value = correctValue,
                    onValueChange = { newValue ->
                        onValueChanged(
                            newValue.trim().take(inputSymbolLimit)
                        )
                    },
                    singleLine = true,
                    textStyle = textStyle,
                    cursorBrush = SolidColor(VolleyColor.White),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    decorationBox = @Composable { innerTextField ->
                        Box(
                            modifier = Modifier
                                .width(widthOfField.dp)
                        ) {
                            innerTextField()
                        }
                    }
                )

                VolleyText.BodyRegular(
                    text = currency,
                    color = VolleyColor.White,
                    maxLines = 1,
                    textAlign = TextAlign.Start
                )
            }
        }
    }

    private fun getCorrectValue(value: String, inputSymbolLimit: Int): String = value.trim().take(inputSymbolLimit)

    private fun getFieldWidth(value: String): Int {
        if (value.isEmpty()) return EMPTY_FIELD_WIDTH_DP

        return value.sumOf { char ->
            if (char == '1') NARROW_DIGIT_WIDTH_DP else WIDE_DIGIT_WIDTH_DP
        }
    }

    private const val EMPTY_FIELD_WIDTH_DP = 9

    /**
     * width of "1"
     */
    private const val NARROW_DIGIT_WIDTH_DP = 6

    /**
     * width of 0, 2-9
     */
    private const val WIDE_DIGIT_WIDTH_DP = 10
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewCashField() {
    VolleyContainersRootTransparent.Root {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            Column {
                VolleyCashField.CashField(
                    value = "0",
                    currency = "$"
                ) { }

                Spacer(Modifier.height(16.dp))

                VolleyCashField.CashField(
                    value = "100",
                    currency = "€"
                ) { }

                Spacer(Modifier.height(16.dp))

                // Here we check long value crop
                VolleyCashField.CashField(
                    value = "7777777777777",
                    currency = "₽"
                ) { }
            }
        }
    }
}
