package cy.volleybolley.core.presentation.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.Root
import cy.volleybolley.core.presentation.ui.component.PreviewContainer
import cy.volleybolley.core.presentation.ui.component.model.UiLibraryMarker
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.CodeField
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.GradientFieldAlert
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.GradientFieldLight
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.GradientFieldMedium
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.referencedata.domain.model.City

@UiLibraryMarker
object VolleyTextFieldGradient {
    @Stable
    @Composable
    fun SearchField(
        modifier: Modifier = Modifier,
        text: String = "",
        hint: String = stringResource(R.string.search_field_hint),
        actionToTransferContent: (String) -> Unit,
        actionOnInputComplete: (String) -> Unit,
    ) {
        TextFieldBaseGradient(
            height = 44,
            textInputValue = text,
            hint = hint,
            hintTextStyle = GradientFieldLight,
            fieldTextStyle = GradientFieldLight,
            keyboardActionButtonType = ImeAction.Search,
            actionOnInputCompleteButton = actionOnInputComplete,
            actionToTransferContent = actionToTransferContent,
            composablePrefix = {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    tint = VolleyColor.TextDark
                )

                Spacer(modifier = Modifier.width(8.dp))
            },
            modifier = modifier,
        )
    }

    @Stable
    @Composable
    fun SimpleGradientTextField(
        modifier: Modifier = Modifier,
        text: String = "",
        hint: String,
        isReadOnly: Boolean = false,
        trailingComposable: (@Composable () -> Unit)? = null,
        actionToTransferContent: (String) -> Unit,
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        TextFieldBaseGradient(
            modifier = modifier,
            textInputValue = text,
            hint = hint,
            hintTextStyle = GradientFieldLight,
            fieldTextStyle = GradientFieldMedium,
            isReadOnly = isReadOnly,
            trailingComposable = trailingComposable,
            actionToTransferContent = actionToTransferContent,
            actionOnInputCompleteButton = { keyboardController?.hide() }
        )
    }

    @Stable
    @Composable
    fun GradientTextFieldWithLabel(
        modifier: Modifier = Modifier,
        text: String,
        hint: String,
        isReadOnly: Boolean = false,
        trailingComposable: (@Composable () -> Unit)? = null,
        actionToTransferContent: (String) -> Unit,
    ) {
        Column(modifier = modifier) {
            VolleyText.BodyBold(
                text = hint,
                color = VolleyColor.White
            )
            SimpleGradientTextField(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                text = text,
                hint = hint,
                isReadOnly = isReadOnly,
                trailingComposable = trailingComposable,
                actionToTransferContent = actionToTransferContent
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Stable
    @Composable
    fun <T> GradientSpinner(
        modifier: Modifier = Modifier,
        selectedItem: T,
        hint: String,
        itemList: List<T>,
        getTextByItem: (T) -> String,
        onItemSelect: (T, index: Int) -> Unit,
    ) {
        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = modifier
        ) {
            val dropDownIconAngle by animateFloatAsState(targetValue = if (expanded) 180f else 0f)
            GradientTextFieldWithLabel(
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .padding(bottom = 6.dp),
                text = getTextByItem(selectedItem),
                hint = hint,
                isReadOnly = true,
                trailingComposable = {
                    Icon(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .rotate(dropDownIconAngle),
                        painter = painterResource(R.drawable.ic_dropdown),
                        tint = VolleyColor.TextDark,
                        contentDescription = null
                    )
                },
                actionToTransferContent = {}
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = VolleyColor.White,
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient)
                    )
                )
            ) {
                itemList.forEachIndexed { index, item ->
                    Column {
                        DropdownMenuItem(
                            text = {
                                Text(getTextByItem(item), style = GradientFieldMedium)
                            },
                            onClick = {
                                onItemSelect(item, index)
                                expanded = false
                            },
                            contentPadding = PaddingValues(all = 16.dp),
                            colors = MenuDefaults.itemColors(textColor = VolleyColor.TextField)
                        )
                        if (index != itemList.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                thickness = 1.dp,
                                color = VolleyColor.TextCalendarLightGrey
                            )
                        }
                    }
                }
            }
        }
    }

    @Stable
    @Composable
    fun PhoneTextField(
        modifier: Modifier = Modifier,
        text: String = "",
        label: String = stringResource(R.string.your_phone_number),
        hint: String = stringResource(R.string.registration_phone_field_hint),
        alertMessage: String,
        actionToTransferContent: (String) -> Unit,
    ) {
        Column(modifier = modifier) {
            VolleyText.BodyBold(
                text = label,
                color = VolleyColor.White
            )
            TextFieldBaseGradient(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                textInputValue = text,
                fieldTextStyle = GradientFieldMedium,
                hint = hint,
                hintTextStyle = GradientFieldLight,
                alertMessage = alertMessage,
                keyboardType = KeyboardType.Number,
                actionToTransferContent = actionToTransferContent,
                composablePrefix = {
                    Text(
                        text = stringResource(R.string.registration_phone_field_code_symbol),
                        style = GradientFieldMedium,
                        color = if (alertMessage.isNotEmpty()) VolleyColor.Alert else Color.Unspecified
                    )

                    Spacer(modifier = Modifier.width(4.dp))
                }
            )
        }
    }

    @Stable
    @Composable
    fun PhoneCodeTextField(
        modifier: Modifier = Modifier,
        text: String = "",
        label: String = stringResource(R.string.enter_the_6_digit_code),
        hint: String = stringResource(R.string.registration_code_field_hint),
        alertMessage: String,
        actionToTransferContent: (String) -> Unit,
    ) {
        Column(modifier = modifier) {
            VolleyText.BodyBold(
                text = label,
                color = VolleyColor.White
            )
            TextFieldBaseGradient(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                textInputValue = text,
                maxTextLength = 6,
                fieldTextStyle = CodeField,
                hint = hint,
                hintTextStyle = CodeField,
                alertMessage = alertMessage,
                messageHorizontalAlignment = Alignment.CenterHorizontally,
                contentAlignmentInsideField = Alignment.Center,
                keyboardType = KeyboardType.Number,
                actionToTransferContent = actionToTransferContent
            )
        }
    }

    @Stable
    @Composable
    fun PaymentAccountTextField(
        modifier: Modifier = Modifier,
        text: String,
        hint: String,
        showPrefix: Boolean,
        actionToTransferContent: (String) -> Unit,
    ) {
        TextFieldBaseGradient(
            height = 48,
            textInputValue = text,
            fieldTextStyle = GradientFieldMedium,
            hint = hint,
            hintTextStyle = GradientFieldLight,
            actionToTransferContent = actionToTransferContent,
            composablePrefix = {
                if (showPrefix) {
                    Text(
                        text = stringResource(R.string.enter_payment_revolut_acc_prefix),
                        style = GradientFieldMedium,
                    )
                }
            },
            modifier = modifier,
        )
    }

    @Stable
    @Composable
    private fun TextFieldBaseGradient(
        modifier: Modifier = Modifier,
        cornerRadius: Int = 16,
        height: Int = 52,
        textInputValue: String = "",
        maxTextLength: Int? = null,
        hint: String,
        hintTextStyle: TextStyle,
        fieldTextStyle: TextStyle,
        alertMessage: String = "",
        isReadOnly: Boolean = false,
        messageHorizontalAlignment: Alignment.Horizontal = Alignment.Start,
        contentAlignmentInsideField: Alignment = Alignment.CenterStart,
        keyboardType: KeyboardType = KeyboardType.Unspecified,
        keyboardActionButtonType: ImeAction = ImeAction.Done,
        actionOnInputCompleteButton: (String) -> Unit = {},
        actionToTransferContent: (String) -> Unit,
        trailingComposable: (@Composable () -> Unit)? = null,
        composablePrefix: @Composable () -> Unit = {},
    ) {
        val inputText = VolleyUiUtil.getLimitedText(maxTextLength, textInputValue)
        val alertMode = alertMessage.isNotEmpty()

        val gradientBrush = remember {
            Brush.verticalGradient(
                colors = listOf(
                    VolleyColor.YellowForGradient,
                    VolleyColor.GreenForGradient
                )
            )
        }

        val realHintTextStyle = getTextStyleByAlertMode(alertMode, hintTextStyle)
        val realFieldTextStyle = getTextStyleByAlertMode(alertMode, fieldTextStyle)

        Box(
            modifier = modifier
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height.dp)
                        .background(
                            color = VolleyColor.White,
                            shape = RoundedCornerShape(cornerRadius.dp)
                        )
                        .border(
                            width = 1.dp,
                            brush = if (alertMode) SolidColor(VolleyColor.Alert) else gradientBrush,
                            shape = RoundedCornerShape(cornerRadius.dp)
                        ),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp, 0.dp)
                    ) {
                        composablePrefix()

                        Box(
                            contentAlignment = contentAlignmentInsideField,
                            modifier = Modifier.weight(1f)
                        ) {
                            if (inputText.isEmpty()) {
                                Text(
                                    text = hint,
                                    style = realHintTextStyle
                                )
                            }

                            BasicTextField(
                                value = inputText,
                                onValueChange = { text ->
                                    checkedTransferContent(text, maxTextLength, actionToTransferContent)
                                },
                                singleLine = true,
                                textStyle = realFieldTextStyle,
                                cursorBrush = SolidColor(VolleyColor.TextDark),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = keyboardType,
                                    imeAction = keyboardActionButtonType
                                ),
                                readOnly = isReadOnly,
                                keyboardActions = KeyboardActions {
                                    actionOnInputCompleteButton(inputText)
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        trailingComposable?.invoke()
                    }
                }

                if (alertMode) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = alertMessage,
                        style = GradientFieldAlert,
                        modifier = Modifier.align(messageHorizontalAlignment)
                    )
                }
            }
        }
    }

    private inline fun checkedTransferContent(
        text: String,
        maxTextLength: Int? = null,
        actionToTransferContent: (String) -> Unit,
    ) {
        if (maxTextLength != null) {
            if (text.length <= maxTextLength) {
                actionToTransferContent(text)
            }
        } else {
            actionToTransferContent(text)
        }
    }

    private fun getTextStyleByAlertMode(alertMode: Boolean, baseTextStyle: TextStyle): TextStyle {
        return if (alertMode) baseTextStyle.copy(color = VolleyColor.Alert) else baseTextStyle
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewGradientTextFields() {
    Root {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(VolleyColor.TurquoiseDark)
        ) {
            Spacer(modifier = Modifier.height(44.dp))

            VolleyTextFieldGradient.SearchField(
                modifier = Modifier.padding(16.dp),
                actionToTransferContent = {}
            ) { }

            Spacer(modifier = Modifier.height(16.dp))

            VolleyTextFieldGradient.SimpleGradientTextField(
                hint = "Name",
                modifier = Modifier.padding(16.dp)
            ) { }

            Spacer(modifier = Modifier.height(16.dp))

            VolleyTextFieldGradient.SimpleGradientTextField(
                text = "Blablablabla",
                hint = "Surname",
                modifier = Modifier.padding(16.dp)
            ) { }

            Spacer(modifier = Modifier.height(16.dp))

            VolleyTextFieldGradient.PhoneTextField(
                alertMessage = "",
                modifier = Modifier.padding(16.dp)
            ) { }

            Spacer(modifier = Modifier.height(16.dp))

            VolleyTextFieldGradient.PhoneTextField(
                text = "66 000000",
                alertMessage = "alarm message!",
                modifier = Modifier.padding(16.dp)
            ) { }

            Spacer(modifier = Modifier.height(16.dp))

            VolleyTextFieldGradient.PhoneCodeTextField(
                alertMessage = "",
                modifier = Modifier.padding(16.dp)
            ) { }

            Spacer(modifier = Modifier.height(16.dp))

            VolleyTextFieldGradient.PhoneCodeTextField(
                text = "623456SOME",
                alertMessage = "alarm message!",
                modifier = Modifier.padding(16.dp)
            ) { }
        }
    }
}

@Preview
@Composable
fun PreviewGradientTextFieldWithLabel() {
    PreviewContainer {
        VolleyTextFieldGradient.GradientTextFieldWithLabel(
            modifier = Modifier.fillMaxWidth(),
            text = "",
            hint = stringResource(R.string.text_field_hint_name),
            actionToTransferContent = {}
        )
    }
}

@Preview
@Composable
fun PreviewGradientSpinner() {
    PreviewContainer(modifier = Modifier.height(400.dp)) {
        VolleyTextFieldGradient.GradientSpinner(
            modifier = Modifier.fillMaxWidth(),
            selectedItem = City(id = 0, name = "Very very long city"),
            hint = "Your city",
            itemList = listOf(
                City(id = 0, name = "Very very long city"),
                City(id = 1, name = "Koh Samui")
            ),
            getTextByItem = { it.name },
            onItemSelect = { city, index -> }
        )
    }
}
