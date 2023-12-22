package com.tourify.tourifyapp.ui.main.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.constants.Constants
import com.tourify.tourifyapp.data.db.DBHistorySearchHandler
import com.tourify.tourifyapp.data.factory.ViewModelDestinationsFactory
import com.tourify.tourifyapp.data.factory.ViewModelFavoriteFactory
import com.tourify.tourifyapp.data.factory.ViewModelRecommendationFactory
import com.tourify.tourifyapp.data.sources.LocationDetails
import com.tourify.tourifyapp.data.viewmodels.DestinationsViewModel
import com.tourify.tourifyapp.data.viewmodels.FavoriteViewModel
import com.tourify.tourifyapp.data.viewmodels.RecommendationViewModel
import com.tourify.tourifyapp.di.Injection
import com.tourify.tourifyapp.model.DestinationsResponse
import com.tourify.tourifyapp.preference.LoginDataStore
import com.tourify.tourifyapp.ui.common.UiState
import com.tourify.tourifyapp.ui.component.GreetingBar
import com.tourify.tourifyapp.ui.component.ModalBottomSheetAllNearbyWisata
import com.tourify.tourifyapp.ui.component.ModalBottomSheetAllPopularWisata
import com.tourify.tourifyapp.ui.component.ModalBottomSheetDetailWisata
import com.tourify.tourifyapp.ui.component.ModalBottomSheetFavoritWisata
import com.tourify.tourifyapp.ui.component.ModalBottomSheetFilters
import com.tourify.tourifyapp.ui.component.ModalBottomSheetListProvince
import com.tourify.tourifyapp.ui.component.ModalBottomSheetSearchWisata
import com.tourify.tourifyapp.ui.component.NearbyWisata
import com.tourify.tourifyapp.ui.component.NearbyWisataLoading
import com.tourify.tourifyapp.ui.component.PopularWisata
import com.tourify.tourifyapp.ui.component.PopularWisataLoading
import com.tourify.tourifyapp.ui.component.RequestPermission
import com.tourify.tourifyapp.ui.component.TextFieldSearch
import com.tourify.tourifyapp.ui.component.TopBarAllWisata
import com.tourify.tourifyapp.ui.component.TopBarScreenModal
import com.tourify.tourifyapp.ui.component.WisataCategory
import com.tourify.tourifyapp.ui.theme.ColorBlue
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.checkKeywords
import com.tourify.tourifyapp.utils.getMyProvince
import kotlinx.coroutines.launch

@SuppressLint("PermissionLaunchedDuringComposition")
@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    context: Context,
    navController: NavController,
    paddingValues: PaddingValues,
    navigateToNotice: (Int) -> Unit = {},
    navigateToPayOrder: (String) -> Unit = {},
    destinationsViewModel: DestinationsViewModel = viewModel(
        factory = ViewModelDestinationsFactory(
            Injection.provideDestinationsRepository()
        )
    ),
    recommendationViewModel: RecommendationViewModel = viewModel(
        factory = ViewModelRecommendationFactory(
            Injection.provideRecommendationRepository()
        )
    ),
    favoriteViewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFavoriteFactory(
            Injection.provideFavoriteRepository()
        )
    ),
    emailUser: String,
    userId: Int
) {
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = false)
        onDispose {}
    }

    //val recommendationResult by recommendationViewModel.uiState.collectAsState(initial = UiState.Loading)
    //val favoriteItems by favoriteViewModel.favoriteItems.collectAsState()
    val destinationsResult by destinationsViewModel.uiState.collectAsState(initial = UiState.Loading)
    LaunchedEffect(destinationsViewModel) {
        if (destinationsResult is UiState.Loading) {
            destinationsViewModel.getDestinations()
        }
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
    val modalFavoritBottomState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val interactionSource = remember { MutableInteractionSource() }
    val scope = rememberCoroutineScope()
    val fucusManager = LocalFocusManager.current
    val gradientColors = listOf(ColorBlue, ColorPrimary)

    var currentLocation by remember { mutableStateOf(LocationDetails(0.toDouble(), 0.toDouble()))}
    var isPermissionGranted by rememberSaveable { mutableStateOf(false) }
    var isChangeProvince by rememberSaveable { mutableStateOf(false) }
    var firstProvince by rememberSaveable { mutableStateOf(Constants.ACEH) }
    var showListProvince by rememberSaveable { mutableStateOf(false) }
    var showHistorySearch by rememberSaveable { mutableStateOf(true) }
    var showSearchWisata by rememberSaveable { mutableStateOf(false) }
    var showAllPopularWisata by rememberSaveable { mutableStateOf(false) }
    var showAllNearbyWisata by rememberSaveable { mutableStateOf(false) }
    var showDetailWisata by rememberSaveable { mutableStateOf(false) }
    var showFavoritWisata by rememberSaveable { mutableStateOf(false) }
    var showDetailWisataAgain by rememberSaveable { mutableStateOf(false) }
    var showFilters by rememberSaveable { mutableStateOf(false) }
    var idDetailWisata by rememberSaveable { mutableIntStateOf(0) }
    var keywords by rememberSaveable { mutableStateOf("") }
    var isAscending by rememberSaveable { mutableStateOf(false) }
    var isFree by rememberSaveable { mutableStateOf(false) }

    RequestPermission(
        context = context,
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        currentLocation = { location ->
            currentLocation = location
        },
        isPermissionGranted = { isGranted ->
            isPermissionGranted = isGranted
        }
    ) { adds ->
        val myLocation = adds[0].adminArea
        if(!isChangeProvince){
            firstProvince = getMyProvince(myLocation)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ColorWhite),
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(ColorWhite),
                        content = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .align(Alignment.TopCenter),
                                content = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .align(Alignment.TopCenter)
                                            .padding(bottom = 25.dp),
                                        content = {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .wrapContentHeight()
                                                    .align(Alignment.TopCenter)
                                                    .background(
                                                        brush = Brush.horizontalGradient(colors = gradientColors),
                                                    ),
                                                content = {
                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .align(Alignment.TopCenter),
                                                        content = {
                                                            GreetingBar(
                                                                context = context,
                                                                onShowListProvince = { showValue ->
                                                                    showListProvince = showValue
                                                                },
                                                                onShowNotice = { navigateToNotice(1) },
                                                                onShowFavorite = {
                                                                    showFavoritWisata = true
                                                                },
                                                                firstProvince = firstProvince
                                                            )
                                                            Column(
                                                                modifier = Modifier
                                                                    .fillMaxWidth()
                                                                    .padding(
                                                                        start = 18.dp,
                                                                        end = 18.dp,
                                                                        bottom = 37.dp,
                                                                        top = 2.dp
                                                                    ),
                                                                verticalArrangement = Arrangement.Center,
                                                                content = {
                                                                    Text(
                                                                        text = "Hi, $emailUser \uD83D\uDC4B",
                                                                        style = StyleText.copy(
                                                                            color = ColorWhite,
                                                                            fontFamily = fonts,
                                                                            fontWeight = FontWeight.Normal,
                                                                            fontSize = 12.sp,
                                                                            lineHeight = 12.sp
                                                                        )
                                                                    )
                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            4.dp
                                                                        )
                                                                    )
                                                                    Text(
                                                                        text = "Temukan Kebahagiaan Anda Bersama Kami!",
                                                                        maxLines = 3,
                                                                        style = StyleText.copy(
                                                                            color = ColorWhite,
                                                                            fontFamily = fonts,
                                                                            fontWeight = FontWeight.Bold,
                                                                            fontSize = 22.sp,
                                                                            lineHeight = 30.sp
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
                                    Box(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .wrapContentHeight()
                                            .padding(start = 18.dp, end = 18.dp)
                                            .align(Alignment.BottomCenter)
                                            .shadow(
                                                6.dp,
                                                Shapes.small,
                                                true,
                                                spotColor = TextPrimary
                                            ),
                                        content = {
                                            TextFieldSearch(
                                                placeholder = stringResource(R.string.search_wisata),
                                                icon = R.drawable.ic_search,
                                                iconDescription = R.string.search_wisata,
                                                keyboardType = KeyboardType.Text,
                                                onClick = { showSearchWisata = true },
                                                onTextChanged = {},
                                                isWithBorder = false
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        content = {
                            Column(
                                modifier = Modifier
                                    .padding(top = 18.dp)
                                    .fillMaxWidth(),
                                content = {
                                    WisataCategory(
                                        onClick = { categoryValue ->
                                            keywords = categoryValue
                                            showSearchWisata = true
                                        }
                                    )
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
                            when(destinationsResult){
                                is UiState.Loading -> {
                                    LazyRow(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 18.dp),
                                        state = rememberLazyListState()
                                    ) {
                                        items(2) {
                                            PopularWisataLoading()
                                            Spacer(modifier = Modifier.width(10.dp))
                                        }
                                    }
                                    favoriteViewModel.getFavorites(userId)
                                }
                                is UiState.Success -> {
                                    val daftarDestinations = (destinationsResult as UiState.Success<DestinationsResponse>).data.data
                                    val favoriteItems by favoriteViewModel.favoriteItems.collectAsState()
                                    PopularWisata(
                                        daftarDestinations = daftarDestinations,
                                        favoriteItems = favoriteItems,
                                        onDetail = {
                                            idDetailWisata = it
                                            showDetailWisata = true
                                        },
                                        onToggleFavorite = {
                                            val isFavorite = favoriteItems.contains(it)
                                            if (!isFavorite) {
                                                favoriteViewModel.addToFavorite(userId, it)
                                            } else {
                                                favoriteViewModel.deleteFromFavorite(userId, it)
                                            }
                                        }
                                    )
                                }
                                is UiState.Error -> {}
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 18.dp, top = 15.dp, bottom = 2.dp, end = 18.dp),
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
                            when(destinationsResult){
                                is UiState.Loading -> {
                                    Column(
                                        modifier = Modifier
                                            .padding(
                                                start = 18.dp,
                                                end = 18.dp,
                                                bottom = paddingValues.calculateBottomPadding()
                                            )
                                    ) {
                                        repeat(5) {
                                            NearbyWisataLoading()
                                            Spacer(modifier = Modifier.height(10.dp))
                                        }
                                    }
                                }
                                is UiState.Success -> {
                                    val daftarDestinations = (destinationsResult as UiState.Success<DestinationsResponse>).data.data
                                    val favoriteItems by favoriteViewModel.favoriteItems.collectAsState()
                                    NearbyWisata(
                                        modifier = Modifier
                                            .padding(
                                                start = 18.dp,
                                                end = 18.dp,
                                                bottom = paddingValues.calculateBottomPadding()
                                            ),
                                        currentLocation = currentLocation,
                                        daftarDestinations = daftarDestinations,
                                        favoriteItems = favoriteItems,
                                        myProvince = firstProvince,
                                        onDetail = {
                                            idDetailWisata = it
                                            showDetailWisata = true
                                        },
                                        onToggleFavorite = {
                                            val isFavorite = favoriteItems.contains(it)
                                            if (!isFavorite) {
                                                favoriteViewModel.addToFavorite(userId, it)
                                            } else {
                                                favoriteViewModel.deleteFromFavorite(userId, it)
                                            }
                                        }
                                    )
                                }
                                is UiState.Error -> {}
                            }
                        }
                    )
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
            windowInsets = WindowInsets(0.dp),
            containerColor = ColorWhite,
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            dragHandle = {},
            content = {
                val daftarDestinations = (destinationsResult as UiState.Success<DestinationsResponse>).data.data
                val favoriteItems by favoriteViewModel.favoriteItems.collectAsState()
                ModalBottomSheetDetailWisata(
                    context = context,
                    idDetailWisata = idDetailWisata,
                    currentLocation = currentLocation,
                    navController = navController,
                    onShowDetailWisata = { id ->
                        scope.launch { modalDetailBottomState.hide() }.invokeOnCompletion {
                            if (!modalDetailBottomState.isVisible) {
                                showDetailWisata = false
                            }
                        }
                        idDetailWisata = id
                        showDetailWisataAgain = true
                    },
                    onPayOrder = {
                        navigateToPayOrder(it)
                    },
                    daftarDestinations = daftarDestinations,
                    favoriteItems = favoriteItems,
                    onToggleFavorite = { id ->
                        val isFavorite = favoriteItems.contains(id)
                        if (!isFavorite) {
                            favoriteViewModel.addToFavorite(userId, id)
                        } else {
                            favoriteViewModel.deleteFromFavorite(userId, id)
                        }
                    }
                )
            }
        )
    }
    LaunchedEffect(showDetailWisata) {
        if (showDetailWisataAgain) {
            showDetailWisata = true
            showDetailWisataAgain = false
        }
    }

    if (showAllPopularWisata) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(),
            onDismissRequest = {
                showAllPopularWisata = false
            },
            sheetState = modalAllPopularWisataBottomState,
            windowInsets = WindowInsets(0.dp),
            containerColor = ColorWhite,
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            dragHandle = {
                BottomSheetDefaults.DragHandle(color = ColorSecondary)
            },
            content = {
                val daftarDestinations = (destinationsResult as UiState.Success<DestinationsResponse>).data.data
                val favoriteItems by favoriteViewModel.favoriteItems.collectAsState()
                Scaffold(
                    containerColor = ColorWhite,
                    topBar = {
                        TopBarAllWisata(
                            context = context,
                            onKeywords = {
                                keywords = it
                                if(it.isEmpty()){
                                    fucusManager.clearFocus()
                                }
                            },
                            onShowFilters = {
                                showFilters = it
                                fucusManager.clearFocus()
                            }
                        )
                    }) { paddingValues ->
                    ModalBottomSheetAllPopularWisata(
                        modifier = Modifier
                            .padding(top = paddingValues.calculateTopPadding())
                            .background(ColorWhite),
                        daftarDestinations = daftarDestinations,
                        keywords = keywords,
                        isAscending = isAscending,
                        isFree = isFree,
                        onDetail = {
                            idDetailWisata = it
                            showDetailWisata = true
                        },
                        favoriteItems = favoriteItems,
                        onToggleFavorite = { id ->
                            val isFavorite = favoriteItems.contains(id)
                            if (!isFavorite) {
                                favoriteViewModel.addToFavorite(userId, id)
                            } else {
                                favoriteViewModel.deleteFromFavorite(userId, id)
                            }
                        }
                    )
                }
            }
        )
    }
    LaunchedEffect(showAllPopularWisata) {
        if (showAllPopularWisata) {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = true)
        } else {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = false)
        }
    }

    if (showAllNearbyWisata) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(),
            onDismissRequest = {
                showAllNearbyWisata = false
            },
            sheetState = modalAllNearbyWisataBottomState,
            windowInsets = WindowInsets(0.dp),
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
                            onKeywords = {
                                keywords = it
                                if(it.isEmpty()){
                                    fucusManager.clearFocus()
                                }
                            },
                            onShowFilters = {
                                showFilters = it
                                fucusManager.clearFocus()
                            }
                        )
                    }) { paddingValues ->
                    val daftarDestinations = (destinationsResult as UiState.Success<DestinationsResponse>).data.data
                    val favoriteItems by favoriteViewModel.favoriteItems.collectAsState()
                    ModalBottomSheetAllNearbyWisata(
                        modifier = Modifier
                            .padding(top = paddingValues.calculateTopPadding())
                            .background(ColorWhite),
                        daftarDestinations = daftarDestinations,
                        currentLocation = currentLocation,
                        keywords = keywords,
                        isAscending = isAscending,
                        isFree = isFree,
                        onDetail = {
                            idDetailWisata = it
                            showDetailWisata = true
                        },
                        favoriteItems = favoriteItems,
                        onToggleFavorite = { id ->
                            val isFavorite = favoriteItems.contains(id)
                            if (!isFavorite) {
                                favoriteViewModel.addToFavorite(userId, id)
                            } else {
                                favoriteViewModel.deleteFromFavorite(userId, id)
                            }
                        }
                    )
                }
            }
        )
    }
    LaunchedEffect(showAllNearbyWisata) {
        if (showAllNearbyWisata) {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = true)
        } else {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = false)
        }
    }

    if (showSearchWisata) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(),
            onDismissRequest = {
                showSearchWisata = false
            },
            sheetState = modalSearchBottomState,
            windowInsets = WindowInsets(0.dp),
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
                    val daftarDestinations = (destinationsResult as UiState.Success<DestinationsResponse>).data.data
                    val favoriteItems by favoriteViewModel.favoriteItems.collectAsState()
                    ModalBottomSheetSearchWisata(
                        context = context,
                        modifier = Modifier
                            .padding(top = paddingValues.calculateTopPadding())
                            .background(ColorWhite),
                        showHistorySearch = showHistorySearch,
                        daftarDestinations = daftarDestinations,
                        keywords = keywords,
                        isAscending = isAscending,
                        isFree = isFree,
                        onDetail = { idDetail ->
                            idDetailWisata = idDetail
                            showDetailWisata = true
                        },
                        onKeywords = { word ->
                            keywords = word
                        },
                        favoriteItems = favoriteItems,
                        onToggleFavorite = { id ->
                            val isFavorite = favoriteItems.contains(id)
                            if (!isFavorite) {
                                favoriteViewModel.addToFavorite(userId, id)
                            } else {
                                favoriteViewModel.deleteFromFavorite(userId, id)
                            }
                        }
                    )
                }
            }
        )
    }
    LaunchedEffect(showSearchWisata) {
        if (showSearchWisata) {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = true)
        } else {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = false)
        }
    }

    if (showFavoritWisata) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(),
            onDismissRequest = {
                showFavoritWisata = false
            },
            sheetState = modalFavoritBottomState,
            windowInsets = WindowInsets(0.dp),
            containerColor = ColorWhite,
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            content = {
                Scaffold(
                    containerColor = ColorWhite,
                    topBar = {
                        TopBarScreenModal(
                            title = "Daftar Favorit",
                            onBack = { showValue ->
                                scope.launch { modalFavoritBottomState.hide() }.invokeOnCompletion {
                                    if (!modalFavoritBottomState.isVisible) {
                                        showFavoritWisata = showValue
                                    }
                                }
                            }
                        )
                    }) { paddingValues ->
                    val daftarDestinations = (destinationsResult as UiState.Success<DestinationsResponse>).data.data
                    val favoriteItems by favoriteViewModel.favoriteItems.collectAsState()
                    ModalBottomSheetFavoritWisata(
                        modifier = Modifier
                            .padding(top = paddingValues.calculateTopPadding()),
                        daftarDestinations = daftarDestinations,
                        onDetail = { idDetail ->
                            idDetailWisata = idDetail
                            showDetailWisata = true
                        },
                        favoriteItems = favoriteItems,
                        onToggleFavorite = { id ->
                            val isFavorite = favoriteItems.contains(id)
                            if (!isFavorite) {
                                favoriteViewModel.addToFavorite(userId, id)
                            } else {
                                favoriteViewModel.deleteFromFavorite(userId, id)
                            }
                        }
                    )
                }
            }
        )
    }
    LaunchedEffect(showFavoritWisata) {
        if (showFavoritWisata) {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = true)
        } else {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = false)
        }
    }

    if (showListProvince) {
        ModalBottomSheet(
            modifier = Modifier
                .height(350.dp),
            onDismissRequest = {
                showListProvince = false
            },
            windowInsets = WindowInsets(0.dp),
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
                        onProvince = {
                            isChangeProvince = true
                            firstProvince = it
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
            windowInsets = WindowInsets(0.dp),
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

