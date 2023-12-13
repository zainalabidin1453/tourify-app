package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.ui.theme.ColorBackground
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun BayarPesananSuccess() {
    Scaffold(
        modifier = Modifier
            .background(ColorBackground)
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.payment_done),
                    contentDescription = stringResource(id = R.string.success_ic),
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.CenterHorizontally)
                        .scale(1.2f)
                )
                Text(
                    text = stringResource(id = R.string.success),
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Medium,
                    color = ColorPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 10.dp)
                )
                Text(
                    text = stringResource(id = R.string.success_payment),
                    style = MaterialTheme.typography.body2,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.waiting_payment),
                    style = MaterialTheme.typography.body2,
                    fontFamily = fonts,
                    color = TextSecondary,
                    fontWeight = FontWeight.Light,
                    fontSize = 11.sp,
                    lineHeight = 14.sp,
                    textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                )
                ButtonPrimary(
                    text = stringResource(id = R.string.success_payment_btn),
                    background = ColorPrimary,
                    contentColor = ColorWhite,
                    enabled = true,
                    onClick = {},
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BayarPesananSuccessPreview() {
    BayarPesananSuccess()
}