package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun ModalBottomSheetFilters(
    modifier: Modifier = Modifier,
    onAscending: (Boolean) -> Unit,
    onFree: (Boolean) -> Unit,
    isFree: Boolean,
    isAscending: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 18.dp, bottom = 18.dp),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(ColorSecondary),
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        content =  {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_filters_2),
                                        contentDescription = stringResource(id = R.string.sort_data),
                                        modifier = Modifier
                                            .size(15.dp),
                                        tint = ColorPrimary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = stringResource(id = R.string.sort_data),
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 12.sp,
                                            lineHeight = 12.sp
                                        )
                                    )
                                }
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_descending),
                                        contentDescription = stringResource(id = R.string.descending),
                                        modifier = Modifier
                                            .size(13.dp),
                                        tint = TextPrimary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    CustomSwitchButtonIsAscending(
                                        isAscending = isAscending,
                                        size = 20.dp,
                                        onClick = { onAscending(!isAscending) },
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_ascending),
                                        contentDescription = stringResource(id = R.string.ascending),
                                        modifier = Modifier
                                            .size(13.dp),
                                        tint = TextPrimary
                                    )
                                }
                            )
                        }
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(ColorSecondary),
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        content =  {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_coin),
                                        contentDescription = stringResource(id = R.string.filter_by_ticket),
                                        modifier = Modifier
                                            .size(15.dp),
                                        tint = ColorPrimary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = stringResource(id = R.string.filter_data),
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 12.sp,
                                            lineHeight = 12.sp
                                        )
                                    )
                                }
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Text(
                                        text = stringResource(id = R.string.paid),
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Light,
                                            fontSize = 11.sp,
                                            lineHeight = 11.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    CustomSwitchButtonIsFree(
                                        isFree = isFree,
                                        size = 20.dp,
                                        onClick = { onFree(!isFree) },
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = stringResource(id = R.string.free),
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Light,
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

@Preview(showBackground = true)
@Composable
fun ModalBottomSheetFiltersPreview() {
    ModalBottomSheetFilters(
       onAscending = {},
       onFree = {},
       isAscending = true,
       isFree = true
    )
}
