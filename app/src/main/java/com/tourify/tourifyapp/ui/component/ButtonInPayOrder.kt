package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.modifyMoneyFormat

@Composable
fun ButtonInPayOrder(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = false,
    onClick: () -> Unit,
    totalPrice: Int,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Text(
                        text = "Total Pembayaran",
                        style = StyleText.copy(
                            color = TextPrimary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            textAlign = TextAlign.Start
                        )
                    )
                    Text(
                        text = modifyMoneyFormat(totalPrice),
                        style = StyleText.copy(
                            color = ColorDanger,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            textAlign = TextAlign.End
                        )
                    )
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                content = {
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

@Composable
fun LoadingButtonInPayOrder(
    modifier: Modifier = Modifier,
    totalPrice: Int,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                content = {
                    Text(
                        text = "Total Pembayaran",
                        style = StyleText.copy(
                            color = TextPrimary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            textAlign = TextAlign.Start
                        )
                    )
                    Text(
                        text = modifyMoneyFormat(totalPrice),
                        style = StyleText.copy(
                            color = ColorDanger,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            textAlign = TextAlign.End
                        )
                    )
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    LoadingButtonPrimary(
                        modifier = Modifier
                            .fillMaxWidth(),
                        background = ColorPrimary,
                        height = 55.dp,
                        onClick = {}
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonInPayOrderPreview(){
    ButtonInPayOrder(
        modifier = Modifier.padding(18.dp),
        text = "Booking Perjalanan",
        enabled= true,
        totalPrice = 100000,
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingButtonInPayOrderPreview(){
    LoadingButtonInPayOrder(
        modifier = Modifier.padding(18.dp),
        totalPrice = 100000,
    )
}
