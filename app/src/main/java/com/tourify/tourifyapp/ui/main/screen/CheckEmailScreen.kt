package com.tourify.tourifyapp.ui.main.screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.component.ButtonPrimary
import com.tourify.tourifyapp.ui.component.CircleButton
import com.tourify.tourifyapp.ui.component.LoadingButtonPrimary
import com.tourify.tourifyapp.ui.component.TextField
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.Toasty
import com.tourify.tourifyapp.utils.isValidEmail

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CheckEmailScreen(
    context: Context,
    navController: NavController,
    navigateToVerifCode: (String) -> Unit,
    navigateToEnterPassword: (String) -> Unit
) {
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(ColorWhite, darkIcons = true)
        onDispose {}
    }
    var email by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets(0.dp)),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
                        true, spotColor = ColorSecondary)
                    .clip(RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp))
                    .background(ColorWhite),
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.73f)
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                content = {
                                    Text(
                                        text = "Login/Daftar",
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 22.sp,
                                            lineHeight = 22.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "Silahkan masuk ke akun Anda",
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 14.sp,
                                            lineHeight = 14.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(21.dp))
                                    TextField(
                                        placeholder = stringResource(R.string.email_address),
                                        icon = R.drawable.ic_mail,
                                        iconDescription = R.string.email_address,
                                        keyboardType = KeyboardType.Email,
                                        onTextChanged = { newEmail ->
                                            email = newEmail
                                            isError = false
                                        },
                                        isError = isError
                                    )
                                    Spacer(modifier = Modifier.height(30.dp))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        CircleButton(
                                            context = context,
                                            title = R.string.security_info,
                                            icon = R.drawable.ic_key,
                                            sizeCircle = 25.dp,
                                            shadow = 5.dp,
                                            isIcon = true,
                                            tint = TextSecondary,
                                            onClick = {}
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = stringResource(id = R.string.notif_security),
                                            style = StyleText.copy(
                                                color = TextSecondary,
                                                fontFamily = fonts,
                                                fontWeight = FontWeight.Light,
                                                fontSize = 11.sp,
                                                lineHeight = 15.sp
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(21.dp))
                                    if (isLoading) {
                                        LoadingButtonPrimary(
                                            onClick = {
                                                Toasty.show(
                                                    context,
                                                    R.string.loading
                                                )
                                            }
                                        )
                                        navigateToEnterPassword(email)
                                    } else {
                                        ButtonPrimary(
                                            text = "Lanjutkan",
                                            background = ColorPrimary,
                                            contentColor = ColorWhite,
                                            enabled = true,
                                            onClick = {
                                                val emailValue = email
                                                if (emailValue.isNotEmpty() && isValidEmail(emailValue)) {
                                                    isLoading = true
                                                } else {
                                                    isError = true
                                                    isLoading = false
                                                    Toasty.show(
                                                        context,
                                                        R.string.invalid_email
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                content = {
                                    Spacer(modifier = Modifier.height(30.dp))
                                    Text(
                                        text = "atau login/daftar dengan",
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 14.sp,
                                            lineHeight = 14.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(30.dp))
                                    AuthSocialMedia()
                                    Spacer(modifier = Modifier.height(30.dp))
                                }
                            )
                            Text(
                                text = "Dengan mendaftar, Saya menyetujui Syarat & Ketentuan dan Kebijakan Privasi Tourify",
                                style = StyleText.copy(
                                    color = TextSecondary,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Light,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    )
                }
            )
        }
    ){
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.banner_welcome),
            contentDescription = stringResource(id = R.string.app_name),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun AuthSocialMedia() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val context = LocalContext.current
            CircleButton(
                context = context,
                title = R.string.login_with_google,
                icon = R.drawable.ic_google,
                sizeCircle = 45.dp,
                sizeIcon = 26.dp,
                shadow = 6.dp,
                onClick = { Toasty.show(context, R.string.in_development) }
            )
            Spacer(modifier = Modifier.width(10.dp))
            CircleButton(
                context = context,
                title = R.string.login_with_facebook,
                icon = R.drawable.ic_facebook,
                sizeCircle = 45.dp,
                sizeIcon = 26.dp,
                shadow = 6.dp,
                onClick = { Toasty.show(context, R.string.in_development) }
            )
            Spacer(modifier = Modifier.width(10.dp))
            CircleButton(
                context = context,
                title = R.string.login_with_appleid,
                icon = R.drawable.ic_apple,
                sizeCircle = 45.dp,
                sizeIcon = 26.dp,
                shadow = 6.dp,
                onClick = { Toasty.show(context, R.string.in_development) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckEmailScreenPreview() {
    val context = LocalContext.current
    CheckEmailScreen(
        context = context,
        navController = NavController(context),
        navigateToVerifCode = {},
        navigateToEnterPassword = {}
    )
}

