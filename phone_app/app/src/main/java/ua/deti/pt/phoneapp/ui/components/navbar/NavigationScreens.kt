package ua.deti.pt.phoneapp.ui.components.navbar

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.deti.pt.phoneapp.Auth.GoogleAuthUiClient
import ua.deti.pt.phoneapp.ui.screens.HomeScreen
import ua.deti.pt.phoneapp.ui.screens.NotificationsScreen
import ua.deti.pt.phoneapp.ui.screens.PlannedExercises
import ua.deti.pt.phoneapp.ui.screens.ProfileScreen
import ua.deti.pt.phoneapp.ui.screens.SleepScreen

import androidx.lifecycle.viewmodel.compose.viewModel

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun NavigationScreens(
    navController: NavHostController,
    onSignOut: () -> Unit,
    googleAuthUiClient: GoogleAuthUiClient,
    context: Context
) {

    NavHost(navController, startDestination = NavItem.Home.path) {
        composable(NavItem.Home.path) { googleAuthUiClient.getSignedInUser()
            ?.let { it1 -> HomeScreen(googleAuthUiClient, it1) } }
        composable(NavItem.Notifications.path) { NotificationsScreen(navController, context) }
        composable(NavItem.Sleep.path) { SleepScreen(context) }
        composable(NavItem.Settings.path) {
            val userData = googleAuthUiClient.getSignedInUser()
            ProfileScreen(userData, onSignOut, context, googleAuthUiClient)
        }
        composable(NavItem.Exercises.path) { PlannedExercises() }
    }
}
