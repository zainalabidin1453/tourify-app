package com.tourify.tourifyapp.ui.main.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.data.factory.ViewModelCulinaryFactory
import com.tourify.tourifyapp.data.viewmodels.CulinaryViewModel
import com.tourify.tourifyapp.di.Injection
import com.tourify.tourifyapp.model.CulinaryResponse
import com.tourify.tourifyapp.ui.common.UiState
import com.tourify.tourifyapp.ui.component.BoxEmptyLoad
import com.tourify.tourifyapp.ui.component.BoxErrorLoad
import com.tourify.tourifyapp.ui.component.BoxLoading
import com.tourify.tourifyapp.ui.component.DaftarCulinary
import com.tourify.tourifyapp.ui.component.ModalBottomSheetDetailCulinary
import com.tourify.tourifyapp.ui.component.TextFieldBasicSearch
import com.tourify.tourifyapp.ui.theme.ColorBlue
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorTransparent
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CulinaryScreen(
    context: Context,
    culinaryViewModel: CulinaryViewModel = viewModel(
        factory = ViewModelCulinaryFactory(
            Injection.provideCulinaryRepository()
        )
    )
) {
    val systemUiController = rememberSystemUiController()
    val byProvince by rememberSaveable { mutableStateOf("") }
    var keywords by rememberSaveable { mutableStateOf("") }

    val culinaryResult by culinaryViewModel.uiState.collectAsState(initial = UiState.Loading)
    LaunchedEffect(culinaryViewModel) {
        if (culinaryResult is UiState.Loading) {
            culinaryViewModel.getCulinary()
        }
    }
    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false
        )
        onDispose {}
    }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val modalDetailBottomState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showDetailCulinary by rememberSaveable { mutableStateOf(false) }
    var showDetailCulinaryAgain by rememberSaveable { mutableStateOf(false) }
    var idDetailCulinary by rememberSaveable { mutableIntStateOf(0) }
    val gradientColors = listOf(ColorBlue, ColorPrimary)
    LaunchedEffect(showDetailCulinary) {
        if (showDetailCulinaryAgain) {
            showDetailCulinary = true
            showDetailCulinaryAgain = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorWhite)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            },
        content = {
            when (culinaryResult) {
                is UiState.Loading -> {
                    BoxLoading(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 136.dp)
                            .background(ColorTransparent)
                            .align(Alignment.TopCenter)
                    )
                }

                is UiState.Success -> {
                    val daftarCulinary =
                        (culinaryResult as UiState.Success<CulinaryResponse>).data.data
                    val sortedData = daftarCulinary!!
                        .sortedBy { it.name }

                    val filteredData = sortedData
                        .filter { it.name.contains(keywords, ignoreCase = true) }

                    if (byProvince.isNotEmpty()) {
                        filteredData.filter { it.province.contains(byProvince, ignoreCase = true) }
                    }

                    if (filteredData.isNotEmpty()) {
                        DaftarCulinary(
                            modifier = Modifier
                                .padding(top = 136.dp, start = 12.dp, end = 12.dp)
                                .align(Alignment.TopCenter),
                            daftarCulinary = filteredData
                        ) {
                            idDetailCulinary = it
                            showDetailCulinary = true
                        }
                    } else {
                        BoxEmptyLoad(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 136.dp)
                                .background(ColorTransparent)
                                .align(Alignment.TopCenter),
                            title = "Kata kunci tidak ditemukan.",
                            desc = "Silakan coba kata kunci lain untuk menemukan pilihan kuliner yang sesuai dengan preferensi Anda.",
                        )
                    }
                }

                is UiState.Error -> {
                    BoxErrorLoad(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 136.dp)
                            .background(ColorTransparent)
                            .align(Alignment.TopCenter),
                        type = when ((culinaryResult as UiState.Error).error) {
                            400 -> 2
                            500 -> 2
                            else -> 1
                        }
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 35.dp, bottomEnd = 35.dp))
                    .background(ColorTransparent),
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(bottomStart = 35.dp, bottomEnd = 35.dp))
                            .background(ColorWhite),
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(
                                        RoundedCornerShape(
                                            bottomStart = 35.dp,
                                            bottomEnd = 35.dp
                                        )
                                    )
                                    .background(ColorTransparent),
                                content = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .background(
                                                brush = Brush.horizontalGradient(colors = gradientColors),
                                            ),
                                        content = {
                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        start = 18.dp,
                                                        top = 54.dp,
                                                        end = 18.dp,
                                                        bottom = 39.dp
                                                    ),
                                                text = "Jelajahi Kelezatan Lokal di Sekitar Anda!",
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
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(
                                                RoundedCornerShape(
                                                    bottomStart = 35.dp,
                                                    bottomEnd = 35.dp
                                                )
                                            )
                                            .height(39.dp)
                                            .background(ColorTransparent),
                                    )
                                }
                            )
                        }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .padding(start = 18.dp, end = 18.dp, bottom = 14.dp)
                            .align(Alignment.BottomCenter),
                        content = {
                            TextFieldBasicSearch(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(
                                        6.dp,
                                        RoundedCornerShape(100),
                                        true,
                                        spotColor = TextPrimary
                                    ),
                                keywords = keywords,
                                placeholder = stringResource(R.string.input_keywords),
                                icon = R.drawable.ic_search,
                                iconDescription = R.string.input_keywords,
                                keyboardType = KeyboardType.Text,
                                onTextChanged = { words ->
                                    keywords = words
                                    if (words.isEmpty()) {
                                        focusManager.clearFocus()
                                    }
                                },
                                clip = RoundedCornerShape(100)
                            )
                        }
                    )
                }
            )
        }
    )
    if (showDetailCulinary) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxSize(),
            onDismissRequest = {
                showDetailCulinary = false
            },
            sheetState = modalDetailBottomState,
            windowInsets = WindowInsets(0.dp),
            containerColor = ColorWhite,
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            dragHandle = {},
            content = {
                val daftarCulinary = (culinaryResult as UiState.Success<CulinaryResponse>).data.data
                ModalBottomSheetDetailCulinary(
                    context = context,
                    idDetailCulinary = idDetailCulinary,
                    daftarCulinary = daftarCulinary,
                    onBack = {
                        scope.launch { modalDetailBottomState.hide() }.invokeOnCompletion {
                            if (!modalDetailBottomState.isVisible) {
                                showDetailCulinary = false
                            }
                        }
                    },
                    onShowDetailCulinary = { id ->
                        scope.launch { modalDetailBottomState.hide() }.invokeOnCompletion {
                            if (!modalDetailBottomState.isVisible) {
                                showDetailCulinary = false
                            }
                        }
                        idDetailCulinary = id
                        showDetailCulinaryAgain = true
                    }
                )
            }
        )
    }
}


