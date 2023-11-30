package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ModalBottomSheetAllNearbyWisata(
    modifier: Modifier = Modifier,
    onDetail: (Int) -> Unit
) {
    val scrollModalAllPopularWisataBottomState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollModalAllPopularWisataBottomState)
            .padding(start = 18.dp, end = 18.dp, bottom = 8.dp),
        content = {
            repeat(25) {
                NearbyWisataCard(
                    onClick = { id ->
                        onDetail(id)
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ModalBottomSheetAllNearbyWisataPreview() {
    ModalBottomSheetAllNearbyWisata(
       onDetail = {}
    )
}