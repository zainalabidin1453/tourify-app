package com.tourify.tourifyapp.ui.main.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.component.ButtonInPayOrder
import com.tourify.tourifyapp.ui.component.LoadingButtonInPayOrder
import com.tourify.tourifyapp.ui.component.TopBarPayOrderScreen
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.modifyMoneyFormat
import kotlinx.coroutines.delay

@Composable
fun PayOrderScreen(
    onBack: () -> Unit,
    navigateToPayStatus: (String) -> Unit = {},
) {
    var isLoadingPay by rememberSaveable { mutableStateOf(false) }
    var payBy by rememberSaveable { mutableIntStateOf(4) } // 0 = saldo, 1 = Bank, 2 = Kartu Kredit, 3 = e-wallet, 4 = null
    LaunchedEffect(isLoadingPay) {
        delay(2000)
        if (isLoadingPay){
            isLoadingPay = false
            if (payBy != 4) {
                navigateToPayStatus("TRF202342092019")
            }
        }
    }
    Scaffold (
        topBar = {
            TopBarPayOrderScreen(
                title = "Bayar Pesanan",
                desc = "#myTRF20234209201912",
                onBack = {
                    onBack()
                }
            )
        },
        bottomBar = {
            if (isLoadingPay) {
                LoadingButtonInPayOrder(
                    modifier = Modifier
                        .padding(18.dp),
                    totalPrice = 10000
                )
            } else {
                ButtonInPayOrder(
                    modifier = Modifier
                        .padding(18.dp),
                    text = "Booking Perjalanan",
                    enabled = payBy != 4,
                    onClick = {
                        isLoadingPay = true
                    },
                    totalPrice = 10000
                )
            }
        }
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
                Spacer(modifier = Modifier.height(12.dp))
                repeat(4) { payWith ->
                    val borderCheckBoxModifier = if (payBy == payWith) {
                        Modifier.border(1.dp, ColorPrimary, RoundedCornerShape(100))
                    } else {
                        Modifier.border(2.dp, ColorSecondary, RoundedCornerShape(100))
                    }
                    val text = if (payWith == 0) "Saldo" else if(payWith == 1) "Virtual Account" else if(payWith == 2) "Kartu Kredit" else "E-Wallet"
                    val desc = if (payWith == 0) "Bayar dengan Saldo" else if(payWith == 1) "Bayar melalui Bank" else if(payWith == 2) "Bayar melalui Kartu Kredit" else "Bayar melalui E-Wallet"
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(2.dp, Shapes.small, true, spotColor = TextPrimary)
                            .clip(Shapes.small)
                            .background(ColorWhite),
                        content = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Box(
                                        modifier = Modifier
                                            .size(14.dp)
                                            .clip(RoundedCornerShape(100))
                                            .background(ColorWhite)
                                            .clickable(
                                                onClick = { payBy = payWith }
                                            )
                                            .then(borderCheckBoxModifier),
                                        contentAlignment = Alignment.Center,
                                        content = {
                                            if(payBy == payWith) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.ic_check),
                                                    contentDescription = desc,
                                                    modifier = Modifier
                                                        .size(25.dp),
                                                    tint = ColorPrimary
                                                )
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                        content = {
                                            Text(
                                                text = text,
                                                style = StyleText.copy(
                                                    color = TextSecondary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Normal,
                                                    fontSize = 12.sp,
                                                    lineHeight = 12.sp
                                                ),
                                                textAlign = TextAlign.Start
                                            )
                                            when (payWith) {
                                                0 -> {
                                                    Text(
                                                        text = modifyMoneyFormat(0),
                                                        style = StyleText.copy(
                                                            color = ColorDanger,
                                                            fontFamily = fonts,
                                                            fontWeight = FontWeight.Normal,
                                                            fontSize = 12.sp,
                                                            lineHeight = 12.sp
                                                        ),
                                                        textAlign = TextAlign.End
                                                    )
                                                }
                                                1 -> {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.ic_bank_transfer),
                                                        contentDescription = text,
                                                        modifier = Modifier
                                                            .height(18.dp),
                                                    )
                                                }
                                                2 -> {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.ic_credit_payment),
                                                        contentDescription = text,
                                                        modifier = Modifier
                                                            .height(18.dp),
                                                    )
                                                }
                                                else -> {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.ic_ewallet),
                                                        contentDescription = text,
                                                        modifier = Modifier
                                                            .fillMaxWidth(0.4f)
                                                            .height(18.dp),
                                                    )
                                                }
                                            }
                                        }
                                    )
                                }
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PayOrderScreenPreview() {
    PayOrderScreen(
        onBack = {}
    )
}
