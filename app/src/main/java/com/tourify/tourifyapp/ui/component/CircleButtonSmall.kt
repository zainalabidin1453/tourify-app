package com.tourify.tourifyapp.ui.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.TextPrimary

@Composable
fun CircleButtonSmall(
    modifier: Modifier = Modifier,
    context: Context,
    title: Int,
    icon: Int,
    size: Dp = 30.dp,
    sizeIcon: Dp = 22.dp,
    shadow: Dp = 4.dp,
    isIcon: Boolean = false,
    tint: Color = ColorPrimary,
    color: Color = ColorWhite,
    onClick: () -> Unit
){
    Box(
        modifier = modifier
            .shadow(shadow, RoundedCornerShape(percent = 100), true, spotColor = TextPrimary)
            .size(size)
            .background(color)
            .clickable {
                onClick()
            }
    ) {
        if (isIcon) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = title),
                modifier = Modifier
                    .padding(3.dp)
                    .size(sizeIcon)
                    .align(Alignment.Center),
                tint = tint
            )
        } else {
            Image(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = title),
                modifier = Modifier
                    .padding(3.dp)
                    .size(sizeIcon)
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop,
            )
        }
    }
}