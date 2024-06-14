package ua.deti.pt.phoneapp.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Instant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HealthConnectScreen() {
    val context = LocalContext.current
    var stepsCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        val status = HealthConnectUtils.checkForHealthConnectInstalled(context)
        if (status == HealthConnectClient.SDK_AVAILABLE) {
            CoroutineScope(Dispatchers.IO).launch {
                val steps = readStepsByTimeRange(Instant.now().minusSeconds(3600), Instant.now())
                stepsCount = steps.toInt()
            }
            Toast.makeText(context, "Health Connect SDK available", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Health Connect SDK not available", Toast.LENGTH_SHORT).show()
        }
    }

    Column {
        Text("Health Connect Screen")
        Text("Steps in the last hour: $stepsCount")
    }
}

object HealthConnectUtils {

    var healthConnectClient: HealthConnectClient? = null
        private set

    fun checkForHealthConnectInstalled(context: Context): Int {
        val availabilityStatus = HealthConnectClient.getSdkStatus(context, "com.google.android.apps.healthdata")

        when (availabilityStatus) {
            HealthConnectClient.SDK_UNAVAILABLE -> {
                // The Health Connect SDK is unavailable on this device at the time.
                // This can be due to the device running a lower than required Android Version.
                // Apps should hide any integration points to Health Connect in this case.
            }
            HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> {
                // The Health Connect SDK APIs are currently unavailable, the provider is either not installed
                // or needs to be updated. You may choose to redirect to package installers to find a suitable APK.
            }
            HealthConnectClient.SDK_AVAILABLE -> {
                // Health Connect SDK is available on this device.
                // You can proceed with querying data from Health Connect using the client.
                healthConnectClient = HealthConnectClient.getOrCreate(context)
            }
        }

        return availabilityStatus
    }
}

suspend fun readStepsByTimeRange(startTime: Instant, endTime: Instant): Long {
    return try {
        val response = HealthConnectUtils.healthConnectClient?.readRecords(
            ReadRecordsRequest(
                StepsRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )
        response?.records?.sumOf { it.count } ?: 0
    } catch (e: Exception) {
        // Run error handling here
        0
    }
}
