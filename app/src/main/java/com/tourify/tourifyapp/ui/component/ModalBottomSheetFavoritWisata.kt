package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tourify.tourifyapp.model.DataDestinationsResponse

@Composable
fun ModalBottomSheetFavoritWisata(
    modifier: Modifier = Modifier,
    daftarDestinations: List<DataDestinationsResponse>? = null,
    favoriteItems: List<Int>,
    onDetail: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit,
) {
    if (favoriteItems.isNotEmpty() && daftarDestinations!!.isNotEmpty()) {
        val filteredData = daftarDestinations.filter { data ->
            favoriteItems.any { destinationId ->
                destinationId == data.id
            }
        }
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 18.dp, end = 18.dp),
            content = {
                items(filteredData, key = { it.id }) { item ->
                    val itemIndex = filteredData.indexOf(item)
                    val spacerModifier = if (itemIndex == 0) {
                        Modifier.height(18.dp)
                    } else {
                        Modifier.height(10.dp)
                    }
                    val isFavorite = favoriteItems.contains(item.id)
                    Spacer(modifier = spacerModifier)
                    CardWisataMaxWidth(
                        item = item,
                        isFavorite = isFavorite,
                        onClick = { onDetail(item.id) },
                        onToggleFavorite = {
                            onToggleFavorite(item.id)
                        }
                    )
                    if (itemIndex == filteredData.size - 1) {
                        Spacer(modifier = Modifier.height(18.dp))
                    }
                }
            }
        )
    } else {
        BoxEmptyLoad(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            title = "Daftar Favorit Kosong.",
            desc = "Anda dapat menambahkan destinasi wisata ke daftar favorit Anda untuk melihatnya di sini.",
        )
    }
}