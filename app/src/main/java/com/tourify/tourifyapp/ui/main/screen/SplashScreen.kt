package com.tourify.tourifyapp.ui.main.screen

import android.content.Context
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.preference.OnBoardingDataStore
import com.tourify.tourifyapp.route.Routes
import com.tourify.tourifyapp.ui.theme.ColorBlue
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    context: Context,
    navigateTo: (String) -> Unit
) {
    val scale = remember { androidx.compose.animation.core.Animatable(0f) }
    val onBoardingPref = remember { OnBoardingDataStore.getInstance(context) }
    val onBoardingStatus by onBoardingPref.getOnBoardingStatus().collectAsState( initial = false)
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(3000L)
        val start = if (onBoardingStatus) Routes.CheckEmail.routes else Routes.OnBoarding.routes
        navigateTo(start)
    }
    val gradient = Brush.verticalGradient(
        0.5f to ColorBlue,
        1.0f to ColorPrimary,
        startY = 0.0f,
        endY = 1500.0f
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_tourify_fix),
            contentDescription = stringResource(id = R.string.logo),
            modifier = Modifier
                .scale(scale.value)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    val context = LocalContext.current
    SplashScreen(
        context = context,
        navigateTo = {}
    )
}
