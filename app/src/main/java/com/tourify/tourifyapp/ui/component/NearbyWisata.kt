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
import com.tourify.tourifyapp.data.sources.LocationDetails
import com.tourify.tourifyapp.model.DataDestinationsResponse
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorInfo
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWarning
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.getDistanceLocation
import com.tourify.tourifyapp.utils.modifyNumberFormat
import com.tourify.tourifyapp.utils.shimmerBrush

@Composable
fun NearbyWisata(
    modifier: Modifier = Modifier,
    daftarDestinations: List<DataDestinationsResponse>? = null,
    currentLocation: LocationDetails? = null,
    myProvince: String? = null,
    onDetail: (Int) -> Unit,
    favoriteItems: List<Int>,
    onToggleFavorite: (Int) -> Unit = {},
) {
    val filteredData = daftarDestinations
        ?.filter { it.province == myProvince }
    val sortedData = filteredData
        ?.sortedBy { item ->
            getDistanceLocation(
                LocationDetails(currentLocation!!.lat, currentLocation.lon),
                LocationDetails(item.lat, item.lon)
            )
        }
        ?.take(20)
    Column(
        modifier = modifier
            .fillMaxWidth(),
        content = {
            sortedData?.forEach { itemNearby ->
                val distance = getDistanceLocation(
                    LocationDetails(currentLocation!!.lat, currentLocation.lon),
                    LocationDetails(itemNearby.lat, itemNearby.lon)
                )
                val isFavorite = favoriteItems.contains(itemNearby.id)
                Spacer(modifier = Modifier.height(10.dp))
                NearbyWisataCard(
                    distance = distance,
                    item = itemNearby,
                    isFavorite = isFavorite,
                    onClick = {
                        onDetail(itemNearby.id)
                    },
                    onToggleFavorite = {
                        onToggleFavorite(itemNearby.id)
                    }
                )
            }
        }
    )
}

@Composable
fun NearbyWisataCard(
    distance: Int? = null,
    item: DataDestinationsResponse? = null,
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
                                    .data(item!!.photo)
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
                                    .padding(end = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                content = {
                                    Column(
                                        content = {
                                            Text(
                                                text = item!!.name,
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
                                                        text = "${item.regency}, ${item.province}",
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
                                        onClick = {
                                            onToggleFavorite()
                                        }
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 2.dp, end = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                content = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        content = {
                                            Text(
                                                text = "$distance Km",
                                                style = StyleText.copy(
                                                    color = ColorInfo,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Normal,
                                                    fontSize = 10.sp,
                                                    lineHeight = 10.sp
                                                )
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
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
                                                        text = item!!.type,
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

@Composable
fun NearbyWisataLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(shimmerBrush(targetValue = 500f, showShimmer = true))
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "",
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
                    .clip(
                        shape = RoundedCornerShape(10.dp)
                    )
                    .background(shimmerBrush(targetValue = 10f, showShimmer = true))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                AsyncImage(
                    model = "",
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .clip(
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(
                            shimmerBrush(
                                targetValue = 10f,
                                showShimmer = true
                            )
                        )
                )
            }
        }
    }
}