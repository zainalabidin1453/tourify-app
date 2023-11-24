package com.tourify.tourifyapp.ui.component

import android.content.Context
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorWhite

@Composable
fun CircleButtonLarge(
    context: Context,
    title: Int,
    icon: Int,
    isIcon: Boolean = false,
    tint: Color = ColorPrimary,
    onClick: () -> Unit
){
    Box(
        modifier = Modifier
            .shadow(6.dp, RoundedCornerShape(percent = 100))
            .clip(RoundedCornerShape(percent = 100))
            .size(50.dp)
            .background(ColorWhite)
            .clickable {
                onClick()
            }
    ) {
        if (isIcon){
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = title),
                modifier = Modifier
                    .padding(8.dp)
                    .size(28.dp)
                    .align(Alignment.Center),
                tint = tint
            )
        } else {
            Image(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = title),
                modifier = Modifier
                    .padding(8.dp)
                    .size(28.dp)
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop,
            )
        }
    }
}