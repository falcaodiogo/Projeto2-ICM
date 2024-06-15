package ua.deti.pt.phoneapp.data.health

import android.os.RemoteException
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID

class SleepSessionViewModel(private val healthConnectManager: HealthConnectManager) :
    ViewModel() {

    val permissions = setOf(
        HealthPermission.getReadPermission(SleepSessionRecord::class),
        HealthPermission.getWritePermission(SleepSessionRecord::class)
    )

    var permissionsGranted = mutableStateOf(false)
        private set

    var sessionsList: MutableState<List<SleepSessionData>> = mutableStateOf(listOf())
        private set

    var uiState: UiState by mutableStateOf(UiState.Uninitialized)
        private set

    val permissionsLauncher = healthConnectManager.requestPermissionsActivityContract()

    fun initialLoad() {
        viewModelScope.launch {
            tryWithPermissionsCheck {
                sessionsList.value = healthConnectManager.readSleepSessions()
            }
        }
    }

    fun generateSleepData() {
        viewModelScope.launch {
            tryWithPermissionsCheck {
                // Delete all existing sleep data before generating new random sleep data.
                healthConnectManager.deleteAllSleepData()
                healthConnectManager.generateSleepData()
                sessionsList.value = healthConnectManager.readSleepSessions()
            }
        }
    }

    private suspend fun tryWithPermissionsCheck(block: suspend () -> Unit) {
        permissionsGranted.value = healthConnectManager.hasAllPermissions(permissions)
        uiState = try {
            if (permissionsGranted.value) {
                block()
            }
            UiState.Done
        } catch (remoteException: RemoteException) {
            UiState.Error(remoteException)
        } catch (securityException: SecurityException) {
            UiState.Error(securityException)
        } catch (ioException: IOException) {
            UiState.Error(ioException)
        } catch (illegalStateException: IllegalStateException) {
            UiState.Error(illegalStateException)
        }
    }

    sealed class UiState {
        object Uninitialized : UiState()
        object Done : UiState()

        data class Error(val exception: Throwable, val uuid: UUID = UUID.randomUUID()) : UiState()
    }
}

class SleepSessionViewModelFactory(
    private val healthConnectManager: HealthConnectManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepSessionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SleepSessionViewModel(
                healthConnectManager = healthConnectManager
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}