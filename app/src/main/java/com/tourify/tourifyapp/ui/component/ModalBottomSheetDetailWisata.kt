package com.tourify.tourifyapp.ui.component

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.data.sources.LocationDetails
import com.tourify.tourifyapp.model.DataDestinationsResponse
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorInfo
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWarning
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.Toasty
import com.tourify.tourifyapp.utils.checkStatusTime
import com.tourify.tourifyapp.utils.cutLocation
import com.tourify.tourifyapp.utils.getDistanceLocation
import com.tourify.tourifyapp.utils.isValidEndDate
import com.tourify.tourifyapp.utils.modifyDateRange
import com.tourify.tourifyapp.utils.modifyMoneyFormat
import com.tourify.tourifyapp.utils.modifyNumberFormat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetDetailWisata(
    context: Context,
    idDetailWisata: Int,
    navController: NavController,
    daftarDestinations: List<DataDestinationsResponse>? = null,
    favoriteItems: List<Int>,
    onPayOrder: (String) -> Unit,
    currentLocation: LocationDetails? = null,
    onShowDetailWisata: (Int) -> Unit = {},
    onToggleFavorite: (Int) -> Unit,
) {
    val modalMapsBottomState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val modalInBookingBottomState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showMapsWisata by rememberSaveable { mutableStateOf(false) }
    var showInBooking by rememberSaveable { mutableStateOf(false) }
    val scrollModalDetailBottomState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val sortedData = daftarDestinations?.firstOrNull { it.id == idDetailWisata }
    sortedData?.let { destinations ->
        val distance = getDistanceLocation(
            LocationDetails(currentLocation!!.lat, currentLocation.lon),
            LocationDetails(destinations.lat, destinations.lon)
        )
        val thisLocation = cutLocation("${destinations.regency}, ${destinations.province}")
        val isFavorite = favoriteItems.contains(destinations.id)
        Column(
            modifier = Modifier
                .verticalScroll(scrollModalDetailBottomState),
            content = {
                Box(
                    content = {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(destinations.photo)
                                .crossfade(true)
                                .error(R.drawable.error_image)
                                .build(),
                            contentDescription = stringResource(id = R.string.photo_wisata),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(282.dp)
                                .shadow(
                                    25.dp,
                                    RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp),
                                    true,
                                    spotColor = TextPrimary
                                )
                                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                        )
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(start = 17.dp, top = 18.dp, end = 18.dp, bottom = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.7f),
                            content = {
                                Text(
                                    modifier = Modifier
                                        .padding(start = 1.dp),
                                    text = destinations.name,
                                    style = StyleText.copy(
                                        color = TextPrimary,
                                        fontFamily = fonts,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 18.sp,
                                        lineHeight = 22.sp
                                    ),
                                    maxLines = 2
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    content = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_location),
                                            contentDescription = stringResource(id = R.string.choose_location),
                                            modifier = Modifier
                                                .size(13.dp),
                                            tint = ColorDanger
                                        )
                                        Spacer(modifier = Modifier.width(1.dp))
                                        Text(
                                            text = thisLocation,
                                            style = StyleText.copy(
                                                color = TextPrimary,
                                                fontFamily = fonts,
                                                fontWeight = FontWeight.Light,
                                                fontSize = 12.sp,
                                                lineHeight = 16.sp
                                            ),
                                            maxLines = 2
                                        )
                                    }
                                )
                            }
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentWidth(),
                            content = {
                                CircleButton(
                                    context = context,
                                    title = R.string.maps,
                                    icon = R.drawable.ic_google_maps,
                                    sizeCircle = 35.dp,
                                    shadow = 10.dp,
                                    isIcon = false,
                                    onClick = { showMapsWisata = true }
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                CircleButton(
                                    context = context,
                                    title = if (isFavorite) R.string.remove_from_favorite else R.string.add_to_favorite,
                                    icon = if (isFavorite) R.drawable.ic_heart_fill else R.drawable.ic_heart,
                                    sizeCircle = 35.dp,
                                    shadow = 10.dp,
                                    isIcon = true,
                                    tint = if (isFavorite) ColorDanger else ColorSecondary,
                                    onClick = { onToggleFavorite(destinations.id) }
                                )
                            }
                        )
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            content = {
                                CircleButton(
                                    context = context,
                                    title = R.string.total_rating,
                                    icon = R.drawable.ic_rating,
                                    sizeCircle = 35.dp,
                                    sizeIcon = 26.dp,
                                    shadow = 4.dp,
                                    isIcon = true,
                                    tint = ColorWarning,
                                    onClick = {}
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    content = {
                                        Text(
                                            text = stringResource(id = R.string.rating),
                                            style = StyleText.copy(
                                                color = TextSecondary,
                                                fontFamily = fonts,
                                                fontWeight = FontWeight.Light,
                                                fontSize = 11.sp,
                                                lineHeight = 11.sp
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        val userRating = modifyNumberFormat("0")
                                        Text(
                                            text = "0.0 ($userRating)",
                                            style = StyleText.copy(
                                                color = TextPrimary,
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            content = {
                                CircleButton(
                                    context = context,
                                    title = R.string.distance,
                                    icon = R.drawable.ic_distance,
                                    sizeCircle = 35.dp,
                                    sizeIcon = 26.dp,
                                    shadow = 4.dp,
                                    isIcon = true,
                                    tint = ColorDanger,
                                    onClick = {}
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    content = {
                                        Text(
                                            text = stringResource(id = R.string.distance),
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
                                            text = "$distance Km",
                                            style = StyleText.copy(
                                                color = TextPrimary,
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            content = {
                                CircleButton(
                                    context = context,
                                    title = R.string.culinary,
                                    icon = R.drawable.ic_culinary,
                                    sizeCircle = 35.dp,
                                    sizeIcon = 20.dp,
                                    shadow = 4.dp,
                                    isIcon = true,
                                    tint = ColorInfo,
                                    onClick = {}
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    content = {
                                        Text(
                                            text = stringResource(id = R.string.culinary),
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
                                            text = "0",
                                            style = StyleText.copy(
                                                color = TextPrimary,
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
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                        .shadow(3.dp, Shapes.small, true, spotColor = TextPrimary)
                        .clip(Shapes.small)
                        .background(ColorWhite),
                    content = {
                        val timeOpen = destinations.openOn
                        val timeClosed = destinations.closedOn
                        Column(
                            modifier = Modifier
                                .padding(start = 14.dp, end = 14.dp, top = 12.dp, bottom = 12.dp),
                            content = {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    content = {
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth(0.8f),
                                            text = destinations.name,
                                            style = StyleText.copy(
                                                color = TextPrimary,
                                                fontFamily = fonts,
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 12.sp,
                                                lineHeight = 18.sp,
                                            ),
                                            maxLines = 2
                                        )
                                        val timeStatus = checkStatusTime(timeOpen,timeClosed)
                                        Box(
                                            modifier = Modifier
                                                .wrapContentWidth()
                                                .wrapContentHeight()
                                                .clip(Shapes.small)
                                                .background(if(timeStatus) ColorSecondary else ColorDanger.copy(0.5f)),
                                            content = {
                                                Text(
                                                    modifier = Modifier
                                                        .padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 2.dp),
                                                    text = if(timeStatus) "Buka" else "Tutup",
                                                    style = StyleText.copy(
                                                        color = if(timeStatus) TextPrimary else ColorWhite,
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
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    content = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.End,
                                            content = {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.ic_clock),
                                                    contentDescription = stringResource(id = R.string.time),
                                                    modifier = Modifier
                                                        .size(12.dp),
                                                    tint = ColorInfo
                                                )
                                                Spacer(modifier = Modifier.width(2.dp))
                                                val timeGate = "$timeOpen - $timeClosed WIB"
                                                Text(
                                                    text = timeGate,
                                                    style = StyleText.copy(
                                                        color = ColorInfo,
                                                        fontFamily = fonts,
                                                        fontWeight = FontWeight.Normal,
                                                        fontSize = 11.sp,
                                                        lineHeight = 11.sp,
                                                        textAlign = TextAlign.Start
                                                    )
                                                )
                                            }
                                        )
                                        Text(
                                            text = "Tiket Masuk ${modifyMoneyFormat(destinations.ticketPrice)}",
                                            style = StyleText.copy(
                                                color = ColorDanger,
                                                fontFamily = fonts,
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 11.sp,
                                                lineHeight = 11.sp,
                                                textAlign = TextAlign.End
                                            )
                                        )
                                    }
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                ExpandedText(text = destinations.caption)
                            }
                        )
                    }
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 18.dp)
                        .shadow(3.dp, Shapes.small, true, spotColor = TextPrimary)
                        .clip(Shapes.small)
                        .background(ColorWhite),
                    content = {
                        Column(
                            modifier = Modifier
                                .padding(start = 14.dp, top = 12.dp, bottom = 12.dp),
                            content = {
                                Text(
                                    text = "Daftar Pemandu Wisata",
                                    style = StyleText.copy(
                                        color = TextPrimary,
                                        fontFamily = fonts,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 12.sp,
                                        lineHeight = 12.sp,
                                    )
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                DaftarTourGuide(context = context, navController = navController)
                            }
                        )
                    }
                )
                val filteredData = daftarDestinations
                    .filter { it.type == destinations.type && it.id != destinations.id }
                    .sortedByDescending { it.rating }
                if (filteredData.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 18.dp, end = 18.dp, top = 14.dp, bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Text(
                                text = "Wisata Terkait \uD83C\uDF1F",
                                style = StyleText.copy(
                                    color = TextPrimary,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    lineHeight = 14.sp,
                                )
                            )
                        }
                    )
                    RelatedWisata(
                        daftarDestinations = filteredData,
                        onDetail = {
                            onShowDetailWisata(it)
                        },
                        favoriteItems = favoriteItems,
                        onToggleFavorite = {
                            onToggleFavorite(it)
                        }
                    )
                }
                ButtonPrimary(
                    modifier = Modifier
                        .padding(18.dp),
                    text = "Pesan Perjalanan",
                    background = ColorPrimary,
                    contentColor = ColorWhite,
                    enabled = true,
                    onClick = { showInBooking = true }
                )
            }
        )
        if (showInBooking) {
            ModalBottomSheet(
                modifier = Modifier
                    .fillMaxSize(),
                onDismissRequest = {
                    showInBooking = false
                },
                sheetState = modalInBookingBottomState,
                containerColor = ColorWhite,
                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
                dragHandle = {
                    BottomSheetDefaults.DragHandle(color = ColorSecondary)
                },
                content = {
                    var isLoadingPay by rememberSaveable { mutableStateOf(false) }
                    var progress by rememberSaveable { mutableIntStateOf(1) }
                    var startTripDateBooking by rememberSaveable { mutableStateOf("") }
                    var endTripDateBooking by rememberSaveable { mutableStateOf("") }
                    var allTripDateBooking by rememberSaveable { mutableStateOf("") }
                    var nameBooking by rememberSaveable { mutableStateOf("") }
                    var emailBooking by rememberSaveable { mutableStateOf("") }
                    var telpBooking by rememberSaveable { mutableStateOf("") }
                    var tourGuideIdBooking by rememberSaveable { mutableStateOf(0) }
                    var isWithNoTourGuide by rememberSaveable { mutableStateOf(false) }
                    var isChoosedTourGuide by rememberSaveable { mutableStateOf(false) }
                    var totalTicketsBooking by rememberSaveable { mutableIntStateOf(1) }
                    var totalPriceBooking by rememberSaveable { mutableIntStateOf(0) }
                    LaunchedEffect(isLoadingPay) {
                        delay(2000)
                        if (progress == 3) {
                            onPayOrder("TRF20221120102211")
                        }
                    }
                    Scaffold(
                        containerColor = ColorWhite,
                        topBar = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 18.dp, end = 18.dp, bottom = 18.dp),
                                content = {
                                    Text(
                                        text = "Pesan Perjalanan",
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 14.sp,
                                            lineHeight = 14.sp,
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    ProgressBarBooking(
                                        progressValue = progress,
                                        onClick = { progressId ->
                                            progress = progressId
                                        }
                                    )
                                }
                            )
                        },
                        bottomBar = {
                            if(isLoadingPay) {
                                LoadingButtonInBooking(
                                    modifier = Modifier.padding(18.dp)
                                )
                            } else {
                                ButtonInBooking(
                                    modifier = Modifier.padding(18.dp),
                                    text = if (progress <= 2) "Lanjutkan" else "Pembayaran",
                                    onClick = {
                                        if (progress <= 2) progress += 1 else isLoadingPay = true
                                    },
                                    onInfo = {
                                        Toasty.show(context, R.string.complete_your_order_data)
                                    },
                                    onBatal = {
                                        scope.launch { modalInBookingBottomState.hide() }.invokeOnCompletion {
                                            if (!modalInBookingBottomState.isVisible) {
                                                showInBooking = false
                                            }
                                        }
                                    },
                                    enabled = when (progress) {
                                        1 -> isChoosedTourGuide
                                        2 -> isValidEndDate(startTripDateBooking) && nameBooking.isNotEmpty() && emailBooking.isNotEmpty() && telpBooking.isNotEmpty()
                                        3 -> true
                                        else -> false
                                    },
                                    info = when (progress) {
                                        1 -> !isWithNoTourGuide
                                        else -> false
                                    },
                                )
                            }
                        }) { paddingValues ->
                        val dataWisataBooking = daftarDestinations
                            .filter { it.id == destinations.id }
                            .take(1)
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(
                                    top = paddingValues.calculateTopPadding(),
                                    bottom = paddingValues.calculateBottomPadding()
                                )
                                .background(ColorWhite),
                            content = {
                                ModalBottomSheetBookingTrip(
                                    context = context,
                                    daftarDestinations = dataWisataBooking,
                                    idDetailWisata = idDetailWisata,
                                    progress = progress,
                                    onProgress = { progress = it },
                                    onStartTripDateBooking = {
                                        startTripDateBooking = it
                                        allTripDateBooking = modifyDateRange(startTripDateBooking, endTripDateBooking)
                                    },
                                    onEndTripDateBooking = {
                                        endTripDateBooking = it
                                        allTripDateBooking = modifyDateRange(startTripDateBooking, endTripDateBooking)
                                    },
                                    onNameBooking = { nameBooking = it },
                                    onEmailBooking = { emailBooking = it },
                                    onTelpBooking = { telpBooking = it },
                                    onTourGuideIdBooking = {
                                        tourGuideIdBooking = it
                                        isChoosedTourGuide = true
                                    },
                                    onWithNoTourGuide = {
                                        isWithNoTourGuide = it
                                        if (isWithNoTourGuide) {
                                            tourGuideIdBooking = 0
                                        }
                                        isChoosedTourGuide = true
                                    },
                                    onTotalTicketsBooking = { totalTicketsBooking = it},
                                    onTotalPriceBooking = { totalPriceBooking = it },
                                    isWithNoTourGuide = isWithNoTourGuide,
                                    totalTicketsBooking = totalTicketsBooking,
                                    startTripDateBooking = startTripDateBooking,
                                    endTripDateBooking = endTripDateBooking,
                                    allTripDateBooking = allTripDateBooking,
                                    tourGuideIdBooking = tourGuideIdBooking,
                                    nameBooking = nameBooking,
                                    emailBooking = emailBooking,
                                    telpBooking = telpBooking
                                )
                            }
                        )
                    }
                }
            )
        }
        if (showMapsWisata) {
            ModalBottomSheet(
                modifier = Modifier
                    .fillMaxSize(),
                onDismissRequest = {
                    showMapsWisata = false
                },
                sheetState = modalMapsBottomState,
                containerColor = ColorWhite,
                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
                dragHandle = {
                    BottomSheetDefaults.DragHandle(color = ColorSecondary)
                },
                content = {
                    Scaffold(
                        containerColor = ColorWhite,
                        topBar = {
                            TopBarScreenModal(
                                title = destinations.name,
                                onBack = { showValue ->
                                    scope.launch { modalMapsBottomState.hide() }.invokeOnCompletion {
                                        if (!modalMapsBottomState.isVisible) {
                                            showMapsWisata = showValue
                                        }
                                    }
                                }
                            )
                        }) { paddingValues ->
                        ModalBottomSheetMaps(
                            modifier = Modifier
                                .padding(top = paddingValues.calculateTopPadding())
                                .background(ColorWhite),
                            context = context,
                            lon = destinations.lon,
                            lat = destinations.lat,
                            name = destinations.name,
                            location = thisLocation
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun ExpandedText(modifier: Modifier = Modifier, text: String) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val textLayoutResultState = rememberSaveable { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by rememberSaveable { mutableStateOf(false) }
    var finalText by rememberSaveable { mutableStateOf(text) }

    val textLayoutResult = textLayoutResultState.value
    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect
        when {
            isExpanded -> {
                finalText = "$text Sembunyikan"
            }
            !isExpanded && textLayoutResult.hasVisualOverflow -> {
                val lastCharIndex = textLayoutResult.getLineEnd(5 - 1)
                val showMoreString = " Selengkapnya"
                val adjustedText = text
                    .substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(showMoreString.length)
                    .dropLastWhile { it == ' ' || it == '.' }

                finalText = "$adjustedText$showMoreString"
                isClickable = true
            }
        }
    }

    val annotatedString = buildAnnotatedString {
        append(finalText)
        val indexSembunyikan = finalText.indexOf("Sembunyikan")
        val indexSelengkapnya = finalText.indexOf("Selengkapnya")

        if (indexSembunyikan != -1) {
            addStyle(
                style = SpanStyle(fontWeight = FontWeight.Normal),
                start = indexSembunyikan,
                end = indexSembunyikan + "Sembunyikan".length
            )
        }

        if (indexSelengkapnya != -1) {
            addStyle(
                style = SpanStyle(fontWeight = FontWeight.Normal),
                start = indexSelengkapnya,
                end = indexSelengkapnya + "Selengkapnya".length
            )
        }
    }

    Text(
        text = annotatedString,
        maxLines = if (isExpanded) Int.MAX_VALUE else 5,
        onTextLayout = { textLayoutResultState.value = it },
        style = StyleText.copy(
            color = TextSecondary,
            fontFamily = fonts,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            lineHeight = 18.sp,
        ),
        modifier = modifier
            .clickable(
                enabled = isClickable,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { isExpanded = !isExpanded }
            .animateContentSize(),
    )
}