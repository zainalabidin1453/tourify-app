package com.tourify.tourifyapp.ui.main.screen

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tourify.tourifyapp.route.Routes
import com.tourify.tourifyapp.ui.component.BottomNavigationBar
import com.tourify.tourifyapp.ui.theme.ColorWhite

@Composable
fun DashboardScreen(
    context: Context,
    navController: NavHostController = rememberNavController()
) {
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(ColorWhite)
        onDispose {}
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(bottomBar = {
        if (currentRoute != Routes.SplashSreen.routes &&
            currentRoute != Routes.OnBoarding.routes &&
            currentRoute != Routes.CheckEmail.routes &&
            currentRoute != Routes.VerifCode.routes &&
            currentRoute != Routes.EnterPassword.routes &&
            currentRoute != Routes.CreatePassword.routes &&
            currentRoute != Routes.ScanObjek.routes &&
            currentRoute != Routes.Maps.routes
        ) {
            BottomNavigationBar(navBackStackEntry = navBackStackEntry) { route ->
                navController.navigate(route) {
                    navController.graph.startDestinationRoute?.let { startRoute ->
                        popUpTo(startRoute) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Routes.Home.routes,
            builder = {
                composable(route = Routes.Home.routes) {
                    HomeScreen(
                        context = context,
                        navController = navController,
                        paddingValues = paddingValues,
                        navigateToMapsWisata = { lonLat ->
                            val (lon, lat) = lonLat.split(",")
                            val route = Routes.Maps.createRoute(lon, lat)
                            navController.navigate(route)
                        }
                    )
                }
                composable(
                    route = Routes.Maps.routes,
                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Down,
                            animationSpec = tween(500)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Up,
                            animationSpec = tween(500)
                        )
                    },
                    content = {
                        MapsScreen(
                            navController = navController,
                        )
                    }
                )
                composable(route = Routes.Culinary.routes) {
                    CulinaryScreen(
                        navController = navController,
                        paddingValues = paddingValues
                    )
                }
                composable(
                    route = Routes.ScanObjek.routes,
                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Down,
                            animationSpec = tween(500)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Up,
                            animationSpec = tween(500)
                        )
                    },
                    content = {
                        ScanObjekScreen(
                            navController = navController,
                        )
                    }
                )
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
}

