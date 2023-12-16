package com.tourify.tourifyapp.ui.main.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.tourify.tourifyapp.utils.isValidPassword

@Composable
fun CreatePasswordScreen(
    context: Context,
    navController: NavController,
    navigateToCheckEmail: () -> Unit,
    navigateToDashboard: () -> Unit
) {
    val scrollState = rememberScrollState()
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmNewPassword by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val email = navBackStackEntry?.arguments?.getString("email")
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(ColorBackground, darkIcons = true)
        onDispose {}
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorWhite)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier
                .padding(start = 18.dp, top = 21.dp, end = 18.dp, bottom = 18.dp),
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
                Spacer(modifier = Modifier.height(50.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        Text(
                            text = stringResource(id = R.string.create_password),
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
                            text = stringResource(id = R.string.privacy_notes),
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
                    CircleButton(
                        context = context,
                        title = R.string.security_info,
                        icon = R.drawable.ic_key,
                        sizeCircle = 25.dp,
                        shadow = 2.dp,
                        isIcon = true,
                        tint = TextSecondary,
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.notice_new_password),
                        style = StyleText.copy(
                            color = TextSecondary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Light,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))
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
                        text = stringResource(id = R.string.save_pass),
                        background = ColorPrimary,
                        contentColor = ColorWhite,
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
