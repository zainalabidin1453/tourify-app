package com.tourify.tourifyapp.ui.component

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.model.DataCulinaryResponse
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorTransparent
import com.tourify.tourifyapp.ui.theme.ColorWarning
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.colorRandom
import com.tourify.tourifyapp.utils.cutLocation

@Composable
fun ModalBottomSheetDetailCulinary(
    context: Context,
    idDetailCulinary: Int,
    daftarCulinary: List<DataCulinaryResponse>? = null,
    onBack: () -> Unit = {},
    onShowDetailCulinary: (Int) -> Unit = {}
) {
    val scrollModalDetailBottomState = rememberScrollState()
    val sortedData = daftarCulinary?.firstOrNull { it.id == idDetailCulinary }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .align(Alignment.TopCenter)
                    .background(ColorWarning),
                content = {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(sortedData!!.photo)
                            .crossfade(true)
                            .error(R.drawable.error_image)
                            .build(),
                        contentDescription = stringResource(id = R.string.photo_wisata),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.71f)
                    .align(Alignment.BottomCenter)
                    .background(ColorTransparent),
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .align(Alignment.TopCenter)
                            .background(ColorTransparent),
                        content = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 18.dp, end = 18.dp, bottom = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                content = {
                                    CircleButton(
                                        context = context,
                                        title = R.string.back,
                                        icon = R.drawable.ic_arrow_back,
                                        sizeCircle = 35.dp,
                                        sizeIcon = 24.dp,
                                        shadow = 4.dp,
                                        onClick = { onBack() }
                                    )
                                    Row(
                                        modifier = Modifier
                                            .wrapContentWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        content = {
                                            CircleButton(
                                                context = context,
                                                title = R.string.maps,
                                                icon = R.drawable.ic_google_maps,
                                                sizeCircle = 35.dp,
                                                sizeIcon = 20.dp,
                                                shadow = 4.dp,
                                                onClick = {  }
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            CircleButton(
                                                context = context,
                                                title = R.string.share_to_facebook,
                                                icon = R.drawable.ic_facebook,
                                                sizeCircle = 35.dp,
                                                sizeIcon = 20.dp,
                                                shadow = 4.dp,
                                                onClick = {  }
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(1f)
                            .padding(top = 50.dp)
                            .align(Alignment.BottomCenter)
                            .shadow(6.dp, RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp), true, spotColor = TextPrimary)
                            .background(ColorWhite),
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 18.dp, bottom = 18.dp),
                                content = {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 18.dp, end = 18.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        content = {
                                            Text(
                                                modifier = Modifier
                                                    .padding(start = 1.dp),
                                                text = sortedData!!.name,
                                                maxLines = 2,
                                                style = StyleText.copy(
                                                    color = TextPrimary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Medium,
                                                    fontSize = 18.sp,
                                                    lineHeight = 20.sp,
                                                    textAlign = TextAlign.Center
                                                ),
                                            )
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                content = {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_location),
                                                        contentDescription = stringResource(id = R.string.choose_location),
                                                        modifier = Modifier
                                                            .size(13.dp),
                                                        tint = ColorDanger
                                                    )
                                                    Spacer(modifier = Modifier.width(1.dp))
                                                    Text(
                                                        text = cutLocation("${sortedData.regency}, ${sortedData.province}"),
                                                        style = StyleText.copy(
                                                            color = TextPrimary,
                                                            fontFamily = fonts,
                                                            fontWeight = FontWeight.Light,
                                                            fontSize = 12.sp,
                                                            lineHeight = 16.sp
                                                        ),
                                                        maxLines = 2
                                                    )
                                                }
                                            )
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(18.dp))
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 18.dp, end = 18.dp)
                                            .shadow(3.dp, Shapes.small, true, spotColor = TextPrimary)
                                            .clip(Shapes.small)
                                            .background(ColorWhite),
                                        content = {
                                            Column(
                                                modifier = Modifier
                                                    .padding(start = 14.dp, end = 14.dp, top = 12.dp, bottom = 12.dp),
                                                content = {
                                                    Box(
                                                        modifier = Modifier
                                                            .wrapContentWidth()
                                                            .clip(Shapes.small)
                                                            .background(colorRandom()),
                                                        content = {
                                                            Text(
                                                                modifier = Modifier
                                                                    .padding(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 2.dp),
                                                                text = "Makanan Tradisional",
                                                                style = StyleText.copy(
                                                                    color = ColorWhite,
                                                                    fontFamily = fonts,
                                                                    fontWeight = FontWeight.Light,
                                                                    fontSize = 10.sp,
                                                                    lineHeight = 10.sp,
                                                                    textAlign = TextAlign.Center
                                                                )
                                                            )
                                                        }
                                                    )
                                                    Spacer(modifier = Modifier.height(10.dp))
                                                    ExpandedText(
                                                        modifier = Modifier
                                                            .verticalScroll(scrollModalDetailBottomState),
                                                        text = sortedData!!.caption
                                                    )
                                                }
                                            )
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(14.dp))
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 18.dp, end = 18.dp),
                                        text = "Kuliner Lainnya",
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 12.sp,
                                            lineHeight = 12.sp,
                                        ),
                                        maxLines = 2
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    val filteredData = daftarCulinary!!
                                        .filter {it.id != sortedData!!.id }
                                        .sortedBy { it.name }
                                    RelatedCulinary(
                                        daftarCulinary = filteredData,
                                        onDetail = {
                                            onShowDetailCulinary(it)
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ModalBottomSheetDetailCulinaryPreview() {
    val context = LocalContext.current
    ModalBottomSheetDetailCulinary(
        context = context,
        idDetailCulinary = 1,
    )
}