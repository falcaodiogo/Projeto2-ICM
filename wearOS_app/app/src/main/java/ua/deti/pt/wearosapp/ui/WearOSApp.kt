package ua.deti.pt.wearosapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import ua.deti.pt.wearosapp.PERMISSION
import ua.deti.pt.wearosapp.screens.MainScreen
import ua.deti.pt.wearosapp.service.HealthServicesRepository
import ua.deti.pt.wearosapp.theme.WearOSAppTheme
import ua.deti.pt.wearosapp.ui.viewModels.MeasureDataViewModel
import ua.deti.pt.wearosapp.ui.viewModels.MeasureDataViewModelFactory
import ua.deti.pt.wearosapp.ui.viewModels.UiState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WearOSApp(
    healthServicesRepository: HealthServicesRepository
) {
    WearOSAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            val viewModel: MeasureDataViewModel = viewModel(
                factory = MeasureDataViewModelFactory(healthServicesRepository = healthServicesRepository)
            )

            val uiState by viewModel.uiState
            val navController = rememberSwipeDismissableNavController()

            if (uiState == UiState.Supported) {
                val permissionState = rememberPermissionState(
                    permission = PERMISSION,
                    onPermissionResult = { granted ->
                        if (granted) viewModel.toggleEnabled()
                    }
                )
                MainScreen(
                    navController = navController,
                    permissionState = permissionState,
                    measureDataViewModel = viewModel
                )
            }
        }
    }
}