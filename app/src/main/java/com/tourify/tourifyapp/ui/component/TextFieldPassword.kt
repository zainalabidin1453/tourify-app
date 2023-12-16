package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
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
fun TextFieldPassword(
    placeholder: String,
    iconDescription: Int,
    onTextChanged: (String) -> Unit,
    height: Dp = 55.dp,
    fontSize: TextUnit = 14.sp,
    iconSize: Dp = 22.dp,
    isError: Boolean
) {
    var text by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
            onTextChanged(newText)
        },
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
        shape = Shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        textStyle = StyleText.copy(
            color = TextPrimary,
            fontFamily = fonts,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            lineHeight = fontSize,
        ),
        placeholder = {
            Text(
                text = placeholder,
                color = TextLight,
                style = StyleText.copy(
                    fontFamily = fonts,
                    fontWeight = FontWeight.Normal,
                    fontSize = fontSize,
                    lineHeight = fontSize
                )
            )
        },
        trailingIcon = {
            val iconText =
                if (passwordVisible) painterResource(R.drawable.ic_eye_closed) else painterResource(
                    R.drawable.ic_eye_open
                )
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ),
                painter = iconText,
                contentDescription = stringResource(id = iconDescription),
                tint = TextLight
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldPasswordPreview() {
    var text by rememberSaveable { mutableStateOf("") }
    val isError by rememberSaveable { mutableStateOf(false) }

    TextFieldPassword(
        placeholder = stringResource(R.string.new_password),
        iconDescription = R.string.new_password,
        onTextChanged = { newText ->
            text = newText
        },
        isError = isError
    )
}
