package com.tourify.tourifyapp.ui.component

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorWarning
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.modifyNumberFormat

@Composable
fun PopularWisata(
    modifier: Modifier = Modifier,
    categoryWisata: String,
    onDetail: (Int) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        state = lazyListState,
        content = {
            items(5) {
                val spacerModifier = if (it == 0) {
                    Modifier.width(18.dp)
                } else {
                    Modifier.width(12.dp)
                }
                Spacer(modifier = spacerModifier)
                PopularWisataCard(
                    onClick = { id ->
                        onDetail(id)
                    }
                )
                if (it == 4) {
                    Spacer(modifier = Modifier.width(18.dp))
                }
            }
        }
    )
}

@Composable
fun PopularWisataCard(onClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .shadow(8.dp, Shapes.small, true, spotColor = TextPrimary)
            .width(230.dp)
            .background(ColorWhite)
            .clickable { onClick(1) },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .height(116.dp)
                            .padding(6.dp),
                        content = {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("")
                                    .crossfade(true)
                                    .error(R.drawable.error_image)
                                    .build(),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(12.dp)),
                                alignment = Alignment.Center
                            )
                            CircleButtonSmallFavorite(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(6.dp),
                                context = LocalContext.current,
                                title = R.string.add_to_favorite,
                                icon = R.drawable.ic_heart,
                                size = 20.dp,
                                tint = ColorWhite,
                                onClick = {}
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 11.dp, end = 12.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Column(
                                content = {
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 1.dp),
                                        text = "Wisata Name",
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 14.sp,
                                            lineHeight = 14.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        content = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_location),
                                                contentDescription = stringResource(id = R.string.choose_location),
                                                modifier = Modifier
                                                    .size(11.dp),
                                                tint = ColorDanger
                                            )
                                            Spacer(modifier = Modifier.width(1.dp))
                                            Text(
                                                text = "Location, Indonesia",
                                                style = StyleText.copy(
                                                    color = TextPrimary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = 10.sp,
                                                    lineHeight = 16.sp
                                                )
                                            )
                                        }
                                    )
                                }
                            )
                            Column(
                                content = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.End,
                                        content = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_rating),
                                                contentDescription = stringResource(id = R.string.total_rating),
                                                modifier = Modifier
                                                    .height(14.dp)
                                                    .width(13.dp),
                                                tint = ColorWarning
                                            )
                                            Spacer(modifier = Modifier.width(2.dp))
                                            val totalUserRating = modifyNumberFormat("100")
                                            Text(
                                                text = "0.0 ($totalUserRating)",
                                                style = StyleText.copy(
                                                    color = TextPrimary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Normal,
                                                    fontSize = 10.sp,
                                                    lineHeight = 10.sp
                                                )
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PopularWisataPreview() {
    PopularWisata(
        categoryWisata = "",
        onDetail = {}
    )
}