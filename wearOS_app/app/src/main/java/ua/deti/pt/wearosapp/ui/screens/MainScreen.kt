package ua.deti.pt.wearosapp.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Scaffold
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import ua.deti.pt.wearosapp.components.navbar.NavigationScreen
import ua.deti.pt.wearosapp.ui.viewModels.MeasureDataViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    measureDataViewModel: MeasureDataViewModel,
    permissionState: PermissionState
) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        NavigationScreen(
            navController = navController,
            measureDataViewModel = measureDataViewModel,
            permissionState = permissionState
        )
    }
}