package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
fun ButtonPrimaryWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    background: Color,
    contentColor: Color,
    enabled: Boolean,
    height: Dp = 50.dp,
    shadow: Dp = 10.dp,
    fontSize: TextUnit = 16.sp,
    iconSize: Dp = 26.dp,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .wrapContentWidth()
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Text(
                        text = text,
                        style = ButtonStylePrimary.copy(
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = fontSize,
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = stringResource(id = R.string.further),
                        modifier = Modifier
                            .size(iconSize),
                        tint = contentColor
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonPrimaryWithIconPreview() {
    ButtonPrimaryWithIcon(
        text = stringResource(id = R.string.next),
        background = ColorPrimary,
        contentColor = ColorWhite,
        enabled = true,
        onClick = {}
    )
}
