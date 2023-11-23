package com.tourify.tourifyapp.ui.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.transition.fractionTransition

@Preview
@Composable
fun Loading(
    modifier: Modifier = Modifier,
    durationMillis: Int = 1400,
    delayBetweenDotsMillis: Int = 160,
    size: DpSize = DpSize(56.dp, 56.dp),
    color: Color = ColorWhite,
    shape: Shape = CircleShape
) {
    val transition = rememberInfiniteTransition(label = "")

    val sizeMultiplier1 = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 1f,
        fraction = 1,
        durationMillis = durationMillis / 2,
        repeatMode = RepeatMode.Reverse
    )
    val sizeMultiplier2 = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 1f,
        fraction = 1,
        durationMillis = durationMillis / 2,
        offsetMillis = delayBetweenDotsMillis,
        repeatMode = RepeatMode.Reverse
    )
    val sizeMultiplier3 = transition.fractionTransition(
        initialValue = 0f,
        targetValue = 1f,
        fraction = 1,
        durationMillis = durationMillis / 2,
        offsetMillis = delayBetweenDotsMillis * 2,
        repeatMode = RepeatMode.Reverse
    )

    Row(
        modifier = modifier.size(size),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(size * 3 / 11), contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier.size(size * 3 / 11 * sizeMultiplier1.value),
                shape = shape,
                color = color
            ) {}
        }
        Spacer(modifier = Modifier.width(size.width / 1 / 11))
        Box(modifier = Modifier.size(size * 3 / 11), contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier.size(size * 3 / 11 * sizeMultiplier2.value),
                shape = shape,
                color = color
            ) {}
        }
        Spacer(modifier = Modifier.width(size.width / 1 / 11))
        Box(modifier = Modifier.size(size * 3 / 11), contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier.size(size * 3 / 11 * sizeMultiplier3.value),
                shape = shape,
                color = color
            ) {}
        }
    }
}