package com.tourify.tourifyapp.ui.main.screen

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tourify.tourifyapp.preference.LoginDataStore
import com.tourify.tourifyapp.route.Routes
import com.tourify.tourifyapp.ui.component.BottomNavigationBar
import com.tourify.tourifyapp.ui.theme.ColorWhite

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun DashboardScreen(
    context: Context,
    navController: NavHostController = rememberNavController()
) {
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {
        systemUiController.setSystemBarsColor(ColorWhite, darkIcons = true)
        onDispose {}
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val loginDataStore = LoginDataStore.getInstance(context)
    val loginResponseFlow = loginDataStore.getLoginStatus().collectAsState(initial = null)
    val loginResponse = loginResponseFlow.value
    val emailUser = loginResponse?.data?.email ?: ""
    val dataUserId = loginResponse?.data?.userId?.toInt()
    val userId = dataUserId ?: 0

    Scaffold(
        bottomBar = {
            if (currentRoute != Routes.SplashSreen.routes &&
                currentRoute != Routes.OnBoarding.routes &&
                currentRoute != Routes.CheckEmail.routes &&
                currentRoute != Routes.VerifCode.routes &&
                currentRoute != Routes.EnterPassword.routes &&
                currentRoute != Routes.CreatePassword.routes &&
                currentRoute != Routes.ScanObjek.routes &&
                currentRoute != Routes.Notice.routes &&
                currentRoute != Routes.PayOrder.routes &&
                currentRoute != Routes.PayStatus.routes
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
                        navigateToNotice = { usersId ->
                            val route = Routes.Notice.createRoute(usersId)
                            navController.navigate(route) {
                                popUpTo(Routes.Home.routes) { inclusive = false }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        navigateToPayOrder = { codeOrder ->
                            val route = Routes.PayOrder.createRoute(codeOrder)
                            navController.navigate(route) {
                                popUpTo(Routes.Home.routes) { inclusive = false }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        emailUser = emailUser,
                        userId = userId
                    )
                }
                composable(
                    route = Routes.Notice.routes,
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
                        NoticeScreen(
                            onBack = {
                                navController.popBackStack(
                                    Routes.Home.routes,
                                    inclusive = false
                                )
                            },
                        )
                    }
                )
                composable(
                    route = Routes.PayOrder.routes,
                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(500)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(500)
                        )
                    },
                    content = {
                        PayOrderScreen(
                            onBack = {
                                navController.popBackStack(
                                    Routes.Home.routes,
                                    inclusive = false
                                )
                            },
                            navigateToPayStatus = { codeOrder ->
                                val route = Routes.PayStatus.createRoute(codeOrder)
                                navController.navigate(route)
                            }
                        )
                    }
                )
                composable(
                    route = Routes.PayStatus.routes,
                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(500)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(500)
                        )
                    },
                    content = {
                        PayStatusScreen(
                            onBack = {
                                navController.popBackStack(
                                    Routes.PayOrder.routes,
                                    inclusive = false
                                )
                            },
                            onHome = {
                                navController.popBackStack(
                                    Routes.Home.routes,
                                    inclusive = false
                                )
                            },
                            navigateToMyTickets = {
                                navController.navigate(Routes.MyTickets.routes)
                            }
                        )
                    }
                )
                composable(route = Routes.Culinary.routes) {
                    CulinaryScreen(
                        context = context
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
                            context = context,
                            navController = navController,
                        )
                    }
                )
                composable(route = Routes.MyTickets.routes) {
                    MyTicketsScreen(
                        context = context,
                        navController = navController,
                        paddingValues = paddingValues,
                        userId = userId,
                    )
                }
                composable(route = Routes.Profile.routes) {
                    ProfileScreen(
                        userId = userId,
                        paddingValues = paddingValues
                    )
                }
            }
        )
    }
}

