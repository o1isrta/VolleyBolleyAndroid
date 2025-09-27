package cy.volleybolley.core.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.Root
import cy.volleybolley.core.presentation.ui.component.model.UiLibraryMarker
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyColor.TextField
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyTimeStamp
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.GradientFieldMedium
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import cy.volleybolley.ui.theme.Background
import java.util.Calendar

@UiLibraryMarker
object VolleyTextFieldAttribute {
    @Composable
    fun DatePickerField(
        modifier: Modifier = Modifier,
        cornerRadius: Int = VolleyDimens.DIMEN_16,
        height: Int = VolleyDimens.DIMEN_52,
        inputDate: Long?,
        actionForSaveDate: (Long?) -> Unit,
    ) {
        val shape = RoundedCornerShape(cornerRadius.dp)
        var showDateDialog by remember { mutableStateOf(false) }

        val correctText = inputDate?.let { dateInMillis ->
            VolleyUiUtil.convertMillisToTextDate(
                VolleyUiUtil.DATE_OF_BIRTH_FIELD_PATTERN,
                dateInMillis
            )
        } ?: VolleyUiUtil.DATE_FIELD_HINT

        val gradientBrush = Brush.verticalGradient(
            colors = listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient)
        )

        Box(
            modifier = modifier
                .clickable(
                    interactionSource = null,
                    indication = null,
                ) {
                    showDateDialog = true
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
                    color = VolleyColor.TextField,
                    style = GradientFieldMedium,
                )
            }
        }

        if (showDateDialog) {
            DatePickerDialog(
                onDismiss = { showDateDialog = false },
                actionForSaveDate = actionForSaveDate,
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun DatePickerDialog(
        onDismiss: () -> Unit,
        actionForSaveDate: (Long?) -> Unit
    ) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            shape = RoundedCornerShape(VolleyDimens.DIMEN_24.dp),
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    actionForSaveDate(datePickerState.selectedDateMillis)
                    onDismiss()
                }) {
                    Text(
                        text = stringResource(R.string.registration_date_of_birth_ok),
                        style = GradientFieldMedium,
                        color = VolleyColor.TurquoiseDark
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = stringResource(R.string.registration_date_of_birth_cancel),
                        style = GradientFieldMedium,
                        color = VolleyColor.TurquoiseDark
                    )
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = VolleyColor.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(VolleyDimens.DIMEN_24.dp, 0.dp)
        ) {
            DatePicker(
                title = null,
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = VolleyColor.White,
                    titleContentColor = VolleyColor.TextCalendarDark,
                    headlineContentColor = VolleyColor.TextCalendarDark,
                    weekdayContentColor = VolleyColor.TextCalendarDark,
                    navigationContentColor = VolleyColor.TextCalendarDark,
                    yearContentColor = VolleyColor.TextCalendarDark,
                    disabledYearContentColor = VolleyColor.TextCalendarLightGrey,
                    currentYearContentColor = VolleyColor.TurquoiseDark,
                    selectedYearContentColor = VolleyColor.YellowForGradient,
                    disabledSelectedYearContentColor = VolleyColor.TextCalendarLightGrey,
                    selectedYearContainerColor = VolleyColor.GreenForGradient,
                    disabledSelectedYearContainerColor = VolleyColor.TextDark,
                    dayContentColor = VolleyColor.TextCalendarDark,
                    disabledDayContentColor = VolleyColor.TextCalendarLightGrey,
                    selectedDayContentColor = VolleyColor.YellowForGradient,
                    disabledSelectedDayContentColor = VolleyColor.TextCalendarLightGrey,
                    selectedDayContainerColor = VolleyColor.GreenForGradient,
                    disabledSelectedDayContainerColor = VolleyColor.TextDark,
                    todayContentColor = VolleyColor.TurquoiseDark,
                    todayDateBorderColor = VolleyColor.TurquoiseDark,
                    dividerColor = VolleyColor.TurquoiseDark,
                ),
            )
        }
    }

    @Stable
    @Composable
    fun CountField(
        maximumCount: Int = VolleyDimens.DIMEN_24,
        minimumCount: Int = VolleyDimens.DIMEN_4,
        inputCount: Int = VolleyDimens.DIMEN_4,
        paddingValues: PaddingValues = PaddingValues(),
        actionToTransferCount: (Int) -> Unit,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (inputCount > minimumCount) {
                    Icon(
                        painter = painterResource(R.drawable.ic_minus),
                        contentDescription = null,
                        tint = VolleyColor.White,
                        modifier = Modifier
                            .clickable(null, null) {
                                actionToTransferCount(inputCount - 1)
                               // val newCount = inputCount - 1
                               // actionToTransferCount(newCount)
                            }
                    )
                    Spacer(Modifier.width(VolleyDimens.DIMEN_8.dp))
                }

                CountTextField(text = inputCount.toString())

                if (inputCount < maximumCount) {
                    Spacer(Modifier.width(VolleyDimens.DIMEN_8.dp))
                    Icon(
                        painter = painterResource(R.drawable.ic_plus),
                        contentDescription = null,
                        tint = VolleyColor.White,
                        modifier = Modifier
                            .clickable(null, null) {
                                actionToTransferCount(inputCount + 1)
                               // val newCount = inputCount + 1
                               // actionToTransferCount(newCount)
                            }
                    )
                }
            }
        }
    }

    @Stable
    @Composable
    private fun CountTextField(
        modifier: Modifier = Modifier,
        cornerRadius: Int = VolleyDimens.DIMEN_16,
        height: Int = VolleyDimens.DIMEN_40,
        text: String,
    ) {
        val shape = RoundedCornerShape(cornerRadius.dp)

        val gradientBrush = Brush.verticalGradient(
            colors = listOf(VolleyColor.YellowForGradient, VolleyColor.GreenForGradient)
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
                    color = VolleyColor.TextField,
                    style = GradientFieldMedium,
                )
            }
        }
    }

    @Composable
    fun DurationField(
        modifier: Modifier = Modifier,
        cornerRadius: Int = VolleyDimens.DIMEN_16,
        inputTime: VolleyTimeStamp?,
        actionForSaveTime: (VolleyTimeStamp?) -> Unit,
    ) {
        var showTimePicker by remember { mutableStateOf(false) }
        val correctTimeString = inputTime?.getCorrectTimeString() ?: stringResource(R.string.duration_time_hint)
        val correctAfternoonMark: String = inputTime?.getAfternoonMark() ?: VolleyTimeStamp.PM_MARK

        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = cornerRadius,
            modifier = modifier
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        showTimePicker = true
                    }
            ) {
                VolleyText.BodyRegular(
                    text = correctTimeString,
                    color = VolleyColor.White,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(
                            start = VolleyDimens.DIMEN_16.dp,
                            top = VolleyDimens.DIMEN_13.dp,
                            end = VolleyDimens.DIMEN_4.dp,
                            bottom = VolleyDimens.DIMEN_13.dp
                        )
                )

                VolleyText.BodyRegular(
                    text = correctAfternoonMark,
                    color = VolleyColor.White,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(
                            start = VolleyDimens.DIMEN_4.dp,
                            top = 0.dp,
                            end = VolleyDimens.DIMEN_16.dp,
                            bottom = 0.dp
                        )
                )
            }
        }

        if (showTimePicker) {
            TimePickerDialog(
                onDismiss = { showTimePicker = false },
                actionForSaveTime = actionForSaveTime,
            )
        }
    }

    @Composable
    fun DurationFieldWithArrows(
        modifier: Modifier = Modifier,
        cornerRadius: Int = VolleyDimens.DIMEN_16,
        inputTime: VolleyTimeStamp?,
        actionForSaveTime: (VolleyTimeStamp?) -> Unit,
    ) {
        var showTimePicker by remember { mutableStateOf(false) }
        val correctTimeString = inputTime?.getCorrectTimeString() ?: stringResource(R.string.duration_time_hint)
        val correctAfternoonMark: String = inputTime?.getAfternoonMark() ?: VolleyTimeStamp.PM_MARK

        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = cornerRadius,
            modifier = modifier
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(16.dp,6.dp)
                    .clickable {
                        showTimePicker = true
                    }
            ) {
                VolleyText.BodyRegular(
                    text = correctTimeString,
                    color = VolleyColor.White,
                    maxLines = 1,
                    modifier = Modifier
                    /*    .padding(
                            start = VolleyDimens.DIMEN_16.dp,
                            top = VolleyDimens.DIMEN_13.dp,
                            end = VolleyDimens.DIMEN_4.dp,
                            bottom = VolleyDimens.DIMEN_13.dp
                        )*/
                )
                Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_4.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.width(24.dp)
                )
                {
                    Image(
                        painter = painterResource(R.drawable.up),
                        contentDescription = null,
                        modifier = Modifier.size(width = 8.dp, height = 4.dp)
                    )
                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_4.dp))

                    VolleyText.BodySmall(
                        text = correctAfternoonMark,
                        color = VolleyColor.White,
                        maxLines = 1,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.size(size = VolleyDimens.DIMEN_4.dp))
                    Image(
                        painter = painterResource(R.drawable.down),
                        contentDescription = null,
                        modifier = Modifier.size(width = 8.dp, height = 4.dp)
                    )
                }
            }
        }

        if (showTimePicker) {
            TimePickerDialog(
                onDismiss = { showTimePicker = false },
                actionForSaveTime = actionForSaveTime,
            )
        }
    }

  /*  @Composable
    fun PaymentField(
        modifier: Modifier = Modifier,
        width: Int = VolleyDimens.DIMEN_75,
        height: Int = VolleyDimens.DIMEN_30,
        cornerRadius: Int = VolleyDimens.DIMEN_16,
        inputPayment: Double = 5.0
    ) {
        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = cornerRadius,
            modifier = modifier //modifier.height(height.dp).width(width.dp),
        ) {
              DecimalInputMask(*//*modifier,*//* width, height, inputPayment)
        }
    }

    @Composable
    fun DecimalInputMask(*//*modifier: Modifier, *//*width: Int, height: Int, inputPayment: Double) {
        var text by remember { mutableStateOf(inputPayment.toString()) }

        Row(
            modifier = Modifier
            .height(height.dp)
            .width(width.dp))
        {
            TextField(
                value = text,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedTextColor = VolleyColor.White,
                    focusedTextColor = VolleyColor.White
                ),
                suffix = {
                    VolleyText.BodyRegular(
                        text = stringResource(R.string.dollar),
                        color = VolleyColor.White,
                        maxLines = 1,
                        modifier = Modifier
                    )
                },
                onValueChange = { newText ->
                    // 1. Удаляем все символы, кроме цифр и точки
                    val filteredText = newText.replace(Regex("[^0-9.]"), "")

                    // 2. Проверяем количество точек
                    val dotCount = filteredText.count { it == '.' }
                    if (dotCount > 1) {
                        // Если больше одной точки, оставляем только первую
                        text = text //не меняем значение
                    } else {
                        // 3. Если есть точка, проверяем количество знаков после неё
                        val parts = filteredText.split(".")
                        if (parts.size == 2 && parts[1].length > 2) {
                            //Если больше 2 символов оставляем предыдущее значение
                            text = text
                        } else {
                            // 4. Обновляем текст, если все проверки пройдены
                            text = filteredText
                        }
                    }
                },
                //label = "",//{ Text("Введите число (до 2 знаков после запятой)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                modifier = Modifier.padding(0.dp)
            )
        }
    }*/

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TimePickerDialog(
        onDismiss: () -> Unit,
        actionForSaveTime: (VolleyTimeStamp?) -> Unit,
    ) {
        val currentTime = Calendar.getInstance()
        val timePickerState = rememberTimePickerState(
            initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
            initialMinute = currentTime.get(Calendar.MINUTE),
            is24Hour = false,
        )

        AlertDialog(
            containerColor = VolleyColor.White,
            onDismissRequest = onDismiss,
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    VolleyText.BodyBold(
                        text = stringResource(R.string.registration_date_of_birth_cancel),
                        color = VolleyColor.TurquoiseDark,
                        maxLines = 1
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val stampOfTime = VolleyTimeStamp(
                            hour = timePickerState.hour,
                            minutes = timePickerState.minute,
                            isAfternoon = timePickerState.isAfternoon
                        )
                        actionForSaveTime(stampOfTime)
                        onDismiss()
                    }
                ) {
                    VolleyText.BodyBold(
                        text = stringResource(R.string.ok),
                        color = VolleyColor.Turquoise,
                        maxLines = 1
                    )
                }
            },
            text = {
                TimePicker(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        clockDialColor = VolleyColor.Turquoise,
                        clockDialSelectedContentColor = VolleyColor.TextDark,
                        clockDialUnselectedContentColor = VolleyColor.White,
                        selectorColor = VolleyColor.GreenForGradient,
                        containerColor = VolleyColor.GreenForGradient,
                        periodSelectorBorderColor = VolleyColor.White,
                        periodSelectorSelectedContainerColor = VolleyColor.GreenForGradient,
                        periodSelectorUnselectedContainerColor = VolleyColor.Turquoise,
                        periodSelectorSelectedContentColor = VolleyColor.TextDark,
                        periodSelectorUnselectedContentColor = VolleyColor.White,
                        timeSelectorSelectedContainerColor = VolleyColor.GreenForGradient,
                        timeSelectorUnselectedContainerColor = VolleyColor.Turquoise,
                        timeSelectorSelectedContentColor = VolleyColor.TextDark,
                        timeSelectorUnselectedContentColor = VolleyColor.White,
                    ),
                )
            }
        )
    }

    /*@Composable
    fun MyTextField(modifier: Modifier = Modifier) {
        var text by remember { mutableStateOf("") }

        Box(
            modifier = modifier
                .size(width = 75.dp, height = 30.dp) // Сначала задаем размер
               // .then(modifier) // Потом применяем переданный modifier (который может содержать padding)
                .background(Color.White, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = text,
                onValueChange = { newValue ->
                    if (newValue.matches(Regex("[0-9.]*"))) {
                        text = newValue
                    }
                },
                textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                         //   .padding(horizontal = 8.dp)
                         //   .height(IntrinsicSize.Max),
                      ,  verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(Modifier.weight(1f)) {
                            if (text.isEmpty()) {
                                Text(
                                    text = "0.0",
                                    color = Color.LightGray,
                                    fontSize = 14.sp
                                )
                            }
                            innerTextField()
                        }
                        Text(text = "$", color = Color.Black, fontSize = 14.sp)
                    }
                },
                modifier = Modifier
                    .width(IntrinsicSize.Max),
                visualTransformation = CurrencyAmountTransformation
            )
        }
    }*/

    object CurrencyAmountTransformation : VisualTransformation {
        override fun filter(text: AnnotatedString): TransformedText {

            val digitsAndDots = text.text.filter { it.isDigit() || it == '.' }

            val parts = digitsAndDots.split('.')
            val integerPart = parts.getOrElse(0) { "" }
            val decimalPart = parts.getOrElse(1) { "" }

            val formattedIntegerPart = integerPart.reversed().chunked(3).joinToString(",")
                .reversed()

            val formattedText = if (decimalPart.isNotEmpty()) {
                "$formattedIntegerPart.$decimalPart"
            } else {
                formattedIntegerPart
            }

            return TransformedText(
                AnnotatedString(formattedText),
                object : OffsetMapping {
                    override fun originalToTransformed(offset: Int): Int {
                        if (offset <= digitsAndDots.length) {
                            return formattedText.length
                        } else {
                            return formattedText.length
                        }
                    }

                    override fun transformedToOriginal(offset: Int): Int {
                        if (offset <= formattedText.length) {
                            return digitsAndDots.length
                        } else {
                            return digitsAndDots.length
                        }
                    }
                })
        }
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
            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_44.dp))

            val currentDate = Calendar.getInstance().timeInMillis
            VolleyTextFieldAttribute.DatePickerField(
                inputDate = currentDate,
                modifier = Modifier
                    .padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            VolleyTextFieldAttribute.DatePickerField(
                inputDate = null,
                modifier = Modifier
                    .padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            VolleyTextFieldAttribute.CountField(
                paddingValues = PaddingValues(VolleyDimens.DIMEN_16.dp)
            ) { }

            VolleyTextFieldAttribute.DurationField(
                inputTime = null,
                modifier = Modifier
                    .padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            VolleyTextFieldAttribute.DurationFieldWithArrows(
                inputTime = null,
                modifier = Modifier
                    .padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            VolleyTextFieldAttribute.DurationField(
                inputTime = VolleyTimeStamp(
                    VolleyDimens.DIMEN_4,
                    VolleyDimens.DIMEN_20,
                    false
                ),
                modifier = Modifier
                    .padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            VolleyTextFieldAttribute.DurationFieldWithArrows(
                inputTime = VolleyTimeStamp(
                    14,
                    0,
                    true
                ),
                modifier = Modifier
                    .padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            /*VolleyTextFieldAttribute.PaymentField(
                inputPayment = 6.0,
                modifier = Modifier
                       .padding(VolleyDimens.DIMEN_16.dp)
            )

            VolleyTextFieldAttribute.MyTextField(modifier = Modifier.padding(16.dp))
           */
        }
    }
}
