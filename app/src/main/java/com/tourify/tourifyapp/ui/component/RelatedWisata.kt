package com.tourify.tourifyapp.ui.component

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
import androidx.compose.foundation.lazy.items
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
import com.tourify.tourifyapp.model.DataDestinationsResponse
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorWarning
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.modifyNumberFormat

@Composable
fun RelatedWisata(
    modifier: Modifier = Modifier,
    daftarDestinations: List<DataDestinationsResponse>,
    favoriteItems: List<Int>,
    onDetail: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit = {},
) {
    val lazyListState = rememberLazyListState()
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        state = lazyListState,
        content = {
            items(daftarDestinations, key = { it.id }) { item ->
                val itemIndex = daftarDestinations.indexOf(item)
                val spacerModifier = if (itemIndex == 0) {
                    Modifier.width(18.dp)
                } else {
                    Modifier.width(12.dp)
                }
                val isFavorite = favoriteItems.contains(item.id)
                Spacer(modifier = spacerModifier)
                RelatedWisataCard(
                    item = item,
                    isFavorite = isFavorite,
                    onClick = {
                        onDetail(item.id)
                    },
                    onToggleFavorite = {
                        onToggleFavorite(item.id)
                    }
                )
                if (itemIndex == daftarDestinations.size - 1) {
                    Spacer(modifier = Modifier.width(18.dp))
                }
            }
        }
    )
}

@Composable
fun RelatedWisataCard(
    item: DataDestinationsResponse,
    onClick: () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .clip(Shapes.small)
            .width(230.dp)
            .clickable { onClick() },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .height(120.dp),
                        content = {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(item.photo)
                                    .crossfade(true)
                                    .error(R.drawable.error_image)
                                    .build(),
                                contentDescription = item.name,
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
                                title = if (isFavorite) R.string.remove_from_favorite else R.string.add_to_favorite,
                                icon = if (isFavorite) R.drawable.ic_heart_fill else R.drawable.ic_heart,
                                size = 23.dp,
                                tint = if (isFavorite) ColorDanger else ColorWhite,
                                onClick = { onToggleFavorite() }
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .wrapContentHeight(),
                                content = {
                                    val destinationsName = if (item.name.length > 18) {
                                        "${item.name.substring(0, 15)}..."
                                    } else {
                                        item.name
                                    }
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 1.dp),
                                        text = destinationsName,
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 14.sp,
                                            lineHeight = 14.sp
                                        ),
                                        maxLines = 1
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
                                                text = "${item.province}, Indonesia",
                                                style = StyleText.copy(
                                                    color = TextPrimary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = 10.sp,
                                                    lineHeight = 14.sp
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
                                                    .height(15.dp)
                                                    .width(14.dp),
                                                tint = ColorWarning
                                            )
                                            Spacer(modifier = Modifier.width(2.dp))
                                            val totalUserRating = modifyNumberFormat("0")
                                            Text(
                                                text = "0.0 ($totalUserRating)",
                                                style = StyleText.copy(
                                                    color = TextPrimary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Normal,
                                                    fontSize = 11.sp,
                                                    lineHeight = 11.sp
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