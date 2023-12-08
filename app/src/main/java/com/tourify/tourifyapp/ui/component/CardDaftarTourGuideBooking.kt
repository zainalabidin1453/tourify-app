package com.tourify.tourifyapp.ui.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.data.sources.ItemWisataCategory
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorTransparent
import com.tourify.tourifyapp.ui.theme.ColorWarning
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun CardDaftarTourGuideBooking(
    context: Context
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        state = rememberLazyListState(),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            items(ItemWisataCategory.dataCategory, key = { it.id }) { itemCategory ->
                val itemIndex = ItemWisataCategory.dataCategory.indexOf(itemCategory)
                val spacerModifier = if (itemIndex == 0) {
                    Modifier.width(0.dp)
                } else {
                    Modifier.width(10.dp)
                }
                Spacer(modifier = spacerModifier)
                ItemTourGuideBooking(
                    context = context,
                    id = itemCategory.id,
                    onClick = {}
                )
                if (itemIndex == ItemWisataCategory.dataCategory.size - 1) {
                    Spacer(modifier = Modifier.width(14.dp))
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemTourGuideBooking(context: Context, id: Int, onClick: () -> Unit) {
    var showProfile by rememberSaveable { mutableStateOf(false) }
    var checked = rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .background(ColorSecondary)
            .clickable(
                onClick = { showProfile = true }
            ),
        contentAlignment = Alignment.Center,
        content = {
            if (showProfile) {
                ModalBottomSheet(
                    modifier = Modifier
                        .height(341.dp),
                    onDismissRequest = {
                        showProfile = false
                    },
                    containerColor = ColorTransparent,
                    shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
                    dragHandle = {},
                    content = {
                        Column(
                            content = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 18.dp, end = 18.dp, top = 18.dp)
                                        .clip(Shapes.small)
                                        .background(ColorWhite),
                                    contentAlignment = Alignment.Center,
                                    content = {
                                        ModalBottomSheetProfileTourGuide(context = context)
                                    }
                                )
                                ButtonPrimary(
                                    modifier = Modifier
                                        .padding(start = 18.dp, end = 18.dp, bottom = 18.dp, top = 14.dp),
                                    text = "Tutup",
                                    background = ColorSecondary,
                                    contentColor = TextPrimary,
                                    enabled = true,
                                    onClick = { showProfile = false }
                                )
                            }
                        )
                    }
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, bottom = 7.dp, top = 9.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    CircleAssyncImgProfile(
                        context = context,
                        title = R.string.photo_profile,
                        img = R.drawable.avatar.toString(),
                        size = 55.dp,
                        errorImg = R.drawable.avatar,
                        onClick = { showProfile = true }
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(
                        text = "TourGuide Name",
                        style = StyleText.copy(
                            color = TextPrimary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 10.sp,
                            lineHeight = 10.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Rp9000/jam",
                        style = StyleText.copy(
                            color = ColorDanger,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Light,
                            fontSize = 10.sp,
                            lineHeight = 10.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(23.dp))
                }
            )
            Box(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .align(Alignment.BottomEnd)
                    .size(15.dp)
                    .clip(RoundedCornerShape(100))
                    .border(2.dp, ColorWhite, RoundedCornerShape(100))
                    .background(ColorSecondary)
                    .clickable(
                        onClick = {
                            checked.value = !checked.value
                        }
                    ),
                contentAlignment = Alignment.Center,
                content = {
                    if(checked.value) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_check_small),
                            contentDescription = "TourGuide Dipilih",
                            modifier = Modifier
                                .size(25.dp),
                            tint = ColorPrimary
                        )
                    }
                }
            )
            Row(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp)
                    .align(Alignment.BottomStart),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_rating),
                        contentDescription = stringResource(id = R.string.total_rating),
                        modifier = Modifier
                            .height(15.dp)
                            .width(14.dp),
                        tint = ColorWarning
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "0.0 (0)",
                        style = StyleText.copy(
                            color = TextPrimary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 10.sp,
                            lineHeight = 10.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CardDaftarTourGuideBookingPreview() {
    val context = LocalContext.current
    CardDaftarTourGuideBooking(
        context = context,
    )
}