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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.modifyMoneyFormat

@Composable
fun CardWisataTicketsBooking(
    photo: String,
    name: String,
    location: String,
    category: String,
    priceTickets: Int,
    totalTickets: Int,
    onPlusTickets: (Int) -> Unit,
    onMinTickets: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, Shapes.small, true, spotColor = TextPrimary)
                    .background(ColorWhite),
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
                                            .data(photo)
                                            .crossfade(true)
                                            .error(R.drawable.error_image)
                                            .build(),
                                        contentDescription = name,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        alignment = Alignment.Center
                                    )
                                }
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.SpaceBetween,
                                content = {
                                    Row (
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 1.dp, end = 6.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        content = {
                                            Column(
                                                content = {
                                                    val destinationsName = if (name.length > 18) {
                                                        "${name.substring(0, 15)}..."
                                                    } else {
                                                        name
                                                    }
                                                    Text(
                                                        text = destinationsName,
                                                        style = StyleText.copy(
                                                            color = TextPrimary,
                                                            fontFamily = fonts,
                                                            fontWeight = FontWeight.Medium,
                                                            fontSize = 14.sp,
                                                            lineHeight = 14.sp
                                                        )
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
                                        verticalAlignment = Alignment.CenterVertically,
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
                                                            .padding(start = 4.dp, end = 4.dp, top = 1.dp, bottom = 1.dp),
                                                        text = category,
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
                                            val price = if (priceTickets > 0) modifyMoneyFormat(priceTickets) else "Rp0,-"
                                            Text(
                                                text = price,
                                                style = StyleText.copy(
                                                    color = TextPrimary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Normal,
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
            )
            if (priceTickets > 0) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Row(
                            modifier = Modifier
                                .wrapContentWidth(),
                            content = {
                                Box(
                                    modifier = Modifier
                                        .size(25.dp)
                                        .shadow(2.dp, RoundedCornerShape(100), spotColor = TextPrimary)
                                        .background(ColorWhite)
                                        .clickable(
                                            onClick = {
                                                if (totalTickets > 1) {
                                                    onMinTickets(totalTickets - 1)
                                                } else {
                                                    onMinTickets(totalTickets)
                                                }
                                            }
                                        ),
                                    contentAlignment = Alignment.Center,
                                    content = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_min),
                                            contentDescription = "Kurangkan",
                                            modifier = Modifier
                                                .size(16.dp)
                                                .padding(2.dp),
                                            tint = ColorSecondary
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .height(25.dp)
                                        .shadow(3.dp, RoundedCornerShape(100), spotColor = TextPrimary)
                                        .background(ColorWhite),
                                    contentAlignment = Alignment.Center,
                                    content = {
                                        Text(
                                            modifier = Modifier
                                                .padding(start = 18.dp, end = 18.dp),
                                            text = totalTickets.toString(),
                                            style = StyleText.copy(
                                                color = TextPrimary,
                                                fontFamily = fonts,
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 12.sp,
                                                lineHeight = 12.sp
                                            )
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .size(25.dp)
                                        .shadow(
                                            2.dp,
                                            RoundedCornerShape(100),
                                            true,
                                            spotColor = TextPrimary
                                        )
                                        .background(ColorWhite)
                                        .clickable(
                                            onClick = {
                                                onPlusTickets(totalTickets + 1)
                                            }
                                        ),
                                    contentAlignment = Alignment.Center,
                                    content = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_plus),
                                            contentDescription = "Tambahkan",
                                            modifier = Modifier
                                                .size(16.dp)
                                                .padding(2.dp),
                                            tint = ColorSecondary
                                        )
                                    }
                                )
                            }
                        )
                    }
                )
            } else {
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
                            text = "Tiket masuk di tempat wisata ini Rp0,- (Gratis), Anda tidak perlu membeli tiket masuk. Jika Anda membutuhkan pemandu wisata lokal, Anda dapat booking sekarang.",
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
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CardWisataTicketsBookingPreview() {
    CardWisataTicketsBooking(
        photo = "",
        name = "Wisata Name",
        location = "Location, Indonesia",
        category = "Pantai",
        priceTickets = 0,
        totalTickets = 5,
        onPlusTickets = {},
        onMinTickets = {})
}