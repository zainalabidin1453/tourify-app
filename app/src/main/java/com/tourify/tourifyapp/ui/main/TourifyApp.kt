package com.tourify.tourifyapp.ui.main

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tourify.tourifyapp.route.Routes
import com.tourify.tourifyapp.ui.main.screen.CheckEmailScreen
import com.tourify.tourifyapp.ui.main.screen.CreatePasswordScreen
import com.tourify.tourifyapp.ui.main.screen.DashboardScreen
import com.tourify.tourifyapp.ui.main.screen.EnterPasswordScreen
import com.tourify.tourifyapp.ui.main.screen.OnBoardingScreen
import com.tourify.tourifyapp.ui.main.screen.SplashScreen
import com.tourify.tourifyapp.ui.main.screen.VerifCodeScreen

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun TourifyApp(
    context: Context,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SplashSreen.routes,
        builder = {
            composable(route = Routes.SplashSreen.routes) {
                SplashScreen(
                    context = context,
                    navigateTo = {
                        navController.navigate(it) {
                            popUpTo(Routes.SplashSreen.routes) { inclusive = true }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                )
            }
            composable(route = Routes.OnBoarding.routes) {
                OnBoardingScreen(
                    context = context,
                    navigateTo = {
                        navController.navigate(Routes.CheckEmail.routes) {
                            popUpTo(Routes.OnBoarding.routes) { inclusive = true }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                )
            }
            composable(route = Routes.CheckEmail.routes) {
                CheckEmailScreen(
                    context = context,
                    navigateToVerifCode = { email ->
                        val route = Routes.VerifCode.createRoute(email)
                        navController.navigate(route) {
                            popUpTo(Routes.CheckEmail.routes) { inclusive = true }
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    navigateToEnterPassword = { email ->
                        val route = Routes.EnterPassword.createRoute(email)
                        navController.navigate(route) {
                            popUpTo(Routes.CheckEmail.routes) { inclusive = true }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                )
            }
            composable(route = Routes.VerifCode.routes) {
                VerifCodeScreen(
                    context = context,
                    navController = navController,
                    navigateToCheckEmail = {
                        navController.navigate(Routes.CheckEmail.routes) {
                            popUpTo(Routes.VerifCode.routes) { inclusive = true }
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    navigateToCreatePassword = { email ->
                        val route = Routes.CreatePassword.createRoute(email)
                        navController.navigate(route) {
                            popUpTo(Routes.VerifCode.routes) { inclusive = true }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                )
            }
            composable(route = Routes.EnterPassword.routes) {
                EnterPasswordScreen(
                    context = context,
                    navController = navController,
                    navigateToCheckEmail = {
                        navController.navigate(Routes.CheckEmail.routes) {
                            popUpTo(Routes.EnterPassword.routes) { inclusive = true }
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    navigateToDashboard = {
                        navController.navigate(Routes.Dashboard.routes) {
                            popUpTo(Routes.EnterPassword.routes) { inclusive = true }
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    navigateToForgotPassword = { email ->
                        val route = Routes.VerifCode.createRoute(email)
                        navController.navigate(route) {
                            popUpTo(Routes.EnterPassword.routes) { inclusive = true }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                )
            }
            composable(route = Routes.CreatePassword.routes) {
                CreatePasswordScreen(
                    context = context,
                    navController = navController,
                    navigateToCheckEmail = {
                        navController.navigate(Routes.CheckEmail.routes) {
                            popUpTo(Routes.CreatePassword.routes) { inclusive = true }
                            launchSingleTop = true
                            restoreState = false
                        }
                    },
                    navigateToDashboard = {
                        navController.navigate(Routes.Dashboard.routes) {
                            popUpTo(Routes.CreatePassword.routes) { inclusive = true }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                )
            }
            composable(route = Routes.Dashboard.routes) {
                DashboardScreen(
                    context = context
                )
            }
        }
    )
}