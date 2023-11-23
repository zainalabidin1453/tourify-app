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
import androidx.compose.ui.text.input.TextFieldValue
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
import com.tourify.tourifyapp.ui.component.TextFieldPrimary
import com.tourify.tourifyapp.ui.theme.ColorBackground
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.Toasty
import com.tourify.tourifyapp.utils.isValidPassword

@Composable
fun CreatePasswordScreen(
    context: Context,
    navController: NavController,
    navigateToCheckEmail: () -> Unit,
    navigateToDashboard: () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(ColorBackground, darkIcons = true)
        onDispose {}
    }
    val scrollState = rememberScrollState()
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmNewPassword by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val email = navBackStackEntry?.arguments?.getString("email")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Row(
            modifier = Modifier
                .padding(start = 21.dp, top = 30.dp, end = 21.dp, bottom = 21.dp),
            content = {
                Box(
                    modifier = Modifier
                        .shadow(4.dp, RoundedCornerShape(percent = 100))
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
                    text = "Buat Kata Sandi",
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
                    text = "Pertahankan privasi anda: buat kata sandi yang sulit ditembus!",
                    style = StyleText.copy(
                        color = TextPrimary,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(21.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_password),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(21.dp))
                TextFieldPassword(
                    placeholder = stringResource(R.string.new_password),
                    iconDescription = R.string.new_password,
                    onTextChanged = { passValue ->
                        newPassword = passValue
                        isError =  false
                    },
                    isError = isError
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextFieldPassword(
                    placeholder = stringResource(R.string.confirm_password),
                    iconDescription = R.string.confirm_password,
                    onTextChanged = { confirmValue ->
                        confirmNewPassword = confirmValue
                        isError =  false
                    },
                    isError = isError
                )
                Spacer(modifier = Modifier.height(12.dp))
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
                        text = stringResource(id = R.string.notice_new_password),
                        style = StyleText.copy(
                            color = TextSecondary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 10.sp,
                            lineHeight = 16.sp
                        )
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .padding(start = 21.dp, top = 21.dp, end = 21.dp, bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
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
                        text = "Simpan",
                        background = ColorPrimary,
                        color = ColorWhite,
                        enabled = true,
                        onClick = {
                            val newPasswordValue = isValidPassword(newPassword)
                            val confirmNewPasswordValue = isValidPassword(confirmNewPassword)
                            if (newPasswordValue && confirmNewPasswordValue && newPassword == confirmNewPassword) {
                                isLoading = true
                            } else if (newPassword != confirmNewPassword) {
                                isError = true
                                isLoading = false
                                Toasty.show(
                                    context,
                                    R.string.incorrect_password
                                )
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

@Preview(showBackground = true)
@Composable
fun CreatePasswordScreenPreview() {
    val context = LocalContext.current
    CreatePasswordScreen(
        context = context,
        navController = NavController(context),
        navigateToCheckEmail = {},
        navigateToDashboard = {}
    )
}


