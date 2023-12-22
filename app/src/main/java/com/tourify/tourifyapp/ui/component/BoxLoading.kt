package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.tourify.tourifyapp.ui.theme.ColorPrimary

@Preview
@Composable
fun BoxLoading(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
        content = {
            Loading(
                size = DpSize(40.dp, 40.dp),
                color = ColorPrimary
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun BoxLoadingPreview() {
    BoxLoading()
}