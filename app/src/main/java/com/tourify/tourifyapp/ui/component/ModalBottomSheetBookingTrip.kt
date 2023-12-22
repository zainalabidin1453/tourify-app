package com.tourify.tourifyapp.ui.component

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.model.DataDestinationsResponse
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.calculateHours
import com.tourify.tourifyapp.utils.cutLocation
import com.tourify.tourifyapp.utils.modifyMoneyFormat

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ModalBottomSheetBookingTrip(
    context: Context,
    daftarDestinations: List<DataDestinationsResponse>,
    idDetailWisata: Int,
    progress: Int,
    onProgress: (Int) -> Unit,
    onStartTripDateBooking: (String) -> Unit,
    onEndTripDateBooking: (String) -> Unit,
    onNameBooking: (String) -> Unit,
    onEmailBooking: (String) -> Unit,
    onTelpBooking: (String) -> Unit,
    onTourGuideIdBooking: (Int) -> Unit,
    onWithNoTourGuide: (Boolean) -> Unit,
    onTotalTicketsBooking: (Int) -> Unit,
    onTotalPriceBooking: (Int) -> Unit,
    totalTicketsBooking: Int,
    isWithNoTourGuide: Boolean,
    startTripDateBooking: String,
    endTripDateBooking: String,
    allTripDateBooking: String,
    nameBooking: String,
    emailBooking: String,
    telpBooking: String,
    tourGuideIdBooking: Int,
    scrollState: ScrollState = rememberScrollState(),
) {
    val focusManager = LocalFocusManager.current
    AnimatedVisibility(
        visible = true,
        enter = slideInHorizontally(
            initialOffsetX = { fullSize -> fullSize },
            animationSpec = tween(700)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { fullSize -> -fullSize },
            animationSpec = tween(700)
        ),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ColorWhite)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                focusManager.clearFocus()
                            }
                        )
                        detectHorizontalDragGestures{ _, dragAmount ->
                            onProgress(
                                when (progress) {
                                    1 -> {
                                        if (dragAmount > 0) {
                                            1
                                        } else {
                                            2
                                        }
                                    }
                                    2 -> {
                                        if (dragAmount > 0) {
                                            1
                                        } else {
                                            3
                                        }
                                    }
                                    else -> {
                                        if (dragAmount > 0) {
                                            2
                                        } else {
                                            3
                                        }
                                    }
                                }
                            )
                        }
                    },
                content = {
                    when (progress) {
                        1 -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(scrollState)
                                    .padding(start = 18.dp, end = 18.dp, top = 2.dp),
                                content = {
                                    daftarDestinations.let { destinations ->
                                        val thisLocation =
                                            cutLocation("${destinations[0].regency}, ${destinations[0].province}")
                                        CardWisataTicketsBooking(
                                            photo = destinations[0].photo,
                                            name = destinations[0].name,
                                            location = thisLocation,
                                            category = destinations[0].type,
                                            priceTickets = destinations[0].ticketPrice,
                                            totalTickets = totalTicketsBooking,
                                            onPlusTickets = {
                                                onTotalTicketsBooking(it)
                                            },
                                            onMinTickets = {
                                                if (totalTicketsBooking > 0) {
                                                    onTotalTicketsBooking(it)
                                                }
                                            },
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                    DaftarTourGuideBooking(
                                        context = context,
                                        tourGuideIdBooking = tourGuideIdBooking,
                                        onChooseTourGuide = {
                                            onTourGuideIdBooking(it)
                                            focusManager.clearFocus()
                                        },
                                        isWithNoTourGuide = isWithNoTourGuide,
                                        onWithNoTourGuide = {
                                            onWithNoTourGuide(it)
                                            focusManager.clearFocus()
                                        },
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        content = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_circle),
                                                contentDescription = "Informasi",
                                                modifier = Modifier
                                                    .padding(top = 6.dp)
                                                    .size(4.dp),
                                                tint = ColorDanger
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "Pilih pemandu wisata lokal, jika Anda membutuhkannya. Anda dapat memilih untuk tidak menggunakan layanan ini.",
                                                maxLines = 4,
                                                style = StyleText.copy(
                                                    color = TextSecondary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = 10.sp,
                                                    lineHeight = 15.sp
                                                )
                                            )
                                        }
                                    )
                                }
                            )
                        }
                        2 -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .verticalScroll(scrollState)
                                    .padding(start = 18.dp, end = 18.dp, top = 2.dp),
                                content = {
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
                                        value = allTripDateBooking,
                                        icon = R.drawable.ic_calendar,
                                        iconDescription = R.drawable.ic_calendar,
                                        keyboardType = KeyboardType.Text,
                                        isError = false,
                                        fontSize = 12.sp,
                                        onStartDate = {
                                            onStartTripDateBooking(it)
                                        },
                                        onEndDate = {
                                            onEndTripDateBooking(it)
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        content = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_circle),
                                                contentDescription = "Informasi",
                                                modifier = Modifier
                                                    .padding(top = 6.dp)
                                                    .size(4.dp),
                                                tint = ColorDanger
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "Pastikan tanggal perjalanan Anda sudah sesuai. Anda tidak dapat mengatur ulang tanggal perjalanan setelah memesan.",
                                                maxLines = 4,
                                                style = StyleText.copy(
                                                    color = TextSecondary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = 10.sp,
                                                    lineHeight = 15.sp
                                                )
                                            )
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(14.dp))
                                    Text(
                                        text = "Detail Pemesan",
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 12.sp,
                                            lineHeight = 12.sp,
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    TextField(
                                        placeholder = "Nama Lengkap",
                                        icon = R.drawable.ic_user,
                                        iconDescription = R.string.full_name,
                                        keyboardType = KeyboardType.Text,
                                        onTextChanged = {
                                            onNameBooking(it)
                                        },
                                        isError = false,
                                        height = 50.dp,
                                        fontSize = 12.sp,
                                        value = nameBooking
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    TextField(
                                        placeholder = "Alamat Email",
                                        icon = R.drawable.ic_mail,
                                        iconDescription = R.string.email_address,
                                        keyboardType = KeyboardType.Email,
                                        onTextChanged = {
                                            onEmailBooking(it)
                                        },
                                        isError = false,
                                        height = 50.dp,
                                        fontSize = 12.sp,
                                        value = emailBooking
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    TextField(
                                        placeholder = "No Telepon",
                                        icon = R.drawable.ic_phone,
                                        iconDescription = R.string.phone_number,
                                        keyboardType = KeyboardType.Number,
                                        onTextChanged = {
                                            onTelpBooking(it)
                                        },
                                        isError = false,
                                        height = 50.dp,
                                        fontSize = 12.sp,
                                        value = telpBooking
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        content = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_circle),
                                                contentDescription = "Informasi",
                                                modifier = Modifier
                                                    .padding(top = 6.dp)
                                                    .size(4.dp),
                                                tint = ColorDanger
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "Informasi pesanan, tiket, dan konfirmasi akan dikirim ke detail kontak tertera.",
                                                maxLines = 4,
                                                style = StyleText.copy(
                                                    color = TextSecondary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = 10.sp,
                                                    lineHeight = 15.sp
                                                )
                                            )
                                        }
                                    )
                                }
                            )
                        }
                        3 -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(scrollState)
                                    .padding(start = 18.dp, end = 18.dp, top = 2.dp),
                                content = {
                                    Text(
                                        text = "Rincian Perjalanan",
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 12.sp,
                                            lineHeight = 12.sp,
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .shadow(3.dp, Shapes.small, true, spotColor = TextPrimary)
                                            .clip(Shapes.small)
                                            .background(ColorWhite),
                                        content = {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(14.dp),
                                                verticalArrangement = Arrangement.SpaceBetween,
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                content = {
                                                    if(!isWithNoTourGuide) {
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            content = {
                                                                CircleAssyncImgSmall(
                                                                    context = context,
                                                                    title = R.string.photo_profile,
                                                                    img = R.drawable.avatar.toString(),
                                                                    errorImg = R.drawable.avatar,
                                                                    size = 45.dp,
                                                                    onClick = {}
                                                                )
                                                                Spacer(modifier = Modifier.width(10.dp))
                                                                Column(
                                                                    verticalArrangement = Arrangement.SpaceBetween,
                                                                    content = {
                                                                        Text(
                                                                            text = "TourGuide Name",
                                                                            style = StyleText.copy(
                                                                                color = TextPrimary,
                                                                                fontFamily = fonts,
                                                                                fontWeight = FontWeight.Normal,
                                                                                fontSize = 12.sp,
                                                                                lineHeight = 12.sp
                                                                            )
                                                                        )
                                                                        Spacer(modifier = Modifier.height(1.dp))
                                                                        Text(
                                                                            text = "emailanda@example.com",
                                                                            style = StyleText.copy(
                                                                                color = TextSecondary,
                                                                                fontFamily = fonts,
                                                                                fontWeight = FontWeight.Light,
                                                                                fontSize = 11.sp,
                                                                                lineHeight = 11.sp
                                                                            )
                                                                        )
                                                                        Spacer(modifier = Modifier.height(2.dp))
                                                                        Text(
                                                                            text = "${modifyMoneyFormat(9000)}/jam",
                                                                            style = StyleText.copy(
                                                                                color = TextSecondary,
                                                                                fontFamily = fonts,
                                                                                fontWeight = FontWeight.Light,
                                                                                fontSize = 10.sp,
                                                                                lineHeight = 10.sp
                                                                            )
                                                                        )
                                                                    }
                                                                )
                                                            }
                                                        )
                                                        Spacer(modifier = Modifier.height(16.dp))
                                                    }
                                                    TrackTrip(
                                                        modifier = Modifier
                                                            .fillMaxWidth(),
                                                        tripDate = allTripDateBooking,
                                                        startTripDate = startTripDateBooking,
                                                        endTripDate = endTripDateBooking
                                                    )
                                                    Spacer(modifier = Modifier.height(16.dp))
                                                    if(daftarDestinations[0].ticketPrice > 0) {
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth(),
                                                            horizontalArrangement = Arrangement.SpaceBetween,
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            content = {
                                                                Text(
                                                                    text = "Harga Tiket",
                                                                    style = StyleText.copy(
                                                                        color = TextSecondary,
                                                                        fontFamily = fonts,
                                                                        fontWeight = FontWeight.Light,
                                                                        fontSize = 12.sp,
                                                                        lineHeight = 12.sp,
                                                                    )
                                                                )
                                                                Text(
                                                                    text = "(x$totalTicketsBooking) ${modifyMoneyFormat(daftarDestinations[0].ticketPrice)}",
                                                                    style = StyleText.copy(
                                                                        color = TextSecondary,
                                                                        fontFamily = fonts,
                                                                        fontWeight = FontWeight.Light,
                                                                        fontSize = 12.sp,
                                                                        lineHeight = 12.sp,
                                                                    )
                                                                )
                                                            }
                                                        )
                                                        Spacer(modifier = Modifier.height(6.dp))
                                                    }
                                                    val totalTicketsBooking = totalTicketsBooking
                                                    val totalTicketPrice = daftarDestinations[0].ticketPrice * totalTicketsBooking
                                                    val totalHours = calculateHours(startTripDateBooking, endTripDateBooking)
                                                    val totalPriceServiceTourGuide = totalHours * 9000
                                                    val totalDiscount = 0
                                                    val totalServiceApp = 4000
                                                    val totalAll = if (!isWithNoTourGuide) {
                                                        if (daftarDestinations[0].ticketPrice > 0) {
                                                            (totalTicketPrice + totalPriceServiceTourGuide + totalServiceApp) - totalDiscount
                                                        } else {
                                                            (totalPriceServiceTourGuide + totalServiceApp) - totalDiscount
                                                        }
                                                    } else {
                                                        if (daftarDestinations[0].ticketPrice > 0) {
                                                            (totalTicketPrice + totalServiceApp) - totalDiscount
                                                        } else {
                                                            (totalServiceApp) - totalDiscount
                                                        }
                                                    }
                                                    onTotalPriceBooking(totalAll)
                                                    if(!isWithNoTourGuide) {
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth(),
                                                            horizontalArrangement = Arrangement.SpaceBetween,
                                                            verticalAlignment = Alignment.CenterVertically,
                                                            content = {
                                                                Text(
                                                                    text = "Jasa Pemandu Wisata",
                                                                    style = StyleText.copy(
                                                                        color = TextSecondary,
                                                                        fontFamily = fonts,
                                                                        fontWeight = FontWeight.Light,
                                                                        fontSize = 12.sp,
                                                                        lineHeight = 12.sp,
                                                                    )
                                                                )
                                                                Text(
                                                                    text = modifyMoneyFormat(totalPriceServiceTourGuide),
                                                                    style = StyleText.copy(
                                                                        color = TextSecondary,
                                                                        fontFamily = fonts,
                                                                        fontWeight = FontWeight.Light,
                                                                        fontSize = 12.sp,
                                                                        lineHeight = 12.sp,
                                                                    )
                                                                )
                                                            }
                                                        )
                                                        Spacer(modifier = Modifier.height(6.dp))
                                                    }
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        content = {
                                                            Text(
                                                                text = "Diskon/Voucher (%)",
                                                                style = StyleText.copy(
                                                                    color = TextSecondary,
                                                                    fontFamily = fonts,
                                                                    fontWeight = FontWeight.Light,
                                                                    fontSize = 12.sp,
                                                                    lineHeight = 12.sp,
                                                                )
                                                            )
                                                            Text(
                                                                text = modifyMoneyFormat(totalDiscount),
                                                                style = StyleText.copy(
                                                                    color = TextSecondary,
                                                                    fontFamily = fonts,
                                                                    fontWeight = FontWeight.Light,
                                                                    fontSize = 12.sp,
                                                                    lineHeight = 12.sp,
                                                                )
                                                            )
                                                        }
                                                    )
                                                    Spacer(modifier = Modifier.height(6.dp))
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        content = {
                                                            Text(
                                                                text = "Layanan Aplikasi",
                                                                style = StyleText.copy(
                                                                    color = TextSecondary,
                                                                    fontFamily = fonts,
                                                                    fontWeight = FontWeight.Light,
                                                                    fontSize = 12.sp,
                                                                    lineHeight = 12.sp,
                                                                )
                                                            )
                                                            Text(
                                                                text = modifyMoneyFormat(totalServiceApp),
                                                                style = StyleText.copy(
                                                                    color = TextSecondary,
                                                                    fontFamily = fonts,
                                                                    fontWeight = FontWeight.Light,
                                                                    fontSize = 12.sp,
                                                                    lineHeight = 12.sp,
                                                                )
                                                            )
                                                        }
                                                    )
                                                    Spacer(modifier = Modifier.height(6.dp))
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        content = {
                                                            Text(
                                                                text = "Total",
                                                                style = StyleText.copy(
                                                                    color = ColorDanger,
                                                                    fontFamily = fonts,
                                                                    fontWeight = FontWeight.Normal,
                                                                    fontSize = 12.sp,
                                                                    lineHeight = 12.sp,
                                                                )
                                                            )
                                                            Text(
                                                                text = modifyMoneyFormat(totalAll),
                                                                style = StyleText.copy(
                                                                    color = ColorDanger,
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
                                    Spacer(modifier = Modifier.height(14.dp))
                                    Text(
                                        text = "Catatan",
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 12.sp,
                                            lineHeight = 12.sp,
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    TextField(
                                        placeholder = "Tambah Catatan",
                                        icon = R.drawable.ic_message_square,
                                        iconDescription = R.string.add_notes,
                                        keyboardType = KeyboardType.Text,
                                        onTextChanged = {

                                        },
                                        isError = false,
                                        height = 50.dp,
                                        fontSize = 12.sp,
                                        value = ""
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        content = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_circle),
                                                contentDescription = "Informasi",
                                                modifier = Modifier
                                                    .padding(top = 6.dp)
                                                    .size(4.dp),
                                                tint = ColorDanger
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "Tambahkan catatan untuk pemandu wisata Anda. Misalnya, kebutuhan khusus, dll.",
                                                maxLines = 4,
                                                style = StyleText.copy(
                                                    color = TextSecondary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = 10.sp,
                                                    lineHeight = 15.sp
                                                )
                                            )
                                        }
                                    )
                                }
                            )
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
        daftarDestinations = listOf(),
        idDetailWisata = 1,
        progress = 3,
        onProgress = {},
        onStartTripDateBooking = {},
        onEndTripDateBooking = {},
        onNameBooking = {},
        onEmailBooking = {},
        onTelpBooking = {},
        onTourGuideIdBooking = {},
        onWithNoTourGuide = {},
        onTotalTicketsBooking = {},
        onTotalPriceBooking = {},
        totalTicketsBooking = 0,
        isWithNoTourGuide = false,
        startTripDateBooking = "",
        endTripDateBooking = "",
        allTripDateBooking = "",
        nameBooking = "",
        emailBooking = "",
        telpBooking = "",
        tourGuideIdBooking = 0
    )
}