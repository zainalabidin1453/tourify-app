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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
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
                                    CardWisataTicketsBooking(
                                        photo = R.drawable.error_image,
                                        name = stringResource(id = R.string.tourism),
                                        location = stringResource(id = R.string.location),
                                        category = stringResource(id = R.string.types_tourism),
                                        priceTickets = 10000,
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
                                    Spacer(modifier = Modifier.height(12.dp))
                                    DaftarTourGuideBooking(
                                        context = context,
                                        tourGuideIdBooking = tourGuideIdBooking,
                                        onChooseTourGuide = {
                                            onTourGuideIdBooking(it)
                                        },
                                        isWithNoTourGuide = isWithNoTourGuide,
                                        onWithNoTourGuide = {
                                            onWithNoTourGuide(it)
                                        },
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        content = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_circle),
                                                contentDescription = stringResource(id = R.string.information),
                                                modifier = Modifier
                                                    .padding(top = 6.dp)
                                                    .size(4.dp),
                                                tint = ColorDanger
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = stringResource (id = R.string.guide_info),
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
                                        text = stringResource (id = R.string.choose_date),
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
                                        placeholder = stringResource (id = R.string.choose_date),
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
                                                contentDescription = stringResource (id = R.string.information),
                                                modifier = Modifier
                                                    .padding(top = 6.dp)
                                                    .size(4.dp),
                                                tint = ColorDanger
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = stringResource (id = R.string.info_date),
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
                                        text = stringResource (id = R.string.users_details),
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
                                        placeholder = stringResource (id = R.string.full_name),
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
                                        placeholder = stringResource (id = R.string.email_address),
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
                                        placeholder = stringResource (id = R.string.phone_number),
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
                                                contentDescription = stringResource (id = R.string.information),
                                                modifier = Modifier
                                                    .padding(top = 6.dp)
                                                    .size(4.dp),
                                                tint = ColorDanger
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = stringResource(id = R.string.order_info),
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
                                        text = stringResource(id = R.string.order_details),
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
                                                                        text = stringResource(id = R.string.guide_name),
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
                                                                        text = stringResource(id = R.string.guide_email),
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
                                                                        text = stringResource(id = R.string.guide_price),
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
                                                    TrackTrip(
                                                        modifier = Modifier
                                                            .fillMaxWidth(),
                                                        tripDate = allTripDateBooking
                                                    )
                                                    Spacer(modifier = Modifier.height(16.dp))
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth(),
                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        content = {
                                                            Text(
                                                                text = stringResource(id = R.string.ticket_price),
                                                                style = StyleText.copy(
                                                                    color = TextSecondary,
                                                                    fontFamily = fonts,
                                                                    fontWeight = FontWeight.Light,
                                                                    fontSize = 12.sp,
                                                                    lineHeight = 12.sp,
                                                                )
                                                            )
                                                            Text(
                                                                text = stringResource(id = R.string.ticket_price_total),
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
                                                                text = stringResource(id = R.string.guide_services),
                                                                style = StyleText.copy(
                                                                    color = TextSecondary,
                                                                    fontFamily = fonts,
                                                                    fontWeight = FontWeight.Light,
                                                                    fontSize = 12.sp,
                                                                    lineHeight = 12.sp,
                                                                )
                                                            )
                                                            Text(
                                                                text = stringResource(id = R.string.guide_price_total),
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
                                                                text = stringResource(id = R.string.discount_order),
                                                                style = StyleText.copy(
                                                                    color = TextSecondary,
                                                                    fontFamily = fonts,
                                                                    fontWeight = FontWeight.Light,
                                                                    fontSize = 12.sp,
                                                                    lineHeight = 12.sp,
                                                                )
                                                            )
                                                            Text(
                                                                text = stringResource(id = R.string.discount_order_total),
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
                                                                text = stringResource(id = R.string.app_service_fee),
                                                                style = StyleText.copy(
                                                                    color = TextSecondary,
                                                                    fontFamily = fonts,
                                                                    fontWeight = FontWeight.Light,
                                                                    fontSize = 12.sp,
                                                                    lineHeight = 12.sp,
                                                                )
                                                            )
                                                            Text(
                                                                text = stringResource(id = R.string.app_service_fee_total),
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
                                                                text = stringResource(id = R.string.total),
                                                                style = StyleText.copy(
                                                                    color = ColorDanger,
                                                                    fontFamily = fonts,
                                                                    fontWeight = FontWeight.Normal,
                                                                    fontSize = 12.sp,
                                                                    lineHeight = 12.sp,
                                                                )
                                                            )
                                                            Text(
                                                                text = stringResource(id = R.string.total_order),
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
                                        text = stringResource(id = R.string.notes),
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
                                        placeholder = stringResource(id = R.string.add_notes),
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
                                                contentDescription = stringResource(id = R.string.information),
                                                modifier = Modifier
                                                    .padding(top = 6.dp)
                                                    .size(4.dp),
                                                tint = ColorDanger
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = stringResource(id = R.string.notes_for_guide),
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
