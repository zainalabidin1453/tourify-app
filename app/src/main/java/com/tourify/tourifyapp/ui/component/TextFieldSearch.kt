package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextLight
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun TextFieldSearch(
    placeholder: String,
    icon: Int,
    iconDescription: Int,
    keyboardType: KeyboardType,
    onClick: () -> Unit = {},
    onTextChanged: (String) -> Unit,
    isError: Boolean = false
) {
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        value = text,
        onValueChange = { newText ->
            text = newText
            onTextChanged(newText)
        },
        enabled = false,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = ColorWhite,
            unfocusedContainerColor = ColorWhite,
            disabledContainerColor = ColorWhite,
            cursorColor = ColorPrimary,
            focusedBorderColor = ColorPrimary,
            focusedTextColor = TextPrimary,
            unfocusedBorderColor = ColorSecondary,
            unfocusedPlaceholderColor = TextLight,
            unfocusedTextColor = TextLight
        ),
        maxLines = 1,
        shape = Shapes.small,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        placeholder = {
            Text(
                text = placeholder,
                style = StyleText.copy(
                    fontFamily = fonts,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    lineHeight = 12.sp
                )
            )
        },
        trailingIcon = {
            val iconText = painterResource(icon)
            Icon(
                painter = iconText,
                contentDescription = stringResource(id = iconDescription),
                tint = if (isError) ColorDanger else TextLight
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldSearchPreview() {
    var text by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }

    TextFieldSearch(
        placeholder = stringResource(R.string.email_address),
        icon = R.drawable.ic_mail,
        iconDescription = R.string.email_address,
        keyboardType = KeyboardType.Email,
        onClick = {},
        onTextChanged = { newText ->
            text = newText
        },
        isError = isError
    )
}