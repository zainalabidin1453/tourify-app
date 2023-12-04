package com.tourify.tourifyapp.ui.component

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
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextLight
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun TextFieldSearch(
    modifier: Modifier = Modifier,
    placeholder: String,
    icon: Int,
    iconDescription: Int,
    keyboardType: KeyboardType,
    onClick: () -> Unit = {},
    onTextChanged: (String) -> Unit,
    isError: Boolean = false,
    isEnabled: Boolean = false
) {
    var text by rememberSaveable { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        value = text,
        onValueChange = { newText ->
            text = newText
            onTextChanged(newText)
        },
        enabled = isEnabled,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = ColorWhite,
            focusedBorderColor = ColorPrimary,
            unfocusedContainerColor = ColorWhite,
            unfocusedBorderColor = TextLight,
            errorContainerColor = ColorWhite,
            errorBorderColor = ColorDanger,
            disabledContainerColor = ColorWhite,
            cursorColor = ColorPrimary,
        ),
        isError = isError,
        maxLines = 1,
        shape = Shapes.small,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        textStyle = StyleText.copy(
            color = TextPrimary,
            fontFamily = fonts,
            fontWeight = FontWeight.Light,
            fontSize = 11.sp,
            lineHeight = 11.sp,
        ),
        placeholder = {
            Text(
                text = placeholder,
                color = TextLight,
                style = StyleText.copy(
                    fontFamily = fonts,
                    fontWeight = FontWeight.Light,
                    fontSize = 11.sp,
                    lineHeight = 11.sp
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
fun TextFieldSearchPreview() {
    var text by rememberSaveable { mutableStateOf("") }
    val isError by rememberSaveable { mutableStateOf(false) }

    TextFieldSearch(
        placeholder = "Cari tempat wisata",
        icon = R.drawable.ic_search,
        iconDescription = R.string.email_address,
        keyboardType = KeyboardType.Text,
        onClick = {},
        onTextChanged = { newText ->
            text = newText
        },
        isError = isError
    )
}