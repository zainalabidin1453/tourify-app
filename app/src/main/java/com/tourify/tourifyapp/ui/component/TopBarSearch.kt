package com.tourify.tourifyapp.ui.component

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tourify.tourifyapp.R

@Composable
fun TopBarSearch(
    context: Context,
    placeholder: Int = R.string.search_wisata,
    value: String = "",
    onKeywords: (String) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 36.dp),
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
                        keywords = value,
                        placeholder = stringResource(placeholder),
                        icon = R.drawable.ic_search,
                        iconDescription = R.string.search_wisata,
                        keyboardType = KeyboardType.Text,
                        onTextChanged = { onKeywords(it) },
                        isEnabled = true
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarSearchPreview() {
    val context = LocalContext.current
    TopBarAllWisata(context)
}