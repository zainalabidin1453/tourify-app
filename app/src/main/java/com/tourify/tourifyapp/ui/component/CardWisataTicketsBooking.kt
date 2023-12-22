package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWarning
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.modifyMoneyFormat
import com.tourify.tourifyapp.utils.modifyNumberFormat

@Composable
fun CardWisataTicketsBooking(
    photo: Int,
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
                                    Image(
                                        painter = painterResource(id = R.drawable.error_image),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .align(Alignment.Center),
                                        contentScale = ContentScale.Crop,
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
                                                    Text(
                                                        text = "Wisata Name",
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
                                                                text = "Location, Indonesia",
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
                                                        text = "Pantai",
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
                                            Text(
                                                text = modifyMoneyFormat(10000),
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
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CardWisataTicketsBookingPreview() {
    CardWisataTicketsBooking(
        photo = R.drawable.error_image,
        name = "Wisata Name",
        location = "Location, Indonesia",
        category = "Pantai",
        priceTickets = 10000,
        totalTickets = 5,
        onPlusTickets = {},
        onMinTickets = {})
}