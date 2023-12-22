package com.tourify.tourifyapp.ui.main.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.preference.OnBoardingDataStore
import com.tourify.tourifyapp.ui.component.ButtonPrimaryWithIcon
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun OnBoardingScreen(
    context: Context,
    navigateTo: () -> Unit,
) {
    var numberScreen by rememberSaveable { mutableIntStateOf(0) }
    val interactionSource = remember { MutableInteractionSource() }
    val onBoardingPref = remember { OnBoardingDataStore.getInstance(context) }
    val onBoardingValue = listOf(
        OnBoardingItem(
            R.drawable.onboarding_2,
            stringResource(id = R.string.onboarding_title_1),
            stringResource(id = R.string.onboarding_teks_1),
        ),
        OnBoardingItem(
            R.drawable.onboarding_1,
            stringResource(id = R.string.onboarding_title_2),
            stringResource(id = R.string.onboarding_teks_2),
        ),
        OnBoardingItem(
            R.drawable.onboarding_3,
            stringResource(id = R.string.onboarding_title_3),
            stringResource(id = R.string.onboarding_teks_3),
        ),
        OnBoardingItem(
            R.drawable.onboarding_4,
            stringResource(id = R.string.onboarding_title_4),
            stringResource(id = R.string.onboarding_teks_4),
        )
    )
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = false)
        onDispose {}
    }
    Scaffold(
        containerColor = ColorWhite,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(29.dp),
                horizontalArrangement = if (numberScreen == 3) Arrangement.End else Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    if (numberScreen != 3) {
                        Row(
                            content = {
                                repeat(4) {
                                    Box(
                                        modifier = Modifier
                                            .padding(end = 4.dp)
                                            .size(if (it == numberScreen) 10.dp else 8.dp)
                                            .clip(RoundedCornerShape(percent = 100))
                                            .background(if (it == numberScreen) ColorPrimary else ColorSecondary)
                                            .align(Alignment.CenterVertically)
                                            .clickable { numberScreen = it },
                                    )
                                }
                            }
                        )
                    }
                    if (numberScreen == 3) {
                        LaunchedEffect(Unit) {
                            onBoardingPref.saveOnBoardingStatus(true)
                        }
                        ButtonPrimaryWithIcon(
                            text = stringResource(id = R.string.start),
                            background = ColorPrimary,
                            contentColor = ColorWhite,
                            enabled = true,
                            onClick = {
                                if (numberScreen == 3) {
                                    navigateTo()
                                } else {
                                    numberScreen += 1
                                }
                            }
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_right),
                            contentDescription = stringResource(id = R.string.further),
                            modifier = Modifier
                                .size(30.dp)
                                .align(Alignment.CenterVertically)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null,
                                    onClick = {
                                        if (numberScreen == 3) {
                                            navigateTo()
                                        } else {
                                            numberScreen += 1
                                        }
                                    }
                                ),
                            tint = ColorPrimary
                        )
                    }
                }
            )
        }
    ){ paddingValues ->  
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()),
            content = {
                OnBoardingContent(
                    content = onBoardingValue[numberScreen]
                )
            }
        )
    }
}

@Composable
fun OnBoardingContent(
    content: OnBoardingItem
) {
    Box(
        content = {
            Image(
                painter = painterResource(id = content.imageRes),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .align(Alignment.Center)
                    .shadow(
                        25.dp,
                        RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp),
                        true,
                        spotColor = TextPrimary
                    )
                    .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
                contentScale = ContentScale.Crop,
            )
        }
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 29.dp, top = 30.dp, end = 29.dp),
        content = {
            Text(
                text = content.title,
                style = StyleText.copy(
                    color = TextPrimary,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    lineHeight = 40.sp
                )
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = content.description,
                style = StyleText.copy(
                    color = TextPrimary,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 24.sp
                )
            )
        }
    )
}
data class OnBoardingItem(
    val imageRes: Int,
    val title: String,
    val description: String
)

@Preview(showBackground = true)
@Composable
fun OnBoardingScreenPreview() {
    val context = LocalContext.current
    OnBoardingScreen(
        context = context,
        navigateTo = {}
    )
}
