package com.tourify.tourifyapp.ui.component

import android.content.Context
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tourify.tourifyapp.R
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetDetailWisata(
    context: Context,
    navController: NavController,
    onShowDetailWisata: (Boolean) -> Unit,
) {
    val modalMapsBottomState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showMapsWisata by rememberSaveable { mutableStateOf(false) }
    var isFavorite by rememberSaveable { mutableStateOf(false) }
    val scrollModalDetailBottomState = rememberScrollState()
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .verticalScroll(scrollModalDetailBottomState),
        content = {
            Box(
                content = {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data("")
                            .crossfade(true)
                            .error(R.drawable.error_image)
                            .build(),
                        contentDescription = stringResource(id = R.string.photo_wisata),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(282.dp)
                            .shadow(25.dp, RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp), true, spotColor = TextPrimary)
                            .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                    )
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 17.dp, top = 18.dp, end = 18.dp, bottom = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Column(
                        content = {
                            Text(
                                modifier = Modifier
                                    .padding(start = 1.dp),
                                text = "Wisata Name",
                                style = StyleText.copy(
                                    color = TextPrimary,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 18.sp,
                                    lineHeight = 18.sp
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
                                            .size(13.dp),
                                        tint = ColorDanger
                                    )
                                    Spacer(modifier = Modifier.width(1.dp))
                                    Text(
                                        text = "Location, Indonesia",
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
                                title = R.string.add_to_favorite,
                                icon = if (isFavorite) R.drawable.ic_heart_fill else R.drawable.ic_heart,
                                sizeCircle = 35.dp,
                                shadow = 10.dp,
                                isIcon = true,
                                tint = if (isFavorite) ColorDanger else ColorSecondary,
                                onClick = {}
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
                                    Text(
                                        text = "0.0 (0)",
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
                                        text = "0.0 Km",
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
                    Column(
                        modifier = Modifier
                            .padding(start = 14.dp, end = 14.dp, top = 12.dp, bottom = 12.dp),
                        content = {
                            Text(
                                text = "Wisata Name",
                                style = StyleText.copy(
                                    color = TextPrimary,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    lineHeight = 18.sp,
                                ),
                                maxLines = 2
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        content = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_clock),
                                                contentDescription = stringResource(id = R.string.time),
                                                modifier = Modifier
                                                    .size(12.dp),
                                                tint = ColorInfo
                                            )
                                            Spacer(modifier = Modifier.width(2.dp))
                                            Text(
                                                text = "Buka (08:00 - 15:00 WIB)",
                                                style = StyleText.copy(
                                                    color = ColorInfo,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = 10.sp,
                                                    lineHeight = 10.sp
                                                )
                                            )
                                        }
                                    )
                                    Text(
                                        text = "Tiket Masuk (Rp0,-)",
                                        style = StyleText.copy(
                                            color = ColorDanger,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 10.sp,
                                            lineHeight = 10.sp,
                                        )
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            ExpandedText(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                        }
                    )
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 18.dp, end = 18.dp, bottom = 18.dp)
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
            ButtonPrimary(
                modifier = Modifier
                    .padding(start = 18.dp, end = 18.dp, bottom = 18.dp),
                text = "Pesan Perjalanan",
                background = ColorPrimary,
                contentColor = ColorWhite,
                enabled = true,
                onClick = {}
            )
        }
    )
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
                            title = "Wisata Name",
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
                        lon = 100.3499316,
                        lat = -0.9295652,
                        onBack = { showValue ->
                            scope.launch { modalMapsBottomState.hide() }.invokeOnCompletion {
                                if (!modalMapsBottomState.isVisible) {
                                    showMapsWisata = showValue
                                }
                            }
                        }
                    )
                }
            }
        )
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
        if (finalText.endsWith("Sembunyikan") || finalText.endsWith("Selengkapnya")) {
            addStyle(
                style = SpanStyle(fontWeight = FontWeight.Normal),
                start = finalText.length - "Sembunyikan".length,
                end = finalText.length
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

@Preview(showBackground = true)
@Composable
fun ModalBottomSheetDetailWisataPreview() {
    val context = LocalContext.current
    ModalBottomSheetDetailWisata(
        context = context,
        navController = NavController(context),
        onShowDetailWisata = {}
    )
}