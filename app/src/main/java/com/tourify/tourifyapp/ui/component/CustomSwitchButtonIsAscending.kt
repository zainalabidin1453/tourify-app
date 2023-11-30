package com.tourify.tourifyapp.ui.component

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWhite

@Composable
fun CustomSwitchButtonIsAscending(
    isAscending: Boolean,
    size: Dp = 26.dp,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
    onClick: (Boolean) -> Unit
) {
    val offset by animateDpAsState(
        targetValue = if (isAscending) size else 0.dp,
        animationSpec = animationSpec,
        label = "isAscending"
    )
    val bullColor = if (isAscending) ColorPrimary else ColorSecondary
    Box(
        modifier = Modifier
            .width(size * 2)
            .height(size)
            .clip(shape = RoundedCornerShape(percent = 100))
            .border(1.dp, ColorSecondary, RoundedCornerShape(percent = 100))
            .clickable { onClick(!isAscending) }
            .background(ColorWhite),
        content = {
            Box(
                modifier = Modifier
                    .size(size)
                    .offset(x = offset)
                    .padding(3.dp)
                    .clip(shape = RoundedCornerShape(percent = 100))
                    .background(bullColor),
                content = {}
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CustomSwitchButtonIsAscendingPreview() {
    CustomSwitchButtonIsAscending(
        isAscending = false,
        onClick = {}
    )
}