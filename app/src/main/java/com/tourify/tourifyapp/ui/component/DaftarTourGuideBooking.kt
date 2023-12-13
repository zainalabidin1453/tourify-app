package com.tourify.tourifyapp.ui.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun DaftarTourGuideBooking(
    context: Context,
    onChooseTourGuide: (Int) -> Unit,
    isWithNoTourGuide: Boolean,
    onWithNoTourGuide: (Boolean) -> Unit,
    tourGuideIdBooking: Int
) {
    var querySearch by rememberSaveable { mutableStateOf("") }
    val borderCheckBoxModifier = if (isWithNoTourGuide && tourGuideIdBooking == 0) {
        Modifier.border(1.dp, ColorPrimary, RoundedCornerShape(100))
    } else {
        Modifier.border(2.dp, ColorSecondary, RoundedCornerShape(100))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        content = {
            Text(
                text = stringResource(id = R.string.guide_list),
                style = StyleText.copy(
                    color = TextPrimary,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(3.dp, Shapes.small, true, spotColor = TextPrimary)
                    .clip(Shapes.small)
                    .background(ColorWhite),
                content = {
                    Column(
                        modifier = Modifier
                            .padding(start = 14.dp, top = 14.dp, bottom = 12.dp),
                        content = {
                            TextFieldSearch(
                                modifier = Modifier.padding(end = 14.dp),
                                placeholder = stringResource(id = R.string.guide_search),
                                icon = R.drawable.ic_search,
                                iconDescription = R.string.find_tour_guide,
                                keyboardType = KeyboardType.Text,
                                onTextChanged = {
                                    querySearch = it
                                },
                                isEnabled = !isWithNoTourGuide,
                                isToBooking = true
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            CardDaftarTourGuideBooking(
                                context = context,
                                querySearch = querySearch,
                                tourGuideIdBooking = tourGuideIdBooking,
                                onChooseTourGuide = {
                                    onChooseTourGuide(it)
                                },
                                onWithNoTourGuide = {
                                    onWithNoTourGuide(it)
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Box(
                                        modifier = Modifier
                                            .size(15.dp)
                                            .clip(RoundedCornerShape(100))
                                            .background(ColorWhite)
                                            .clickable(
                                                onClick = {
                                                    onWithNoTourGuide(true)
                                                }
                                            )
                                            .then(borderCheckBoxModifier),
                                        contentAlignment = Alignment.Center,
                                        content = {
                                            if(isWithNoTourGuide) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.ic_check),
                                                    contentDescription = stringResource(id = R.string.no_guide),
                                                    modifier = Modifier
                                                        .size(25.dp),
                                                    tint = ColorPrimary
                                                )
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = stringResource(id = R.string.no_guide),
                                        style = StyleText.copy(
                                            color = TextSecondary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 11.sp,
                                            lineHeight = 11.sp
                                        ),
                                        textAlign = TextAlign.Center
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
fun DaftarTourGuidePreview() {
    val context = LocalContext.current
    DaftarTourGuideBooking(
        context = context,
        onChooseTourGuide = {},
        isWithNoTourGuide = false,
        onWithNoTourGuide = {},
        tourGuideIdBooking = 0
    )
}
