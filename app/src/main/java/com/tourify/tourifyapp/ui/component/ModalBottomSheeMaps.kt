package com.tourify.tourifyapp.ui.component

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
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
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.Shapes
import com.tourify.tourifyapp.ui.theme.StyleText
import com.tourify.tourifyapp.ui.theme.TextPrimary
import com.tourify.tourifyapp.ui.theme.fonts
import com.tourify.tourifyapp.utils.bitmapDescriptorFromVector

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ModalBottomSheetMaps(
    modifier: Modifier = Modifier,
    lon: Double,
    lat: Double,
    onBack: (Boolean) -> Unit
) {
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(lat, lon), 15f)
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
    Box(
        modifier = modifier,
        content = {
            PermissionsRequired(
                multiplePermissionsState = multiplePermissionState,
                permissionsNotGrantedContent = { onBack(false) },
                permissionsNotAvailableContent = { onBack(false) },
                content = {
                    GoogleMap(
                        cameraPositionState = cameraPositionState,
                        modifier = Modifier
                            .fillMaxSize(),
                        properties = MapProperties(isMyLocationEnabled = true),
                    ) {
                        val icon = bitmapDescriptorFromVector(
                            LocalContext.current, R.drawable.ic_maps_pin
                        )
                        MarkerInfoWindow(
                            state = MarkerState(position = LatLng(lat, lon)),
                            icon = icon,
                        ) { _ ->
                            Box(
                                modifier = Modifier
                                    .padding(start = 18.dp, end = 18.dp, bottom = 14.dp)
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
    )
}

@Preview(showBackground = true)
@Composable
fun ModalBottomSheetMapsPreview() {
    ModalBottomSheetMaps(
        lon = 0.0,
        lat = 0.0,
        onBack = {}
    )
}