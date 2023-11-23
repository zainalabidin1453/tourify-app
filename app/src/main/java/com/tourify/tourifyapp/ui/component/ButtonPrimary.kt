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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.tourify.tourifyapp.ui.theme.ButtonStylePrimary
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun ButtonPrimary(
    text: String,
    background: Color,
    color: Color,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(10.dp, Shapes.medium, true),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = background),
        shape = Shapes.medium,
        enabled = enabled,
        content = {
            Text(
                text = text,
                color = color,
                style = ButtonStylePrimary.copy(
                    fontFamily = fonts.also { FontWeight.Normal }
                )
            )
        }
    )
}

@Composable
fun LoadingButtonPrimary(
    onClick: () -> Unit,
    gridSize: Dp = 56.dp
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(10.dp, Shapes.medium, true),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary),
        shape = Shapes.medium,
        content = {
            Loading(size = DpSize(gridSize / 2, gridSize / 2))
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonPrimaryPreview() {
    ButtonPrimary(
        text = "Lanjutkan",
        background = ColorPrimary,
        color = ColorWhite,
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