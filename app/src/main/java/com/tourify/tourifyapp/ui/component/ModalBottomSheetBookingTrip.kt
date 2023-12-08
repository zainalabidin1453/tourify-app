package com.tourify.tourifyapp.ui.component

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorBlue
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ModalBottomSheetBookingTrip(
    context: Context,
    progress: Int,
    onProgress: (Int) -> Unit,
    onStartTripDateBooking: (String) -> Unit,
    onEndTripDateBooking: (String) -> Unit,
    onAllTripDateBooking: (String) -> Unit,
    onNameBooking: (String) -> Unit,
    onEmailBooking: (String) -> Unit,
    onTelpBooking: (String) -> Unit,
    onTourGuideIdBooking: (Int) -> Unit,
    isWithTourGuideBooking: (Boolean) -> Unit,
    onTotalTicketsBooking: (Int) -> Unit,
    onTotalPriceBooking: (Int) -> Unit,
) {
    var startTripDateBooking by rememberSaveable { mutableStateOf("") }
    var endTripDateBooking by rememberSaveable { mutableStateOf("") }
    var allTripDateBooking by rememberSaveable { mutableStateOf("") }
    var nameBooking by rememberSaveable { mutableStateOf("") }
    var emailBooking by rememberSaveable { mutableStateOf("") }
    var telpBooking by rememberSaveable { mutableStateOf("") }
    var tourGuideIdBooking by rememberSaveable { mutableIntStateOf(0) }
    var isWithTourGuideBooking by rememberSaveable { mutableStateOf(true) }
    var totalTicketsBooking by rememberSaveable { mutableIntStateOf(0) }
    var totalPriceBooking by rememberSaveable { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()

    AnimatedVisibility(
        visible = true,
        enter = slideInHorizontally(
            initialOffsetX = { fullSize -> fullSize },
            animationSpec = tween(500)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { fullSize -> -fullSize },
            animationSpec = tween(500)
        ),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ColorWhite)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures { _, dragAmount ->
                            onProgress(
                                when (progress) {
                                    1 -> {
                                        if (dragAmount < 0) {
                                            2
                                        } else {
                                            1
                                        }
                                    }
                                    2 -> {
                                        if (dragAmount < 0) {
                                            3
                                        } else {
                                            1
                                        }
                                    }
                                    3 -> {
                                        if (dragAmount < 0) {
                                            3
                                        } else {
                                            2
                                        }
                                    }
                                    else -> {1}
                                }
                            )
                        }
                    },
                content = {
                    when (progress) {
                        1 -> {
                           Column(
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .verticalScroll(scrollState)
                                   .padding(start = 18.dp, end = 18.dp),
                               content = {
                                   CardWisataTicketsBooking(
                                       photo = R.drawable.error_image,
                                       name = "Wisata Name",
                                       location = "Location, Indonesia",
                                       category = "Pantai",
                                       priceTickets = 10000,
                                       totalTickets = totalTicketsBooking,
                                       onPlusTickets = {
                                           totalTicketsBooking = it
                                           onTotalTicketsBooking(totalTicketsBooking)
                                       },
                                       onMinTickets = {
                                           if (totalTicketsBooking > 0) {
                                               totalTicketsBooking = it
                                               onTotalTicketsBooking(totalTicketsBooking)
                                           }
                                       },
                                   )
                                   Spacer(modifier = Modifier.height(12.dp))
                                   DaftarTourGuideBooking(
                                       context = context,
                                       onShowDetailTourGuide = {},
                                       onChooseTourGuide = {}
                                   )
                                   Spacer(modifier = Modifier.height(10.dp))
                                   Text(
                                       text = "*Pilih pemandu wisata lokal, jika Anda membutuhkannya. Anda dapat memilih untuk tidak menggunakan layanan ini.",
                                       maxLines = 4,
                                       style = StyleText.copy(
                                           color = TextSecondary,
                                           fontFamily = fonts,
                                           fontWeight = FontWeight.Light,
                                           fontSize = 10.sp,
                                           lineHeight = 15.sp,
                                       )
                                   )
                                   Spacer(modifier = Modifier.height(14.dp))
                                   Text(
                                       text = "Pilih Tanggal Perjalanan",
                                       style = StyleText.copy(
                                           color = TextPrimary,
                                           fontFamily = fonts,
                                           fontWeight = FontWeight.Medium,
                                           fontSize = 12.sp,
                                           lineHeight = 12.sp,
                                       )
                                   )
                                   Spacer(modifier = Modifier.height(12.dp))
                                   TextFieldDateBooking(
                                       placeholder = "Pilih tanggal perjalanan",
                                       icon = R.drawable.ic_calendar,
                                       iconDescription = R.drawable.ic_calendar,
                                       keyboardType = KeyboardType.Text,
                                       isError = false,
                                       fontSize = 12.sp,
                                       onStartDate = {
                                           startTripDateBooking = it
                                           onStartTripDateBooking(startTripDateBooking)
                                       },
                                       onEndDate = {
                                           endTripDateBooking = it
                                           onEndTripDateBooking(endTripDateBooking)
                                       },
                                       onAllDate = {
                                           allTripDateBooking = it
                                           onAllTripDateBooking(allTripDateBooking)
                                       }
                                   )
                                   Spacer(modifier = Modifier.height(10.dp))
                                   Text(
                                       text = "*Pastikan tanggal perjalanan Anda sudah sesuai. Anda tidak dapat mengatur ulang tanggal perjalanan setelah memesan.",
                                       maxLines = 4,
                                       style = StyleText.copy(
                                           color = TextSecondary,
                                           fontFamily = fonts,
                                           fontWeight = FontWeight.Light,
                                           fontSize = 10.sp,
                                           lineHeight = 15.sp,
                                       )
                                   )
                               }
                           )
                        }
                        2 -> {

                        }
                        3 -> {

                        }
                        else -> {

                        }
                    }
                }
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ModalBottomSheetBookingTripPreview() {
    val context = LocalContext.current
    ModalBottomSheetBookingTrip(
        context = context,
        progress = 0,
        onProgress = {},
        onStartTripDateBooking = {},
        onEndTripDateBooking = {},
        onNameBooking = {},
        onEmailBooking = {},
        onTelpBooking = {},
        onTourGuideIdBooking = {},
        isWithTourGuideBooking = {},
        onTotalTicketsBooking = {},
        onTotalPriceBooking = {},
        onAllTripDateBooking = {}
    )
}