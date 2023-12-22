package com.tourify.tourifyapp.ui.component

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.data.sources.NavigationItem
import com.tourify.tourifyapp.route.Routes
import com.tourify.tourifyapp.ui.theme.ColorBlue
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorTransparent
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.TextPrimary

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BottomNavigationBar(
    navBackStackEntry: NavBackStackEntry?,
    onNavigationSelected: (route: String) -> Unit
) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Culinary,
        NavigationItem.ScanObjek,
        NavigationItem.MyTickets,
        NavigationItem.Profile
    )
    val currentRoute = navBackStackEntry?.destination?.route
    val gradientColors = listOf(ColorBlue, ColorPrimary)
    var showPermission by rememberSaveable { mutableStateOf(false) }

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        containerColor = ColorTransparent,
        contentColor = ColorPrimary,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .shadow(10.dp, RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
                                true, spotColor = TextPrimary
                            )
                            .background(ColorWhite)
                            .align(Alignment.BottomCenter),
                        content = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                content = {
                                    items.forEach { item ->
                                        NavigationBarItem(
                                            selected = currentRoute == item.route,
                                            onClick = {
                                                if (item.route != Routes.ScanObjek.routes) {
                                                    onNavigationSelected(item.route)
                                                } else {
                                                    showPermission = true
                                                }
                                            },
                                            icon = {
                                                if (item.route != Routes.ScanObjek.routes) {
                                                    Icon(
                                                        modifier = Modifier
                                                            .size(26.dp),
                                                        painter = painterResource(id = item.icon),
                                                        contentDescription = stringResource(id = item.title)
                                                    )
                                                }
                                            },
                                            label = null,
                                            alwaysShowLabel = false,
                                            colors = NavigationBarItemDefaults.colors(
                                                indicatorColor = ColorTransparent,
                                                selectedIconColor = if (item.route == Routes.ScanObjek.routes) ColorWhite else ColorPrimary,
                                                unselectedIconColor = if (item.route == Routes.ScanObjek.routes) ColorWhite else ColorSecondary
                                            )
                                        )
                                    }
                                }
                            )
                        }
                    )
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(percent = 100))
                            .background(ColorWhite)
                            .align(Alignment.TopCenter),
                        contentAlignment = Alignment.Center,
                        content = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(6.dp)
                                    .clip(RoundedCornerShape(percent = 100))
                                    .background(
                                        brush = Brush.horizontalGradient(colors = gradientColors),
                                    )
                                    .clickable(
                                        onClick = { showPermission = true }
                                    ),
                                contentAlignment = Alignment.Center,
                                content = {
                                    Icon(
                                        modifier = Modifier
                                            .padding(6.dp)
                                            .size(26.dp),
                                        painter = painterResource(id = R.drawable.ic_camera),
                                        contentDescription = "Scan Objek Wisata",
                                        tint = ColorWhite
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
    if(showPermission) {
        val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
        val status = CheckCameraPermission(permissionState)
        if (status) {
            onNavigationSelected(Routes.ScanObjek.routes)
        } else {
            RequestCameraPermission(
                permission = Manifest.permission.CAMERA,
                onPermissionGranted = {
                    if (it) {
                        onNavigationSelected(Routes.ScanObjek.routes)
                    }
                    showPermission = false
                }
            )
        }
    }
}
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestCameraPermission(
    permission: String,
    onPermissionGranted: (Boolean) -> Unit = {},
){
    val permissionState = rememberPermissionState(permission)
    HandleRequestCameraPermission(
        permissionState = permissionState,
        deniedContent = {
            CustomDialogNotification(
                icon = R.drawable.ic_camera_permission,
                title = "Izinkan Camera",
                desc = "Aplikasi memerlukan izin akses ke kamera Anda. Dengan mengizinkan akses ke kamera, Anda dapat menikmati fitur memindai objek wisata dengan dukungan kecerdasan buatan (AI).",
                actionName = "Izinkan",
                onClick = {
                    permissionState.launchPermissionRequest()
                    if (permissionState.status == PermissionStatus.Granted) {
                        onPermissionGranted(true)
                    }
                },
                onDismiss = {
                    if (permissionState.status != PermissionStatus.Granted) {
                        onPermissionGranted(false)
                    } else {
                        onPermissionGranted(true)
                    }
                }
            )
        },
        content = {
            onPermissionGranted(true)
        }
    )
}

@ExperimentalPermissionsApi
@Composable
fun HandleRequestCameraPermission(
    permissionState: PermissionState,
    deniedContent: @Composable () -> Unit,
    content: @Composable () -> Unit = {}
){
    when (permissionState.status) {
        is PermissionStatus.Granted -> {
            content()
        }
        is PermissionStatus.Denied -> {
            deniedContent()
        }
    }
}

@ExperimentalPermissionsApi
@Composable
fun CheckCameraPermission(
    permissionState: PermissionState,
): Boolean {
    return when (permissionState.status) {
        is PermissionStatus.Granted -> {
           true
        }
        is PermissionStatus.Denied -> {
           false
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    BottomNavigationBar(
        navBackStackEntry = navBackStackEntry,
        onNavigationSelected = {}
    )
}