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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.data.factory.ViewModelLoginFactory
import com.tourify.tourifyapp.data.viewmodels.LoginViewModel
import com.tourify.tourifyapp.di.Injection
import com.tourify.tourifyapp.preference.LoginDataStore
import com.tourify.tourifyapp.ui.common.UiState
import com.tourify.tourifyapp.ui.component.ButtonPrimary
import com.tourify.tourifyapp.ui.component.CircleButton
import com.tourify.tourifyapp.ui.component.LoadingButtonPrimary
import com.tourify.tourifyapp.ui.component.TextFieldPassword
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
fun EnterPasswordScreen(
    context: Context,
    navController: NavController,
    navigateToCheckEmail: () -> Unit,
    navigateToDashboard: () -> Unit,
    navigateToForgotPassword: (String) -> Unit,
    loginViewModel: LoginViewModel = viewModel(
        factory = ViewModelLoginFactory(
            Injection.provideLoginRepository(),
            LoginDataStore.getInstance(context)
        )
    )
) {
    val scrollState = rememberScrollState()
    var password by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val email = navBackStackEntry?.arguments?.getString("email")
    val interactionSource = remember { MutableInteractionSource() }
    val systemUiController = rememberSystemUiController()
    val loginResult by loginViewModel.uiState.collectAsState(initial = UiState.Loading)
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
                            text = "Kata Sandi",
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
                            text = "Rahasia terjaga: masukkan kata sandi akun Anda untuk melanjutkan.",
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
                    painter = painterResource(id = R.drawable.ic_password),
                    contentDescription = "",
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
                TextFieldPassword(
                    modifier = Modifier
                        .bringIntoViewRequester(bringIntoViewRequester),
                    placeholder = stringResource(R.string.new_password),
                    iconDescription = R.string.new_password,
                    onTextChanged = { passValue ->
                        password = passValue
                        isError = false
                    },
                    isError = isError,
                    isLastField = true,
                    onSend = {
                        focusManager.clearFocus()
                        if (password.isNotEmpty()) {
                            isLoading = true
                            loginViewModel.login(email!!, password)
                        } else {
                            isError = true
                            isLoading = false
                            Toasty.show(
                                context,
                                R.string.invalid_password
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(77.dp))
                Row(
                    content = {
                        Text(
                            text = "Lupa Kata Sandi?",
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
                                    onClick = { navigateToForgotPassword(email!!) }
                                ),
                            text = "Reset",
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
                    when (loginResult) {
                        is UiState.Loading -> {}
                        is UiState.Success -> {
                            isLoading = false
                            navigateToDashboard()
                        }

                        is UiState.Error -> {
                            isLoading = false
                            val resultCheck = loginResult as UiState.Error
                            when (resultCheck.error) {
                                400 -> Toasty.show(
                                    context,
                                    R.string.wrong_password
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
                        text = "Masuk",
                        background = ColorPrimary,
                        contentColor = ColorWhite,
                        enabled = true,
                        onClick = {
                            focusManager.clearFocus()
                            if (password.isNotEmpty()) {
                                isLoading = true
                                loginViewModel.login(email!!, password)
                            } else {
                                isError = true
                                isLoading = false
                                Toasty.show(
                                    context,
                                    R.string.invalid_password
                                )
                            }
                        }
                    )
                }
            }
        )
    }
}


