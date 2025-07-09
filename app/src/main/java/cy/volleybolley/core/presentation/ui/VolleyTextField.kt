package cy.volleybolley.core.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainer.Root
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyType.TextStyleGradientFieldLight
import cy.volleybolley.core.presentation.ui.model.VolleyType.TextStyleGradientFieldMedium
import cy.volleybolley.core.presentation.ui.model.VolleyType.TextStyleGradientFieldAlert
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object VolleyTextField {

    @Composable
    fun SearchField(
        modifier: Modifier = Modifier,
        hint: String = stringResource(R.string.search_field_hint),
        actionOnInputComplete: (String) -> Unit,
    ) {
        TextFieldBaseGradient(
            isSearchField = true,
            hint = hint,
            modifier = modifier,
            actionOnInputCompleteButton = { string -> actionOnInputComplete(string) },
            actionToTransferContent = {}
        )
    }

    @Composable
    fun NameTextField(
        modifier: Modifier = Modifier,
        actionToTransferContent: (String) -> Unit,
    ) {
        TextFieldBaseGradient(
            hint = stringResource(R.string.text_field_hint_name),
            modifier = modifier,
            actionOnInputCompleteButton = {}
        ) { string ->
            actionToTransferContent(string)
        }
    }

    @Composable
    fun SurnameTextField(
        modifier: Modifier = Modifier,
        actionToTransferContent: (String) -> Unit,
    ) {
        TextFieldBaseGradient(
            hint = stringResource(R.string.text_field_hint_surname),
            modifier = modifier,
            actionOnInputCompleteButton = {}
        ) { string ->
            actionToTransferContent(string)
        }
    }

    @Composable
    fun PhoneTextField(
        modifier: Modifier = Modifier,
        alertMessage: String,
        actionToTransferContent: (String) -> Unit,
    ) {
        TextFieldBaseGradient(
            isPhoneField = true,
            hint = stringResource(R.string.registration_phone_field_hint),
            modifier = modifier,
            alertMessage = alertMessage,
            actionOnInputCompleteButton = {}
        ) { string ->
            actionToTransferContent(string)
        }
    }

    @Composable
    private fun TextFieldBaseGradient(
        modifier: Modifier = Modifier,
        cornerRadius: Int = VolleyDimens.DIMEN_16,
        height: Int = VolleyDimens.DIMEN_52,
        hint: String,
        isSearchField: Boolean = false,
        isPhoneField: Boolean = false,
        isCodeField: Boolean = false,
        alertMessage: String = "",
        actionOnInputCompleteButton: (String) -> Unit = {},
        actionToTransferContent: (String) -> Unit,
    ) {
        var inputText by remember { mutableStateOf("") }
        var alertMode = alertMessage.isNotEmpty()
        val setupHeight = if (isSearchField) VolleyDimens.DIMEN_44 else height

        val gradientBrush = Brush
            .verticalGradient(
                colors = listOf(
                    VolleyColor.YELLOW_GRADIENT,
                    VolleyColor.GREEN_GRADIENT
                )
            )

        val fieldTextStyleMedium =
            if (alertMode) TextStyleGradientFieldMedium.copy(color = VolleyColor.ALERT) else TextStyleGradientFieldMedium

        val fieldTextStyleLight =
            if (alertMode) TextStyleGradientFieldLight.copy(color = VolleyColor.ALERT) else TextStyleGradientFieldLight

        Box(
            modifier = modifier
        ) {
            Column {
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
                            brush = if (alertMode) SolidColor(VolleyColor.ALERT) else gradientBrush,
                            shape = RoundedCornerShape(cornerRadius.dp)
                        ),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(VolleyDimens.DIMEN_16.dp, 0.dp)
                    ) {
                        if (isSearchField) {
                            Icon(
                                painter = painterResource(R.drawable.ic_search),
                                contentDescription = null,
                                tint = VolleyColor.TEXT_DARK
                            )

                            Spacer(modifier = Modifier.width(VolleyDimens.DIMEN_8.dp))
                        }

                        if (isPhoneField) {
                            Text(
                                text = stringResource(R.string.registration_phone_field_code_symbol),
                                style = fieldTextStyleMedium,
                                color = if (alertMode) VolleyColor.ALERT else Color.Unspecified
                            )

                            Spacer(modifier = Modifier.width(VolleyDimens.DIMEN_4.dp))
                        }

                        Box {
                            if (inputText.isEmpty()) {
                                Text(
                                    text = hint,
                                    style = if (isSearchField || isPhoneField) fieldTextStyleLight else TextStyleGradientFieldMedium
                                )
                            }

                            BasicTextField(
                                value = inputText,
                                onValueChange = {
                                    if (alertMode && it.isEmpty()) { alertMode = false }
                                    inputText = it
                                    actionToTransferContent(it)
                                },
                                singleLine = true,
                                textStyle = if (isSearchField) TextStyleGradientFieldLight else fieldTextStyleMedium,
                                cursorBrush = SolidColor(VolleyColor.TEXT_DARK),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = if (isPhoneField || isCodeField) KeyboardType.Number else KeyboardType.Unspecified,
                                    imeAction = if (isSearchField) ImeAction.Search else ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions {
                                    if (isSearchField) actionOnInputCompleteButton(inputText)
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
                        style = TextStyleGradientFieldAlert
                    )
                }
            }
        }
    }

    @Composable
    fun DataPickerField(
        modifier: Modifier = Modifier,
        cornerRadius: Int = VolleyDimens.DIMEN_16,
        height: Int = VolleyDimens.DIMEN_52,
        hint: String = stringResource(R.string.registration_date_of_birth_hint),
        actionForSaveDate: (String) -> Unit,
    ) {
        val shape = RoundedCornerShape(cornerRadius.dp)
        var showModal by remember { mutableStateOf(false) }
        var selectedDate by remember { mutableStateOf<Long?>(null) }

        val correctText = selectedDate?.let { convertMillisToDate(it) } ?: hint

        val gradientBrush = Brush.verticalGradient(
            colors = listOf(VolleyColor.YELLOW_GRADIENT, VolleyColor.GREEN_GRADIENT)
        )

        Box(
            modifier = modifier
                .clickable(
                    interactionSource = null,
                    indication = null,
                ) {
                    showModal = true
                }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(height.dp)
                    .background(
                        color = Color.White,
                        shape = shape
                    )
                    .border(
                        width = VolleyDimens.DIMEN_1.dp,
                        brush = gradientBrush,
                        shape = shape
                    )
                    .padding(VolleyDimens.DIMEN_22.dp, 0.dp)
            ) {
                Text(
                    text = correctText,
                    color = VolleyColor.TEXT_FIELD,
                    style = TextStyleGradientFieldMedium,
                )
            }
        }

        if (showModal) {
            DatePickerModal(
                onDateSelected = { date ->
                    selectedDate = date
                },
                onDismiss = { showModal = false },
                actionForSaveDate = actionForSaveDate,
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun DatePickerModal(
        onDateSelected: (Long?) -> Unit,
        onDismiss: () -> Unit,
        actionForSaveDate: (String) -> Unit
    ) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            shape = RoundedCornerShape(VolleyDimens.DIMEN_24.dp),
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    val dateString = datePickerState.selectedDateMillis?.let { convertMillisToDate(it) } ?: ""
                    actionForSaveDate(dateString)
                    onDismiss()
                }) {
                    Text(
                        text = stringResource(R.string.registration_date_of_birth_ok),
                        style = TextStyleGradientFieldMedium,
                        color = VolleyColor.SEAWAVE_BACKGROUND
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = stringResource(R.string.registration_date_of_birth_cancel),
                        style = TextStyleGradientFieldMedium,
                        color = VolleyColor.SEAWAVE_BACKGROUND
                    )
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = VolleyColor.WHITE,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_24.dp, 0.dp)
        ) {
            DatePicker(
                title = null,
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = VolleyColor.WHITE,
                    titleContentColor = VolleyColor.TEXT_CALENDAR_DARK,
                    headlineContentColor = VolleyColor.TEXT_CALENDAR_DARK,
                    weekdayContentColor = VolleyColor.TEXT_CALENDAR_DARK,
                    navigationContentColor = VolleyColor.TEXT_CALENDAR_DARK,
                    yearContentColor = VolleyColor.TEXT_CALENDAR_DARK,
                    disabledYearContentColor = VolleyColor.TEXT_CALENDAR_LIGHT_GREY,
                    currentYearContentColor = VolleyColor.SEAWAVE_BACKGROUND,
                    selectedYearContentColor = VolleyColor.YELLOW_GRADIENT,
                    disabledSelectedYearContentColor = VolleyColor.TEXT_CALENDAR_LIGHT_GREY,
                    selectedYearContainerColor = VolleyColor.GREEN_GRADIENT,
                    disabledSelectedYearContainerColor = VolleyColor.TEXT_DARK,
                    dayContentColor = VolleyColor.TEXT_CALENDAR_DARK,
                    disabledDayContentColor = VolleyColor.TEXT_CALENDAR_LIGHT_GREY,
                    selectedDayContentColor = VolleyColor.YELLOW_GRADIENT,
                    disabledSelectedDayContentColor = VolleyColor.TEXT_CALENDAR_LIGHT_GREY,
                    selectedDayContainerColor = VolleyColor.GREEN_GRADIENT,
                    disabledSelectedDayContainerColor = VolleyColor.TEXT_DARK,
                    todayContentColor = VolleyColor.SEAWAVE_BACKGROUND,
                    todayDateBorderColor = VolleyColor.SEAWAVE_BACKGROUND,
                    dividerColor = VolleyColor.SEAWAVE_BACKGROUND,
                ),
            )
        }
    }

    private fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    @Composable
    fun CountActionField(
        maximumCount: Int = VolleyDimens.DIMEN_24,
        minimumCount: Int = VolleyDimens.DIMEN_4,
        paddingValues: PaddingValues = PaddingValues(),
        actionForSaveCount: (Int) -> Unit,
    ) {

        var count by remember { mutableIntStateOf(minimumCount) }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (count > minimumCount) {
                    Icon(
                        painter = painterResource(R.drawable.ic_minus),
                        contentDescription = null,
                        tint = VolleyColor.WHITE,
                        modifier = Modifier
                            .clickable(null,null) {
                                count -= 1
                                actionForSaveCount(count)
                            }
                    )
                    Spacer(Modifier.width(VolleyDimens.DIMEN_8.dp))
                }

                CountField(text = count.toString())

                if (count < maximumCount) {
                    Spacer(Modifier.width(VolleyDimens.DIMEN_8.dp))
                    Icon(
                        painter = painterResource(R.drawable.ic_plus),
                        contentDescription = null,
                        tint = VolleyColor.WHITE,
                        modifier = Modifier
                            .clickable(null,null) {
                                count +=1
                                actionForSaveCount(count)
                            }
                    )
                }
            }
        }

    }

    @Composable
    private fun CountField(
        modifier: Modifier = Modifier,
        cornerRadius: Int = VolleyDimens.DIMEN_16,
        height: Int = VolleyDimens.DIMEN_40,
        text: String,
    ) {
        val shape = RoundedCornerShape(cornerRadius.dp)

        val gradientBrush = Brush.verticalGradient(
            colors = listOf(VolleyColor.YELLOW_GRADIENT, VolleyColor.GREEN_GRADIENT)
        )

        Box(
            modifier = modifier
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(height.dp)
                    .background(
                        color = Color.White,
                        shape = shape
                    )
                    .border(
                        width = VolleyDimens.DIMEN_1.dp,
                        brush = gradientBrush,
                        shape = shape
                    )
                    .padding(VolleyDimens.DIMEN_27.dp, 0.dp)
            ) {
                Text(
                    text = text,
                    color = VolleyColor.TEXT_FIELD,
                    style = TextStyleGradientFieldMedium,
                )
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewGradientTextFields() {
    Root {
        Column(modifier = Modifier.background(VolleyColor.SEAWAVE_BACKGROUND)) {

            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_44.dp))

            VolleyTextField.SearchField(
                modifier = Modifier.padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            VolleyTextField.NameTextField(
                modifier = Modifier.padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            VolleyTextField.SurnameTextField(
                modifier = Modifier.padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            VolleyTextField.PhoneTextField(
                modifier = Modifier.padding(VolleyDimens.DIMEN_16.dp),
                alertMessage = "",
            ) { }

            VolleyTextField.PhoneTextField(
                modifier = Modifier.padding(VolleyDimens.DIMEN_16.dp),
                alertMessage = "Please enter valid phone number",
            ) { }

            VolleyTextField.DataPickerField(
                modifier = Modifier.padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            VolleyTextField.CountActionField(
                paddingValues = PaddingValues(VolleyDimens.DIMEN_16.dp)
            ) { }

        }
    }
}