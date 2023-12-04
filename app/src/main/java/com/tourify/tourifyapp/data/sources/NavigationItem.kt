package com.tourify.tourifyapp.data.sources

import com.tourify.tourifyapp.R
import com.tourify.tourifyapp.route.Routes

sealed class NavigationItem(var route: String, var icon: Int, var title: Int) {
    data object Home :
        NavigationItem(Routes.Home.routes, R.drawable.ic_home, R.string.menu_home)

    data object Culinary :
        NavigationItem(Routes.Culinary.routes, R.drawable.ic_food, R.string.menu_culinary)

    data object ScanObjek :
        NavigationItem(Routes.ScanObjek.routes, R.drawable.ic_camera, R.string.menu_scanobjek)

    data object MyTickets :
        NavigationItem(Routes.MyTickets.routes, R.drawable.ic_calendar, R.string.menu_mytickets)

    data object Profile :
        NavigationItem(Routes.Profile.routes, R.drawable.ic_user, R.string.menu_profile)
}