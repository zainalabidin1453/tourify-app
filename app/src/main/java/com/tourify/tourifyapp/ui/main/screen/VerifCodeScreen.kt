package com.tourify.tourifyapp.ui.main.screen

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.data.factory.ViewModelVerificationCodeFactory
import com.tourify.tourifyapp.data.viewmodels.VerificationCodeViewModel
import com.tourify.tourifyapp.di.Injection
import com.tourify.tourifyapp.ui.common.UiState
import com.tourify.tourifyapp.ui.component.ButtonPrimary
import com.tourify.tourifyapp.ui.component.CircleButton
import com.tourify.tourifyapp.ui.component.LoadingButtonPrimary
import com.tourify.tourifyapp.ui.component.TextFieldCodeVerif
import com.tourify.tourifyapp.ui.theme.ColorBackground
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.Toasty

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerifCodeScreen(
    context: Context,
    navController: NavController,
    navigateToCheckEmail: () -> Unit,
    navigateToCreatePassword: (String) -> Unit,
    verificationCodeViewModel: VerificationCodeViewModel = viewModel(
        factory = ViewModelVerificationCodeFactory(
            Injection.provideVerificationCodeRepository()
        )
    )
) {
    val scrollState = rememberScrollState()
    var codeOTP by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val email = navBackStackEntry?.arguments?.getString("email")
    val interactionSource = remember { MutableInteractionSource() }
    val systemUiController = rememberSystemUiController()
    val verificationCodeResult by verificationCodeViewModel.uiState.collectAsState(initial = UiState.Loading)
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = true)
        onDispose {}
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
            .verticalScroll(scrollState)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(start = 18.dp, top = 36.dp, end = 18.dp, bottom = 18.dp),
            content = {
                CircleButton(
                    context = context,
                    title = R.string.back,
                    icon = R.drawable.ic_arrow_back,
                    sizeCircle = 40.dp,
                    sizeIcon = 26.dp,
                    shadow = 4.dp,
                    isIcon = true,
                    tint = TextPrimary,
                    onClick = { navigateToCheckEmail() }
                )
                Spacer(modifier = Modifier.height(40.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        Text(
                            text = "Kode Verifikasi",
                            style = StyleText.copy(
                                color = TextPrimary,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                lineHeight = 22.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Masukkan kode verifikasi yang sudah dikirim melalui email Anda",
                            style = StyleText.copy(
                                color = TextPrimary,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                lineHeight = 22.sp,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                )
            }
        )
        Column(
            modifier = Modifier
                .padding(18.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                Image(
                    painter = painterResource(id = R.drawable.ic_codeotp),
                    contentDescription = "Icon Verifikasi Kode",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
            }
        )
        Column(
            modifier = Modifier
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                TextFieldCodeVerif(
                    modifier = Modifier
                        .bringIntoViewRequester(bringIntoViewRequester),
                    otpTextChange = { newCode ->
                        codeOTP = newCode
                    },
                    onSend = {
                        focusManager.clearFocus()
                        if (codeOTP.isNotEmpty()) {
                            isLoading = true
                            verificationCodeViewModel.verificationCode(email!!, codeOTP)
                        } else {
                            isLoading = false
                            Toasty.show(
                                context,
                                R.string.invalid_code_otp
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(77.dp))
                Row(
                    content = {
                        Text(
                            text = "Belum menerima kode?",
                            style = StyleText.copy(
                                color = TextSecondary,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                lineHeight = 14.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            modifier = Modifier
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                    onClick = {}
                                ),
                            text = "Kirim ulang",
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
                Spacer(modifier = Modifier.height(14.dp))
                if (isLoading) {
                    LoadingButtonPrimary(
                        onClick = {
                            Toasty.show(
                                context,
                                R.string.loading
                            )
                        }
                    )
                    when (verificationCodeResult) {
                        is UiState.Loading -> {}
                        is UiState.Success -> {
                            isLoading = false
                            navigateToCreatePassword(email!!)
                        }

                        is UiState.Error -> {
                            isLoading = false
                            val resultCheck = verificationCodeResult as UiState.Error
                            when (resultCheck.error) {
                                400 -> Toasty.show(
                                    context,
                                    R.string.incorrect_otp_code
                                )

                                else -> Toasty.show(
                                    context,
                                    R.string.happen_wrong
                                )
                            }
                        }
                    }
                } else {
                    ButtonPrimary(
                        text = "Verifikasi kode",
                        background = ColorPrimary,
                        contentColor = ColorWhite,
                        enabled = true,
                        onClick = {
                            focusManager.clearFocus()
                            if (codeOTP.isNotEmpty()) {
                                isLoading = true
                                verificationCodeViewModel.verificationCode(email!!, codeOTP)
                            } else {
                                isLoading = false
                                Toasty.show(
                                    context,
                                    R.string.invalid_code_otp
                                )
                            }
                        }
                    )
                }
            }
        )
    }
}


