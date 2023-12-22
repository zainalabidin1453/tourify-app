package com.tourify.tourifyapp.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWarning
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.calculateDay
import com.tourify.tourifyapp.utils.calculateHours
import com.tourify.tourifyapp.utils.getCheckInDate
import com.tourify.tourifyapp.utils.modifyDateFormat
import com.tourify.tourifyapp.utils.trackTripWithDate
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrackTrip(
    modifier: Modifier = Modifier,
    bookingDate: String = "",
    startTripDate: String = "",
    endTripDate: String = "",
    tripDate: String,
    isFirstBooking: Boolean = true,
    isCheckIn: Boolean = false
) {
    val statusTrack = if (!isFirstBooking) {
        trackTripWithDate(tripDate)
    } else {
        1
    }
    val totalDays = calculateDay(startTripDate, endTripDate)
    val dateNow = LocalDate.now()
    val modifiedDate = modifyDateFormat(dateNow)

    Box(
        modifier = modifier,
        content = {
            Box(
                modifier = Modifier
                    .padding(start = 8.5.dp)
                    .height(70.dp)
                    .width(1.dp)
                    .align(Alignment.CenterStart)
                    .clip(RoundedCornerShape(100))
                    .background(ColorSecondary)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        content = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Box(
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clip(RoundedCornerShape(100))
                                            .background(ColorPrimary),
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        content = {
                                            Text(
                                                text = "Booking Tiket Masuk",
                                                style = StyleText.copy(
                                                    color = TextSecondary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = 10.sp,
                                                    lineHeight = 10.sp,
                                                )
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = modifiedDate,
                                                style = StyleText.copy(
                                                    color = TextPrimary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Normal,
                                                    fontSize = 12.sp,
                                                    lineHeight = 12.sp,
                                                )
                                            )
                                        }
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Box(
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clip(RoundedCornerShape(100))
                                            .background(
                                                if(!isFirstBooking) {
                                                    if (statusTrack >= 2) {
                                                        ColorPrimary
                                                    } else {
                                                        ColorSecondary
                                                    }
                                                } else {
                                                    ColorSecondary
                                                }
                                            ),
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        content = {
                                            Text(
                                                text = "Perjalanan",
                                                style = StyleText.copy(
                                                    color = TextSecondary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = 10.sp,
                                                    lineHeight = 10.sp,
                                                )
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = tripDate,
                                                style = StyleText.copy(
                                                    color = TextPrimary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Normal,
                                                    fontSize = 12.sp,
                                                    lineHeight = 12.sp,
                                                )
                                            )
                                        }
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Box(
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clip(RoundedCornerShape(100))
                                            .background(
                                                if(!isFirstBooking) {
                                                    if (statusTrack >= 2 && isCheckIn) {
                                                        ColorPrimary
                                                    } else {
                                                        ColorSecondary
                                                    }
                                                } else {
                                                    ColorSecondary
                                                }
                                            ),
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        content = {
                                            Text(
                                                text = "Check In",
                                                style = StyleText.copy(
                                                    color = TextSecondary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = 10.sp,
                                                    lineHeight = 10.sp,
                                                )
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(
                                                text = getCheckInDate(tripDate),
                                                style = StyleText.copy(
                                                    color = TextPrimary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Normal,
                                                    fontSize = 12.sp,
                                                    lineHeight = 12.sp,
                                                )
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )
                    Column(
                        horizontalAlignment = Alignment.End,
                        content = {
                            Text(
                                text = "To: $tripDate",
                                style = StyleText.copy(
                                    color = TextPrimary,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Light,
                                    fontSize = 11.sp,
                                    lineHeight = 11.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "$totalDays Hari",
                                style = StyleText.copy(
                                    color = TextPrimary,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Light,
                                    fontSize = 11.sp,
                                    lineHeight = 11.sp
                                )
                            )
                        }
                    )
                }
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TrackTripPreview(){
    TrackTrip(
        tripDate = "12 Nov - 15 Des 2023",
        isFirstBooking = false
    )
}
