package ua.deti.pt.wearosapp.components.navbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import ua.deti.pt.wearosapp.screens.HomeScreen
import ua.deti.pt.wearosapp.screens.NotificationsScreen
import ua.deti.pt.wearosapp.screens.SettingsScreen
import ua.deti.pt.wearosapp.ui.viewModels.MeasureDataViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NavigationScreen(
    navController: NavHostController,
    measureDataViewModel: MeasureDataViewModel,
    permissionState: PermissionState
) {
    SwipeDismissableNavHost(navController = navController, startDestination = NavItem.Home.path) {
        composable(NavItem.Home.path) {
            HomeScreen(
                navController = navController,
                permissionState = permissionState,
                measureDataViewModel = measureDataViewModel
            )
        }
        composable(NavItem.Notifications.path) {
            NotificationsScreen(navController = navController)
        }
        composable(NavItem.Settings.path) {
            SettingsScreen(navController = navController)
        }
    }
}