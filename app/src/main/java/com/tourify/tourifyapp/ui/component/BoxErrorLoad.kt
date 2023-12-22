package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun BoxErrorLoad(
    modifier: Modifier = Modifier,
    type: Int //1 error connection, 2 error system
) {
    val title = when (type) {
        1 -> "Koneksi Bermasalah."
        2 -> "Terjadi Kesalahan."
        else -> "Terjadi Kesalahan."
    }
    val desc = when (type) {
        1 -> "Maaf, terjadi kesalahan dalam menghubungkan ke server. Silakan coba lagi nanti atau periksa koneksi internet Anda untuk melanjutkan."
        2 -> "Sayangnya, sistem sedang mengalami masalah yang menghalangi halaman yang Anda muat. Silakan coba lagi nanti. Terima kasih atas kesabaran dan pengertiannya."
        else -> "Terjadi kesalahan pada sistem, coba lagi nanti"
    }
    val icon = when (type) {
        1 -> R.drawable.ic_loss_connection
        2 -> R.drawable.ic_loss_connection
        else -> R.drawable.ic_loss_connection
    }
    Box(
        modifier = modifier
            .padding(start = 18.dp, end = 18.dp, bottom = 50.dp),
        contentAlignment = Alignment.Center,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = title,
                        modifier = Modifier.size(150.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = title,
                        style = StyleText.copy(
                            color = ColorDanger,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = desc,
                        style = StyleText.copy(
                            color = TextSecondary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun BoxErrorLoadPreview() {
    BoxErrorLoad(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        type = 1
    )
}