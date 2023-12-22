package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.tourify.tourifyapp.model.DataCulinaryResponse
import com.tourify.tourifyapp.model.DataDestinationsResponse
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.cutLocation

@Composable
fun RelatedCulinary(
    modifier: Modifier = Modifier,
    daftarCulinary: List<DataCulinaryResponse>,
    onDetail: (Int) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        state = lazyListState,
        content = {
            items(daftarCulinary, key = { it.id }) { item ->
                val itemIndex = daftarCulinary.indexOf(item)
                val spacerModifier = if (itemIndex == 0) {
                    Modifier.width(18.dp)
                } else {
                    Modifier.width(10.dp)
                }
                Spacer(modifier = spacerModifier)
                RelatedCulinaryCard(
                    item = item,
                    onClick = {
                        onDetail(item.id)
                    }
                )
                if (itemIndex == daftarCulinary.size - 1) {
                    Spacer(modifier = Modifier.width(18.dp))
                }
            }
        }
    )
}

@Composable
fun RelatedCulinaryCard(item: DataCulinaryResponse, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .shadow(4.dp, Shapes.medium, true, spotColor = TextPrimary)
            .background(ColorWhite)
            .clickable { onClick() },
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
                    .clip(Shapes.medium),
                alignment = Alignment.Center
            )
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp),
                content = {
                    Text(
                        modifier = Modifier
                            .padding(start = 1.5.dp),
                        text = item.name,
                        maxLines = 2,
                        style = StyleText.copy(
                            color = ColorWhite,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    )
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
                                text = cutLocation(item.regency),
                                style = StyleText.copy(
                                    color = ColorWhite,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Medium,
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

@Preview(showBackground = true)
@Composable
fun RelatedCulinaryPreview() {
    RelatedCulinary(
        daftarCulinary = listOf(),
        onDetail = {}
    )
}