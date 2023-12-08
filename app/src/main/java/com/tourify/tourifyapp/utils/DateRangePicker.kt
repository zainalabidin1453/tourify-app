package com.tourify.tourifyapp.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.constants.Constants.END_DATE
import com.tourify.tourifyapp.constants.Constants.START_DATE
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale

data class DateRange(
    val startMillis: Long,
    val endMillis: Long
)

@OptIn(ExperimentalMaterial3Api::class)
data class CustomSelectableDates(
    val minDate: Long,
    val maxDate: Long,
    val disabledDates: List<DateRange>
) : SelectableDates

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerScreen(
    onStartDate: (String) -> Unit,
    onEndDate: (String) -> Unit
) {
    var startTripDateBooking by rememberSaveable { mutableStateOf("") }
    var endTripDateBooking by rememberSaveable { mutableStateOf("") }
    val dateTime = LocalDateTime.now()
    val dateRangePickerState = remember {
        val minDate = dateTime.toMillis()
        val maxDate = dateTime.plusMonths(1).toMillis()

        val selectableDates = CustomSelectableDates(
            minDate = minDate,
            maxDate = maxDate,
            disabledDates = listOf()
        )

        DateRangePickerState(
            locale = Locale("id", "ID"),
            initialSelectedStartDateMillis = LocalDateTime.now().toMillis(),
            initialDisplayedMonthMillis = null,
            initialSelectedEndDateMillis = LocalDateTime.now().plusDays(1).toMillis(),
            initialDisplayMode = DisplayMode.Picker,
            yearRange = (dateTime.year)..(dateTime.year + 1),
            selectableDates = selectableDates
        )
    }

    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(timeInMillis: Long): String {
        val calender = Calendar.getInstance()
        calender.timeInMillis = timeInMillis
        val dateFormat = SimpleDateFormat("dd MMM yyyy")
        return dateFormat.format(calender.timeInMillis)
    }

    DateRangePicker(
        state = dateRangePickerState,
        title = null,
        headline = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.weight(1f)) {
                    (if (dateRangePickerState.selectedStartDateMillis != null)
                        dateRangePickerState.selectedStartDateMillis?.let {
                            getFormattedDate(it)
                        }
                    else START_DATE)?.let {
                        startTripDateBooking = it
                        onStartDate(startTripDateBooking)
                        Text(
                            text = startTripDateBooking,
                            style = StyleText.copy(
                                color = if (isValidEndDate(startTripDateBooking)) TextPrimary else TextSecondary,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                lineHeight = 14.sp
                            )
                        )
                    }
                }
                Box(Modifier.weight(1f)) {
                    (if (dateRangePickerState.selectedEndDateMillis != null)
                        dateRangePickerState.selectedEndDateMillis?.let {
                            getFormattedDate(it)
                        }
                    else END_DATE)?.let {
                        endTripDateBooking = it
                        onEndDate(endTripDateBooking)
                        Text(
                            text = endTripDateBooking,
                            style = StyleText.copy(
                                color = if (isValidEndDate(endTripDateBooking)) TextPrimary else TextSecondary,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                lineHeight = 14.sp
                            )
                        )
                    }
                }
            }
        },
        showModeToggle = true,
        colors = DatePickerDefaults.colors(
            containerColor = ColorWhite,
            titleContentColor = TextPrimary,
            headlineContentColor = TextPrimary,
            weekdayContentColor = TextPrimary,
            subheadContentColor = TextPrimary,
            yearContentColor = TextPrimary,
            currentYearContentColor = ColorPrimary,
            selectedYearContainerColor = ColorPrimary,
            disabledDayContentColor = ColorSecondary,
            todayDateBorderColor = ColorPrimary,
            dayInSelectionRangeContainerColor = ColorSecondary,
            dayInSelectionRangeContentColor = TextSecondary,
            selectedDayContainerColor = ColorPrimary,
            selectedDayContentColor = ColorWhite,
            dayContentColor = TextPrimary
        )
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
