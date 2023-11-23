package com.tourify.tourifyapp.ui.main.screen

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tourify.tourifyapp.route.Routes
import com.tourify.tourifyapp.ui.theme.ColorWhite

@Composable
fun DashboardScreen(
    context: Context,
    paddingValues: PaddingValues,
    navController: NavHostController = rememberNavController()
) {
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(ColorWhite)
        onDispose {}
    }
    NavHost(
        navController = navController,
        startDestination = Routes.Home.routes,
        builder = {
            composable(route = Routes.Home.routes) {
                HomeScreen(
                    navController = navController,
                    paddingValues = paddingValues
                )
            }
            composable(route = Routes.Culinary.routes) {
                CulinaryScreen(
                    navController = navController,
                    paddingValues = paddingValues
                )
            }
            composable(route = Routes.ScanObjek.routes) {
                ScanObjekScreen(
                    navController = navController
                )
            }
            composable(route = Routes.MyTickets.routes) {
                MyTicketsScreen(
                    navController = navController,
                    paddingValues = paddingValues
                )
            }
            composable(route = Routes.Profile.routes) {
                ProfileScreen(
                    navController = navController,
                    paddingValues = paddingValues
                )
            }
        }
    )
}

