package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun ProgressBarBooking(
    modifier: Modifier = Modifier,
    progressValue: Int,
    onClick: (Int) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            repeat(3) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = if (it < 2) 6.dp else 0.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = {
                                when (it) {
                                    0 -> {
                                        if (progressValue >= 1) onClick(1)
                                    }
                                    1 -> {
                                        if (progressValue >= 2) onClick(2)
                                    }
                                    else -> {
                                        if (progressValue >= 3) onClick(3)
                                    }
                                }
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Icon(
                            painter = painterResource(id =
                                when (it) {
                                    0 -> {
                                        R.drawable.ic_circle_1
                                    }
                                    1 -> {
                                        R.drawable.ic_circle_2
                                    }
                                    else -> {
                                        R.drawable.ic_circle_3
                                    }
                                }
                            ),
                            contentDescription = "Step 1",
                            modifier = Modifier
                                .size(18.dp)
                                .padding(top = 3.dp),
                            tint = when (it) {
                                0 -> {
                                    if (progressValue >= 1) ColorPrimary else ColorSecondary
                                }
                                1 -> {
                                    if (progressValue >= 2) ColorPrimary else ColorSecondary
                                }
                                else -> {
                                    if (progressValue >= 3) ColorPrimary else ColorSecondary
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            content ={
                                Text(
                                    text = when (it) {
                                        0 -> {
                                           "Pesan"
                                        }
                                        1 -> {
                                            "Detail"
                                        }
                                        else -> {
                                            "Rincian"
                                        }
                                    },
                                    style = StyleText.copy(
                                        color = when (it) {
                                            0 -> {
                                                if (progressValue >= 1) ColorPrimary else ColorSecondary
                                            }
                                            1 -> {
                                                if (progressValue >= 2) ColorPrimary else ColorSecondary
                                            }
                                            else -> {
                                                if (progressValue >= 3) ColorPrimary else ColorSecondary
                                            }
                                        },
                                        fontFamily = fonts,
                                        fontWeight = when (it) {
                                            0 -> {
                                                if (progressValue >= 1) FontWeight.Normal else FontWeight.Light
                                            }
                                            1 -> {
                                                if (progressValue >= 2) FontWeight.Normal else FontWeight.Light
                                            }
                                            else -> {
                                                if (progressValue >= 3) FontWeight.Normal else FontWeight.Light
                                            }
                                        },
                                        fontSize = 12.sp,
                                        lineHeight = 12.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .clip(RoundedCornerShape(percent = 100))
                                        .background(
                                            when (it) {
                                                0 -> {
                                                    if (progressValue >= 1) ColorPrimary.copy(alpha = 0.7f) else ColorSecondary.copy(alpha = 0.7f)
                                                }
                                                1 -> {
                                                    if (progressValue >= 2) ColorPrimary.copy(alpha = 0.7f) else ColorSecondary.copy(alpha = 0.7f)
                                                }
                                                else -> {
                                                    if (progressValue >= 3) ColorPrimary.copy(alpha = 0.7f) else ColorSecondary.copy(alpha = 0.7f)
                                                }
                                            }
                                        )
                                )
                            }
                        )
                    }
                )
            }
       }
    )
}

@Preview(showBackground = true)
@Composable
fun ProgressBarBookingPreview(){
    ProgressBarBooking(
        modifier = Modifier.padding(18.dp),
        progressValue = 1,
        onClick = {}
    )
}
