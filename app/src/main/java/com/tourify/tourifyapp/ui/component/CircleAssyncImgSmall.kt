package com.tourify.tourifyapp.ui.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorWhite

@Composable
fun CircleAssyncImgSmall(
    context: Context,
    title: Int,
    img: String,
    size: Dp = 35.dp,
    errorImg : Int = R.drawable.avatar,
    onClick: () -> Unit
){
    Box(
        modifier = Modifier
            .shadow(2.dp, RoundedCornerShape(percent = 100), true)
            .size(size)
            .background(ColorWhite)
            .border(width = 1.dp, color = ColorPrimary, CircleShape)
            .clickable {
                onClick()
            }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(img)
                .crossfade(true)
                .error(errorImg)
                .build(),
            contentDescription = stringResource(id = title),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(size),
            alignment = Alignment.Center
        )
    }
}