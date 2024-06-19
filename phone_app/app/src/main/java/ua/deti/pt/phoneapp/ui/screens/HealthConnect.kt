package ua.deti.pt.phoneapp.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Instant
import kotlinx.coroutines.launch
import java.time.temporal.ChronoUnit

val PERMISSIONS = setOf(
    HealthPermission.getReadPermission(HeartRateRecord::class),
    HealthPermission.getReadPermission(StepsRecord::class),
    HealthPermission.getReadPermission(SleepSessionRecord::class),
)


@Composable
fun HealthConnectScreen(
    onStepsAndCaloriesUpdated: (Int, Float) -> Unit,
    onSleepDataUpdated: (list: List<SleepSessionRecord.Stage>) -> Unit
) {
    val context = LocalContext.current
    var stepsCount by remember { mutableStateOf(0) }
    var caloriesCount by remember { mutableStateOf(0f) }
    var healthConnectClient by remember { mutableStateOf<HealthConnectClient?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var sleepSessionRecords by remember { mutableStateOf<List<SleepSessionRecord.Stage>>(emptyList()) }

    val requestPermissionsLauncher = rememberLauncherForActivityResult(
        contract = PermissionController.createRequestPermissionResultContract()
    ) { result: Set<String> ->
        if (result.containsAll(PERMISSIONS)) {
            println("Permissions granted")
            coroutineScope.launch {
                readSleepData(
                    healthConnectClient!!,
                    onSleepDataUpdated = { newSleepSessionRecords ->
                        sleepSessionRecords = newSleepSessionRecords
                        onSleepDataUpdated(sleepSessionRecords)
                    }
                )
                readStepsAndCalculateCalories(
                    healthConnectClient!!,
                    onStepsAndCaloriesUpdated = { steps, calories ->
                        stepsCount = steps.toInt()
                        caloriesCount = calories
                        onStepsAndCaloriesUpdated(stepsCount, caloriesCount)
                    }
                )
            }
        } else {
            Toast.makeText(context, "Permissions not granted", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        val availabilityStatus = HealthConnectUtils.checkForHealthConnectInstalled(context)
        if (availabilityStatus == HealthConnectClient.SDK_AVAILABLE) {
            val client = HealthConnectUtils.getOrCreateHealthConnectClient(context)
            healthConnectClient = client

            coroutineScope.launch {
                // Check permissions and request if not granted
                if (client.permissionController.getGrantedPermissions().containsAll(PERMISSIONS)) {
                    readStepsAndCalculateCalories(client) { steps, calories ->
                        stepsCount = steps.toInt()
                        caloriesCount = calories
                        onStepsAndCaloriesUpdated(stepsCount, caloriesCount)
                    }
                    readSleepData(client) { newSleepSessionRecords ->
                        sleepSessionRecords = newSleepSessionRecords
                        onSleepDataUpdated(sleepSessionRecords)
                    }
                } else {
                    requestPermissionsLauncher.launch(PERMISSIONS)
                }
            }
        } else {
            Toast.makeText(context, "Health Connect not available", Toast.LENGTH_SHORT).show()
        }
    }
}


object HealthConnectUtils {
    var healthConnectClient: HealthConnectClient? = null
        private set

    fun checkForHealthConnectInstalled(context: Context): Int {
        val availabilityStatus = HealthConnectClient.getSdkStatus(context, "com.google.android.apps.healthdata")

        when (availabilityStatus) {
            HealthConnectClient.SDK_UNAVAILABLE -> {
                // Health Connect SDK is unavailable on this device.
            }
            HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> {
                // Health Connect SDK APIs are currently unavailable, the provider needs to be updated.
            }
            HealthConnectClient.SDK_AVAILABLE -> {
                healthConnectClient = HealthConnectClient.getOrCreate(context)
            }
        }

        return availabilityStatus
    }

    fun getOrCreateHealthConnectClient(context: Context): HealthConnectClient {
        return healthConnectClient ?: HealthConnectClient.getOrCreate(context).also {
            healthConnectClient = it
        }
    }
}

suspend fun readStepsAndCalculateCalories(
    healthConnectClient: HealthConnectClient,
    onStepsAndCaloriesUpdated: (Long, Float) -> Unit
) {
    try {
        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                StepsRecord::class,
                timeRangeFilter = TimeRangeFilter.between(
                    // 24 hours
                    Instant.now().minus(1, ChronoUnit.DAYS),
                    Instant.now()
                )
            )
        )

        var totalSteps = 0L
        var totalCalories = 0f
        for (stepRecord in response.records) {
            totalSteps += stepRecord.count
            totalCalories += stepRecord.count * 0.06f
        }
        onStepsAndCaloriesUpdated(totalSteps, totalCalories)
    } catch (e: Exception) {
        println("Error reading steps: $e")
        e.printStackTrace()
    }
}

suspend fun readSleepData(
    healthConnectClient: HealthConnectClient,
    onSleepDataUpdated: (list: List<SleepSessionRecord.Stage>) -> Unit
) {
    try {
        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                SleepSessionRecord::class,
                timeRangeFilter = TimeRangeFilter.between(
                    // 24 hours
                    Instant.now().minus(2, ChronoUnit.DAYS),
                    Instant.now()
                )
            )
        )

        val sleepSessions = mutableListOf<SleepSessionRecord.Stage>()
        for (sleepRecord in response.records) {
            sleepSessions.addAll(sleepRecord.stages)
        }

        onSleepDataUpdated(sleepSessions)
    } catch (e: Exception) {
        println("Error reading sleep data: $e")
        e.printStackTrace()
    }
}
