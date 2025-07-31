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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cy.volleybolley.R
import cy.volleybolley.core.presentation.ui.VolleyContainersRootTransparent.Root
import cy.volleybolley.core.presentation.ui.model.TimePickerStamp
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyText
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.GradientFieldMedium
import cy.volleybolley.core.presentation.ui.model.VolleyUiUtil
import java.util.Calendar

object VolleyTextFieldAttribute {
    @Composable
    fun DatePickerField(
        modifier: Modifier = Modifier,
        cornerRadius: Int = VolleyDimens.DIMEN_16,
        height: Int = VolleyDimens.DIMEN_52,
        inputTextValue: String = stringResource(R.string.registration_date_of_birth_hint),
        actionForSaveDate: (Long?) -> Unit,
    ) {
        val shape = RoundedCornerShape(cornerRadius.dp)
        var showDateDialog by remember { mutableStateOf(false) }
        var selectedDate by remember { mutableStateOf<Long?>(null) }

        val correctText = selectedDate?.let { dateInMillis ->
            VolleyUiUtil.convertMillisToTextDate(
                stringResource(R.string.registration_date_of_birth_string_pattern),
                dateInMillis
            )
        } ?: inputTextValue

        val gradientBrush = Brush.verticalGradient(
            colors = listOf(VolleyColor.YELLOW_GRADIENT, VolleyColor.GREEN_GRADIENT)
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
                    color = VolleyColor.TEXT_FIELD,
                    style = GradientFieldMedium,
                )
            }
        }

        if (showDateDialog) {
            DatePickerDialog(
                onDateSelected = { date ->
                    selectedDate = date
                },
                onDismiss = { showDateDialog = false },
                actionForSaveDate = actionForSaveDate,
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun DatePickerDialog(
        onDateSelected: (Long?) -> Unit,
        onDismiss: () -> Unit,
        actionForSaveDate: (Long?) -> Unit
    ) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            shape = RoundedCornerShape(VolleyDimens.DIMEN_24.dp),
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    actionForSaveDate(datePickerState.selectedDateMillis)
                    onDismiss()
                }) {
                    Text(
                        text = stringResource(R.string.registration_date_of_birth_ok),
                        style = GradientFieldMedium,
                        color = VolleyColor.SEAWAVE_BACKGROUND
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(
                        text = stringResource(R.string.registration_date_of_birth_cancel),
                        style = GradientFieldMedium,
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
                        tint = VolleyColor.WHITE,
                        modifier = Modifier
                            .clickable(null, null) {
                                val newCount = inputCount - 1
                                actionToTransferCount(newCount)
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
                        tint = VolleyColor.WHITE,
                        modifier = Modifier
                            .clickable(null, null) {
                                val newCount = inputCount + 1
                                actionToTransferCount(newCount)
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
                    style = GradientFieldMedium,
                )
            }
        }
    }

    @Composable
    fun DurationField(
        modifier: Modifier = Modifier,
        cornerRadius: Int = VolleyDimens.DIMEN_16,
        inputTextValue: String = stringResource(R.string.duration_time_hint),
        actionForSaveTime: (TimePickerStamp?) -> Unit,
    ) {
        var showTimePicker by remember { mutableStateOf(false) }
        var timeStamp: TimePickerStamp? by remember { mutableStateOf(null) }
        var correctTimeString = timeStamp?.getCorrectTimeString() ?: inputTextValue
        var correctAfternoonMark: String = timeStamp?.getAfternoonMark() ?: stringResource(R.string.pm)

        VolleyContainersRootTransparent.TransparentContainer(
            cornerRadius = cornerRadius,
            modifier = modifier
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable(
                        interactionSource = null,
                        indication = null,
                    ) {
                        showTimePicker = true
                    }
            ) {
                VolleyText.BodyRegular(
                    text = correctTimeString,
                    color = VolleyColor.WHITE,
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
                    color = VolleyColor.WHITE,
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
                onConfirm = { stampOfTime -> timeStamp = stampOfTime },
                onDismiss = { showTimePicker = false },
                actionForSaveTime = actionForSaveTime,
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TimePickerDialog(
        onConfirm: (TimePickerStamp) -> Unit,
        onDismiss: () -> Unit,
        actionForSaveTime: (TimePickerStamp?) -> Unit,
    ) {
        val currentTime = Calendar.getInstance()
        val timePickerState = rememberTimePickerState(
            initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
            initialMinute = currentTime.get(Calendar.MINUTE),
            is24Hour = false,
        )

        AlertDialog(
            containerColor = VolleyColor.WHITE,
            onDismissRequest = onDismiss,
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    VolleyText.BodyBold(
                        text = stringResource(R.string.registration_date_of_birth_cancel),
                        color = VolleyColor.SEAWAVE_HEADER,
                        maxLines = 1
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val stampOfTime = TimePickerStamp(
                            hourValue = timePickerState.hour,
                            minutesValue = timePickerState.minute,
                            isAfternoonValue = timePickerState.isAfternoon
                        )
                        actionForSaveTime(stampOfTime)
                        onConfirm(stampOfTime)
                        onDismiss()
                    }
                ) {
                    VolleyText.BodyBold(
                        text = stringResource(R.string.ok),
                        color = VolleyColor.SEAWAVE_HEADER,
                        maxLines = 1
                    )
                }
            },
            text = {
                TimePicker(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        clockDialColor = VolleyColor.SEAWAVE_HEADER,
                        clockDialSelectedContentColor = VolleyColor.TEXT_DARK,
                        clockDialUnselectedContentColor = VolleyColor.WHITE,
                        selectorColor = VolleyColor.GREEN_GRADIENT,
                        containerColor = VolleyColor.GREEN_GRADIENT,
                        periodSelectorBorderColor = VolleyColor.WHITE,
                        periodSelectorSelectedContainerColor = VolleyColor.GREEN_GRADIENT,
                        periodSelectorUnselectedContainerColor = VolleyColor.SEAWAVE_HEADER,
                        periodSelectorSelectedContentColor = VolleyColor.TEXT_DARK,
                        periodSelectorUnselectedContentColor = VolleyColor.WHITE,
                        timeSelectorSelectedContainerColor = VolleyColor.GREEN_GRADIENT,
                        timeSelectorUnselectedContainerColor = VolleyColor.SEAWAVE_HEADER,
                        timeSelectorSelectedContentColor = VolleyColor.TEXT_DARK,
                        timeSelectorUnselectedContentColor = VolleyColor.WHITE,
                    ),
                )
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewGradientTextFields() {
    Root {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(VolleyColor.BLACK)) {
            Spacer(modifier = Modifier.height(VolleyDimens.DIMEN_44.dp))

            VolleyTextFieldAttribute.DatePickerField(
                modifier = Modifier
                    .padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            VolleyTextFieldAttribute.CountField(
                paddingValues = PaddingValues(VolleyDimens.DIMEN_16.dp)
            ) { }

            VolleyTextFieldAttribute.DurationField(
                modifier = Modifier
                    .padding(VolleyDimens.DIMEN_16.dp)
            ) {  }
        }
    }
}
