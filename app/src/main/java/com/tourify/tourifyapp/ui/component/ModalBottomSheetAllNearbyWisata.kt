package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tourify.tourifyapp.data.sources.LocationDetails
import com.tourify.tourifyapp.model.DataDestinationsResponse
import com.tourify.tourifyapp.utils.getDistanceLocation

@Composable
fun ModalBottomSheetAllNearbyWisata(
    modifier: Modifier = Modifier,
    daftarDestinations: List<DataDestinationsResponse>? = null,
    currentLocation: LocationDetails? = null,
    favoriteItems: List<Int>,
    keywords: String = "",
    isAscending: Boolean,
    isFree: Boolean,
    onDetail: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit,
) {
    val fucusManager = LocalFocusManager.current
    val filteredData = daftarDestinations!!
        .filter { it.name.contains(keywords, ignoreCase = true) }
        .sortedBy { item ->
            getDistanceLocation(
                LocationDetails(currentLocation!!.lat, currentLocation.lon),
                LocationDetails(item.lat, item.lon)
            )
        }
    val sortedData =
    if(isAscending) {
        filteredData.sortedBy { it.name }
    } else {
        filteredData.sortedByDescending { it.name }
    }
    if (isFree) {
        filteredData.sortedBy { it.ticketPrice }
    } else {
        filteredData.sortedByDescending { it.ticketPrice }
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 18.dp, end = 18.dp),
        content = {
            items(sortedData, key = { it.id }) { item ->
                val distance = getDistanceLocation(
                    LocationDetails(currentLocation!!.lat, currentLocation.lon),
                    LocationDetails(item.lat, item.lon)
                )
                val itemIndex = sortedData.indexOf(item)
                val spacerModifier = if (itemIndex == 0) {
                    Modifier.height(2.dp)
                } else {
                    Modifier.height(10.dp)
                }
                val isFavorite = favoriteItems.contains(item.id)
                Spacer(modifier = spacerModifier)
                NearbyWisataCard(
                    distance = distance,
                    item = item,
                    isFavorite = isFavorite,
                    onClick = {
                        onDetail(item.id)
                        fucusManager.clearFocus()
                    },
                    onToggleFavorite = {
                        onToggleFavorite(item.id)
                    }
                )
                if (itemIndex == sortedData.size - 1) {
                    Spacer(modifier = Modifier.height(18.dp))
                }
            }
        }
    )
}