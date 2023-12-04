package com.tourify.tourifyapp.ui.main

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tourify.tourifyapp.preference.OnBoardingDataStore
import com.tourify.tourifyapp.route.Routes
import com.tourify.tourifyapp.ui.main.screen.CheckEmailScreen
import com.tourify.tourifyapp.ui.main.screen.CreatePasswordScreen
import com.tourify.tourifyapp.ui.main.screen.DashboardScreen
import com.tourify.tourifyapp.ui.main.screen.EnterPasswordScreen
import com.tourify.tourifyapp.ui.main.screen.OnBoardingScreen
import com.tourify.tourifyapp.ui.main.screen.SplashScreen
import com.tourify.tourifyapp.ui.main.screen.VerifCodeScreen

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
                        }
                    }
                )
            }
            composable(route = Routes.CheckEmail.routes) {
                CheckEmailScreen(
                    context = context,
                    navController = navController,
                    navigateToVerifCode = { email ->
                        val route = Routes.VerifCode.createRoute(email)
                        navController.navigate(route) {
                            popUpTo(Routes.CheckEmail.routes) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    navigateToEnterPassword = { email ->
                        val route = Routes.EnterPassword.createRoute(email)
                        navController.navigate(route) {
                            popUpTo(Routes.CheckEmail.routes) { inclusive = true }
                            launchSingleTop = true
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
                        }
                    },
                    navigateToCreatePassword = { email ->
                        val route = Routes.CreatePassword.createRoute(email)
                        navController.navigate(route) {
                            popUpTo(Routes.VerifCode.routes) { inclusive = true }
                            launchSingleTop = true
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
                        }
                    },
                    navigateToDashboard = {
                        navController.navigate(Routes.Dashboard.routes) {
                            popUpTo(Routes.EnterPassword.routes) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    navigateToForgotPassword = { email ->
                        val route = Routes.VerifCode.createRoute(email)
                        navController.navigate(route) {
                            popUpTo(Routes.EnterPassword.routes) { inclusive = true }
                            launchSingleTop = true
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
                        }
                    },
                    navigateToDashboard = {
                        navController.navigate(Routes.Dashboard.routes) {
                            popUpTo(Routes.CreatePassword.routes) { inclusive = true }
                            launchSingleTop = true
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