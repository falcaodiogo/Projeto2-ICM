package ua.deti.pt.wearosapp

import android.app.Application
import ua.deti.pt.wearosapp.repositories.GoalsRepository
import ua.deti.pt.wearosapp.repositories.HealthServiceRepository

const val TAG = "Passive Goals"
const val PERMISSION = android.Manifest.permission.ACTIVITY_RECOGNITION

class MainApplication : Application() {
    val healthServicesRepository by lazy { HealthServiceRepository(this) }
    val goalsRepository by lazy { GoalsRepository(this) }
}
