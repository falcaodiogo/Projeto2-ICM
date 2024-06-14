package ua.deti.pt.wearosapp.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Scaffold
import ua.deti.pt.wearosapp.components.navbar.NavigationScreen

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        NavigationScreen(navController = navController)
    }
}