package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.ui.theme.ButtonStylePrimary
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun ButtonInBooking(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    onBatal: () -> Unit,
    enabled: Boolean = true,
    info: Boolean = true,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        content = {
            if (info) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    content = {
                        Text(
                            text = "Keseruan Perjalanan Dijamin Bersama Pemandu Wisata Profesional",
                            style = StyleText.copy(
                                color = TextSecondary,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Light,
                                fontSize = 10.sp,
                                lineHeight = 15.sp,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .padding(end = 8.dp)
                            .height(55.dp)
                            .shadow(10.dp, Shapes.medium, spotColor = ColorSecondary),
                        onClick = onBatal,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ColorSecondary,
                            contentColor = TextSecondary
                        ),
                        shape = Shapes.medium,
                        content = {
                            Text(
                                text = "Batal",
                                style = ButtonStylePrimary.copy(
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp,
                                )
                            )
                        }
                    )
                    ButtonPrimary(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = text,
                        background = ColorPrimary,
                        contentColor = ColorWhite,
                        height = 55.dp,
                        enabled = enabled,
                        onClick = {
                            onClick()
                        }
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonInBookingPreview(){
    ButtonInBooking(
        modifier = Modifier.padding(18.dp),
        text = "Lanjutkan",
        onClick = {},
        onBatal = {},
        enabled = true
    )
}
