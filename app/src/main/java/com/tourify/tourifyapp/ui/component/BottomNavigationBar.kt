package com.tourify.tourifyapp.ui.component

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.data.sources.NavigationItem
import com.tourify.tourifyapp.route.Routes
import com.tourify.tourifyapp.ui.theme.ColorPrimary
import com.tourify.tourifyapp.ui.theme.ColorSecondary
import com.tourify.tourifyapp.ui.theme.ColorTransparent
import com.tourify.tourifyapp.ui.theme.ColorWhite
import com.tourify.tourifyapp.ui.theme.TextPrimary

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
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 14.dp)
            .height(60.dp),
        containerColor = ColorTransparent,
        contentColor = ColorPrimary,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
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
                                                onNavigationSelected(item.route)
                                            },
                                            icon = {
                                                if (item.route != Routes.ScanObjek.routes) {
                                                    Icon(
                                                        modifier = Modifier
                                                            .size(24.dp),
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
                            .size(52.dp)
                            .clip(RoundedCornerShape(percent = 100))
                            .background(ColorWhite)
                            .align(Alignment.TopCenter),
                        contentAlignment = Alignment.Center,
                        content = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(percent = 100))
                                    .background(ColorPrimary)
                                    .clickable(
                                        onClick = { onNavigationSelected(Routes.ScanObjek.routes) }
                                    ),
                                contentAlignment = Alignment.Center,
                                content = {
                                    Icon(
                                        modifier = Modifier
                                            .padding(6.dp)
                                            .size(24.dp),
                                        painter = painterResource(id = R.drawable.ic_camera),
                                        contentDescription = "",
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