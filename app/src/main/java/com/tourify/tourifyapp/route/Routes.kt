package com.tourify.tourifyapp.route

sealed class Routes(val routes: String) {
    data object SplashSreen : Routes("splashScreen")
    data object OnBoarding : Routes("onboarding")
    data object CheckEmail : Routes("checkEmail")
    data object EnterPassword : Routes("enterPassword/{email}") {
        fun createRoute(email: String) = "enterPassword/$email"
    }

    data object VerifCode : Routes("codeOtp/{email}") {
        fun createRoute(email: String) = "codeOtp/$email"
    }

    data object CreatePassword : Routes("createPassword/{email}") {
        fun createRoute(email: String) = "createPassword/$email"
    }

    data object Dashboard : Routes("dashboard")
    data object Home : Routes("home")
    data object Profile : Routes("profile")
    data object MyTickets : Routes("myTickets")
    data object Culinary : Routes("culinary")
    data object ScanObjek : Routes("scanObjek")
    data object Notice : Routes("notice/{usersId}") {
        fun createRoute(usersId: Int) = "notice/$usersId"
    }

    data object PayOrder : Routes("payOrder/{orderCode}") {
        fun createRoute(orderCode: String) = "payOrder/$orderCode"
    }

    data object PayStatus : Routes("payStatus/{orderCode}") {
        fun createRoute(orderCode: String) = "payStatus/$orderCode"
    }

    data object AboutApp : Routes("aboutApp")
}