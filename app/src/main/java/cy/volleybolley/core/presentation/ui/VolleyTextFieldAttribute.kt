package cy.volleybolley.core.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import cy.volleybolley.core.presentation.ui.VolleyContainer.Root
import cy.volleybolley.core.presentation.ui.model.VolleyColor
import cy.volleybolley.core.presentation.ui.model.VolleyDimens
import cy.volleybolley.core.presentation.ui.model.VolleyTypography.GradientFieldMedium
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        var showModal by remember { mutableStateOf(false) }
        var selectedDate by remember { mutableStateOf<Long?>(null) }

        val correctText = selectedDate?.let { dateInMillis ->
            convertMillisToDate(
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
                    style = GradientFieldMedium,
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

    private fun convertMillisToDate(stringPattern: String, millis: Long): String {
        val formatter = SimpleDateFormat(stringPattern, Locale.getDefault())
        return formatter.format(Date(millis))
    }

    @Stable
    @Composable
    fun CountActionField(
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

                CountField(text = inputCount.toString())

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
                    style = GradientFieldMedium,
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

            VolleyTextFieldAttribute.DatePickerField(
                modifier = Modifier.padding(VolleyDimens.DIMEN_16.dp)
            ) { }

            VolleyTextFieldAttribute.CountActionField(
                paddingValues = PaddingValues(VolleyDimens.DIMEN_16.dp)
            ) { }
        }
    }
}
