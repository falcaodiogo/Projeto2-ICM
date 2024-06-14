package ua.deti.pt.wearosapp.service

import android.content.Context
import android.util.Log
import androidx.concurrent.futures.await
import androidx.health.services.client.HealthServices
import androidx.health.services.client.MeasureCallback
import androidx.health.services.client.data.Availability
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.DataTypeAvailability
import androidx.health.services.client.data.DeltaDataType
import androidx.health.services.client.data.SampleDataPoint
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.runBlocking
import ua.deti.pt.wearosapp.TAG

/**
 * Repository class responsible for managing interactions with the health services API,
 * specifically focused on heart rate measurement functionality.
 *
 * It utilizes the Android Health Services framework to fetch heart rate data and provides
 * a Kotlin Flow for consuming this data in a reactive manner.
 *
 * @property context The application context used to create the Health Services client.
 */
class HealthServicesRepository(context: Context) {

    private val healthServicesClient = HealthServices.getClient(context)
    private val measureClient = healthServicesClient.measureClient

    /**
     * Asynchronously checks if the device supports heart rate measurements.
     * @return true if heart rate capability is supported, false otherwise.
     */
    suspend fun hasHeartRateCapability(): Boolean {
        val capabilities = measureClient.getCapabilitiesAsync().await()
        return (DataType.HEART_RATE_BPM in capabilities.supportedDataTypesMeasure)
    }

    /**
     * Returns a cold flow. When activated, the flow will register a callback for heart rate data
     * and start to emit messages. When the consuming coroutine is cancelled, the measure callback
     * is unregistered.
     *
     * [callbackFlow] is used to bridge between a callback-based API and Kotlin flows.
     */
    fun heartRateMeasureFlow() = callbackFlow {
        val callback = object : MeasureCallback {

            // Handles changes in the availability of heart rate data
            override fun onAvailabilityChanged(
                dataType: DeltaDataType<*, *>,
                availability: Availability
            ) {
                // Only send back DataTypeAvailability (not LocationAvailability)
                if (availability is DataTypeAvailability) {
                    trySendBlocking(MeasureMessage.MeasureAvailability(availability))
                }
            }

            // Processes incoming heart rate data and sends it
            override fun onDataReceived(data: DataPointContainer) {
                val heartRateBpm = data.getData(DataType.HEART_RATE_BPM)
                trySendBlocking(MeasureMessage.MeasureData(heartRateBpm))
            }
        }

        Log.d(TAG, "Registering measure callback for heart rate")
        measureClient.registerMeasureCallback(DataType.HEART_RATE_BPM, callback)

        awaitClose {
            Log.d(TAG, "Unregistering for data")
            // Ensures the callback is unregistered when the flow collector cancels its subscription.
            runBlocking {
                measureClient.unregisterMeasureCallbackAsync(DataType.HEART_RATE_BPM, callback)
                    .await()
            }
        }
    }
}

/**
 * Represents messages related to heart rate measurement status or data.
 * Used within the HealthServicesRepository to communicate measurement availability and actual data points.
 */
sealed class MeasureMessage {

    /**
     * Indicates the availability of heart rate measurement data.
     * Contains information about whether the data is currently available or not.
     */
    class MeasureAvailability(val availability: DataTypeAvailability) : MeasureMessage()

    /**
     * Contains actual heart rate measurement data.
     * Each instance represents a single measurement event, encapsulating the measured heart rate values.
     */
    class MeasureData(val data: List<SampleDataPoint<Double>>) : MeasureMessage()
}