package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.model.DataDestinationsResponse
import com.tourify.tourifyapp.model.DataMyTicketsResponse
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.changeDateTimeGMT
import com.tourify.tourifyapp.utils.cutLocation

@Composable
fun CardMyTickets(
    destination: DataDestinationsResponse? = null,
    item: DataMyTicketsResponse,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, Shapes.small, true, spotColor = TextPrimary)
            .background(ColorWhite)
            .clickable(
                onClick = {
                    onClick()
                }
            ),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .padding(6.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .align(Alignment.CenterVertically),
                        content = {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(destination!!.photo)
                                    .crossfade(true)
                                    .error(R.drawable.error_image)
                                    .build(),
                                contentDescription = destination.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize(),
                                alignment = Alignment.Center
                            )
                        }
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp, bottom = 6.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        content = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 1.dp, end = 6.dp),
                                content = {
                                    Column(
                                        content = {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                content = {
                                                    val destinationsName = if (destination!!.name.length > 13) {
                                                        "${destination.name.substring(0, 12)}..."
                                                    } else {
                                                        destination.name
                                                    }
                                                    Text(
                                                        modifier = Modifier
                                                            .fillMaxWidth(0.6f),
                                                        text = destinationsName,
                                                        style = StyleText.copy(
                                                            color = TextPrimary,
                                                            fontFamily = fonts,
                                                            fontWeight = FontWeight.Medium,
                                                            fontSize = 14.sp,
                                                            lineHeight = 14.sp
                                                        )
                                                    )
                                                    Box(
                                                        modifier = Modifier
                                                            .wrapContentWidth()
                                                            .wrapContentHeight()
                                                            .clip(Shapes.small)
                                                            .background(ColorSecondary),
                                                        content = {
                                                            val status =
                                                                if (item.checkInDate == null) {
                                                                    if (item.statusPayment == 0) {
                                                                        "Belum Bayar"
                                                                    } else {
                                                                        "Belum Check In"
                                                                    }
                                                                } else {
                                                                    "Sudah Check In"
                                                                }
                                                            Text(
                                                                modifier = Modifier
                                                                    .padding(
                                                                        start = 5.dp,
                                                                        end = 5.dp,
                                                                        top = 2.dp,
                                                                        bottom = 2.dp
                                                                    ),
                                                                text = status,
                                                                style = StyleText.copy(
                                                                    color = TextPrimary,
                                                                    fontFamily = fonts,
                                                                    fontWeight = FontWeight.Light,
                                                                    fontSize = 10.sp,
                                                                    lineHeight = 10.sp,
                                                                    textAlign = TextAlign.Center
                                                                )
                                                            )
                                                        }
                                                    )
                                                }
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                content = {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_location),
                                                        contentDescription = stringResource(id = R.string.choose_location),
                                                        modifier = Modifier
                                                            .size(11.dp),
                                                        tint = ColorDanger
                                                    )
                                                    Spacer(modifier = Modifier.width(1.dp))
                                                    val location =
                                                        cutLocation("${destination!!.regency}, ${destination.province}")
                                                    Text(
                                                        text = location,
                                                        style = StyleText.copy(
                                                            color = TextPrimary,
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
                                }
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 3.dp, end = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom,
                                content = {
                                    Box(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .wrapContentHeight()
                                            .clip(Shapes.small)
                                            .background(ColorSecondary),
                                        content = {
                                            Text(
                                                modifier = Modifier
                                                    .padding(
                                                        start = 4.dp,
                                                        end = 4.dp,
                                                        top = 1.dp,
                                                        bottom = 1.dp
                                                    ),
                                                text = destination!!.type,
                                                style = StyleText.copy(
                                                    color = TextPrimary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = 8.sp,
                                                    lineHeight = 8.sp,
                                                    textAlign = TextAlign.Center
                                                )
                                            )
                                        }
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.End,
                                        content = {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth(0.9f),
                                                horizontalAlignment = Alignment.End,
                                                content = {
                                                    val isWithTourGuide =
                                                        if (item.tourGuideData != null) {
                                                            "(x1) bersama pemandu wisata ${item.tourGuideData.name}"
                                                        } else {
                                                            "(x1) e-tiket masuk"
                                                        }
                                                    Text(
                                                        text = isWithTourGuide,
                                                        style = StyleText.copy(
                                                            color = TextSecondary,
                                                            fontFamily = fonts,
                                                            fontWeight = FontWeight.Light,
                                                            fontSize = 8.sp,
                                                            lineHeight = 10.sp,
                                                            textAlign = TextAlign.End
                                                        )
                                                    )
                                                    Spacer(modifier = Modifier.height(2.dp))
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        content = {
                                                            val tripDate =
                                                                changeDateTimeGMT(item.tripDate)
                                                            Text(
                                                                text = tripDate,
                                                                style = StyleText.copy(
                                                                    color = TextSecondary,
                                                                    fontFamily = fonts,
                                                                    fontWeight = FontWeight.Light,
                                                                    fontSize = 11.sp,
                                                                    lineHeight = 11.sp,
                                                                    textAlign = TextAlign.End
                                                                )
                                                            )
                                                            Spacer(modifier = Modifier.width(4.dp))
                                                            Icon(
                                                                painter = painterResource(id = R.drawable.ic_small_down),
                                                                contentDescription = "Selengkapnya",
                                                                modifier = Modifier
                                                                    .size(10.dp),
                                                                tint = TextSecondary
                                                            )
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}