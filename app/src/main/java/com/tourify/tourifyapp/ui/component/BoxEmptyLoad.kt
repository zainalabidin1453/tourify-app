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
fun BoxEmptyLoad(
    modifier: Modifier = Modifier,
    title: String,
    desc: String
) {
    Box(
        modifier = modifier
            .padding(start = 18.dp, end = 18.dp, bottom = 50.dp),
        contentAlignment = Alignment.Center,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_empty_data),
                        contentDescription = title,
                        modifier = Modifier.size(150.dp)
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
fun BoxEmptyLoadPreview() {
    BoxEmptyLoad(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        title = "Daftar Favorit Kosong",
        desc = "Maaf, tidak ada konten yang tersedia untuk ditampilkan. Anda dapat menambahkan destinasi wisata ke daftar favorit Anda untuk melihatnya di sini."
    )
}