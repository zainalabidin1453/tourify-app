package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.TextPrimary

@Composable
fun ModalBottomSheetSearchWisata(
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(3.dp, Shapes.small, true, spotColor = TextPrimary)
                    .clip(Shapes.small)
                    .background(ColorWhite),
                content = {
                    Column(
                        modifier = Modifier
                            .padding(start = 14.dp, end = 14.dp, top = 12.dp, bottom = 12.dp),
                        content = {

                        }
                    )
                }
            )
            Spacer(modifier = Modifier.height(14.dp))
            repeat(2) {
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
fun ModalBottomSheetSearchWisataPreview() {
    ModalBottomSheetSearchWisata(
       onDetail = {}
    )
}