package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ButtonStylePrimary
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun ButtonPrimary(
    modifier: Modifier = Modifier,
    text: String,
    background: Color,
    contentColor: Color,
    enabled: Boolean,
    height: Dp = 55.dp,
    shadow: Dp = 10.dp,
    fontSize: TextUnit = 16.sp,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .shadow(shadow, Shapes.medium, true, spotColor = background),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            contentColor = contentColor,
            disabledContainerColor = ColorSecondary,
            disabledContentColor = TextSecondary
        ),
        shape = Shapes.medium,
        enabled = enabled,
        content = {
            Text(
                text = text,
                style = ButtonStylePrimary.copy(
                    fontFamily = fonts,
                    fontWeight = FontWeight.Normal,
                    fontSize = fontSize,
                )
            )
        }
    )
}

@Composable
fun LoadingButtonPrimary(
    modifier: Modifier = Modifier,
    background: Color = ColorPrimary,
    onClick: () -> Unit,
    height: Dp = 55.dp,
    shadow: Dp = 10.dp
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .shadow(shadow, Shapes.medium, true, spotColor = background),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = background),
        shape = Shapes.medium,
        content = {
            Loading(size = DpSize(height / 2, height / 2))
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonPrimaryPreview() {
    ButtonPrimary(
        text = stringResource(id = R.string.next),
        background = ColorPrimary,
        contentColor = ColorWhite,
        enabled = true,
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingButtonPrimaryPreview() {
    LoadingButtonPrimary(
        onClick = {}
    )
}
