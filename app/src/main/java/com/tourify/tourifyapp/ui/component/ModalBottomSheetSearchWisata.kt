package com.tourify.tourifyapp.ui.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.data.db.DBHistorySearchHandler
import com.tourify.tourifyapp.data.db.HistorySearchItem
import com.tourify.tourifyapp.model.DataDestinationsResponse
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.aiSearchByKeywords

@Composable
fun ModalBottomSheetSearchWisata(
    context: Context,
    modifier: Modifier = Modifier,
    onDetail: (Int) -> Unit,
    showHistorySearch: Boolean,
    daftarDestinations: List<DataDestinationsResponse>? = null,
    favoriteItems: List<Int>,
    keywords: String = "",
    isAscending: Boolean? = null,
    isFree: Boolean? = null,
    onKeywords: (String) -> Unit = {},
    onToggleFavorite: (Int) -> Unit,
) {
    val fucusManager = LocalFocusManager.current
    val findWisata = aiSearchByKeywords(keywords)
    var isFirstSearch by rememberSaveable { mutableStateOf(true) }
    val filteredData =
        if (findWisata.isNotEmpty()) {
            daftarDestinations!!
                .filter { data ->
                    findWisata.any { destinationType ->
                        destinationType == data.type
                    }
                }
                .sortedByDescending { it.rating }
        } else {
            if (keywords.isNotEmpty()) {
                daftarDestinations!!
                    .filter { it.name.contains(keywords, ignoreCase = true) }
                    .sortedByDescending { it.rating }
            } else {
                emptyList()
            }
        }

    val sortedData =
        if (isAscending == true) {
            filteredData.sortedBy { it.name }
        } else {
            filteredData.sortedByDescending { it.name }
        }
    if (isFree == true) {
        filteredData.sortedBy { it.ticketPrice }
    } else {
        filteredData.sortedByDescending { it.ticketPrice }
    }

    isFirstSearch = if (keywords.isNotEmpty()) {
        false
    } else {
        !(!isFirstSearch && keywords.isNotEmpty())
    }

    val historySearchItems = remember { mutableStateListOf<HistorySearchItem>() }
    val dbHistorySearchHandler = DBHistorySearchHandler(context)
    val interactionSource = remember { MutableInteractionSource() }
    val visibleItemCount = remember { mutableIntStateOf(5) }
    val shouldShowMore = historySearchItems.size > visibleItemCount.intValue
    DisposableEffect(Unit) {
        historySearchItems.addAll(dbHistorySearchHandler.getAllHistorySearch())
        onDispose { }
    }
    fun refreshListAfterDeletion() {
        historySearchItems.clear()
        historySearchItems.addAll(dbHistorySearchHandler.getAllHistorySearch())
    }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 18.dp, end = 18.dp, bottom = 8.dp, top = 2.dp),
        content = {
            items(1) {
                if (showHistorySearch) {
                    refreshListAfterDeletion()
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(3.dp, Shapes.small, true, spotColor = TextPrimary)
                            .clip(Shapes.small)
                            .background(ColorWhite),
                        content = {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        start = 12.dp,
                                        end = 14.dp,
                                        top = 12.dp,
                                        bottom = 12.dp
                                    ),
                                content = {
                                    historySearchItems.take(visibleItemCount.intValue)
                                        .forEachIndexed { index, item ->
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                content = {
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        content = {
                                                            Icon(
                                                                painter = painterResource(id = R.drawable.ic_clock),
                                                                contentDescription = "Riwayat Pencarian",
                                                                modifier = Modifier
                                                                    .size(13.dp),
                                                                tint = TextSecondary
                                                            )
                                                            Spacer(modifier = Modifier.width(6.dp))
                                                            Text(
                                                                text = item.name,
                                                                style = StyleText.copy(
                                                                    color = TextSecondary,
                                                                    fontFamily = fonts,
                                                                    fontWeight = FontWeight.Normal,
                                                                    fontSize = 12.sp,
                                                                    lineHeight = 12.sp
                                                                )
                                                            )
                                                        }
                                                    )
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_cross),
                                                        contentDescription = "Hapus Pencarian",
                                                        modifier = Modifier
                                                            .size(11.dp)
                                                            .clickable(
                                                                interactionSource = interactionSource,
                                                                indication = null,
                                                                onClick = {
                                                                    dbHistorySearchHandler.deleteHistorySearch(
                                                                        item.id
                                                                    )
                                                                    refreshListAfterDeletion()
                                                                }
                                                            ),
                                                        tint = TextSecondary
                                                    )
                                                }
                                            )
                                            if (index < historySearchItems.size - 1) Spacer(
                                                modifier = Modifier.height(
                                                    6.dp
                                                )
                                            )
                                        }
                                    if (historySearchItems.size >= 5) {
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center,
                                            content = {
                                                Row(
                                                    modifier = Modifier
                                                        .clickable(
                                                            interactionSource = interactionSource,
                                                            indication = null,
                                                            onClick = {
                                                                if (shouldShowMore)
                                                                    visibleItemCount.intValue =
                                                                        historySearchItems.size
                                                                else
                                                                    visibleItemCount.intValue = 5
                                                            }
                                                        ),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    content = {
                                                        Text(
                                                            text = if (shouldShowMore) "Lihat lainnya" else "Lebih sedikit",
                                                            style = StyleText.copy(
                                                                color = TextPrimary,
                                                                fontFamily = fonts,
                                                                fontWeight = FontWeight.Normal,
                                                                fontSize = 12.sp,
                                                                lineHeight = 12.sp
                                                            )
                                                        )
                                                        Spacer(modifier = Modifier.width(4.dp))
                                                        Icon(
                                                            painter = painterResource(id = if (shouldShowMore) R.drawable.ic_small_down else R.drawable.ic_small_up),
                                                            contentDescription = "",
                                                            modifier = Modifier
                                                                .width(11.dp),
                                                            tint = TextPrimary
                                                        )
                                                    }
                                                )
                                            }
                                        )
                                    }
                                    if (historySearchItems.size > 0) Spacer(
                                        modifier = Modifier.height(
                                            12.dp
                                        )
                                    )
                                    Column(
                                        modifier = Modifier
                                            .padding(start = 2.dp),
                                        content = {
                                            Text(
                                                text = "Anda mungkin suka",
                                                style = StyleText.copy(
                                                    color = TextPrimary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Medium,
                                                    fontSize = 12.sp,
                                                    lineHeight = 12.sp
                                                )
                                            )
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Row(
                                                modifier = Modifier
                                                    .padding(start = 1.dp)
                                                    .clickable(
                                                        interactionSource = interactionSource,
                                                        indication = null,
                                                        onClick = {
                                                            onKeywords("Pantai yang Indah di Padang")
                                                        }
                                                    ),
                                                verticalAlignment = Alignment.CenterVertically,
                                                content = {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_circle),
                                                        contentDescription = "Sedang Populer",
                                                        modifier = Modifier
                                                            .size(6.dp),
                                                        tint = ColorDanger
                                                    )
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    Text(
                                                        text = "Pantai yang Indah di Padang",
                                                        style = StyleText.copy(
                                                            color = TextSecondary,
                                                            fontFamily = fonts,
                                                            fontWeight = FontWeight.Normal,
                                                            fontSize = 12.sp,
                                                            lineHeight = 12.sp
                                                        )
                                                    )
                                                }
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                }
            }
            if (sortedData.isNotEmpty()) {
                items(sortedData, key = { it.id }) { item ->
                    val itemIndex = sortedData.indexOf(item)
                    val spacerModifier = if (itemIndex == 0) {
                        Modifier.height(0.dp)
                    } else {
                        Modifier.height(10.dp)
                    }
                    val isFavorite = favoriteItems.contains(item.id)
                    Spacer(modifier = spacerModifier)
                    CardWisataMaxWidth(
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
            } else {
                if (!isFirstSearch) {
                    items(1) {
                        BoxEmptyLoad(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(18.dp),
                            title = "Keywords tidak ditemukan.",
                            desc = "Periksa kata kunci atau coba kata kunci lain untuk mencari informasi yang relevan."
                        )
                    }
                }
            }
        }
    )
}