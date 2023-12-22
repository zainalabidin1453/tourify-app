package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
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
import kotlin.math.pow

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    icon: Int,
    iconDescription: Int,
    height: Dp = 55.dp,
    fontSize: TextUnit = 14.sp,
    iconSize: Dp = 22.dp,
    keyboardType: KeyboardType,
    onTextChanged: (String) -> Unit,
    isError: Boolean,
    value: String = "",
    isLastField: Boolean = false,
    onSend: () -> Unit = {}
) {
    var text by rememberSaveable { mutableStateOf(value) }
    val focusManager = LocalFocusManager.current
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
            unfocusedBorderColor = ColorSecondary,
            errorContainerColor = ColorWhite,
            errorBorderColor = ColorDanger,
            disabledContainerColor = ColorWhite,
            cursorColor = ColorPrimary,
        ),
        isError = isError,
        maxLines = 1,
        shape = Shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType, imeAction = if(isLastField) ImeAction.Send else ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) },
            onSend = {
                onSend()
            }
        ),
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
            Icon(
                modifier = Modifier
                    .size(iconSize),
                painter = painterResource(icon),
                contentDescription = stringResource(id = iconDescription),
                tint = TextLight
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TextFiledPreview() {
    var text by rememberSaveable { mutableStateOf("") }
    val isError by rememberSaveable { mutableStateOf(false) }
    TextField(
        placeholder = stringResource(R.string.email_address),
        icon = R.drawable.ic_mail,
        iconDescription = R.string.email_address,
        keyboardType = KeyboardType.Email,
        onTextChanged = { newText ->
            text = newText
        },
        isError = isError
    )
}