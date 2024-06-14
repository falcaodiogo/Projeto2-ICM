package ua.deti.pt.wearosapp.components.navbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import ua.deti.pt.wearosapp.screens.HomeScreen
import ua.deti.pt.wearosapp.screens.NotificationsScreen
import ua.deti.pt.wearosapp.screens.SettingsScreen

@Composable
fun NavigationScreen(navController: NavHostController) {
    SwipeDismissableNavHost(navController = navController, startDestination = NavItem.Home.path) {
        composable(NavItem.Home.path) {
            HomeScreen(navController = navController)
        }
        composable(NavItem.Notifications.path) {
            NotificationsScreen(navController = navController)
        }
        composable(NavItem.Settings.path) {
            SettingsScreen(navController = navController)
        }
    }
}