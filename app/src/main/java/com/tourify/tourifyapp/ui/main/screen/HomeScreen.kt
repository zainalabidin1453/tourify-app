package com.tourify.tourifyapp.ui.main.screen

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.data.db.DBHistorySearchHandler
import com.tourify.tourifyapp.ui.component.GreetingBar
import com.tourify.tourifyapp.ui.component.ModalBottomSheetAllNearbyWisata
import com.tourify.tourifyapp.ui.component.ModalBottomSheetAllPopularWisata
import com.tourify.tourifyapp.ui.component.ModalBottomSheetDetailWisata
import com.tourify.tourifyapp.ui.component.ModalBottomSheetFilters
import com.tourify.tourifyapp.ui.component.ModalBottomSheetListProvince
import com.tourify.tourifyapp.ui.component.ModalBottomSheetSearchWisata
import com.tourify.tourifyapp.ui.component.NearbyWisata
import com.tourify.tourifyapp.ui.component.PopularWisata
import com.tourify.tourifyapp.ui.component.TextFieldSearch
import com.tourify.tourifyapp.ui.component.TopBarAllWisata
import com.tourify.tourifyapp.ui.component.WisataCategory
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.checkKeywords
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    context: Context,
    navController: NavController,
    paddingValues: PaddingValues,
    navigateToFavorite: (Int) -> Unit = {},
    navigateToNotice: (Int) -> Unit = {},
) {
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(ColorWhite, darkIcons = true)
        onDispose {}
    }
    val scrollState = rememberScrollState()
    val modalBottomState = rememberModalBottomSheetState()
    val modalSearchBottomState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val modalAllPopularWisataBottomState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val modalAllNearbyWisataBottomState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val modalFiltersBottomState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val modalDetailBottomState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showListProvince by rememberSaveable { mutableStateOf(false) }
    var showHistorySearch by rememberSaveable { mutableStateOf(true) }
    var showSearchWisata by rememberSaveable { mutableStateOf(false) }
    var showAllPopularWisata by rememberSaveable { mutableStateOf(false) }
    var showAllNearbyWisata by rememberSaveable { mutableStateOf(false) }
    var showDetailWisata by rememberSaveable { mutableStateOf(false) }
    var showFilters by rememberSaveable { mutableStateOf(false) }
    var idDetailWisata by rememberSaveable { mutableIntStateOf(0) }
    var firstProvince by rememberSaveable { mutableStateOf("Sumatra Barat") }
    var keywords by rememberSaveable { mutableStateOf("") }
    var categoryWisata by rememberSaveable { mutableStateOf("") }
    var isAscending by rememberSaveable { mutableStateOf(false) }
    var isFree by rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val scope = rememberCoroutineScope()
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
                onShowNotice = { navigateToNotice(1) },
                onShowFavorite = { navigateToFavorite(1) },
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
                            fontSize = 12.sp,
                            lineHeight = 12.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Temukan Kebahagiaan Anda Bersama Kami!",
                        maxLines = 3,
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
                }
            )
            Column(
                modifier = Modifier
                    .padding(top = 18.dp)
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
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = {
                                    showAllPopularWisata = true
                                }
                            ),
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
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = {
                                    showAllNearbyWisata = true
                                }
                            ),
                        painter = painterResource(id = R.drawable.ic_chevron_small_right),
                        contentDescription = stringResource(id = R.string.nearby_wisata),
                        tint = TextPrimary
                    )
                }
            )
            NearbyWisata(
                modifier = Modifier
                    .padding(
                        start = 18.dp,
                        end = 18.dp,
                        bottom = paddingValues.calculateBottomPadding()
                    ),
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
                    onPayOrder = {}
                )
            }
        )
    }
    if (showAllPopularWisata) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(),
            onDismissRequest = {
                showAllPopularWisata = false
            },
            sheetState = modalAllPopularWisataBottomState,
            containerColor = ColorWhite,
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            dragHandle = {
                BottomSheetDefaults.DragHandle(color = ColorSecondary)
            },
            content = {
                Scaffold(
                    containerColor = ColorWhite,
                    topBar = {
                        TopBarAllWisata(
                            context = context,
                            onKeywords = { keywords = it },
                            onShowFilters = { showFilters = it }
                        )
                    }) { paddingValues ->
                    ModalBottomSheetAllPopularWisata(
                        modifier = Modifier
                            .padding(top = paddingValues.calculateTopPadding())
                            .background(ColorWhite),
                        onDetail = {
                            idDetailWisata = it
                            showDetailWisata = true
                        },
                    )
                }
            }
        )
    }
    if (showAllNearbyWisata) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(),
            onDismissRequest = {
                showAllNearbyWisata = false
            },
            sheetState = modalAllNearbyWisataBottomState,
            containerColor = ColorWhite,
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            dragHandle = {
                BottomSheetDefaults.DragHandle(color = ColorSecondary)
            },
            content = {
                Scaffold(
                    containerColor = ColorWhite,
                    topBar = {
                        TopBarAllWisata(
                            context = context,
                            onKeywords = { keywords = it },
                            onShowFilters = { showFilters = it }
                        )
                    }) { paddingValues ->
                    ModalBottomSheetAllNearbyWisata(
                        modifier = Modifier
                            .padding(top = paddingValues.calculateTopPadding())
                            .background(ColorWhite),
                        onDetail = {
                            idDetailWisata = it
                            showDetailWisata = true
                        },
                    )
                }
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
                Scaffold(
                    containerColor = ColorWhite,
                    topBar = {
                        TopBarAllWisata(
                            context = context,
                            value = keywords,
                            onKeywords = {
                                keywords = it
                            },
                            onShowFilters = { showFilters = it },
                            onShowHistorySearch = { showHistorySearch = it },
                            onAddHistorySearch = { isAdd ->
                                val cKey = checkKeywords(keywords)
                                if (isAdd && cKey) {
                                    val dbHistorySearchHandler = DBHistorySearchHandler(context)
                                    dbHistorySearchHandler.addHistorySearch(keywords)
                                }
                            }
                        )
                    }) { paddingValues ->
                    ModalBottomSheetSearchWisata(
                        context = context,
                        modifier = Modifier
                            .padding(top = paddingValues.calculateTopPadding())
                            .background(ColorWhite),
                        showHistorySearch = showHistorySearch,
                        onDetail = { idDetail ->
                            idDetailWisata = idDetail
                            showDetailWisata = true
                        },
                        onKeywords = { word ->
                            keywords = word
                        }
                    )
                }
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
            dragHandle = null,
            content = {
                Scaffold(
                    containerColor = ColorWhite,
                    topBar = {
                        Column(
                            modifier = Modifier
                                .padding(18.dp),
                            content = {
                                Text(
                                    text = "Pilih Provinsi \uD83D\uDCCD",
                                    style = StyleText.copy(
                                        color = TextPrimary,
                                        fontFamily = fonts,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp
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
                            scope.launch { modalBottomState.hide() }.invokeOnCompletion {
                                if (!modalBottomState.isVisible) {
                                    showListProvince = showValue
                                }
                            }
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
    if (showFilters) {
        ModalBottomSheet(
            modifier = Modifier
                .height(165.dp),
            onDismissRequest = {
                showFilters = false
            },
            sheetState = modalFiltersBottomState,
            containerColor = ColorWhite,
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            dragHandle = {},
            content = {
                Scaffold(
                    containerColor = ColorWhite,
                    topBar = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            content = {
                                Text(
                                    text = "Sortir & Filter \uD83D\uDD0D",
                                    style = StyleText.copy(
                                        color = TextPrimary,
                                        fontFamily = fonts,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp
                                    )
                                )
                            }
                        )
                    }) { paddingValues ->
                    ModalBottomSheetFilters(
                        modifier = Modifier
                            .padding(top = paddingValues.calculateTopPadding())
                            .background(ColorWhite),
                        isAscending = isAscending,
                        isFree = isFree,
                        onAscending = {
                            isAscending = it
                        },
                        onFree = {
                            isFree = it
                        }
                    )
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
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


