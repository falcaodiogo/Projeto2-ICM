package ua.deti.pt.wearosapp.ui.state

import java.time.Duration

data class SummaryScreenState(
    val averageHeartRate: Double,
    val totalDistance: Double,
    val totalCalories: Double,
    val elapsedTime: Duration
)
