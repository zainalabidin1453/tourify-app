package com.tourify.tourifyapp.ui.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun DaftarTourGuideBooking(
    context: Context,
    onShowDetailTourGuide: (Int) -> Unit,
    onChooseTourGuide: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        content = {
            Text(
                text = "Daftar Pemandu Wisata",
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
                                placeholder = "Cari Pemandu Wisata",
                                icon = R.drawable.ic_search,
                                iconDescription = R.string.find_tour_guide,
                                keyboardType = KeyboardType.Text,
                                onTextChanged = {},
                                isEnabled = true,
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            CardDaftarTourGuideBooking(context = context)
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                content = {

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
        onShowDetailTourGuide = {},
        onChooseTourGuide = {}
    )
}