package com.tourify.tourifyapp.ui.main.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.component.ButtonPrimary
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

@Composable
fun EnterPasswordScreen(
    context: Context,
    navController: NavController,
    navigateToCheckEmail: () -> Unit,
    navigateToDashboard: () -> Unit,
    navigateToForgotPassword: (String) -> Unit
) {
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(ColorWhite, darkIcons = true)
        onDispose {}
    }
    val scrollState = rememberScrollState()
    var password by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val email = navBackStackEntry?.arguments?.getString("email")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorWhite)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Row(
            modifier = Modifier
                .padding(start = 21.dp, top = 30.dp, end = 21.dp, bottom = 21.dp),
            content = {
                Box(
                    modifier = Modifier
                        .shadow(4.dp, RoundedCornerShape(percent = 100), true, spotColor = TextPrimary)
                        .clip(RoundedCornerShape(percent = 100))
                        .size(40.dp)
                        .background(ColorWhite)
                        .clickable {
                            navigateToCheckEmail()
                        },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = stringResource(id = R.string.back),
                            modifier = Modifier
                                .padding(4.dp)
                                .size(26.dp)
                                .align(Alignment.Center),
                            tint = TextPrimary
                        )
                    }
                )
            }
        )
        Column(
            modifier = Modifier
                .padding(start = 21.dp, end = 21.dp, bottom = 21.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                Text(
                    text = "Kata Sandi",
                    style = StyleText.copy(
                        color = TextPrimary,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Bold,
                        fontSize = 34.sp,
                        lineHeight = 34.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Rahasia terjaga: masukkan kata sandi tak terlupakan Anda!",
                    style = StyleText.copy(
                        color = TextPrimary,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(30.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_password),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(30.dp))
                TextFieldPassword(
                    placeholder = stringResource(R.string.new_password),
                    iconDescription = R.string.new_password,
                    onTextChanged = { passValue ->
                        password = passValue
                        isError =  false
                    },
                    isError = isError
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
        )
        Column(
            modifier = Modifier
                .padding(start = 21.dp, top = 21.dp, end = 21.dp, bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
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
                                .clickable { navigateToForgotPassword(email!!) },
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
                    navigateToDashboard()
                } else {
                    ButtonPrimary(
                        text = "Masuk",
                        background = ColorPrimary,
                        color = ColorWhite,
                        enabled = true,
                        onClick = {
                            if (password.isNotEmpty()) {
                                isLoading = true
                            } else {
                                isError =  true
                                isLoading = false
                                Toasty.show(
                                    context,
                                    R.string.wrong_password
                                )
                            }
                        }
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EnterPasswordScreenPreview() {
    val context = LocalContext.current
    EnterPasswordScreen(
        context = context,
        navController = NavController(context),
        navigateToCheckEmail = {},
        navigateToDashboard = {},
        navigateToForgotPassword = {}
    )
}


