package cy.volleybolley.core.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.Root
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.CodeField
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.GradientFieldAlert
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.GradientFieldLight
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.GradientFieldMedium
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil

object VolleyTextFieldGradient {
    @Stable
    @Composable
    fun SearchField(
        modifier: Modifier = Modifier,
        text: String = "",
        hint: String = stringResource(R.string.search_field_hint),
        actionOnInputComplete: (String) -> Unit,
    ) {
        TextFieldBaseGradient(
            height = VolleyDimens.DIMEN_44,
            textInputValue = text,
            hint = hint,
            hintTextStyle = GradientFieldLight,
            fieldTextStyle = GradientFieldLight,
            keyboardActionButtonType = ImeAction.Search,
            actionOnInputCompleteButton = actionOnInputComplete,
            actionToTransferContent = {},
            composablePrefix = {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    tint = VolleyColor.TextDark
                )

                Spacer(modifier = Modifier.width(VolleyDimens.DIMEN_8.dp))
            },
            modifier = modifier,
        )
    }

    @Stable
    @Composable
    fun SimpleGradientTextField(
        modifier: Modifier = Modifier,
        text: String = "",
        hint: String = stringResource(R.string.text_field_hint_name),
        actionToTransferContent: (String) -> Unit,
    ) {
        TextFieldBaseGradient(
            textInputValue = text,
            hint = hint,
            hintTextStyle = GradientFieldLight,
            fieldTextStyle = GradientFieldMedium,
            actionToTransferContent = actionToTransferContent,
            modifier = modifier,
        )
    }

    @Stable
    @Composable
    fun PhoneTextField(
        modifier: Modifier = Modifier,
        text: String = "",
        hint: String = stringResource(R.string.registration_phone_field_hint),
        alertMessage: String,
        actionToTransferContent: (String) -> Unit,
    ) {
        TextFieldBaseGradient(
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

                Spacer(modifier = Modifier.width(VolleyDimens.DIMEN_4.dp))
            },
            modifier = modifier,
        )
    }

    @Stable
    @Composable
    fun PhoneCodeTextField(
        modifier: Modifier = Modifier,
        text: String = "",
        hint: String = stringResource(R.string.registration_code_field_hint),
        alertMessage: String,
        actionToTransferContent: (String) -> Unit,
    ) {
        TextFieldBaseGradient(
            textInputValue = text,
            maxTextLength = VolleyDimens.DIMEN_6,
            fieldTextStyle = CodeField,
            hint = hint,
            hintTextStyle = CodeField,
            alertMessage = alertMessage,
            messageHorizontalAlignment = Alignment.CenterHorizontally,
            contentAlignmentInsideField = Alignment.Center,
            keyboardType = KeyboardType.Number,
            actionToTransferContent = actionToTransferContent,
            modifier = modifier,
        )
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
            height = VolleyDimens.DIMEN_48,
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
        cornerRadius: Int = VolleyDimens.DIMEN_16,
        height: Int = VolleyDimens.DIMEN_52,
        textInputValue: String = "",
        maxTextLength: Int? = null,
        hint: String,
        hintTextStyle: TextStyle,
        fieldTextStyle: TextStyle,
        alertMessage: String = "",
        messageHorizontalAlignment: Alignment.Horizontal = Alignment.Start,
        contentAlignmentInsideField: Alignment = Alignment.CenterStart,
        keyboardType: KeyboardType = KeyboardType.Unspecified,
        keyboardActionButtonType: ImeAction = ImeAction.Done,
        actionOnInputCompleteButton: (String) -> Unit = {},
        actionToTransferContent: (String) -> Unit,
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
                            width = VolleyDimens.DIMEN_1.dp,
                            brush = if (alertMode) SolidColor(VolleyColor.Alert) else gradientBrush,
                            shape = RoundedCornerShape(cornerRadius.dp)
                        ),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(VolleyDimens.DIMEN_16.dp, 0.dp)
                    ) {
                        composablePrefix()

                        Box(
                            contentAlignment = contentAlignmentInsideField
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
                                    actionToTransferContent(text)
                                },
                                singleLine = true,
                                textStyle = realFieldTextStyle,
                                cursorBrush = SolidColor(VolleyColor.TextDark),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = keyboardType,
                                    imeAction = keyboardActionButtonType
                                ),
                                keyboardActions = KeyboardActions {
                                    actionOnInputCompleteButton(inputText)
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                if (alertMode) {
                    Spacer(Modifier.height(VolleyDimens.DIMEN_4.dp))
                    Text(
                        text = alertMessage,
                        style = GradientFieldAlert,
                        modifier = Modifier.align(messageHorizontalAlignment)
                    )
                }
            }
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
        Column(modifier = Modifier.fillMaxSize().background(VolleyColor.TurquoiseDark)) {
            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_44.dp))

            VolleyTextFieldGradient.SearchField(
                modifier = Modifier.padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

            VolleyTextFieldGradient.SimpleGradientTextField(
                hint = "Name",
                modifier = Modifier.padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

            VolleyTextFieldGradient.SimpleGradientTextField(
                text = "Blablablabla",
                hint = "Surname",
                modifier = Modifier.padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

            VolleyTextFieldGradient.PhoneTextField(
                alertMessage = "",
                modifier = Modifier.padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

            VolleyTextFieldGradient.PhoneTextField(
                text = "66 000000",
                alertMessage = "alarm message!",
                modifier = Modifier.padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

            VolleyTextFieldGradient.PhoneCodeTextField(
                alertMessage = "",
                modifier = Modifier.padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_16.dp))

            VolleyTextFieldGradient.PhoneCodeTextField(
                text = "623456SOME",
                alertMessage = "alarm message!",
                modifier = Modifier.padding(VolleyDimens.DIMEN_16.dp)
            ) { }
        }
    }
}
