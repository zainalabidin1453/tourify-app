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
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.model.DataDestinationsResponse
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWarning
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.cutLocation
import com.tourify.tourifyapp.utils.modifyNumberFormat

@Composable
fun CardWisataMaxWidth(
    item: DataDestinationsResponse,
    onClick: () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, Shapes.small, true, spotColor = TextPrimary)
            .background(ColorWhite)
            .clickable(
                onClick = {
                    onClick()
                }
            ),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .padding(6.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .align(Alignment.CenterVertically),
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
                                    .fillMaxSize(),
                                alignment = Alignment.Center
                            )
                        }
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        content = {
                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start  = 1.dp, end = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                content = {
                                    Column(
                                        content = {
                                            val destinationsName = if (item.name.length > 22) {
                                                "${item.name.substring(0, 19)}..."
                                            } else {
                                                item.name
                                            }
                                            Text(
                                                text = destinationsName,
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
                                                    val location = cutLocation("${item.regency}, ${item.province}")
                                                    Text(
                                                        text = location,
                                                        style = StyleText.copy(
                                                            color = TextPrimary,
                                                            fontFamily = fonts,
                                                            fontWeight = FontWeight.Light,
                                                            fontSize = 10.sp,
                                                            lineHeight = 10.sp
                                                        )
                                                    )
                                                }
                                            )
                                        }
                                    )
                                    CircleButtonSmallFavorite(
                                        context = LocalContext.current,
                                        title = if (isFavorite) R.string.remove_from_favorite else R.string.add_to_favorite,
                                        icon = if (isFavorite) R.drawable.ic_heart_fill else R.drawable.ic_heart,
                                        size = 25.dp,
                                        tint = if (isFavorite) ColorDanger else TextPrimary,
                                        color = ColorSecondary,
                                        onClick = { onToggleFavorite() }
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start  = 3.dp, end = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Box(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .wrapContentHeight()
                                            .clip(Shapes.small)
                                            .background(ColorSecondary),
                                        content = {
                                            Text(
                                                modifier = Modifier
                                                    .padding(start = 4.dp, end = 4.dp, top = 1.dp, bottom = 1.dp),
                                                text = item.type,
                                                style = StyleText.copy(
                                                    color = TextPrimary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = 8.sp,
                                                    lineHeight = 8.sp,
                                                    textAlign = TextAlign.Center
                                                )
                                            )
                                        }
                                    )
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
                                            val totalUserRating = modifyNumberFormat("0")
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