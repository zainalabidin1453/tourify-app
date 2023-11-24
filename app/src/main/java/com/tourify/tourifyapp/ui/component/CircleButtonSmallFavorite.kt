package com.tourify.tourifyapp.ui.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.TextPrimary

@Composable
fun CircleButtonSmallFavorite(
    modifier: Modifier = Modifier,
    context: Context,
    title: Int,
    icon: Int,
    size: Dp,
    tint: Color = TextPrimary,
    color: Color = ColorWhite.copy(alpha = 0.3f),
    onClick: () -> Unit
){
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(percent = 100))
            .background(color)
            .clickable {
                onClick()
            }
    ) {
        Icon(
            modifier = Modifier
                .padding(top = 1.dp)
                .size(14.dp)
                .align(Alignment.Center),
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = title),
            tint = tint
        )
    }
}