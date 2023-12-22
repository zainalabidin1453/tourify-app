package com.tourify.tourifyapp.ui.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tourify.tourifyapp.constants.Constants
import com.tourify.tourifyapp.data.sources.ItemProvince
import com.tourify.tourifyapp.model.ModelItemProvince
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun ModalBottomSheetListProvince(
    modifier: Modifier = Modifier,
    context: Context,
    navController: NavController,
    onShowListProvince: (Boolean) -> Unit,
    onProvince: (String) -> Unit,
    firstProvince: String
) {
    val scrollModalBottomState = rememberScrollState()
    Column(
        modifier = modifier
            .padding(start = 18.dp, end = 18.dp)
            .verticalScroll(scrollModalBottomState),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, ColorPrimary, RoundedCornerShape(12.dp))
                    .background(ColorSecondary),
                content = {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Text(
                                text = firstProvince,
                                style = StyleText.copy(
                                    color = TextSecondary,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    lineHeight = 14.sp
                                )
                            )
                        }
                    )
                }
            )
            Spacer(modifier = Modifier.height(18.dp))
            val sortedItemsProvince = ItemProvince.data.sortedBy { it.name }
            sortedItemsProvince.forEach { item ->
                ItemMenuProvince(
                    item = item,
                    onClick = {
                        onProvince(item.name)
                        onShowListProvince(false)
                    },
                    currentProvince = firstProvince
                )
            }
        }
    )
}

@Composable
fun ItemMenuProvince(item: ModelItemProvince, onClick: (String) -> Unit, currentProvince: String) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(ColorSecondary)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    onClick(item.name)
                }
            )
            .then(
                if (isPressed) Modifier.border(1.dp, ColorPrimary, RoundedCornerShape(12.dp))
                else Modifier
            ),
        content = {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Text(
                        text = item.name,
                        style = StyleText.copy(
                            color = TextSecondary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            lineHeight = 14.sp
                        )
                    )
                }
            )
        }
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Preview(showBackground = true)
@Composable
fun ModalBottomSheetListProvincePreview() {
    val context = LocalContext.current
    ModalBottomSheetListProvince(
        context = context,
        navController = NavController(context),
        onShowListProvince = {},
        onProvince = {},
        firstProvince = Constants.ACEH
    )
}