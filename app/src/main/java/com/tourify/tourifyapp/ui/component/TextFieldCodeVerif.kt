package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextLight
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun TextFieldCodeVerif(
    modifier: Modifier = Modifier,
    otpCount: Int = 6,
    otpTextChange: (String) -> Unit,
    onSend: () -> Unit = {}
) {
    var codeOTP by rememberSaveable { mutableStateOf("") }
    BasicTextField(
        value = codeOTP,
        onValueChange = { newText ->
            val filteredText = newText.take(otpCount).filter { it.isDigit() }
            codeOTP = filteredText
            otpTextChange(filteredText)
        },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Send),
        keyboardActions = KeyboardActions(
            onSend = {
                onSend()
            }
        ),
        textStyle = TextStyle.Default.copy(textAlign = TextAlign.Center),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                val codeList = codeOTP.toList()
                for (index in 0 until otpCount) {
                    Spacer(modifier = Modifier.width(2.dp))
                    val isBoxFilled = codeList.getOrNull(index)?.toString()?.isNotEmpty() == true
                    val borderStroke = if (isBoxFilled) {
                        BorderStroke(1.dp, ColorPrimary)
                    } else {
                        BorderStroke(1.dp, TextLight)
                    }
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(ColorWhite, RoundedCornerShape(20.dp))
                            .border(borderStroke, RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = codeList.getOrNull(index)?.toString() ?: "",
                            color = TextPrimary,
                            style = StyleText.copy(
                                fontFamily = fonts,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                lineHeight = 14.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldCodeVerif() {
    var codeOTP by rememberSaveable { mutableStateOf("") }
    TextFieldCodeVerif(
        otpTextChange = { codeOTP = it }
    )
}