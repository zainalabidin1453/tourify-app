package com.tourify.tourifyapp.ui.main.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.component.ButtonPrimary
import com.tourify.tourifyapp.ui.component.CircleButtonLarge
import com.tourify.tourifyapp.ui.component.LoadingButtonPrimary
import com.tourify.tourifyapp.ui.component.TextFieldPrimary
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.Toasty
import com.tourify.tourifyapp.utils.isValidEmail

@Composable
fun CheckEmailScreen(
    context: Context,
    navController: NavController,
    navigateToVerifCode: (String) -> Unit,
    navigateToEnterPassword: (String) -> Unit
) {
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(ColorPrimary)
        onDispose {}
    }

    val scrollState = rememberScrollState()
    var email by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorWhite)
            .verticalScroll(scrollState)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            painter = painterResource(id = R.drawable.banner_login),
            contentDescription = stringResource(id = R.string.app_name),
            contentScale = ContentScale.Crop
        )
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp)
                .clip(RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp)),
            colors = CardDefaults.cardColors(
                containerColor = ColorWhite
            ),
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(21.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Login/Daftar",
                        style = StyleText.copy(
                            color = TextPrimary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            lineHeight = 12.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Silahkan masuk ke Akun Anda",
                        style = StyleText.copy(
                            color = TextPrimary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            lineHeight = 14.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(21.dp))
                    TextFieldPrimary(
                        placeholder = stringResource(R.string.email_address),
                        icon = R.drawable.ic_mail,
                        iconDescription = R.string.email_address,
                        keyboardType = KeyboardType.Email,
                        onTextChanged = { newEmail ->
                            email = newEmail
                            isError =  false
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
                        Card(
                            modifier = Modifier
                                .size(25.dp)
                                .shadow(4.dp, RoundedCornerShape(percent = 100))
                                .clip(RoundedCornerShape(percent = 100)),
                            colors = CardDefaults.cardColors(containerColor = ColorWhite)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_key),
                                modifier = Modifier.padding(4.dp),
                                contentDescription = "Ikon kunci",
                                tint = TextSecondary
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.notif_security),
                            style = StyleText.copy(
                                color = TextPrimary,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Normal,
                                fontSize = 10.sp,
                                lineHeight = 16.sp
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
                            color = ColorWhite,
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
                    Text(
                        text = "Dengan mendaftar, Saya menyetujui Syarat & Ketentuan dan Kebijakan Privasi Tourify",
                        style = StyleText.copy(
                            color = TextSecondary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            textAlign = TextAlign.Center
                        ),
                    )
                }
            }
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
            CircleButtonLarge(
                context = context,
                title = R.string.login_with_google,
                icon = R.drawable.ic_google,
                onClick = {
                    Toasty.show(context, R.string.in_development)
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            CircleButtonLarge(
                context = context,
                title = R.string.login_with_facebook,
                icon = R.drawable.ic_facebook,
                onClick = {
                    Toasty.show(context, R.string.in_development)
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            CircleButtonLarge(
                context = context,
                title = R.string.login_with_tiktok,
                icon = R.drawable.ic_tiktok,
                onClick = {
                    Toasty.show(context, R.string.in_development)
                }
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

