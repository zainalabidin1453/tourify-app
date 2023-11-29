package com.tourify.tourifyapp.ui.main.screen

import android.Manifest
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.ui.theme.ColorDanger
import com.tourify.tourifyapp.ui.theme.ColorTransparent
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.bitmapDescriptorFromVector

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MapsScreen(
    navController: NavController
) {
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(ColorWhite, darkIcons = true)
        onDispose {}
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val lon = navBackStackEntry?.arguments?.getString("lon")
    val lat = navBackStackEntry?.arguments?.getString("lat")
    var cameraPositionState: CameraPositionState
    lon?.let { lonValue ->
        lat?.let { latValue ->
            LatLng(latValue.toDouble(), lonValue.toDouble())
            cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(LatLng(latValue.toDouble(), lonValue.toDouble()), 15f)
            }
            val multiplePermissionState = rememberMultiplePermissionsState(
                permissions = listOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
            LaunchedEffect(Unit) {
                multiplePermissionState.launchMultiplePermissionRequest()
            }
            Scaffold (
                topBar = {
                    TopAppBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
                        colors = TopAppBarColors(
                            containerColor = ColorWhite,
                            navigationIconContentColor = TextPrimary,
                            titleContentColor = TextPrimary,
                            actionIconContentColor = ColorTransparent,
                            scrolledContainerColor = ColorWhite
                        ),
                        title = {
                            Text(
                                text = stringResource(id = R.string.maps),
                                style = StyleText.copy(
                                    color = TextPrimary,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    lineHeight = 16.sp
                                )
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    navController.popBackStack()
                                },
                                content = {
                                    Icon(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .size(26.dp),
                                        painter = painterResource(id = R.drawable.ic_arrow_back),
                                        tint = TextPrimary,
                                        contentDescription = stringResource(id = R.string.back)
                                    )
                                }
                            )
                        }
                    )
                },
            ){
                PermissionsRequired(
                    multiplePermissionsState = multiplePermissionState,
                    permissionsNotGrantedContent = { /* ... */ },
                    permissionsNotAvailableContent = { /* ... */ },
                    content = {
                        GoogleMap(
                            cameraPositionState = cameraPositionState,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = it.calculateTopPadding()),
                            properties = MapProperties(isMyLocationEnabled = true),
                        ) {
                            val icon = bitmapDescriptorFromVector(
                                LocalContext.current, R.drawable.ic_maps_pin
                            )
                            MarkerInfoWindow(
                                state = MarkerState(position = LatLng(latValue.toDouble(), lonValue.toDouble())),
                                icon = icon,
                            ) { _ ->
                                Box(
                                    modifier = Modifier
                                        .padding(start = 18.dp, end = 18.dp)
                                        .clip(Shapes.small)
                                        .background(ColorWhite),
                                    content = {
                                        Column (
                                            modifier = Modifier
                                                .padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            content = {
                                                Text(
                                                    text = "Wisata Name",
                                                    maxLines = 2,
                                                    style = StyleText.copy(
                                                        color = TextPrimary,
                                                        fontFamily = fonts,
                                                        fontWeight = FontWeight.Medium,
                                                        fontSize = 14.sp,
                                                        lineHeight = 14.sp
                                                    )
                                                )
                                                Spacer(modifier = Modifier.height(2.dp))
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    content = {
                                                        Icon(
                                                            painter = painterResource(id = R.drawable.ic_location),
                                                            contentDescription = stringResource(id = R.string.choose_location),
                                                            modifier = Modifier
                                                                .size(13.dp),
                                                            tint = ColorDanger
                                                        )
                                                        Spacer(modifier = Modifier.width(1.dp))
                                                        Text(
                                                            text = "Location, Sumatra Barat",
                                                            maxLines = 2,
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
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapsScreenPreview() {
    MapsScreen(navController = NavController(LocalContext.current))
}
