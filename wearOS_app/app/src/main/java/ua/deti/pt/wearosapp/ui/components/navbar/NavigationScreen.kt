package ua.deti.pt.wearosapp.ui.components.navbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import ua.deti.pt.wearosapp.screens.SettingsScreen
import ua.deti.pt.wearosapp.ui.screens.HomeScreen
import ua.deti.pt.wearosapp.ui.screens.NotificationsScreen
import ua.deti.pt.wearosapp.ui.viewModels.PassiveGoalsViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NavigationScreen(
    navController: NavHostController,
    passiveGoalsViewModel: PassiveGoalsViewModel,
    permissionState: PermissionState
) {
    SwipeDismissableNavHost(navController = navController, startDestination = NavItem.Home.path) {
        composable(NavItem.Home.path) {
            HomeScreen(
                navController = navController,
            )
        }
        composable(NavItem.Notifications.path) {
            NotificationsScreen(
                navController = navController,
                passiveGoalsViewModel = passiveGoalsViewModel,
                permissionState = permissionState
            )
        }
        composable(NavItem.Settings.path) {
            SettingsScreen(navController = navController)
        }
    }
}