package com.tourify.tourifyapp.ui.component

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.tourify.tourifyapp.data.sources.LocationDetails
import java.util.Locale

@SuppressLint("MissingPermission")
@ExperimentalPermissionsApi
@Composable
fun RequestPermission(
    context: Context,
    permission: String,
    currentLocation: (LocationDetails) -> Unit = {},
    isPermissionGranted: (Boolean) -> Unit = {},
    getAddress: (MutableList<Address>) -> Unit = {}
) {
    val permissionState = rememberPermissionState(permission)
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val localeIndonesia = Locale("id", "ID")
    val geocoder = Geocoder(context, localeIndonesia)
    HandleRequest(
        permissionState = permissionState,
        deniedContent = {
            CustomDialogNotification(
                title = "Aktifkan Lokasi",
                desc = "Hai! Kami ingin memberikan pengalaman terbaik kepada Anda dalam menggunakan aplikasi ini. Untuk melakukan itu, kami butuh sedikit bantuan dari Anda. Bisakah Anda mengizinkan kami mengakses informasi lokasi Anda?. Jangan khawatir, kami menghormati privasi Anda.",
                onClick = {
                    permissionState.launchPermissionRequest()
                    if (permissionState.status == PermissionStatus.Granted) {
                        isPermissionGranted(true)
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location ->
                                if (location != null) {
                                    currentLocation(
                                        LocationDetails(
                                            lat = location.latitude,
                                            lon = location.longitude
                                        )
                                    )
                                    val address = geocoder.getFromLocation(
                                        location.latitude,
                                        location.longitude,
                                        1
                                    )
                                    getAddress(address!!)
                                }
                            }
                    }
                },
                onDismiss = {
                    if (permissionState.status != PermissionStatus.Granted) {
                        isPermissionGranted(false)
                        permissionState.launchPermissionRequest()
                    } else {
                        isPermissionGranted(true)
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location ->
                                if (location != null) {
                                    currentLocation(
                                        LocationDetails(
                                            lat = location.latitude,
                                            lon = location.longitude
                                        )
                                    )
                                    val address = geocoder.getFromLocation(
                                        location.latitude,
                                        location.longitude,
                                        1
                                    )
                                    getAddress(address!!)
                                }
                            }
                    }
                }
            )
        },
        content = {
            isPermissionGranted(true)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        currentLocation(
                            LocationDetails(
                                lat = location.latitude,
                                lon = location.longitude
                            )
                        )
                        val address = geocoder.getFromLocation(
                            location.latitude,
                            location.longitude,
                            1
                        )
                        getAddress(address!!)
                    }
                }
        }
    )
}

@ExperimentalPermissionsApi
@Composable
fun HandleRequest(
    permissionState: PermissionState,
    deniedContent: @Composable () -> Unit,
    content: @Composable () -> Unit = {}
) {
    when (permissionState.status) {
        is PermissionStatus.Granted -> {
            content()
        }

        is PermissionStatus.Denied -> {
            deniedContent()
        }
    }
}
