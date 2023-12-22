package com.tourify.tourifyapp.ui.main.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.component.ButtonInPayStatus
import com.tourify.tourifyapp.ui.component.TopBarPayOrderScreen
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorGreen
import com.tourify.tourifyapp.ui.theme.ColorWarning
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun PayStatusScreen(
    onBack: () -> Unit = {},
    onHome: () -> Unit = {},
    navigateToMyTickets: () -> Unit = {},
) {
    var statusPay by rememberSaveable { mutableStateOf(1) }
    val textButton = when (statusPay) {
        0 -> "Kembali"
        1 -> "Kembali" //Lihat Pesanan
        2 -> "Coba Lagi"
        else -> "Kembali"
    }
    val buttonEnabled = when (statusPay) {
        0 -> false
        1 -> true
        2 -> true
        else -> false
    }
    Scaffold (
        topBar = {
            TopBarPayOrderScreen(
                title = "Status Pesanan",
                desc = "#myTRF20241211230452",
                onBack = {
                    onBack()
                }
            )
        },
        bottomBar = {
            ButtonInPayStatus(
                modifier = Modifier
                    .padding(18.dp),
                text = textButton,
                enabled = buttonEnabled,
                onClick = {
                    when (statusPay) {
                        0 -> {
                            onHome()
                        }
                        1 -> {
                            //navigateToMyTickets()
                            onHome()
                        }
                        2 -> {
                            onBack()
                        }
                    }
                }
            )
        }
    ){
        Box(
            modifier = Modifier
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
                .fillMaxSize()
                .padding(start = 18.dp, top = 18.dp, end = 18.dp),
            contentAlignment = Alignment.Center,
            content = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        val icon = when (statusPay) {
                            0 -> R.drawable.ic_payment_pending
                            1 -> R.drawable.ic_payment_success
                            2 -> R.drawable.ic_payment_failed
                            else -> R.drawable.ic_payment_failed
                        }
                        val text = when (statusPay) {
                            0 -> "Pending"
                            1 -> "Success"
                            2 -> "Failed"
                            else -> "Failed"
                        }
                        val desc = when (statusPay) {
                            0 -> "Mohon bersabar sejenak, Pembayaran Sedang Diproses. Kami sedang bekerja keras untuk menyelesaikannya agar Anda dapat segera memulai petualangan yang tak terlupakan!"
                            1 -> "Selamat pembayaran Anda berhasil dan pesanan telah dikonfirmasi! Nikmati pengalaman tanpa hambatan dan persiapkan diri untuk petualangan yang tak terlupakan bersama kami."
                            2 -> "Maaf, Pembayaran Gagal. Terjadi kendala saat proses pembayaran Anda. Mohon coba lagi atau hubungi tim dukungan kami untuk bantuan lebih lanjut. Kami siap membantu Anda."
                            else -> "Maaf, Pembayaran Gagal. Terjadi kendala saat proses pembayaran Anda. Mohon coba lagi atau hubungi tim dukungan kami untuk bantuan lebih lanjut. Kami siap membantu Anda."
                        }
                        val color = when (statusPay) {
                            0 -> ColorWarning
                            1 -> ColorGreen
                            2 -> ColorDanger
                            else -> ColorDanger
                        }
                        Image(
                            painter = painterResource(id = icon),
                            contentDescription = text,
                            modifier = Modifier
                                .size(170.dp),
                        )
                        Text(
                            text = text,
                            style = StyleText.copy(
                                color = color,
                                fontFamily = fonts,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                                lineHeight = 18.sp,
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
}

@Preview(showBackground = true)
@Composable
fun PayStatusScreenPreview() {
    PayStatusScreen(
       onBack = {},
       onHome = {},
    )
}
