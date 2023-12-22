package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun ButtonInPayStatus(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = false,
    onClick: () -> Unit
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
                horizontalArrangement = Arrangement.Center,
                content = {
                    Text(
                        text = "Terima kasih telah melakukan pembayaran! Harap bersabar sejenak dan jangan kembali. Status pesanan Anda akan segera diperbarui dalam beberapa detik.",
                        style = StyleText.copy(
                            color = TextSecondary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Light,
                            fontSize = 10.sp,
                            lineHeight = 14.sp,
                            textAlign = TextAlign.Center
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
                        background = if (!enabled) ColorSecondary else ColorPrimary,
                        contentColor = ColorWhite,
                        height = 55.dp,
                        enabled = enabled,
                        onClick = { onClick() }
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonInPayStatusPreview() {
    ButtonInPayStatus(
        modifier = Modifier.padding(18.dp),
        text = "Lihat Pesanan",
        enabled = true ,
        onClick = {}
    )
}
