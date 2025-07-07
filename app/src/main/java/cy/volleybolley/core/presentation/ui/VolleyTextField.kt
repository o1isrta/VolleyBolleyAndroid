package cy.volleybolley.core.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainer.Root
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyType.TextStyleLight14
import cy.volleybolley.core.presentation.ui.model.VolleyType.TextStyleMedium16

object VolleyTextField {

    @Composable
    fun SearchField(
        modifier: Modifier = Modifier,
        actionOnInputComplete: (String) -> Unit,
    ) {
        TextFieldBaseGradient(
            isSearchField = true,
            hint = stringResource(R.string.search_field_hint),
            modifier = modifier,
        ) { string ->
            actionOnInputComplete(string)
        }
    }

    @Composable
    fun NameTextField(
        modifier: Modifier = Modifier,
        actionOnInputComplete: (String) -> Unit,
    ) {
        TextFieldBaseGradient(
            isSearchField = false,
            hint = stringResource(R.string.text_field_hint_name),
            modifier = modifier,
        ) { string ->
            actionOnInputComplete(string)
        }
    }

    @Composable
    fun SurnameTextField(
        modifier: Modifier = Modifier,
        actionOnInputComplete: (String) -> Unit,
    ) {
        TextFieldBaseGradient(
            isSearchField = false,
            hint = stringResource(R.string.text_field_hint_surname),
            modifier = modifier,
        ) { string ->
            actionOnInputComplete(string)
        }
    }

    @Composable
    private fun TextFieldBaseGradient(
        modifier: Modifier = Modifier,
        cornerRadius: Int = VolleyDimens.DIMEN_16,
        height: Int = VolleyDimens.DIMEN_52,
        hint: String,
        isSearchField: Boolean,
        actionOnInputComplete: (String) -> Unit,
    ) {
        var inputText by remember { mutableStateOf("") }

        val gradientBrush = Brush.verticalGradient(
            colors = listOf(VolleyColor.YELLOW_GRADIENT, VolleyColor.GREEN_GRADIENT)
        )

        val setupHeight = if (isSearchField) VolleyDimens.DIMEN_44 else height

        Box(
            modifier = modifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(setupHeight.dp)
                    .background(
                        color = VolleyColor.WHITE,
                        shape = RoundedCornerShape(cornerRadius.dp)
                    )
                    .border(
                        width = VolleyDimens.DIMEN_1.dp,
                        brush = gradientBrush,
                        shape = RoundedCornerShape(cornerRadius.dp)
                    ),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize().padding(VolleyDimens.DIMEN_16.dp, 0.dp)
                ) {
                    if (isSearchField) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = null,
                            tint = VolleyColor.TEXT_DARK
                        )

                        Spacer(modifier = Modifier.width(VolleyDimens.DIMEN_8.dp))
                    }

                    Box {
                        if (inputText.isEmpty()) {
                            Text(
                                text = hint,
                                style = if (isSearchField) TextStyleLight14 else TextStyleMedium16
                            )
                        }

                        BasicTextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            singleLine = true,
                            textStyle = if (isSearchField) TextStyleLight14 else TextStyleMedium16,
                            cursorBrush = SolidColor(VolleyColor.TEXT_DARK),
                            keyboardOptions = KeyboardOptions(
                                imeAction = if (isSearchField) ImeAction.Search else ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions { actionOnInputComplete(inputText) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SearchField() {
    Root {

    }
}