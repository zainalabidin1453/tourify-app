package com.tourify.tourifyapp.ui.main.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.data.factory.ViewModelMyProfileFactory
import com.tourify.tourifyapp.data.factory.ViewModelMyTicketsFactory
import com.tourify.tourifyapp.data.sources.ItemProfile
import com.tourify.tourifyapp.data.viewmodels.MyProfileViewModel
import com.tourify.tourifyapp.data.viewmodels.MyTicketsViewModel
import com.tourify.tourifyapp.di.Injection
import com.tourify.tourifyapp.model.ModelItemProfile
import com.tourify.tourifyapp.model.MyProfileResponse
import com.tourify.tourifyapp.model.MyTicketsResponse
import com.tourify.tourifyapp.ui.common.UiState
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorWarning
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.TextSecondary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.modifyMoneyFormat

@Composable
fun ProfileScreen(
    paddingValues: PaddingValues,
    userId: Int? = 0,
    myProfileViewModel: MyProfileViewModel = viewModel(
        factory = ViewModelMyProfileFactory(
            Injection.provideMyProfileRepository()
        )
    ),
) {
    var numberTab by rememberSaveable { mutableIntStateOf(0) }
    val interactionSource = remember { MutableInteractionSource() }
    val myProfileResult by myProfileViewModel.uiState.collectAsState(initial = UiState.Loading)
    LaunchedEffect(myProfileViewModel) {
        if (myProfileResult is UiState.Loading) {
            myProfileViewModel.getMyProfile(userId ?: 0)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorWhite),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                content = {
                    when(myProfileResult) {
                        is UiState.Loading -> {

                        }
                        is UiState.Success -> {
                            val daftarMyProfile = (myProfileResult as UiState.Success<MyProfileResponse>).data.data
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 18.dp, end = 18.dp, top = 54.dp, bottom = 18.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                content = {
                                    Box(
                                        modifier = Modifier
                                            .size(80.dp)
                                            .border(1.dp, ColorPrimary, RoundedCornerShape(100))
                                            .shadow(
                                                8.dp,
                                                RoundedCornerShape(100),
                                                true,
                                                spotColor = ColorPrimary
                                            )
                                            .background(ColorWhite),
                                        content = {
                                            Image(
                                                painter = painterResource(id = R.drawable.avatar),
                                                contentDescription = "Foto Profil",
                                                modifier = Modifier
                                                    .fillMaxSize(),
                                                contentScale = ContentScale.Crop,
                                                alignment = Alignment.Center
                                            )
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                        content = {
                                            Text(
                                                text = daftarMyProfile!![0].name,
                                                style = StyleText.copy(
                                                    color = TextPrimary,
                                                    fontFamily = fonts,
                                                    fontWeight = FontWeight.Medium,
                                                    fontSize = 14.sp,
                                                    lineHeight = 14.sp,
                                                    textAlign = TextAlign.Center
                                                ),
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_edit),
                                                contentDescription = "Edit Profile",
                                                modifier = Modifier
                                                    .size(18.dp),
                                                tint = TextPrimary
                                            )
                                        }
                                    )
                                }
                            )
                        }
                        is UiState.Error -> {

                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 18.dp, end = 18.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        content = {
                            repeat(3) {
                                Text(
                                    modifier = Modifier
                                        .clickable(
                                            interactionSource = interactionSource,
                                            indication = null,
                                            onClick = {
                                                numberTab = it
                                            }
                                        ),
                                    text = when (it) {
                                        0 -> "USER"
                                        1 -> "TOUR GUIDE"
                                        else -> "AKTIVITAS"
                                    },
                                    style = StyleText.copy(
                                        color = if (it == numberTab) TextPrimary else ColorSecondary,
                                        fontFamily = fonts,
                                        fontWeight = if (it == numberTab) FontWeight.Medium else FontWeight.Normal,
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 18.dp, end = 18.dp, top = 18.dp, bottom = 14.dp)
                            .shadow(3.dp, Shapes.small, true, spotColor = TextPrimary)
                            .background(ColorWhite),
                        content = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { },
                                content = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 14.dp,
                                                end = 14.dp,
                                                top = 12.dp,
                                                bottom = 12.dp
                                            ),
                                        content = {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                content = {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.ic_wallet_rp),
                                                        contentDescription = stringResource(id = R.string.total_saldo),
                                                        modifier = Modifier
                                                            .size(20.dp),
                                                        contentScale = ContentScale.Fit,
                                                        alignment = Alignment.Center
                                                    )
                                                    Spacer(modifier = Modifier.width(14.dp))
                                                    Column(
                                                        verticalArrangement = Arrangement.SpaceBetween,
                                                        content = {
                                                            Text(
                                                                text = stringResource(id = R.string.total_saldo),
                                                                style = StyleText.copy(
                                                                    color = TextSecondary,
                                                                    fontFamily = fonts,
                                                                    fontWeight = FontWeight.Light,
                                                                    fontSize = 12.sp,
                                                                    lineHeight = 12.sp,
                                                                )
                                                            )
                                                            Spacer(modifier = Modifier.height(2.dp))
                                                            Text(
                                                                text = modifyMoneyFormat(0),
                                                                style = StyleText.copy(
                                                                    color = ColorDanger,
                                                                    fontFamily = fonts,
                                                                    fontWeight = FontWeight.Medium,
                                                                    fontSize = 14.sp,
                                                                    lineHeight = 14.sp,
                                                                )
                                                            )
                                                        }
                                                    )
                                                }
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .wrapContentWidth()
                                                    .wrapContentHeight()
                                                    .clip(Shapes.small)
                                                    .background(ColorSecondary.copy(0.6f))
                                                    .align(Alignment.BottomEnd)
                                                    .clickable { },
                                                contentAlignment = Alignment.Center,
                                                content = {
                                                    Text(
                                                        modifier = Modifier
                                                            .padding(
                                                                start = 6.dp,
                                                                end = 6.dp,
                                                                top = 2.dp,
                                                                bottom = 2.dp
                                                            ),
                                                        text = "Tarik",
                                                        style = StyleText.copy(
                                                            color = TextPrimary,
                                                            fontFamily = fonts,
                                                            fontWeight = FontWeight.Light,
                                                            fontSize = 11.sp,
                                                            lineHeight = 11.sp,
                                                            textAlign = TextAlign.Center
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
                    Text(
                        modifier = Modifier
                            .padding(start = 18.dp, end = 18.dp),
                        text = "Akun Saya",
                        style = StyleText.copy(
                            color = TextPrimary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 18.dp, end = 18.dp, bottom = 14.dp)
                            .shadow(3.dp, Shapes.small, true, spotColor = TextPrimary)
                            .background(ColorWhite),
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                content = {
                                    ItemProfile.data.forEach { item ->
                                        if (item.id <= 5) {
                                            ItemMenuProfile(
                                                item = item,
                                                onClick = { }
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 18.dp, end = 18.dp),
                        text = "Lainnya",
                        style = StyleText.copy(
                            color = TextPrimary,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 18.dp, end = 18.dp, bottom = 65.dp)
                            .shadow(3.dp, Shapes.small, true, spotColor = TextPrimary)
                            .background(ColorWhite),
                        content = {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                content = {
                                    ItemProfile.data.forEach { item ->
                                        if (item.id >= 6) {
                                            ItemMenuProfile(
                                                item = item,
                                                onClick = { }
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun ItemMenuProfile(item: ModelItemProfile, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 14.dp,
                        end = 13.dp,
                        top = if (item.id == 1 || item.id == 6) 12.dp else 6.dp,
                        bottom = if (item.id == 5 || item.id == 7) 12.dp else 6.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.95f),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = stringResource(id = item.label),
                                modifier = Modifier
                                    .size(if (item.id == 6) 20.dp else 18.dp),
                                tint = ColorPrimary
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                content = {
                                    Text(
                                        text = stringResource(id = item.label),
                                        style = StyleText.copy(
                                            color = TextPrimary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 12.sp,
                                            lineHeight = 12.sp,
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(1.dp))
                                    Text(
                                        text = stringResource(id = item.desc),
                                        style = StyleText.copy(
                                            color = TextSecondary,
                                            fontFamily = fonts,
                                            fontWeight = FontWeight.Light,
                                            fontSize = 10.sp,
                                            lineHeight = 10.sp,
                                        )
                                    )
                                }
                            )
                        }
                    )
                    Box(
                        contentAlignment = Alignment.Center,
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_chevron_small_right),
                                contentDescription = stringResource(id = item.label),
                                modifier = Modifier
                                    .size(10.dp),
                                tint = TextPrimary
                            )
                        }
                    )
                }
            )
        }
    )
}


