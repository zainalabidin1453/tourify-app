package com.tourify.tourifyapp.ui.main.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.ui.component.TopBarPayOrderScreen
import com.tourify.tourifyapp.ui.component.TopBarScreen
import com.tourify.tourifyapp.ui.component.TopBarScreenModal
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun PayOrderScreen(
    onBack: () -> Unit
) {
    Scaffold (
        topBar = {
            TopBarPayOrderScreen(
                title = "Bayar Pesanan",
                onBack = {
                    onBack()
                }
            )
        },
        bottomBar = {
            
        },
    ){
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
                .fillMaxSize()
                .padding(start = 18.dp, top = 18.dp, end = 18.dp),
            content = {
                Text(
                    text = "Metode Pembayaran",
                    style = StyleText.copy(
                        color = TextPrimary,
                        fontFamily = fonts,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                    )
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PayOrderScreenPreview() {
    PayOrderScreen( onBack = {} )
}
