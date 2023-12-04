package com.tourify.tourifyapp.ui.component

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.TextPrimary

@Composable
fun TopBarAllWisata(
    context: Context,
    onShowFilters: (Boolean) -> Unit = {},
    onKeywords: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 18.dp, top = 1.dp, end = 18.dp, bottom = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    TextFieldSearch(
                        modifier = Modifier.weight(0.8f),
                        placeholder = stringResource(R.string.search_wisata),
                        icon = R.drawable.ic_search,
                        iconDescription = R.string.search_wisata,
                        keyboardType = KeyboardType.Text,
                        onTextChanged = { onKeywords(it) },
                        isEnabled = true
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    CircleButton(
                        context = context,
                        title = R.string.filters,
                        icon = R.drawable.ic_filters,
                        sizeCircle = 45.dp,
                        sizeIcon = 26.dp,
                        shadow = 4.dp,
                        isIcon = true,
                        tint = TextPrimary,
                        onClick = { onShowFilters(true) }
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarAllWisataPreview() {
    val context = LocalContext.current
    TopBarAllWisata(context)
}