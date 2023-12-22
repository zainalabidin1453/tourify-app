package com.tourify.tourifyapp.ui.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.model.DataDestinationsResponse
import com.tourify.tourifyapp.model.DataMyTicketsResponse
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorTransparent
import com.tourify.tourifyapp.ui.theme.ColorWarning
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.changeDateToSimply
import com.tourify.tourifyapp.utils.changeDateToSlash
import com.tourify.tourifyapp.utils.checkStatusTrip
import com.tourify.tourifyapp.utils.modifyMoneyFormat

@Composable
fun ModalBottomSheetDetailTickets(
    context: Context,
    dataDestinations: DataDestinationsResponse? = null,
    dataMyTickets: DataMyTicketsResponse? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorTransparent),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp)
                    .align(Alignment.TopCenter)
                    .shadow(
                        4.dp,
                        RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                        true,
                        spotColor = TextPrimary
                    )
                    .background(ColorWhite),
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 18.dp, end = 18.dp, top = 78.dp, bottom = 18.dp)
                            .align(Alignment.TopCenter),
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                content = {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        text = "Detail Perjalanan",
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 14.sp,
                                            lineHeight = 14.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(18.dp))
                                    val statusCheckIn =
                                        checkStatusTrip(dataMyTickets!!.tripDate)
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        content = {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .align(Alignment.TopCenter)
                                                    .padding(
                                                        top = 9.dp,
                                                        start = 18.dp,
                                                        end = 18.dp
                                                    ),
                                                horizontalArrangement = Arrangement.SpaceAround,
                                                verticalAlignment = Alignment.CenterVertically,
                                                content = {
                                                    repeat(2) {
                                                        Box(
                                                            modifier = Modifier
                                                                .fillMaxWidth(0.15f)
                                                                .height(1.dp)
                                                                .clip(RoundedCornerShape(100))
                                                                .background(
                                                                    if (statusCheckIn) {
                                                                        if (dataMyTickets.checkInDate == null && it == 1) {
                                                                            ColorSecondary
                                                                        } else {
                                                                            ColorPrimary
                                                                        }
                                                                    } else {
                                                                        ColorSecondary
                                                                    }
                                                                )
                                                        )
                                                    }
                                                }
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                content = {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .align(Alignment.BottomCenter),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        content = {
                                                            repeat(3) {
                                                                Column(
                                                                    verticalArrangement = Arrangement.SpaceBetween,
                                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                                    content = {
                                                                        Box(
                                                                            modifier = Modifier
                                                                                .size(18.dp)
                                                                                .clip(
                                                                                    RoundedCornerShape(
                                                                                        100
                                                                                    )
                                                                                )
                                                                                .background(
                                                                                    if (it > 0) {
                                                                                        if (statusCheckIn) {
                                                                                            if (dataMyTickets.checkInDate == null && it == 2) {
                                                                                                ColorSecondary
                                                                                            } else {
                                                                                                ColorPrimary
                                                                                            }
                                                                                        } else {
                                                                                            ColorSecondary
                                                                                        }
                                                                                    } else {
                                                                                        ColorPrimary
                                                                                    }
                                                                                ),
                                                                        )
                                                                        Spacer(
                                                                            modifier = Modifier.height(
                                                                                2.dp
                                                                            )
                                                                        )
                                                                        val nameStatus = when (it) {
                                                                            0 -> {
                                                                                "Booking"
                                                                            }

                                                                            1 -> {
                                                                                "Perjalanan"
                                                                            }

                                                                            else -> {
                                                                                "Check In"
                                                                            }
                                                                        }
                                                                        Text(
                                                                            text = nameStatus,
                                                                            style = StyleText.copy(
                                                                                color = TextSecondary,
                                                                                fontFamily = fonts,
                                                                                fontWeight = FontWeight.Light,
                                                                                fontSize = 10.sp,
                                                                                lineHeight = 10.sp,
                                                                                textAlign = TextAlign.Center
                                                                            )
                                                                        )
                                                                        Spacer(
                                                                            modifier = Modifier.height(
                                                                                2.dp
                                                                            )
                                                                        )
                                                                        val bookingDate =
                                                                            when (it) {
                                                                                0 -> {
                                                                                    changeDateToSimply(
                                                                                        dataMyTickets.bookingDate
                                                                                    )
                                                                                }

                                                                                1 -> {
                                                                                    changeDateToSimply(
                                                                                        dataMyTickets.tripDate
                                                                                    )
                                                                                }

                                                                                else -> {
                                                                                    changeDateToSimply(
                                                                                        dataMyTickets.tripDate
                                                                                    )
                                                                                }
                                                                            }
                                                                        Text(
                                                                            text = bookingDate,
                                                                            style = StyleText.copy(
                                                                                color = TextPrimary,
                                                                                fontFamily = fonts,
                                                                                fontWeight = FontWeight.Normal,
                                                                                fontSize = 12.sp,
                                                                                lineHeight = 12.sp,
                                                                                textAlign = TextAlign.Center
                                                                            )
                                                                        )
                                                                    }
                                                                )
                                                            }
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    )
                                    if (dataMyTickets!!.tourGuideData != null) {
                                        Spacer(modifier = Modifier.height(18.dp))
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            text = "Pemandu Wisata",
                                            style = StyleText.copy(
                                                color = TextPrimary,
                                                fontFamily = fonts,
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 14.sp,
                                                lineHeight = 14.sp,
                                                textAlign = TextAlign.Center
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
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
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    content = {
                                                        Column(
                                                            verticalArrangement = Arrangement.SpaceBetween,
                                                            content = {
                                                                Text(
                                                                    text = dataMyTickets.tourGuideData!!.name,
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
                                                                    text = dataMyTickets.tourGuideData.email,
                                                                    style = StyleText.copy(
                                                                        color = TextSecondary,
                                                                        fontFamily = fonts,
                                                                        fontWeight = FontWeight.Light,
                                                                        fontSize = 11.sp,
                                                                        lineHeight = 11.sp
                                                                    )
                                                                )
                                                                Spacer(modifier = Modifier.height(2.dp))
                                                                Row(
                                                                    verticalAlignment = Alignment.CenterVertically,
                                                                    content = {
                                                                        Text(
                                                                            text = "(+62) 8127570800",
                                                                            style = StyleText.copy(
                                                                                color = TextSecondary,
                                                                                fontFamily = fonts,
                                                                                fontWeight = FontWeight.Light,
                                                                                fontSize = 10.sp,
                                                                                lineHeight = 10.sp
                                                                            )
                                                                        )
                                                                        Spacer(
                                                                            modifier = Modifier.width(
                                                                                4.dp
                                                                            )
                                                                        )
                                                                        Image(
                                                                            painter = painterResource(
                                                                                id = R.drawable.ic_whatsapp
                                                                            ),
                                                                            contentDescription = "Hubungi Via Whatsapp",
                                                                            modifier = Modifier
                                                                                .size(10.5.dp),
                                                                            alignment = Alignment.Center,
                                                                            contentScale = ContentScale.Fit
                                                                        )
                                                                        Spacer(
                                                                            modifier = Modifier.width(
                                                                                4.dp
                                                                            )
                                                                        )
                                                                        Image(
                                                                            painter = painterResource(
                                                                                id = R.drawable.ic_facebook
                                                                            ),
                                                                            contentDescription = "Hubungi Via Facebook",
                                                                            modifier = Modifier
                                                                                .size(10.dp),
                                                                            alignment = Alignment.Center,
                                                                            contentScale = ContentScale.Fit
                                                                        )
                                                                    }
                                                                )
                                                            }
                                                        )
                                                        Spacer(modifier = Modifier.width(10.dp))
                                                        Column(
                                                            horizontalAlignment = Alignment.End,
                                                            content = {
                                                                val dateSlash =
                                                                    changeDateToSlash(dataMyTickets.tripDate)
                                                                Text(
                                                                    text = "To: ${dateSlash}",
                                                                    style = StyleText.copy(
                                                                        color = TextSecondary,
                                                                        fontFamily = fonts,
                                                                        fontWeight = FontWeight.Light,
                                                                        fontSize = 12.sp,
                                                                        lineHeight = 12.sp,
                                                                        textAlign = TextAlign.End
                                                                    )
                                                                )
                                                                Spacer(modifier = Modifier.height(2.dp))
                                                                Text(
                                                                    text = "1 Hari",
                                                                    style = StyleText.copy(
                                                                        color = TextSecondary,
                                                                        fontFamily = fonts,
                                                                        fontWeight = FontWeight.Light,
                                                                        fontSize = 12.sp,
                                                                        lineHeight = 12.sp,
                                                                        textAlign = TextAlign.End
                                                                    )
                                                                )
                                                            }
                                                        )
                                                    }
                                                )
                                            }
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(18.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        content = {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                content = {
                                                    val ticketPrice = dataDestinations!!.ticketPrice
                                                    val tourGuidePrice =
                                                        dataMyTickets.tourGuideData!!.servicePrice * 24
                                                    val servicePrice = 4000
                                                    val discount = 0
                                                    val totalPrice =
                                                        ticketPrice + tourGuidePrice + servicePrice - discount
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
                                                                text = "(x1) ${
                                                                    modifyMoneyFormat(
                                                                        ticketPrice
                                                                    )
                                                                }",
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
                                                                text = modifyMoneyFormat(
                                                                    tourGuidePrice
                                                                ),
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
                                                                text = "- ${
                                                                    modifyMoneyFormat(
                                                                        discount
                                                                    )
                                                                }",
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
                                                                text = modifyMoneyFormat(
                                                                    servicePrice
                                                                ),
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
                                                                text = modifyMoneyFormat(totalPrice),
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
                                            if (dataMyTickets.statusPayment == 1) {
                                                Box(
                                                    modifier = Modifier
                                                        .align(Alignment.Center),
                                                    contentAlignment = Alignment.Center,
                                                    content = {
                                                        Icon(
                                                            modifier = Modifier
                                                                .padding(start = 50.dp),
                                                            painter = painterResource(id = R.drawable.ic_paid),
                                                            contentDescription = "Lunas",
                                                            tint = ColorPrimary
                                                        )
                                                    }
                                                )
                                            }
                                        }
                                    )
                                    if (dataMyTickets.ordererNote.isNotEmpty()) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = 18.dp),
                                            content = {
                                                Text(
                                                    text = "Catatan :",
                                                    style = StyleText.copy(
                                                        color = TextPrimary,
                                                        fontFamily = fonts,
                                                        fontWeight = FontWeight.Normal,
                                                        fontSize = 12.sp,
                                                        lineHeight = 12.sp,
                                                    )
                                                )
                                                Spacer(modifier = Modifier.height(12.dp))
                                                Text(
                                                    text = dataMyTickets.ordererNote,
                                                    maxLines = 3,
                                                    style = StyleText.copy(
                                                        color = TextSecondary,
                                                        fontFamily = fonts,
                                                        fontWeight = FontWeight.Light,
                                                        fontSize = 12.sp,
                                                        lineHeight = 18.sp,
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(105.dp)
                    .align(Alignment.TopCenter)
                    .clip(RoundedCornerShape(12.dp))
                    .background(ColorTransparent),
                contentAlignment = Alignment.Center,
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .align(Alignment.TopCenter)
                            .shadow(4.dp, RoundedCornerShape(12.dp), true, spotColor = TextPrimary)
                            .background(ColorWhite),
                        contentAlignment = Alignment.Center,
                        content = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(ColorWhite),
                                content = {
                                    val code = "TRF12345"
                                    if (BarcodeType.PDF_417.isValueValid(code)) {
                                        Barcode(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .height(100.dp),
                                            resolutionFactor = 10,
                                            type = BarcodeType.PDF_417,
                                            value = code,
                                            showProgress = true,
                                        )
                                    }
                                },
                            )
                        }
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 2.dp),
                        text = "Scan Barcode di Pintu Masuk.",
                        style = StyleText.copy(
                            color = TextSecondary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            lineHeight = 12.sp
                        )
                    )
                }
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun ModalBottomSheetDetailTicketsPreview() {
    val context = LocalContext.current
    ModalBottomSheetDetailTickets(context)
}