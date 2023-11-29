package com.tourify.tourifyapp.ui.main.screen

import android.content.Context
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.maps.model.LatLng
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.data.sources.ItemProvince
import com.tourify.tourifyapp.model.ItemProvinceModel
import com.tourify.tourifyapp.ui.component.GreetingBar
import com.tourify.tourifyapp.ui.component.ModalBottomSheetDetailWisata
import com.tourify.tourifyapp.ui.component.ModalBottomSheetListProvince
import com.tourify.tourifyapp.ui.component.NearbyWisata
import com.tourify.tourifyapp.ui.component.PopularWisata
import com.tourify.tourifyapp.ui.component.SliderBanner
import com.tourify.tourifyapp.ui.component.TextFieldSearch
import com.tourify.tourifyapp.ui.component.WisataCategory
import com.tourify.tourifyapp.ui.theme.ColorBackground
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    context: Context,
    navController: NavController,
    paddingValues: PaddingValues,
    navigateToMapsWisata: (String) -> Unit = {}
) {
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(ColorBackground, darkIcons = true)
        onDispose {}
    }
    val scrollState = rememberScrollState()
    val modalBottomState = rememberModalBottomSheetState()
    val modalSearchBottomState = rememberModalBottomSheetState()
    val scrollModalSearchBottomState = rememberScrollState()
    val modalDetailBottomState = rememberModalBottomSheetState()
    var showListProvince by rememberSaveable { mutableStateOf(false) }
    var showSearchWisata by rememberSaveable { mutableStateOf(false) }
    var showDetailWisata by rememberSaveable { mutableStateOf(false) }
    var idDetailWisata by rememberSaveable { mutableIntStateOf(0) }
    var firstProvince by rememberSaveable { mutableStateOf("Sumatra Barat") }
    var categoryWisata by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorWhite)
            .verticalScroll(scrollState),
        content = {
            GreetingBar(
                context = context,
                navController = navController,
                onShowListProvince = { showValue ->
                    showListProvince = showValue
                },
                firstProvince = firstProvince
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 18.dp, end = 18.dp, bottom = 0.dp),
                verticalArrangement = Arrangement.Center,
                content = {
                    Text(
                        text = "Hi, namaanda@example.com \uD83D\uDC4B",
                        style = StyleText.copy(
                            color = TextSecondary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            lineHeight = 14.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Temukan Kebahagiaan Anda Bersama Kami!",
                        maxLines = 2,
                        style = StyleText.copy(
                            color = TextPrimary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            lineHeight = 30.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    TextFieldSearch(
                        placeholder = stringResource(R.string.search_wisata),
                        icon = R.drawable.ic_search,
                        iconDescription = R.string.search_wisata,
                        keyboardType = KeyboardType.Text,
                        onClick = { showSearchWisata = true },
                        onTextChanged = {}
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    SliderBanner()
                }
            )
            Column(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                content = {
                    WisataCategory(
                        onClick = { categoryValue ->
                            categoryWisata = categoryValue
                        }
                    )
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 18.dp, top = 16.dp, bottom = 12.dp, end = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Text(
                        text = stringResource(id = R.string.popular_wisata),
                        style = StyleText.copy(
                            color = TextPrimary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            lineHeight = 14.sp
                        )
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_small_right),
                        contentDescription = stringResource(id = R.string.popular_wisata),
                        tint = TextPrimary
                    )
                }
            )
            PopularWisata(
                categoryWisata = categoryWisata,
                onDetail = {
                    idDetailWisata = it
                    showDetailWisata = true
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 18.dp, top = 14.dp, bottom = 12.dp, end = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Text(
                        text = stringResource(id = R.string.nearby_wisata),
                        style = StyleText.copy(
                            color = TextPrimary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            lineHeight = 14.sp
                        )
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_small_right),
                        contentDescription = stringResource(id = R.string.nearby_wisata),
                        tint = TextPrimary
                    )
                }
            )
            NearbyWisata(
                modifier = Modifier
                    .padding(start = 18.dp, end = 18.dp, bottom = paddingValues.calculateBottomPadding()),
                onDetail = {
                    idDetailWisata = it
                    showDetailWisata = true
                }
            )
        }
    )
    if (showDetailWisata) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(),
            onDismissRequest = {
                showDetailWisata = false
            },
            sheetState = modalDetailBottomState,
            containerColor = ColorWhite,
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            dragHandle = {},
            content = {
                ModalBottomSheetDetailWisata(
                    context = context,
                    navController = navController,
                    onShowDetailWisata = { showValue ->
                        showDetailWisata = showValue
                    },
                    onShowMapsWisata = { lonLat ->
                        navigateToMapsWisata(lonLat)
                    }
                )
            }
        )
    }
    if (showSearchWisata) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(),
            onDismissRequest = {
                showSearchWisata = false
            },
            sheetState = modalSearchBottomState,
            containerColor = ColorWhite,
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    content = {

                    }
                )
            }
        )
    }
    if (showListProvince) {
        ModalBottomSheet(
            modifier = Modifier
                .height(350.dp),
            onDismissRequest = {
                showListProvince = false
            },
            sheetState = modalBottomState,
            containerColor = ColorWhite,
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            dragHandle = {},
            content = {
                Scaffold(
                    containerColor = ColorWhite,
                    topBar = {
                    Column(
                        modifier = Modifier
                            .padding(start = 18.dp, end = 18.dp, top = 14.dp, bottom = 14.dp),
                        content = {
                            Text(
                                text = "Pilih Provinsi",
                                style = StyleText.copy(
                                    color = TextPrimary,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    lineHeight = 16.sp
                                )
                            )
                        }
                    )
                }) { paddingValues ->
                    ModalBottomSheetListProvince(
                        modifier = Modifier
                            .padding(top = paddingValues.calculateTopPadding())
                            .background(ColorWhite),
                        context = context,
                        navController = navController,
                        onShowListProvince = { showValue ->
                            showListProvince = showValue
                        },
                        onProvince = { province ->
                            firstProvince = province
                        },
                        firstProvince = firstProvince
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val context = LocalContext.current
    HomeScreen(
        context = context,
        navController = NavController(context),
        paddingValues = PaddingValues(0.dp)
    )
}


