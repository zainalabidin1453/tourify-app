package com.tourify.tourifyapp.ui.component

import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextLight
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts

@Composable
fun GreetingBar(
    context: Context,
    navController: NavController,
    onShowListProvince: (Boolean) -> Unit,
    onShowNotice: () -> Unit,
    onShowFavorite: () -> Unit,
    firstProvince: String
){
    var showListProvince by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        content =  {
            Row(
                modifier = Modifier
                    .wrapContentWidth(),
                content = {
                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .shadow(4.dp, Shapes.medium, true, spotColor = TextPrimary)
                            .height(35.dp)
                            .background(ColorWhite)
                            .clickable {
                                showListProvince = true
                                onShowListProvince(true)
                            },
                        content = {
                            Row(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .align(Alignment.Center),
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_location),
                                        contentDescription = stringResource(id = R.string.choose_location),
                                        modifier = Modifier
                                            .size(16.dp),
                                        tint = ColorDanger
                                    )
                                    Spacer(modifier = Modifier.width(1.dp))
                                    Text(
                                        text = firstProvince,
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Light,
                                            fontSize = 12.sp,
                                            lineHeight = 12.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(3.5.dp))
                                }
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    CircleButton(
                        context = context,
                        title = R.string.favorite_daftar,
                        icon = R.drawable.ic_heart_fill,
                        sizeCircle = 35.dp,
                        sizeIcon = 20.dp,
                        shadow = 4.dp,
                        isIcon = true,
                        tint = ColorDanger,
                        onClick = { onShowFavorite() }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    CircleButton(
                        context = context,
                        title = R.string.notification,
                        icon = R.drawable.ic_bell,
                        sizeCircle = 35.dp,
                        sizeIcon = 22.dp,
                        shadow = 4.dp,
                        isIcon = true,
                        tint = ColorSecondary,
                        onClick = { onShowNotice() }
                    )
                }
            )
            CircleAssyncImgSmall(
                context = context,
                title = R.string.photo_profile,
                img = R.drawable.avatar.toString(),
                errorImg = R.drawable.avatar,
                onClick = {}
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingBarPreview() {
    val context = LocalContext.current
    GreetingBar(
        context = context,
        navController = NavController(context),
        onShowListProvince = {},
        onShowNotice = {},
        onShowFavorite = {},
        firstProvince = "Sumatra Barat"
    )
}