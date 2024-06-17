package ua.deti.pt.wearosapp.ui

import android.annotation.SuppressLint
import android.util.Log
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
import ua.deti.pt.wearosapp.TAG
import ua.deti.pt.wearosapp.repositories.HealthServiceRepository
import ua.deti.pt.wearosapp.theme.WearOSAppTheme
import ua.deti.pt.wearosapp.ui.screens.ErrorScreen
import ua.deti.pt.wearosapp.ui.screens.MainScreen
import ua.deti.pt.wearosapp.ui.viewModels.PassiveGoalsViewModel
import ua.deti.pt.wearosapp.ui.viewModels.PassiveGoalsViewModelFactory
import ua.deti.pt.wearosapp.ui.viewModels.UiState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WearOSApp(
    healthServiceRepository: HealthServiceRepository,
    goalsRepository: GoalsRepository
) {
    WearOSAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) {
            val viewModel: PassiveGoalsViewModel = viewModel(
                factory = PassiveGoalsViewModelFactory(
                    healthServicesRepository = healthServiceRepository,
                    goalsRepository = goalsRepository
                )
            )

            val uiState by viewModel.uiState
            val navController = rememberSwipeDismissableNavController()

            if (uiState == UiState.Supported) {
                Log.i(TAG, "Supported")
                val permissionState = rememberPermissionState(
                    permission = PERMISSION,
                    onPermissionResult = { granted ->
                        if (granted) viewModel.toggleEnabled()
                    }
                )
                MainScreen(
                    navController = navController,
                    measureDataViewModel = viewModel,
                    permissionState = permissionState
                )
            } else if (uiState == UiState.NotSupported) {
                Log.i(TAG, "Not SupportedDDDDDDDDDDD")
                ErrorScreen()
            }
        }
    }
}