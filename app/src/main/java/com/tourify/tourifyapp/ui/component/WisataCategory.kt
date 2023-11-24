package com.tourify.tourifyapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tourify.tourifyapp.data.sources.ItemWisataCategory
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun WisataCategory(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {},
) {
    var clickId by rememberSaveable { mutableStateOf(0) }
    val lazyListState = rememberLazyListState()
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = lazyListState,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            items(ItemWisataCategory.dataCategory, key = { it.id }) { itemCategory ->
                val itemIndex = ItemWisataCategory.dataCategory.indexOf(itemCategory)
                val spacerModifier = if (itemIndex == 0) {
                    Modifier.width(18.dp)
                } else {
                    Modifier.width(10.dp)
                }
                Spacer(modifier = spacerModifier)
                ItemCategory(
                    id = itemCategory.id,
                    clickId = clickId,
                    icon = itemCategory.icon,
                    inLabel = itemCategory.inLabel,
                    color = itemCategory.color,
                    onClick = { id ->
                        clickId = id
                        onClick(itemCategory.enLabel)
                    }
                )
                if (itemIndex == ItemWisataCategory.dataCategory.size - 1) {
                    Spacer(modifier = Modifier.width(18.dp))
                }
            }
        }
    )
}

@Composable
fun ItemCategory(id: Int, clickId: Int, icon: Int, inLabel: Int, color: Color, onClick: (Int) -> Unit = {}) {
    val borderModifier = if (id == clickId) {
        Modifier.border(1.dp, ColorPrimary, RoundedCornerShape(25.dp))
    } else {
        Modifier
    }
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .shadow(2.dp, Shapes.medium, true)
            .height(35.dp)
            .background(ColorWhite)
            .clickable {
                onClick(id)
            }
            .then(borderModifier),
        content = {
            Row(
                modifier = Modifier
                    .padding(6.dp)
                    .align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = stringResource(id = inLabel),
                        modifier = Modifier
                            .size(15.dp),
                        tint = color
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(id = inLabel),
                        style = StyleText.copy(
                            color = TextPrimary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            lineHeight = 12.sp
                        )
                    )
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun WisataCategoryPreview() {
    WisataCategory()
}