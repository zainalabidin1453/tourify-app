package com.tourify.tourifyapp.ui.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorTransparent
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextLight
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun TextFieldBasicSearch(
    modifier: Modifier = Modifier,
    placeholder: String,
    icon: Int,
    iconDescription: Int,
    keyboardType: KeyboardType,
    onTextChanged: (String) -> Unit,
    keywords: String = "",
    clip: RoundedCornerShape = RoundedCornerShape(16.dp),
) {
    var text by rememberSaveable { mutableStateOf(keywords) }
    val interactionSource = remember { MutableInteractionSource() }
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(clip),
        value = text,
        onValueChange = { new ->
            text = new
            Log.d("onValueChange", text)
            onTextChanged(new)
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = ColorWhite,
            focusedBorderColor = ColorWhite,
            unfocusedContainerColor = ColorWhite,
            unfocusedBorderColor = ColorWhite,
            errorContainerColor = ColorWhite,
            disabledContainerColor = ColorSecondary.copy(0.5f),
            cursorColor = ColorPrimary,
        ),
        maxLines = 1,
        shape = clip,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        textStyle = StyleText.copy(
            color = TextPrimary,
            fontFamily = fonts,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            lineHeight = 12.sp,
        ),
        placeholder = {
            Text(
                text = placeholder,
                color = TextLight,
                style = StyleText.copy(
                    fontFamily = fonts,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    lineHeight = 12.sp
                )
            )
        },
        trailingIcon = {
            val iconText = painterResource(icon)
            if (text.length > 1) {
                Image(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(RoundedCornerShape(percent = 100))
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = {
                                text = ""
                                onTextChanged(text)
                            }
                        ),
                    painter = painterResource(R.drawable.ic_cross_circle),
                    contentDescription = stringResource(id = iconDescription),
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = iconText,
                    contentDescription = stringResource(id = iconDescription),
                    tint = TextLight
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldBasicSearchPreview() {
    var text by rememberSaveable { mutableStateOf("") }
    TextFieldBasicSearch(
        placeholder = "Masukkan kata kunci",
        icon = R.drawable.ic_search,
        iconDescription = R.string.email_address,
        keyboardType = KeyboardType.Text,
        onTextChanged = { newText ->
            text = newText
        },
    )
}