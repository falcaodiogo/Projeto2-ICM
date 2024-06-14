package ua.deti.pt.wearosapp

import android.app.Application
import ua.deti.pt.wearosapp.service.HealthServicesRepository

const val TAG = "Measure Data Sample"
const val PERMISSION = android.Manifest.permission.BODY_SENSORS

class MainApplication : Application() {
    val healthServicesRepository by lazy { HealthServicesRepository(this) }
}
