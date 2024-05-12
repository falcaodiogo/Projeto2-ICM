package ua.deti.pt.phoneapp.ui.components.navbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.deti.pt.phoneapp.Auth.GoogleAuthUiClient
import ua.deti.pt.phoneapp.ui.screens.HomeScreen
import ua.deti.pt.phoneapp.ui.screens.NotificationsScreen
import ua.deti.pt.phoneapp.ui.screens.ProfileScreen
import ua.deti.pt.phoneapp.ui.screens.SleepScreen

@Composable
fun NavigationScreens(
    navController: NavHostController,
    onSignOut: () -> Unit,
    googleAuthUiClient: GoogleAuthUiClient
) {
    NavHost(navController, startDestination = NavItem.Home.path) {
        composable(NavItem.Home.path) { HomeScreen() }
        composable(NavItem.Notifications.path) { NotificationsScreen() }
        composable(NavItem.Sleep.path) { SleepScreen() }
        composable(NavItem.Settings.path) {
            val userData = googleAuthUiClient.getSignedInUser()
            ProfileScreen(userData, onSignOut)
        }
    }
}