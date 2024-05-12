package ua.deti.pt.phoneapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import ua.deti.pt.phoneapp.ui.components.navbar.BottomNavigationBar
import ua.deti.pt.phoneapp.ui.components.navbar.NavigationScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(bottomBar = {
        BottomAppBar { BottomNavigationBar(navController = navController) }
    }) { NavigationScreens(navController = navController) }
}