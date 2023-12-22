package com.tourify.tourifyapp.ui.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tourify.tourifyapp.ui.component.BoxEmptyLoad
import com.tourify.tourifyapp.ui.component.TopBarScreen
import com.tourify.tourifyapp.ui.theme.ColorWhite

@Composable
fun NoticeScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBarScreen(
                title = "Pemberitahuan",
                onBack = {
                    onBack()
                }
            )
        },
    ) {
        Box(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .background(ColorWhite),
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    content = {
                        BoxEmptyLoad(
                            modifier = Modifier.padding(bottom = 36.dp),
                            title = "Belum ada pemberitahuan.",
                            desc = "Silakan periksa kembali nanti untuk mendapatkan pembaruan terbaru."
                        )
                    }
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoticeScreenPreview() {
    NoticeScreen(onBack = {})
}
