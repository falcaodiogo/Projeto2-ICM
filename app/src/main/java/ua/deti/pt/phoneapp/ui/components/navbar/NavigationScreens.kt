package ua.deti.pt.phoneapp.ui.components.navbar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.deti.pt.phoneapp.ui.screens.HomeScreen
import ua.deti.pt.phoneapp.ui.screens.ListScreen
import ua.deti.pt.phoneapp.ui.screens.ProfileScreenTest
import ua.deti.pt.phoneapp.ui.screens.SearchScreen

@Composable
fun NavigationScreens(navController: NavHostController) {
    NavHost(navController, startDestination = NavItem.Home.path) {
        composable(NavItem.Home.path) { HomeScreen() }
        composable(NavItem.Search.path) { SearchScreen() }
        composable(NavItem.List.path) { ListScreen() }
        composable(NavItem.Profile.path) { ProfileScreenTest() }
    }
}